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
            store.save(new Product(name: "myproduct"))
        when:
            def all = store.all()
        then:
            all.size() == 1
            all.first().name == "myproduct"
            all.first().id != null
    }

    def "Update"() {

    }

    def "GetByID"() {
    }

    def "Delete"() {
        setup:
            def prod = store.delete("hjrhjr")
        when:
            def all = store.all()
        then:
            ! all.contains(prod)
    }

    def "All"() {
        expect:
        store.all() == []
    }


}
