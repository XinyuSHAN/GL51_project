package projet.gl51.store

class Product {
    String id
    String name
    String description
    double price
    double idealTemperature
	
	Product() {
		this.id = UUID.randomUUID().toString()
	}

	Product(String name, String description, double price, double idealTemperature) {
		this.id = UUID.randomUUID().toString()
		this.name = name
		this.description = description
		this.price = price
		this.idealTemperature = idealTemperature
	}
	String getId() {
		return this.id
}
}
