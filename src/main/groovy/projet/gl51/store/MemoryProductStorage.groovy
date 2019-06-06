package projet.gl51.store
import javax.inject.Singleton

@Singleton
class MemoryProductStorage implements ProductStorage {

    /**
     * creates an new product in the store
     * @param p the product to store
     */
    List<Product> products =  []

    @Override
    void save(Product p) {
        products.add(p)
		p.id
    }

    /**
     * updates an existing product in the store
     * Beware the product id must be filled in
     * @param p the product to update
     */
    @Override
    void update(String id, Product p) {
        Integer index = products.findIndexOf { it.id == id }
       if (index == -1) throw new NotExistingProductException()
        p.id = id;
		products.set(index, p)

    }

    @Override
    Product getByID(String id) {
        Product product = products.find { it.id == id }
		/*if (product == null) throw new NotExistingProductException()
			product*/
}

    /**
     * deletes a product by its id
     * @param id
     */

    @Override

    void delete(String id) {
        Product product = this.getByID(id)
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
