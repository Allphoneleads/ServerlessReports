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
	private int campaign_id;
	
	@Column(name="publisher_id")
	private int publisher_id;
	
	@Column(name="calls")
	private BigDecimal total_calls;
	
	@Column(name="repeat_calls")
	private BigDecimal repeat_calls;
	
	@Column(name="repeat_calls_per")
	private BigDecimal repeat_calls_per;
	
	@Column(name="unique_calls")
	private BigDecimal unique_calls;
	
	@Column(name="paid_calls")
	private BigDecimal paid_calls;
	
	@Column(name="offer_not_found")
	private boolean offer_not_found;
	
	@Column(name="total_revenue")
	private BigDecimal revenue;
	
	@Column(name="publisher_revenue")
	private Double publisher_revenue;
	
	@Column(name="profit")
	private BigDecimal profit;
	
	@Column(name="conv_rate")
	private BigDecimal conv;
	
	@Column(name="unique_conv_rate")
	private BigDecimal unique_conv;
	
	@Column(name="avg_rpc")
	private BigDecimal avg_rpc;
	
	@Column(name="avg_cpc")
	private BigDecimal avg_cpc;
	
	@Column(name="avg_rpk")
	private BigDecimal avg_rpk;
	
	@Column(name="connected_duration")
	private String connected_duration;
	
	@Column(name="avg_connected_duration")
	private String avg_connect_duration;
	
	@Column(name="avg_connected_duration_paid_calls")
	private String avg_connect_duration_paid_calls;

	@Column(name="campaign_name")
	private String campaign_name;
	
	@Column(name="publisher_name")
	private String publisher_name;
	
	@Column(name="offerid")
	private int offer_id;
	
	@Column(name="offer_name")
	private String offer_name;
	
	@Column(name="advertiser_id")
	private String advertiser_id;
	
	@Column(name="advertiser_name")
	private String advertiser_name;
	
	@Column(name="to_number")
	private String promo_number;
	
	@Column(name="promo_id")
	private String promo_id;
	
	@Column(name="description")
	private String promo_number_description;
	
	@Column(name="processed_ivr_keys")
	private String keypress; 
	
	@Column(name="ivr_action")
	private String action; 
	
	@Column(name="filter_name")
	private String filter_name; 
	
	@Column(name="filter_id")
	private int filter_id;
	
	@Column(name="key_calls")
	private Integer key_calls;
	
	@Column(name="keypress_in_percentage")
	private BigDecimal keypress_in_percentage;
	
	@Column(name="paidCalls_in_percentage")
	private BigDecimal paidCalls_in_percentage;
	
	@Column(name="paid_keypress_rate")
	private BigDecimal paid_keypress_rate;
	
	@Column(name="avg_call_duration")
	private String avg_call_duration;
	
	@Column(name="inbound_duration")
	private int inbound_duration;
	
	@Column(name="outbound_duration")
	private int outbound_duration;
	
	@Column(name="total_duration")
	private int total_duration;
	
	
	@Column(name="inbound_cost")
	private BigDecimal inbound_cost;
	
	@Column(name="outbound_cost")
	private BigDecimal outbound_cost;
	
	@Column(name="total_cost")
	private BigDecimal cost;
	
	@Column(name="from_state")
	private String from_state;
	
	@Column(name="end_time")
	private String daypart;

	@Column(name="call_uuid")
	private String call_uuid;
	
	@Column(name="from_number")
	private String from_number;
	
	@Column(name="call_type")
	private String call_type;
	
	@Column(name="answer_type")
	private String answer_type;
	
	@Column(name="agent_id")
	private int agent_id;
	
	@Column(name="from_line_type")
	private String from_line_type;
	
	@Column(name="from_city")
	private String from_city;
	
	@Column(name="from_country")
	private String from_country;
	
	@Column(name="from_zip")
	private String from_zip;
	
	@Column(name="b_leg_duration")
	private int b_leg_duration;
	
	@Column(name="status")
	private String status;
	
	@Column(name="duration")
	private int duration;
	
	@Column(name="is_repeat")
	private boolean repeat;
	
	@Column(name="file_url")
	private String file_url;
	
	@Column(name="algo")
	private String algo;
	
	@Column(name="keyword")
	private String keyword;
	
	@Column(name="keywordmatchtype")
	private String keyword_match_type;
	
	@Column(name="ad_group_name")
	private String ad_group_name;
	
	@Column(name="selected_ivr_keys")
	private String selected_ivr_keys;

	@Column(name="created_at")
	private String created_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCampaign_id() {
		return campaign_id;
	}

	public void setCampaign_id(int campaign_id) {
		this.campaign_id = campaign_id;
	}

	public int getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(int publisher_id) {
		this.publisher_id = publisher_id;
	}

	public BigDecimal getTotal_calls() {
		return total_calls;
	}

	public void setTotal_calls(BigDecimal total_calls) {
		this.total_calls = total_calls;
	}

	public BigDecimal getRepeat_calls() {
		return repeat_calls;
	}

	public void setRepeat_calls(BigDecimal repeat_calls) {
		this.repeat_calls = repeat_calls;
	}

	public BigDecimal getRepeat_calls_per() {
		return repeat_calls_per;
	}

	public void setRepeat_calls_per(BigDecimal repeat_calls_per) {
		this.repeat_calls_per = repeat_calls_per;
	}

	public BigDecimal getUnique_calls() {
		return unique_calls;
	}

	public void setUnique_calls(BigDecimal unique_calls) {
		this.unique_calls = unique_calls;
	}

	public BigDecimal getPaid_calls() {
		return paid_calls;
	}

	public void setPaid_calls(BigDecimal paid_calls) {
		this.paid_calls = paid_calls;
	}

	public boolean isOffer_not_found() {
		return offer_not_found;
	}

	public void setOffer_not_found(boolean offer_not_found) {
		this.offer_not_found = offer_not_found;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public Double getPublisher_revenue() {
		return publisher_revenue;
	}

	public void setPublisher_revenue(Double publisher_revenue) {
		this.publisher_revenue = publisher_revenue;
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

	public BigDecimal getUnique_conv() {
		return unique_conv;
	}

	public void setUnique_conv(BigDecimal unique_conv) {
		this.unique_conv = unique_conv;
	}

	public BigDecimal getAvg_rpc() {
		return avg_rpc;
	}

	public void setAvg_rpc(BigDecimal avg_rpc) {
		this.avg_rpc = avg_rpc;
	}

	public BigDecimal getAvg_cpc() {
		return avg_cpc;
	}

	public void setAvg_cpc(BigDecimal avg_cpc) {
		this.avg_cpc = avg_cpc;
	}

	public BigDecimal getAvg_rpk() {
		return avg_rpk;
	}

	public void setAvg_rpk(BigDecimal avg_rpk) {
		this.avg_rpk = avg_rpk;
	}

	public String getConnected_duration() {
		return connected_duration;
	}

	public void setConnected_duration(String connected_duration) {
		this.connected_duration = connected_duration;
	}

	public String getAvg_connect_duration() {
		return avg_connect_duration;
	}

	public void setAvg_connect_duration(String avg_connect_duration) {
		this.avg_connect_duration = avg_connect_duration;
	}

	public String getAvg_connect_duration_paid_calls() {
		return avg_connect_duration_paid_calls;
	}

	public void setAvg_connect_duration_paid_calls(String avg_connect_duration_paid_calls) {
		this.avg_connect_duration_paid_calls = avg_connect_duration_paid_calls;
	}

	public String getCampaign_name() {
		return campaign_name;
	}

	public void setCampaign_name(String campaign_name) {
		this.campaign_name = campaign_name;
	}

	public String getPublisher_name() {
		return publisher_name;
	}

	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	public int getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(int offer_id) {
		this.offer_id = offer_id;
	}

	public String getOffer_name() {
		return offer_name;
	}

	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}

	public String getAdvertiser_id() {
		return advertiser_id;
	}

	public void setAdvertiser_id(String advertiser_id) {
		this.advertiser_id = advertiser_id;
	}

	public String getAdvertiser_name() {
		return advertiser_name;
	}

	public void setAdvertiser_name(String advertiser_name) {
		this.advertiser_name = advertiser_name;
	}
	
	public String getPromo_number() {
		return promo_number;
	}

	public void setPromo_number(String promo_number) {
		this.promo_number = promo_number;
	}

	public String getPromo_number_description() {
		return promo_number_description;
	}

	public void setPromo_number_description(String promo_number_description) {
		this.promo_number_description = promo_number_description;
	}

	public String getPromo_id() {
		return promo_id;
	}

	public void setPromo_id(String promo_id) {
		this.promo_id = promo_id;
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

	public String getFilter_name() {
		return filter_name;
	}

	public void setFilter_name(String filter_name) {
		this.filter_name = filter_name;
	}

	public int getFilter_id() {
		return filter_id;
	}

	public void setFilter_id(int filter_id) {
		this.filter_id = filter_id;
	}

	public Integer getKey_calls() {
		return key_calls;
	}

	public void setKey_calls(Integer key_calls) {
		this.key_calls = key_calls;
	}

	public BigDecimal getKeypress_in_percentage() {
		return keypress_in_percentage;
	}

	public void setKeypress_in_percentage(BigDecimal keypress_in_percentage) {
		this.keypress_in_percentage = keypress_in_percentage;
	}

	public BigDecimal getPaidCalls_in_percentage() {
		return paidCalls_in_percentage;
	}

	public void setPaidCalls_in_percentage(BigDecimal paidCalls_in_percentage) {
		this.paidCalls_in_percentage = paidCalls_in_percentage;
	}

	public BigDecimal getPaid_keypress_rate() {
		return paid_keypress_rate;
	}

	public void setPaid_keypress_rate(BigDecimal paid_keypress_rate) {
		this.paid_keypress_rate = paid_keypress_rate;
	}

	public String getAvg_call_duration() {
		return avg_call_duration;
	}

	public void setAvg_call_duration(String avg_call_duration) {
		this.avg_call_duration = avg_call_duration;
	}

	public int getInbound_duration() {
		return inbound_duration;
	}

	public void setInbound_duration(int inbound_duration) {
		this.inbound_duration = inbound_duration;
	}

	public int getOutbound_duration() {
		return outbound_duration;
	}

	public void setOutbound_duration(int outbound_duration) {
		this.outbound_duration = outbound_duration;
	}

	public int getTotal_duration() {
		return total_duration;
	}

	public void setTotal_duration(int total_duration) {
		this.total_duration = total_duration;
	}

	public BigDecimal getInbound_cost() {
		return inbound_cost;
	}

	public void setInbound_cost(BigDecimal inbound_cost) {
		this.inbound_cost = inbound_cost;
	}

	public BigDecimal getOutbound_cost() {
		return outbound_cost;
	}

	public void setOutbound_cost(BigDecimal outbound_cost) {
		this.outbound_cost = outbound_cost;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getFrom_state() {
		return from_state;
	}

	public void setFrom_state(String from_state) {
		this.from_state = from_state;
	}

	public String getDaypart() {
		return daypart;
	}

	public void setDaypart(String daypart) {
		this.daypart = daypart;
	}

	public String getCall_uuid() {
		return call_uuid;
	}

	public void setCall_uuid(String call_uuid) {
		this.call_uuid = call_uuid;
	}

	public String getFrom_number() {
		return from_number;
	}

	public void setFrom_number(String from_number) {
		this.from_number = from_number;
	}

	public String getCall_type() {
		return call_type;
	}

	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	public String getAnswer_type() {
		return answer_type;
	}

	public void setAnswer_type(String answer_type) {
		this.answer_type = answer_type;
	}

	public int getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}

	public String getFrom_line_type() {
		return from_line_type;
	}

	public void setFrom_line_type(String from_line_type) {
		this.from_line_type = from_line_type;
	}

	public String getFrom_city() {
		return from_city;
	}

	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}

	public String getFrom_country() {
		return from_country;
	}

	public void setFrom_country(String from_country) {
		this.from_country = from_country;
	}

	public String getFrom_zip() {
		return from_zip;
	}

	public void setFrom_zip(String from_zip) {
		this.from_zip = from_zip;
	}

	public int getB_leg_duration() {
		return b_leg_duration;
	}

	public void setB_leg_duration(int b_leg_duration) {
		this.b_leg_duration = b_leg_duration;
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

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
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

	public String getKeyword_match_type() {
		return keyword_match_type;
	}

	public void setKeyword_match_type(String keyword_match_type) {
		this.keyword_match_type = keyword_match_type;
	}

	public String getAd_group_name() {
		return ad_group_name;
	}

	public void setAd_group_name(String ad_group_name) {
		this.ad_group_name = ad_group_name;
	}

	public String getSelected_ivr_keys() {
		return selected_ivr_keys;
	}

	public void setSelected_ivr_keys(String selected_ivr_keys) {
		this.selected_ivr_keys = selected_ivr_keys;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "GeneralReportDTO [id=" + id + ", campaign_id=" + campaign_id + ", publisher_id=" + publisher_id
				+ ", total_calls=" + total_calls + ", repeat_calls=" + repeat_calls + ", repeat_calls_per="
				+ repeat_calls_per + ", unique_calls=" + unique_calls + ", paid_calls=" + paid_calls
				+ ", offer_not_found=" + offer_not_found + ", revenue=" + revenue + ", publisher_revenue="
				+ publisher_revenue + ", profit=" + profit + ", conv=" + conv + ", unique_conv=" + unique_conv
				+ ", avg_rpc=" + avg_rpc + ", avg_cpc=" + avg_cpc + ", avg_rpk=" + avg_rpk + ", connected_duration="
				+ connected_duration + ", avg_connect_duration=" + avg_connect_duration
				+ ", avg_connect_duration_paid_calls=" + avg_connect_duration_paid_calls + ", campaign_name="
				+ campaign_name + ", publisher_name=" + publisher_name + ", offer_id=" + offer_id + ", offer_name="
				+ offer_name + ", advertiser_id=" + advertiser_id + ", advertiser_name=" + advertiser_name
				+ ", promo_number=" + promo_number + ", promo_id=" + promo_id + ", promo_number_description="
				+ promo_number_description + ", keypress=" + keypress + ", action=" + action + ", filter_name="
				+ filter_name + ", filter_id=" + filter_id + ", key_calls=" + key_calls + ", keypress_in_percentage="
				+ keypress_in_percentage + ", paidCalls_in_percentage=" + paidCalls_in_percentage
				+ ", paid_keypress_rate=" + paid_keypress_rate + ", avg_call_duration=" + avg_call_duration
				+ ", inbound_duration=" + inbound_duration + ", outbound_duration=" + outbound_duration
				+ ", total_duration=" + total_duration + ", inbound_cost=" + inbound_cost + ", outbound_cost="
				+ outbound_cost + ", cost=" + cost + ", from_state=" + from_state + ", daypart=" + daypart
				+ ", call_uuid=" + call_uuid + ", from_number=" + from_number + ", call_type=" + call_type
				+ ", answer_type=" + answer_type + ", agent_id=" + agent_id + ", from_line_type=" + from_line_type
				+ ", from_city=" + from_city + ", from_country=" + from_country + ", from_zip=" + from_zip
				+ ", b_leg_duration=" + b_leg_duration + ", status=" + status + ", duration=" + duration + ", repeat="
				+ repeat + ", file_url=" + file_url + ", algo=" + algo + ", keyword=" + keyword
				+ ", keyword_match_type=" + keyword_match_type + ", ad_group_name=" + ad_group_name
				+ ", selected_ivr_keys=" + selected_ivr_keys + ", created_at=" + created_at + "]";
	}

}