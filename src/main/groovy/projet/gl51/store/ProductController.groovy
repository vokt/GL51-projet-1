package projet.gl51.store

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.MediaType
import io.micronaut.http.client.exceptions.HttpClientResponseException

import javax.inject.Inject


@Controller("/product")
class ProductController {

    @Inject
    ProductStorage inMemory


    /**
     * get all product
     *
     * @return a list of product
     */
    @Get("/")
    List<Product> allProduct(){
        inMemory.all()
    }


    /**
     * get a product by its id
     * @param id
     * @return a list of product
     */
    @Get("/{id}")
    Product prodById(String id) {
        try {
            return inMemory.getByID(id)
        } catch (Exception e) {
            e.printStackTrace()
            HttpStatus.NOT_FOUND
        }

    }


    /**
     * save a product
     * @param product
     * @return string
     */
    @Post(uri ="/save" )
    String save(@Body Product product){
        inMemory.save(product)
    }


    /**
     * delete a product
     * @param product
     * @return string
     */
    @Delete(uri ="/{id}" )
    HttpStatus delete(String id){
        try {
            inMemory.delete(id)
            return HttpStatus.OK

        } catch (Exception e) {
            e.printStackTrace()
            return HttpStatus.NOT_FOUND
        }

    }


    /**
     * update a product
     * @param product
     * @return string
     */
    @Patch(uri ="/update" )
    HttpStatus update(@Body Product product) {
        inMemory.update(product.id,product)
        return HttpStatus.OK
    }


}


