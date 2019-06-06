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

	

	

	
}