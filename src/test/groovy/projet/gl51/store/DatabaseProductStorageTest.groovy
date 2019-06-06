package projet.gl51.store

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import static io.micronaut.http.HttpStatus.*

class DatabaseProductStorageTest extends Specification {

	@Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
	@Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


	void "empty product list"() {
		given:
		List<Product> products = client.toBlocking().retrieve(HttpRequest.GET('/store/product'), Argument.listOf(Product).type)

		expect:
		products == []
	}

	void "update-->change props"() {
		setup:
		String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', new Product(name, description, price, idealTemperature)))

		when:
		Product otherProduct = new Product(name, description, price, idealTemperature)
		HttpStatus status = client.toBlocking().retrieve(HttpRequest.PATCH('/store/product/' + id, otherProduct), Argument.of(HttpStatus).type)
		Product updatedProduct = client.toBlocking().retrieve(HttpRequest.GET('/store/product/' + id), Argument.of(Product).type)

		then:
		status == OK
		updatedProduct.getPrice() == otherProduct.getPrice()
		updatedProduct.getName() == otherProduct.getName()

		where:
		name | description | price | idealTemperature | name1 | description1 | price1 | idealTemperature1
		"Chips" | "delicious" | 2 | 10 | "chips lays" | "more delicious" | 20 | 10
	}
	void "create"(){
		when:
		String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', new Product(name, description, price, idealTemperature)))
		Product productReturned = client.toBlocking().retrieve(HttpRequest.GET('/store/product/' + id), Argument.of(Product).type)

		then:
		id != ""
		productReturned.getId() == id

		where:
		name | description | price | idealTemperature
		"Chips" | "delicious" | 2 | 10
	}
	
	void "delete"(){
		setup:
		String id = client.toBlocking().retrieve(HttpRequest.POST('/store/product', new Product(name, description, price, idealTemperature)))

		when:
		HttpStatus status = client.toBlocking().retrieve(HttpRequest.DELETE('/store/product/' + id), Argument.of(HttpStatus).type)
		Product productReturned = client.toBlocking().retrieve(HttpRequest.GET('/store/product/' + id), Argument.of(Product).type)

		then:
		status == OK
		productReturned == null
		thrown HttpClientResponseException

		where:
		name | description | price | idealTemperature
		"Chips" | "delicious" | 2 | 10
	}

	void "deleting non-exist"(){
		setup:
		String id = UUID.randomUUID().toString()

		when:
		HttpStatus status = client.toBlocking().retrieve(HttpRequest.DELETE('/store/product/' + id), Argument.of(HttpStatus).type)

		then:
		thrown HttpClientResponseException
	}

	

	
}