package com.callx.aws.lambda.util;

public class HeaderColumnUtils {



	public static String queryFileName = "query.txt";



	public static String[] granularReportHeaderColumns = new String[]{"id","call_uuid","campaign_id","offerid","publisher_id","advertiser_id","to_number","from_number","offer_name","publisher_name","advertiser_name",
			"description","call_type","answer_type","agent_id","from_line_type","from_state","from_city","from_country","from_zip","b_leg_duration","status",
			"processed_ivr_keys","filter_id","duration","connected_duration","total_revenue","total_cost","total_profit","publisher_revenue","file_url","algo",
			"keyword","keywordmatchtype","offer_not_found","filter_name","campaign_name","selected_ivr_keys","created_at" };

	// Standard Query's columns

	public static String[]  campaignColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","campaign_id", "campaign_name"};

	public static String[]  campaignByPublisherColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","campaign_id", "campaign_name", "publisher_id", "publisher_name"};

	public static String[]  offersColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id","offer_name","advertiser_name"};

	public static String[]  offerByPublisherColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id", "offer_name", "advertiser_name", "publisher_id", "publisher_name"};

	public static String[]  promoNumberColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","promo_id", "campaign_id","publisher_id", "to_number", "campaign_name", "publisher_name", "description"};

	public static String[]  offerByPromoNumberColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id", "offer_name", "advertiser_name", "promo_id", "to_number", "description"};

	public static String[]  advertiserColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","advertiser_id", "advertiser_name"};

	public static String[]  publisherColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","publisher_id", "publisher_name"};

	public static String[] ivrFeeCampaigns = new String[] {"campaign_name","inbound_duration","outbound_duration","total_duration","inbound_cost","outbound_cost","total_cost"};

	public static String[]  ivrFeePromoNumbers =new String[] {"to_number","inbound_duration","outbound_duration","total_duration","inbound_cost","outbound_cost","total_cost"};

	public static String[]  keyPress =new String[] {"campaign_id","campaign_name","filter_name","key_calls","processed_ivr_keys","filter_id", "ivr_action","paid_calls","total_revenue","total_duration"};


	// Geo Report Query's columns 

	public static String[]  campaignGeoColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","campaign_id", "campaign_name","from_state"};

	public static String[]  campaignByPublisherGeoColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","campaign_id", "campaign_name", "publisher_id", "publisher_name","from_state"};

	public static String[]  offersGeoColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id","offer_name","advertiser_name","from_state"};

	public static String[]  offerByPublisherGeoColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id", "offer_name", "advertiser_name", "publisher_id", "publisher_name","from_state"};

	public static String[]  promoNumberGeoColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","promo_id", "campaign_id","publisher_id", "to_number", "campaign_name", "publisher_name", "description","from_state"};

	public static String[]  offerByPromoNumberGeoColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id", "offer_name", "advertiser_name", "promo_id", "to_number", "description","from_state"};

	public static String[]  advertiserGeoColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","advertiser_id", "advertiser_name","from_state"};

	public static String[]  publisherGeoColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","publisher_id", "publisher_name","from_state"};



	// Day Part Query's Columns 
	
	public static String[]  campaignDaypartColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","campaign_id", "campaign_name","end_time"};

	public static String[]  campaignByPublisherDaypartColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","campaign_id", "campaign_name", "publisher_id", "publisher_name","end_time"};

	public static String[]  offersDaypartColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id","offer_name","advertiser_name","end_time"};

	public static String[]  offerByPublisherDaypartColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id", "offer_name", "advertiser_name", "publisher_id", "publisher_name","end_time"};

	public static String[]  promoNumberDaypartColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","promo_id", "campaign_id","publisher_id", "to_number", "campaign_name", "publisher_name", "description","end_time"};

	public static String[]  offerByPromoNumberDaypartColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","offerid", "advertiser_id", "offer_name", "advertiser_name", "promo_id", "to_number", "description","end_time"};

	public static String[]  advertiserDaypartColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","advertiser_id", "advertiser_name","end_time"};

	public static String[]  publisherDaypartColumns =new String[] {"calls","paid_calls","repeat_calls","total_revenue","total_cost","profit","connected_duration","offer_not_found","publisher_id", "publisher_name","end_time"};
	
	
	

	
	
	
}
