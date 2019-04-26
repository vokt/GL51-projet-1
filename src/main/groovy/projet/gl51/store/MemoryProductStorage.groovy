package projet.gl51.store

class MemoryProductStorage implements  ProductStorage {

    /**

     * creates an new product in the store

     * @param p the product to store

     */
    List<Product> products = new ArrayList<Product>()

    @Override
    void save(Product p) {
        products.add(p)
    }

    /**

     * updates an existing product in the store

     * Beware the product id must be filled in

     * @param p the product to update

     */
    @Override
    void update(String id, Product p) {
        products.each { Product p2 ->
            if (id == p2.id) {
                products.remove(p2)
                products.add(p)
            }
        }

    }


    /**

     * get a product by its id

     * @param id

     * @return a product

     */
    @Override
    Product getByID(String id) {
        def product
        products.each { Product p ->
            if (p.id == id) {
                product = p
            }
        }

        product
    }

    /**

     * deletes a product by its id

     * @param id

     */

    @Override

    Product delete(String id) {
        def product = getByID(id)

        products.remove(product)

        product
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
