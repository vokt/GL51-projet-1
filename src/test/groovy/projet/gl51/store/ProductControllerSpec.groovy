package projet.gl51.store

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ProductControllerSpec  extends Specification{

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


    void "test allProduct"() {

        given:
        def products = client.toBlocking().exchange("/product/", Argument.listOf(Product).type)

        expect:
        products.status == HttpStatus.OK
        products.body() == []

    }


    void "test save"() {
        setup:
        Product product = new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)

        when: String id = client.toBlocking().retrieve(HttpRequest.POST('/product/save', product))

        Product p = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)

        then:
        product.id != ""
        p.id != ""
        p.name == name
        p.description == description
        p.price == price
        p.idealTemperature == idealTemperature


        where:
        name | description | price | idealTemperature
        "name" | "description" | 500 | 10
    }


    void "test delete"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/product/save', new Product(name: "name", description: "description", price: 600, idealTemperature: 10)))
        Product product = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)

        when:
        client.toBlocking().retrieve(HttpRequest.DELETE('/product/' + id))
        Product p = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)

        then:
        thrown HttpClientResponseException

    }


    void "test update"() {
        setup:
        String id = client.toBlocking().retrieve(HttpRequest.POST('/product/save', new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)))

        when:
        Product product = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)
        product.description = "description updated"
        client.toBlocking().retrieve(HttpRequest.POST('/product/update' , product), Argument.of(HttpStatus).type)

        Product updatedProduct = client.toBlocking().retrieve(HttpRequest.GET('/product/' + product.id), Argument.of(Product).type)

        then:
        product.description == updatedProduct.description
        product.price == updatedProduct.price
        product.name == updatedProduct.name
        product.idealTemperature == updatedProduct.idealTemperature

        where:
        name | description | price | idealTemperature
        "name" | "description" | 500 | 10

    }

    void "test prodById with an existing product"() {
        setup:
        Product product = new Product(name: name, description: description, price: price, idealTemperature: idealTemperature)
        String id = client.toBlocking().retrieve(HttpRequest.POST('/product/save', product))

        when:
        Product retrievedProduct = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)

        then:
        product.name == retrievedProduct.name
        product.description == retrievedProduct.description
        product.price == retrievedProduct.price
        product.idealTemperature == retrievedProduct.idealTemperature

        where:
        name | description | price | idealTemperature
        "name" | "description" | 500 | 10
    }

    void "test prodById with a not existing product"() {

        setup:
        String id = "199id"

        when:
        Product product = client.toBlocking().retrieve(HttpRequest.GET('/product/' + id), Argument.of(Product).type)

        then:
        thrown HttpClientResponseException

    }

}
