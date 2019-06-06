package projet.gl51.store

class NotExistingProductException extends Exception {
	NotExistingProductException(){
		super("The product can't be found !")
	}
}