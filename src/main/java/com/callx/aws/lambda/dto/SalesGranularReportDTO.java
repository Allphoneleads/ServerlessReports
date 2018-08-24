package com.callx.aws.lambda.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import org.joda.time.DateTime;

@Entity
public class SalesGranularReportDTO {

    @Column(name="id")
	private int id;
	
    @Column(name="reference_id")
	private String referenceId;
    
    @Column(name="call_type")
	private String callType; 
    
    @Column(name="publisher_name", length=500)
	private String publisherName;
    
	@Column(name="caller_id")
	private String callerId;
	
	@Column(name="start_time")
	private String start_time_PST;
	
	@Column(name="promo_number")
	private String promoNumber;
	
	@Lob
	@Column(name="promo_number_description")
	private String promoNumberDescription;
	
	@Column(name="offer_name")
	private String offerName;
	
	@Column(name="sell_product_name", length = 100)
	private String sellProductName;
	
	@Column(name = "ad_words_group_name")
	private String adGroupName;
	
	@Column(name="key_press")
	private String keyPress;
	
	@Column(name="ivr_filter")
	private String ivrFilter;
	
	@Column(name="keywords")
	private String keywords;
	
	@Column(name="call_duration")
	private int callDuration;
	
	@Column(name="ivr_duration")
	private int ivrDuration;
	
	@Column(name="connect_duration")
	private int connectDuration;
	
	@Column(name="revenue")
	private BigDecimal revenue;
	
	@Column(name="city")
	private String city;
	
	@Column(name="country")
	private String country;
	
	@Column(name="phone_type")
	private String phoneType;
	
	@Column(name="is_repeat")
	private boolean isRepeat;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public String getStart_time_PST() {
		return start_time_PST;
	}

	public void setStart_time_PST(String start_time_PST) {
		this.start_time_PST = start_time_PST;
	}

	public String getPromoNumber() {
		return promoNumber;
	}

	public void setPromoNumber(String promoNumber) {
		this.promoNumber = promoNumber;
	}

	public String getPromoNumberDescription() {
		return promoNumberDescription;
	}

	public void setPromoNumberDescription(String promoNumberDescription) {
		this.promoNumberDescription = promoNumberDescription;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getSellProductName() {
		return sellProductName;
	}

	public void setSellProductName(String sellProductName) {
		this.sellProductName = sellProductName;
	}

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public String getKeyPress() {
		return keyPress;
	}

	public void setKeyPress(String keyPress) {
		this.keyPress = keyPress;
	}

	public String getIvrFilter() {
		return ivrFilter;
	}

	public void setIvrFilter(String ivrFilter) {
		this.ivrFilter = ivrFilter;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}

	public int getIvrDuration() {
		return ivrDuration;
	}

	public void setIvrDuration(int ivrDuration) {
		this.ivrDuration = ivrDuration;
	}

	public int getConnectDuration() {
		return connectDuration;
	}

	public void setConnectDuration(int connectDuration) {
		this.connectDuration = connectDuration;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public boolean isRepeat() {
		return isRepeat;
	}

	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

	@Override
	public String toString() {
		return "SalesGranularReportDTO [id=" + id + ", referenceId=" + referenceId + ", callType=" + callType
				+ ", publisherName=" + publisherName + ", callerId=" + callerId + ", start_time_PST=" + start_time_PST
				+ ", promoNumber=" + promoNumber + ", promoNumberDescription=" + promoNumberDescription + ", offerName="
				+ offerName + ", sellProductName=" + sellProductName + ", adGroupName=" + adGroupName + ", keyPress="
				+ keyPress + ", ivrFilter=" + ivrFilter + ", keywords=" + keywords + ", callDuration=" + callDuration
				+ ", ivrDuration=" + ivrDuration + ", connectDuration=" + connectDuration + ", revenue=" + revenue
				+ ", city=" + city + ", country=" + country + ", phoneType=" + phoneType + ", isRepeat=" + isRepeat
				+ "]";
	}

}
