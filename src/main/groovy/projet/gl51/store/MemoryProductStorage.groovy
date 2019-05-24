package projet.gl51.store

import javax.inject.Singleton


@Singleton
class MemoryProductStorage implements  ProductStorage {

    /**

     * creates an new product in the store

     * @param p the product to store

     */
    List<Product> products = new ArrayList<Product>()

    @Override
    String save(Product p) {
        p.id = new Random().with {(1..6).collect {(('a'..'z')).join()[ nextInt((('a'..'z')).join().length())]}.join()}
        products.add(p)
        return p.id
    }

    /**

     * updates an existing product in the store

     * Beware the product id must be filled in

     * @param p the product to update

     */
    @Override
    void update(String id, Product p) {
        def index
        products.each { Product p2 ->
            if (id == p2.id) {
                index = products.indexOf(p2)
                products.set(index,p)
            }
        }

    }


    /**

     * get a product by its id

     * @param id

     * @return a product

     */
    @Override
    Product getByID(String id) throws NotExistingProductException{

        def product
        products.each { Product p ->
            if (p.id == id) {
                product = p
            }
        }

        if (product == null)
            throw new NotExistingProductException("Le produit portant l'id "+ id +" n'existe pas.")
        else
            product

    }

    /**

     * deletes a product by its id

     * @param id

     */

    @Override

    void delete(String id) {
        def product = getByID(id)
        products.remove(product)
    }

    /**

     * list all products

     * @return a list of products

     */
    @Override
    List<Product> all() {
        products
    }
}
