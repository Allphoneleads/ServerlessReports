package com.callx.aws.lambda.dto;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SaleCountReportDTO {
	
	@Column(name="campaign_id")
	private int campaignId;
	
	@Column(name="campaign_name")
	private String campaignName;
	
	@Column(name="advertiser_id")
	private int advertiserId;
	
	@Column(name="advertiser_name")
	private String advertiserName;
	
	@Column(name="publisher_id")
	private int publisherId;
	
	@Column(name="publisher_name")
	private String publisherName;
	
	@Column(name="promo_number")
	private String promoNumber;
	
	@Column(name="offer_id")
	private int offerId;
	
	@Column(name="offer_name")
	private String offerName;
	
	@Column(name="sale_count")
	private int saleCount;

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public int getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(int advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPromoNumber() {
		return promoNumber;
	}

	public void setPromoNumber(String promoNumber) {
		this.promoNumber = promoNumber;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	@Override
	public String toString() {
		return "SaleCountReportDTO [campaignId=" + campaignId + ", campaignName=" + campaignName + ", advertiserId="
				+ advertiserId + ", advertiserName=" + advertiserName + ", publisherId=" + publisherId
				+ ", publisherName=" + publisherName + ", promoNumber=" + promoNumber + ", offerId=" + offerId
				+ ", offerName=" + offerName + ", saleCount=" + saleCount + "]";
	}

}
