package projet.gl51.store

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import projet.gl51.Student
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ProductControllerSpec  extends Specification{

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


    void "test allProduct"() {
        given:
        def response = client.toBlocking().exchange("/student", Argument.listOf(Student).type)
        expect:
        response.status == HttpStatus.OK
        response.body()[1].firstname == "Regis"
        response.body()[1].lastname == "Mouaffo"
        println(response.body)

    }

    void "test prodById"() {
        given:
        def response = client.toBlocking().exchange("/student", Argument.listOf(Student).type)
        expect:
        response.status == HttpStatus.OK
        response.body()[1].firstname == "Regis"
        response.body()[1].lastname == "Mouaffo"
        println(response.body)

    }


    void "test save"() {
        given:
        def response = client.toBlocking().exchange("/student", Argument.listOf(Student).type)
        expect:
        response.status == HttpStatus.OK
        response.body()[1].firstname == "Regis"
        response.body()[1].lastname == "Mouaffo"
        println(response.body)

    }

    void "test delete"() {
        given:
        def response = client.toBlocking().exchange("/student", Argument.listOf(Student).type)
        expect:
        response.status == HttpStatus.OK
        response.body()[1].firstname == "Regis"
        response.body()[1].lastname == "Mouaffo"
        println(response.body)

    }


    void "test update"() {
        given:
        def response = client.toBlocking().exchange("/student", Argument.listOf(Student).type)
        expect:
        response.status == HttpStatus.OK
        response.body()[1].firstname == "Regis"
        response.body()[1].lastname == "Mouaffo"
        println(response.body)

    }


}
