package projet.gl51.store

import spock.lang.Specification

class MemoryProductStorageTest extends Specification {

	ProductStorage store = new MemoryProductStorage()

	def "empty"(){
		expect:
		store.all() == []
	}

	def "add"(){
		setup:
		store.save(new Product(name, description, price, idealTemperature))

		when:
		List<Product> all = store.all()

		then:
		all.size() == 1
		all.first().getName() == "Chips"

		where:
		name | description | price | idealTemperature
		"Chips" | "delicious" | 2 | 10
	}

	def "add and give id"(){
		setup:
		store.save(new Product())
		store.save(new Product())

		when:
		List<Product> all = store.all()

		then:
		all.get(0).getId() != all.get(1).getId()
	}


	def "delete"(){
		setup:
		store.save(new Product())
		List<Product> all = store.all()
		String productID = all.first().getId()

		when:
		store.delete(productID)

		then:
		all.size() == 0
	}

	def "modify"(){
		setup:
		store.save(new Product(name, description, price, idealTemperature))
		Product updateProduct = new Product(name1, description1, price1, idealTemperature1)
		List<Product> all = store.all()
		String productID = all.first().getId()

		when:
		store.update(productID, updateProduct)

		then:
		all.first().getName() == "chips Lays"
		all.first().getPrice() == 4
		
		where:
		name | description | price | idealTemperature | name1 | description1 | price1 | idealTemperature1
		"Chips" | "delicious" | 2 | 10 | "chips Lays" | "more delicious" | 4 | 10

	}
	


	def "get"(){
		setup:
		Product product = new Product(name, description, price, idealTemperature)
		String productID = product.getId()
		store.save(product)

		when:
		Product productExist = store.getByID(productID)

		then:
		product == productExist

		where:
		name | description | price | idealTemperature
		"Chips" | "delicious" | 2 | 10
	}
}