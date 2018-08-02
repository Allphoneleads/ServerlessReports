package com.callx.aws.lambda.dto;

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
	
	@Column(name="connected_duration")
	private String connectedDuration;
	
	@Column(name="avg_connected_duration")
	private String avgConnectDuration;
	
	@Column(name="avg_connected_duration_paid_calls")
	private String avgConnectDurationPaidCalls;

	@Column(name="campaign_name")
	private String campaignName;
	
	@Column(name="publisher_name")
	private String publisherName;
	
	@Column(name="offer_id")
	private int offerId;
	
	@Column(name="offer_name")
	private String offerName;
	
	@Column(name="advertiser_id")
	private String advertiserId;
	
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
	
	@Column(name="filter_id")
	private int filterId;
	
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
	
	@Column(name="from_state")
	private String state;
	
	@Column(name="end_time")
	private String daypart;

	@Column(name="call_uuid")
	private String callUuid;
	
	@Column(name="from_number")
	private String fromNumber;
	
	@Column(name="call_type")
	private String callType;
	
	@Column(name="answer_type")
	private String answerType;
	
	@Column(name="agent_id")
	private int agentId;
	
	@Column(name="from_line_type")
	private String fromLineType;
	
	@Column(name="from_city")
	private String fromCity;
	
	@Column(name="from_country")
	private String fromCountry;
	
	@Column(name="from_zip")
	private String fromZip;
	
	@Column(name="b_leg_duration")
	private int bLegDuration;
	
	@Column(name="status")
	private String status;
	
	@Column(name="duration")
	private int duration;
	
	@Column(name="is_repeat")
	private boolean repeat;
	
	@Column(name="file_url")
	private String fileUrl;
	
	@Column(name="algo")
	private String algo;
	
	@Column(name="keyword")
	private String keyword;
	
	@Column(name="keywordmatchtype")
	private String keywordMatchType;
	
	@Column(name="ad_group_name")
	private String adGroupName;
	
	@Column(name="selected_ivr_keys")
	private String selectedIvrKeys;

	@Column(name="created_at")
	private String createdAt;

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

	public String getConnectedDuration() {
		return connectedDuration;
	}

	public void setConnectedDuration(String connectedDuration) {
		this.connectedDuration = connectedDuration;
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

	public String getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
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

	public int getFilterId() {
		return filterId;
	}

	public void setFilterId(int filterId) {
		this.filterId = filterId;
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

	public String getCallUuid() {
		return callUuid;
	}

	public void setCallUuid(String callUuid) {
		this.callUuid = callUuid;
	}

	public String getFromNumber() {
		return fromNumber;
	}

	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public String getFromLineType() {
		return fromLineType;
	}

	public void setFromLineType(String fromLineType) {
		this.fromLineType = fromLineType;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getFromCountry() {
		return fromCountry;
	}

	public void setFromCountry(String fromCountry) {
		this.fromCountry = fromCountry;
	}

	public String getFromZip() {
		return fromZip;
	}

	public void setFromZip(String fromZip) {
		this.fromZip = fromZip;
	}

	public int getbLegDuration() {
		return bLegDuration;
	}

	public void setbLegDuration(int bLegDuration) {
		this.bLegDuration = bLegDuration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeywordMatchType() {
		return keywordMatchType;
	}

	public void setKeywordMatchType(String keywordMatchType) {
		this.keywordMatchType = keywordMatchType;
	}

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public String getSelectedIvrKeys() {
		return selectedIvrKeys;
	}

	public void setSelectedIvrKeys(String selectedIvrKeys) {
		this.selectedIvrKeys = selectedIvrKeys;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "GeneralReportDTO [id=" + id + ", campaignId=" + campaignId + ", publisherId=" + publisherId
				+ ", totalCalls=" + totalCalls + ", repeatCalls=" + repeatCalls + ", repeatCallsPer=" + repeatCallsPer
				+ ", uniqueCalls=" + uniqueCalls + ", paidCalls=" + paidCalls + ", offersNotAvailableCount="
				+ offersNotAvailableCount + ", revenue=" + revenue + ", cost=" + cost + ", profit=" + profit + ", conv="
				+ conv + ", uniqueConv=" + uniqueConv + ", avgRpc=" + avgRpc + ", avgCpc=" + avgCpc + ", avgRpk="
				+ avgRpk + ", connectedDuration=" + connectedDuration + ", avgConnectDuration=" + avgConnectDuration
				+ ", avgConnectDurationPaidCalls=" + avgConnectDurationPaidCalls + ", campaignName=" + campaignName
				+ ", publisherName=" + publisherName + ", offerId=" + offerId + ", offerName=" + offerName
				+ ", advertiserId=" + advertiserId + ", advertiserName=" + advertiserName + ", promoNumber="
				+ promoNumber + ", promoId=" + promoId + ", promoNumberDescription=" + promoNumberDescription
				+ ", keypress=" + keypress + ", action=" + action + ", ivrFilter=" + ivrFilter + ", filterId="
				+ filterId + ", keyCalls=" + keyCalls + ", keypressInPercentage=" + keypressInPercentage
				+ ", paidCallsInPercentage=" + paidCallsInPercentage + ", paidKeypressRate=" + paidKeypressRate
				+ ", avgCallDuration=" + avgCallDuration + ", inboundDuration=" + inboundDuration
				+ ", outboundDuration=" + outboundDuration + ", totalDuration=" + totalDuration + ", inboundCost="
				+ inboundCost + ", outboundCost=" + outboundCost + ", totalCost=" + totalCost + ", state=" + state
				+ ", daypart=" + daypart + ", callUuid=" + callUuid + ", fromNumber=" + fromNumber + ", callType="
				+ callType + ", answerType=" + answerType + ", agentId=" + agentId + ", fromLineType=" + fromLineType
				+ ", fromCity=" + fromCity + ", fromCountry=" + fromCountry + ", fromZip=" + fromZip + ", bLegDuration="
				+ bLegDuration + ", status=" + status + ", duration=" + duration + ", repeat=" + repeat + ", fileUrl="
				+ fileUrl + ", algo=" + algo + ", keyword=" + keyword + ", keywordMatchType=" + keywordMatchType
				+ ", adGroupName=" + adGroupName + ", selectedIvrKeys=" + selectedIvrKeys + ", createdAt=" + createdAt
				+ "]";
	}
	
	
	
	
	
}