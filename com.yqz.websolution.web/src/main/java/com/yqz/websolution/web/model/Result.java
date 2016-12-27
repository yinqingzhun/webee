package com.yqz.websolution.web.model;

public class Result<T> {

	private int code;
	private String message;
	private T result;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public static <T> Result<T> buildSuccessResult(T result) {
		Result<T> r = new Result<T>();
		r.setResult(result);
		return r;
	}

	public static <T> Result<T> buildErrorResult(int code, String message) {
		Result<T> r = new Result<T>();
		r.setCode(code);
		r.setMessage(message);
		return r;
	}

}
