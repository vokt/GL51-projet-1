package projet.gl51.store

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class MemoryProductStorageTest extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


    ProductStorage store = new MemoryProductStorage()

    def "Save"() {
        setup:
            store.save(new Product("myproduct"))
        when:
            def all = store.all()
        then:
            all.size() == 1
            all.first().name == "myproduct"
            all.first().id != null
    }

    def "Update"() {
        setup:
            def product = new Product("myproduct")
            store.save(product)
        when:
            def p = store.getByID(product.id)
            store.update(p.id,new Product(p.id ,"myUpdatedproduct"))
            // def p2 = store.getByID(p.id)
        then:
            p.name != store.all().first().name
    }

    def "GetByID with an existing product"() {
        setup:
            def product = new Product("myproduct")
            store.save(product)
        when:
            def p = store.getByID(product.id)
        then:
            p.name == "myproduct"
    }


    def "GetByID with a not existing product"() {
        when:
            store.getByID("199id")
        then:
            thrown NotExistingProductException
    }


    def "Delete"() {
        setup:
            def product = new Product("myproduct")
            store.save(product)
        when:
            store.delete(product.id)
            store.getByID(product.id)
        then:
            thrown NotExistingProductException
    }

    def "All"() {
        expect:
        store.all() == []
    }


}
