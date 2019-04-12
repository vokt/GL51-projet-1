package projet.gl51.store

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus

import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class MemoryProductStorageTest extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


    ProductStorage store = new MemoryProductStorage()

    def "Save"() {
        setup:
        store.save(new Product(name: "myproduct"))

        when:
        def all = store.all()

        then:
        all.size() == 1
        all.first().name == "myproduct"
    }

    def "Update"() {


    }

    def "GetByID"() {
        setup:
        store.save(new Product(name: "myproduct", id: "p1"))

        when:
        def getbyid = store.getByID("p1")

        then:
        getbyid.id == "p1"

    }

    def "Delete"() {

    }

    def "All"() {
        expect:
        store.all() == []
    }


}
