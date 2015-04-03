package com.semantics3;

public class Util {
	
	public static Object[] appendArray(Object[] objs,String...strings) {
		Object[] result= new Object[objs.length+strings.length];
		System.arraycopy(strings, 0, result, 0, strings.length);
		System.arraycopy(objs, 0, result, strings.length, objs.length);
		return result;
	}

}
