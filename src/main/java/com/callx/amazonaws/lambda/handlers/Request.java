package com.callx.amazonaws.lambda.handlers;

/**
 * @author Jayaram
 */
public class Request {
    
	private String ref;
    private String refFrom;
    private String refTo;
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getRefFrom() {
		return refFrom;
	}
	public void setRefFrom(String refFrom) {
		this.refFrom = refFrom;
	}
	public String getRefTo() {
		return refTo;
	}
	public void setRefTo(String refTo) {
		this.refTo = refTo;
	}
	@Override
	public String toString() {
		return "Request [ref=" + ref + ", refFrom=" + refFrom + ", refTo=" + refTo + "]";
	}
	
    
	
}
