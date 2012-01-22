package se.netlight.dimlight.metaframework.session;

public class InvalidSessionException extends RuntimeException {
	private static final long serialVersionUID = 2710749872893679468L;

	public InvalidSessionException() {
	}

	public InvalidSessionException(String arg0) {
		super(arg0);
	}

	public InvalidSessionException(Throwable arg0) {
		super(arg0);
	}

	public InvalidSessionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
