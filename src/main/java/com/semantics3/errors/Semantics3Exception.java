package com.semantics3.errors;

public class Semantics3Exception extends RuntimeException {
	public Semantics3Exception(String code,String message) {
		super(message);
	}
}
