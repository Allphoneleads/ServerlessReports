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
    private String geoType;
    private String dayPart;
    private String campaignId;
    private String publisherId;
    private String offerId;
    private String promoId;
    private String advertiserId;
    private String campPubId;
    private String offerByPromoId;
    private String offerByPubId;
    
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
	public String getGeoType() {
		return geoType;
	}
	public void setGeoType(String geoType) {
		this.geoType = geoType;
	}
	public String getDayPart() {
		return dayPart;
	}
	public void setDayPart(String dayPart) {
		this.dayPart = dayPart;
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
	public String getOfferByPubId() {
		return offerByPubId;
	}
	public void setOfferByPubId(String offerByPubId) {
		this.offerByPubId = offerByPubId;
	}
    
}
