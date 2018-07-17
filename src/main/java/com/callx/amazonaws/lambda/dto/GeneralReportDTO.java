package com.callx.amazonaws.lambda.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GeneralReportDTO {
	
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="campaign_id")
	private int campaignId;
	
	@Column(name="publisher_id")
	private int publisherId;
	
	@Column(name="calls")
	private BigDecimal totalCalls;
	
	@Column(name="repeat_calls")
	private BigDecimal repeatCalls;
	
	@Column(name="repeat_calls_per")
	private BigDecimal repeatCallsPer;
	
	@Column(name="unique_calls")
	private BigDecimal uniqueCalls;
	
	@Column(name="paid_calls")
	private BigDecimal paidCalls;
	
	@Column(name="offer_not_found")
	private BigDecimal offersNotAvailableCount;
	
	@Column(name="total_revenue")
	private BigDecimal revenue;
	
	@Column(name="publisher_revenue")
	private BigDecimal cost;
	
	@Column(name="profit")
	private BigDecimal profit;
	
	@Column(name="conv_rate")
	private BigDecimal conv;
	
	@Column(name="unique_conv_rate")
	private BigDecimal uniqueConv;
	
	@Column(name="avg_rpc")
	private BigDecimal avgRpc;
	
	@Column(name="avg_cpc")
	private BigDecimal avgCpc;
	
	@Column(name="avg_rpk")
	private BigDecimal avgRpk;
	
	@Column(name="avg_connected_duration")
	private String avgConnectDuration;
	
	@Column(name="avg_connected_duration_paid_calls")
	private String avgConnectDurationPaidCalls;

	@Column(name="campaign_name")
	private String campaignName;
	
	@Column(name="publisher_name")
	private String publisherName;
	
	@Column(name="offer_name")
	private String offerName;
	
	@Column(name="advertiser_name")
	private String advertiserName;
	
	@Column(name="to_number")
	private String promoNumber;
	
	@Column(name="promo_id")
	private String promoId;
	
	@Column(name="description")
	private String promoNumberDescription;
	
	@Column(name="processed_ivr_keys")
	private String keypress; 
	
	@Column(name="ivr_action")
	private String action; 
	
	@Column(name="filter_name")
	private String ivrFilter; 
	
	@Column(name="key_calls")
	private Integer keyCalls;
	
	@Column(name="keypress_in_percentage")
	private BigDecimal keypressInPercentage;
	
	@Column(name="paidCalls_in_percentage")
	private BigDecimal paidCallsInPercentage;
	
	@Column(name="paid_keypress_rate")
	private BigDecimal paidKeypressRate;
	
	@Column(name="avg_call_duration")
	private String avgCallDuration;
	
	@Column(name="inbound_duration")
	private int inboundDuration;
	
	@Column(name="outbound_duration")
	private int outboundDuration;
	
	@Column(name="total_duration")
	private int totalDuration;
	
	
	@Column(name="inbound_cost")
	private BigDecimal inboundCost;
	
	@Column(name="outbound_cost")
	private BigDecimal outboundCost;
	
	@Column(name="total_cost")
	private BigDecimal totalCost;
	
	@Column(name="state")
	private String state;
	
	@Column(name="daypart")
	private String daypart;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}

	public BigDecimal getTotalCalls() {
		return totalCalls;
	}

	public void setTotalCalls(BigDecimal totalCalls) {
		this.totalCalls = totalCalls;
	}

	public BigDecimal getRepeatCalls() {
		return repeatCalls;
	}

	public void setRepeatCalls(BigDecimal repeatCalls) {
		this.repeatCalls = repeatCalls;
	}

	public BigDecimal getRepeatCallsPer() {
		return repeatCallsPer;
	}

	public void setRepeatCallsPer(BigDecimal repeatCallsPer) {
		this.repeatCallsPer = repeatCallsPer;
	}

	public BigDecimal getUniqueCalls() {
		return uniqueCalls;
	}

	public void setUniqueCalls(BigDecimal uniqueCalls) {
		this.uniqueCalls = uniqueCalls;
	}

	public BigDecimal getPaidCalls() {
		return paidCalls;
	}

	public void setPaidCalls(BigDecimal paidCalls) {
		this.paidCalls = paidCalls;
	}

	public BigDecimal getOffersNotAvailableCount() {
		return offersNotAvailableCount;
	}

	public void setOffersNotAvailableCount(BigDecimal offersNotAvailableCount) {
		this.offersNotAvailableCount = offersNotAvailableCount;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getConv() {
		return conv;
	}

	public void setConv(BigDecimal conv) {
		this.conv = conv;
	}

	public BigDecimal getUniqueConv() {
		return uniqueConv;
	}

	public void setUniqueConv(BigDecimal uniqueConv) {
		this.uniqueConv = uniqueConv;
	}

	public BigDecimal getAvgRpc() {
		return avgRpc;
	}

	public void setAvgRpc(BigDecimal avgRpc) {
		this.avgRpc = avgRpc;
	}

	public BigDecimal getAvgCpc() {
		return avgCpc;
	}

	public void setAvgCpc(BigDecimal avgCpc) {
		this.avgCpc = avgCpc;
	}

	public BigDecimal getAvgRpk() {
		return avgRpk;
	}

	public void setAvgRpk(BigDecimal avgRpk) {
		this.avgRpk = avgRpk;
	}

	public String getAvgConnectDuration() {
		return avgConnectDuration;
	}

	public void setAvgConnectDuration(String avgConnectDuration) {
		this.avgConnectDuration = avgConnectDuration;
	}

	public String getAvgConnectDurationPaidCalls() {
		return avgConnectDurationPaidCalls;
	}

	public void setAvgConnectDurationPaidCalls(String avgConnectDurationPaidCalls) {
		this.avgConnectDurationPaidCalls = avgConnectDurationPaidCalls;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public String getPromoNumber() {
		return promoNumber;
	}

	public void setPromoNumber(String promoNumber) {
		this.promoNumber = promoNumber;
	}

	public String getPromoId() {
		return promoId;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public String getPromoNumberDescription() {
		return promoNumberDescription;
	}

	public void setPromoNumberDescription(String promoNumberDescription) {
		this.promoNumberDescription = promoNumberDescription;
	}

	public String getKeypress() {
		return keypress;
	}

	public void setKeypress(String keypress) {
		this.keypress = keypress;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIvrFilter() {
		return ivrFilter;
	}

	public void setIvrFilter(String ivrFilter) {
		this.ivrFilter = ivrFilter;
	}

	public Integer getKeyCalls() {
		return keyCalls;
	}

	public void setKeyCalls(Integer keyCalls) {
		this.keyCalls = keyCalls;
	}

	public BigDecimal getKeypressInPercentage() {
		return keypressInPercentage;
	}

	public void setKeypressInPercentage(BigDecimal keypressInPercentage) {
		this.keypressInPercentage = keypressInPercentage;
	}

	public BigDecimal getPaidCallsInPercentage() {
		return paidCallsInPercentage;
	}

	public void setPaidCallsInPercentage(BigDecimal paidCallsInPercentage) {
		this.paidCallsInPercentage = paidCallsInPercentage;
	}

	public BigDecimal getPaidKeypressRate() {
		return paidKeypressRate;
	}

	public void setPaidKeypressRate(BigDecimal paidKeypressRate) {
		this.paidKeypressRate = paidKeypressRate;
	}

	public String getAvgCallDuration() {
		return avgCallDuration;
	}

	public void setAvgCallDuration(String avgCallDuration) {
		this.avgCallDuration = avgCallDuration;
	}

	public int getInboundDuration() {
		return inboundDuration;
	}

	public void setInboundDuration(int inboundDuration) {
		this.inboundDuration = inboundDuration;
	}

	public int getOutboundDuration() {
		return outboundDuration;
	}

	public void setOutboundDuration(int outboundDuration) {
		this.outboundDuration = outboundDuration;
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
	}

	public BigDecimal getInboundCost() {
		return inboundCost;
	}

	public void setInboundCost(BigDecimal inboundCost) {
		this.inboundCost = inboundCost;
	}

	public BigDecimal getOutboundCost() {
		return outboundCost;
	}

	public void setOutboundCost(BigDecimal outboundCost) {
		this.outboundCost = outboundCost;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDaypart() {
		return daypart;
	}

	public void setDaypart(String daypart) {
		this.daypart = daypart;
	}

	@Override
	public String toString() {
		return "GeneralReportDTO [id=" + id + ", campaignId=" + campaignId + ", publisherId=" + publisherId
				+ ", totalCalls=" + totalCalls + ", repeatCalls=" + repeatCalls + ", repeatCallsPer=" + repeatCallsPer
				+ ", uniqueCalls=" + uniqueCalls + ", paidCalls=" + paidCalls + ", offersNotAvailableCount="
				+ offersNotAvailableCount + ", revenue=" + revenue + ", cost=" + cost + ", profit=" + profit + ", conv="
				+ conv + ", uniqueConv=" + uniqueConv + ", avgRpc=" + avgRpc + ", avgCpc=" + avgCpc + ", avgRpk="
				+ avgRpk + ", avgConnectDuration=" + avgConnectDuration + ", avgConnectDurationPaidCalls="
				+ avgConnectDurationPaidCalls + ", campaignName=" + campaignName + ", publisherName=" + publisherName
				+ ", offerName=" + offerName + ", advertiserName=" + advertiserName + ", promoNumber=" + promoNumber
				+ ", promoId=" + promoId + ", promoNumberDescription=" + promoNumberDescription + ", keypress="
				+ keypress + ", action=" + action + ", ivrFilter=" + ivrFilter + ", keyCalls=" + keyCalls
				+ ", keypressInPercentage=" + keypressInPercentage + ", paidCallsInPercentage=" + paidCallsInPercentage
				+ ", paidKeypressRate=" + paidKeypressRate + ", avgCallDuration=" + avgCallDuration
				+ ", inboundDuration=" + inboundDuration + ", outboundDuration=" + outboundDuration + ", totalDuration="
				+ totalDuration + ", inboundCost=" + inboundCost + ", outboundCost=" + outboundCost + ", totalCost="
				+ totalCost + ", state=" + state + ", daypart=" + daypart + "]";
	}

}