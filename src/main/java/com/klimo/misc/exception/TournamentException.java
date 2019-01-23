package com.klimo.misc.exception;

public class TournamentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TournamentException() {
		super();
	}

	public TournamentException(String message) {
		super(message);
	}

	public TournamentException(Throwable t) {
		super(t);
	}

	public TournamentException(String message, Throwable t) {
		super(message, t);
	}

}
