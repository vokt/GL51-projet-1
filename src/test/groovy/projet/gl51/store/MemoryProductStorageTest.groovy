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
    String id = new Random().with {(1..6).collect {(('a'..'z')).join()[ nextInt((('a'..'z')).join().length())]}.join()}

    def "Save"() {
        setup:
            store.save(new Product(id:new Random().with {(1..6).collect {(('a'..'z')).join()[ nextInt((('a'..'z')).join().length())]}.join()},name: "myproduct"))
        when:
            def all = store.all()
        then:
            all.size() == 1
            all.first().name == "myproduct"
            all.first().id != null
    }

    def "Update"() {
        setup:
            def product = new Product(id: new Random().with {(1..6).collect {(('a'..'z')).join()[ nextInt((('a'..'z')).join().length())]}.join()}, name: "myproduct", description: "product" )
            store.save(product)
        when:
            store.update(product.id,new Product(id: product.id , name: "myproduct", description: "product updated" ))
            def p = store.getByID(product.id)
        then:
            p.description == "product updated"
    }

    def "GetByID"() {
        setup:
            def product = new Product(id: new Random().with {(1..6).collect {(('a'..'z')).join()[ nextInt((('a'..'z')).join().length())]}.join()}, name: "myproduct", description: "product" )
            store.save(product)
        when:
            def p = store.getByID(product.id)
        then:
            p.name == "myproduct"
            p.description == "product"
    }

    def "Delete"() {
        setup:
            def product = new Product(id: new Random().with {(1..6).collect {(('a'..'z')).join()[ nextInt((('a'..'z')).join().length())]}.join()}, name: "myproduct", description: "product" )
            store.save(product)
            def prod = store.delete(product.id)
     //   when:
       //     def all = store.all()
        expect:
        //    ! all.contains(prod)
            store.getByID(prod.id) == null
    }

    def "All"() {
        expect:
        store.all() == []
    }


}
