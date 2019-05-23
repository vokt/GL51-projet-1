package projet.gl51.store

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.MediaType

import javax.inject.Inject



@Controller("/product")
class ProductController {

    @Inject
    private MemoryProductStorage inMemory

    /**
     * get all product
     *
     * @return a list of product
     */
    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    List<Product> allProduct(){
        inMemory.all()
    }


    /**
     * get a product by its id
     * @param id
     * @return a list of product
     */
    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Product> prodById(String id){
        inMemory.getByID(id)
    }


    /**
     * save a product
     * @param product
     * @return string
     */
    @Post(uri ="/save" ,consumes = MediaType.APPLICATION_JSON)
    String save(@Body Product product){
        inMemory.save(product)
    }


    /**
     * delete a product
     * @param product
     * @return string
     */
    @Post(uri ="/delete" ,consumes = MediaType.APPLICATION_JSON)
    String delete(@Body Product product){
        inMemory.delete(product.id)
    }


    /**
     * update a product
     * @param product
     * @return string
     */
    @Post(uri ="/save" ,consumes = MediaType.APPLICATION_JSON)
    String update(@Body Product product){
        inMemory.update(product.id,product)
    }


}


