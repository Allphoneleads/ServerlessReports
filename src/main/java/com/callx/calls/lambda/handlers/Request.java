package com.callx.calls.lambda.handlers;

/**
 * @author Jayaram
 */
public class Request {
    
	private String id;
	private String ref;
    private String refFrom;
    private String refTo;
    private String isExport;
    private String reportType;
    private String filterType;
    private String campaignId;
    private String publisherId;
    private String offerId;
    private String promoId;
    private String advertiserId;
    private String campPubId;
    private String offerByPromoId;
    private String offerByPromonumber;
    private String offerByPubId;
    private String promoNumber;
    private String state;
    private int hour;
    private String searchTerm;
    private int pageSize;
    private int pageNumber;
    private int totalRecords;
    private String fileName;
    private String warmEvent;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getPromoId() {
		return promoId;
	}
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}
	public String getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getCampPubId() {
		return campPubId;
	}
	public void setCampPubId(String campPubId) {
		this.campPubId = campPubId;
	}
	public String getOfferByPromoId() {
		return offerByPromoId;
	}
	public void setOfferByPromoId(String offerByPromoId) {
		this.offerByPromoId = offerByPromoId;
	}
	public String getOfferByPromonumber() {
		return offerByPromonumber;
	}
	public void setOfferByPromonumber(String offerByPromonumber) {
		this.offerByPromonumber = offerByPromonumber;
	}
	public String getOfferByPubId() {
		return offerByPubId;
	}
	public void setOfferByPubId(String offerByPubId) {
		this.offerByPubId = offerByPubId;
	}
	public String getPromoNumber() {
		return promoNumber;
	}
	public void setPromoNumber(String promoNumber) {
		this.promoNumber = promoNumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getWarmEvent() {
		return warmEvent;
	}
	public void setWarmEvent(String warmEvent) {
		this.warmEvent = warmEvent;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", ref=" + ref + ", refFrom=" + refFrom + ", refTo=" + refTo + ", isExport="
				+ isExport + ", reportType=" + reportType + ", filterType=" + filterType + ", campaignId=" + campaignId
				+ ", publisherId=" + publisherId + ", offerId=" + offerId + ", promoId=" + promoId + ", advertiserId="
				+ advertiserId + ", campPubId=" + campPubId + ", offerByPromoId=" + offerByPromoId
				+ ", offerByPromonumber=" + offerByPromonumber + ", offerByPubId=" + offerByPubId + ", promoNumber="
				+ promoNumber + ", state=" + state + ", hour=" + hour + ", searchTerm=" + searchTerm + ", pageSize="
				+ pageSize + ", pageNumber=" + pageNumber + ", totalRecords=" + totalRecords + ", fileName=" + fileName
				+ ", warmEvent=" + warmEvent + "]";
	}
	
	
	
}
