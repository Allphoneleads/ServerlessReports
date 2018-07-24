package com.callx.aws.lambda.handlers;

/**
 * @author Jayaram
 */
public class Request {
    
	private String ref;
    private String refFrom;
    private String refTo;
    private String isExport;
    private String reportType;
    
    
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
	
	
	public String getIsExport() {
		return isExport;
	}
	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	@Override
	public String toString() {
		return "Request [ref=" + ref + ", refFrom=" + refFrom + ", refTo=" + refTo + ", isExport=" + isExport
				+ ", reportType=" + reportType + "]";
	}
	
}
