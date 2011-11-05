package se.netlight.dimlight.dao;


public class DAOException extends Exception {
	private static final long serialVersionUID = -3901938134637339024L;

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

}
