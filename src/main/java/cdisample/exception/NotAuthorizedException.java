package cdisample.exception;

public class NotAuthorizedException  extends RuntimeException {
	public NotAuthorizedException() {
	}	
	public NotAuthorizedException(String action) {
		super(action);
	}
	
}
