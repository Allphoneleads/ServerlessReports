package com.callx.aws.athena.querys;

import com.amazonaws.services.lambda.runtime.Context;

public class DynamicQuerysList {
	
	
	
	
	public static final String GENERAL_REPORTS_BASIC_COLUMNS = "SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls, \n" + 
			"	        SUM(total_revenue) AS total_revenue,\n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit, \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found, \n";
	
	
	public static final String DATABASE_AND_DATE_RANGES = " FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 /*AND is_test=0*/ ";
	
	
	public static final String IVR_FEES_CAMPAIGNS="select campaign_name, \n" + 
			"           SUM(duration - connected_duration) AS inbound_duration,\n" + 
			"           SUM(connected_duration) AS outbound_duration,\n" + 
			"           SUM(duration) AS total_duration,\n" + 
			"           ROUND(SUM(provider_cost),2) as inbound_cost, \n" + 
			"           ROUND(SUM(b_leg_total_cost),2) AS outbound_cost,\n" + 
			"           SUM(total_cost) AS total_cost\n" + 
			"           from calls_reporting.callx_history_parquet\n" + 
			"           WHERE campaign_id is not null\n" + 
			"           and ymdhm >= ?1 and ymdhm < ?2\n" + 
			"           GROUP BY campaign_name";
	
	public static final String IVR_FEES_PROMO_NUMBERS="select to_number, \n" + 
			"           SUM(duration - connected_duration) AS inbound_duration,\n" + 
			"           SUM(connected_duration) AS outbound_duration,\n" + 
			"           SUM(duration) AS total_duration,\n" + 
			"           ROUND(SUM(provider_cost),2) as inbound_cost, \n" + 
			"	        ROUND(SUM(b_leg_total_cost),2) AS outbound_cost,\n" + 
			"           SUM(total_cost) AS total_cost\n" + 
			"           from calls_reporting.callx_history_parquet\n" + 
			"           WHERE to_number is not null\n" + 
			"           and ymdhm >= ?1 and ymdhm < ?2\n" + 
			"           GROUP BY to_number";

	public static final String KEY_PRESS= "SELECT campaign_id,\n" + 
			"			campaign_name ,\n" + 
			"			filter_name ,\n" + 
			"			count(id) as key_calls ,\n" + 
			"			processed_ivr_keys,\n" + 
			"			filter_id , ivr_action, \n" + 
			"			SUM(CASE WHEN STATUS = 'unpaid' OR STATUS='Unpaid - Daily Budget Met' THEN 0 ELSE 1 END) AS paid_calls,\n" + 
			"			SUM(total_revenue) AS total_revenue, \n" + 
			"			SUM(duration) as  total_duration \n" + 
			"			FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2  " +
			"           group by campaign_id,campaign_name,filter_name, processed_ivr_keys,filter_id, ivr_action";
	
	public static String getExtraColumnsBasedOnReport(String reportType, Context context ) {
		try {
			context.getLogger().log(" From getExtraColumnsBasedOnReport : " + reportType);
			String appendQuery = "";
			if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN)) {
				
				appendQuery = " campaign_id, campaign_name "+DATABASE_AND_DATE_RANGES+" group by  campaign_id, campaign_name ";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_BY_PUBLISHER)) {
				
				appendQuery = " campaign_id, campaign_name, publisher_id, publisher_name "+DATABASE_AND_DATE_RANGES+" group by campaign_id,campaign_name, publisher_id, publisher_name";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS)) {
				
				appendQuery = " offerid, advertiser_id,offer_name,advertiser_name "+DATABASE_AND_DATE_RANGES+
						      " /*AND offerid > 0*/ AND offer_name is not null group by offerid,advertiser_id,offer_name,advertiser_name";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PUBLISHERS)) {
				
				appendQuery = " offerid, advertiser_id, offer_name, advertiser_name, publisher_id, publisher_name "+DATABASE_AND_DATE_RANGES+
						      " /*AND offerid > 0*/  AND offer_name is not null  group by offerid, publisher_id, advertiser_id,advertiser_name,publisher_name,offer_name";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PROMO_NUMBER)) {
				
				appendQuery = " promo_id, campaign_id,publisher_id, to_number, campaign_name, publisher_name, description "+DATABASE_AND_DATE_RANGES+
						      " group by promo_id, campaign_id,publisher_id, to_number, campaign_name, publisher_name, description";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PROMO_NUMBER)) {
				
				appendQuery = " offerid, advertiser_id, offer_name, advertiser_name, promo_id, to_number, description "+DATABASE_AND_DATE_RANGES+
						      " /*AND offerid > 0*/  AND offer_name is not null group by offerid, promo_id,advertiser_id,offer_name,advertiser_name,to_number,description";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.ADVERTISER)) {
				
				appendQuery = " advertiser_id, advertiser_name "+DATABASE_AND_DATE_RANGES+" AND advertiser_id > 0  group by advertiser_id,advertiser_name";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PUBLISHER)) {
				
				appendQuery = " publisher_id, publisher_name "+DATABASE_AND_DATE_RANGES+" group by publisher_id,publisher_name";
				
			}
			
			/*Geo Reports*/ 
			else if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_GEO)) {
				
				appendQuery = " campaign_id, campaign_name, from_state "+DATABASE_AND_DATE_RANGES+" AND campaign_id=?3 group by campaign_id, campaign_name, from_state ";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_BY_PUBLISHER_GEO)) {
				
				appendQuery = " campaign_id, campaign_name, publisher_id, publisher_name, from_state "+DATABASE_AND_DATE_RANGES+
						      " and campaign_id =?3 and publisher_id=?4 group by campaign_id,campaign_name, publisher_id, publisher_name, from_state";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_GEO)) {
				
				appendQuery = " offerid, advertiser_id,offer_name,advertiser_name, from_state "+DATABASE_AND_DATE_RANGES+
					      " and offerid=?3 group by offerid,advertiser_id,offer_name,advertiser_name, from_state";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PUBLISHERS_GEO)) {
				
				appendQuery = " offerid, advertiser_id, offer_name, advertiser_name, publisher_id, publisher_name,from_state "+DATABASE_AND_DATE_RANGES+
					      " and offerid=?3 and publisher_id=?4 group by offerid, publisher_id, advertiser_id,advertiser_name,publisher_name,offer_name,from_state";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PROMO_NUMBER_GEO)) {
				
				appendQuery = " promo_id, campaign_id,publisher_id, to_number, campaign_name, publisher_name, description,from_state "+DATABASE_AND_DATE_RANGES+
					      "  AND promo_id=?3 group by promo_id, campaign_id,publisher_id, to_number, campaign_name, publisher_name, description,from_state";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PROMO_NUMBER_GEO)) {
				
				appendQuery = " offerid, advertiser_id, offer_name, advertiser_name, promo_id, to_number, description,from_state "+DATABASE_AND_DATE_RANGES+
					      " and offerid=?3 and promo_id=?4 group by offerid, promo_id,advertiser_id,offer_name,advertiser_name,to_number,description, from_state";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.ADVERTISER_GEO)) {
				
				appendQuery = " advertiser_id, advertiser_name, from_state "+DATABASE_AND_DATE_RANGES+" AND advertiser_id=?3 group by advertiser_id, advertiser_name, from_state ";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PUBLISHER_GEO)) {
				
				appendQuery = " publisher_id, publisher_name,from_state "+DATABASE_AND_DATE_RANGES+" and publisher_id=?3 group by publisher_id,publisher_name,from_state";
				
			}
			
			/* Day Part Reports */
			else if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_DAYPART)) {
				
				appendQuery = " campaign_id, campaign_name, hour(end_time) as end_time "+DATABASE_AND_DATE_RANGES+" AND campaign_id=?3 group by campaign_id, campaign_name, end_time ";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_BY_PUBLISHER_DAYPART)) {
				
				appendQuery = " campaign_id, campaign_name, publisher_id, publisher_name, hour(end_time) as end_time "+DATABASE_AND_DATE_RANGES+
						      " and campaign_id =?3 and publisher_id=?4 group by campaign_id,campaign_name, publisher_id, publisher_name, end_time";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_DAYPART)) {
				
				appendQuery = " offerid, advertiser_id,offer_name,advertiser_name, hour(end_time) as end_time "+DATABASE_AND_DATE_RANGES+
					      " and offerid=?3 group by offerid,advertiser_id,offer_name,advertiser_name, end_time";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PUBLISHERS_DAYPART)) {
				
				appendQuery = " offerid, advertiser_id, offer_name, advertiser_name, publisher_id, publisher_name,hour(end_time) as end_time "+DATABASE_AND_DATE_RANGES+
					      " and offerid=?3 and publisher_id=?4 group by offerid, publisher_id, advertiser_id,advertiser_name,publisher_name,offer_name,end_time";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PROMO_NUMBER_DAYPART)) {
				
				appendQuery = " promo_id, campaign_id,publisher_id, to_number, campaign_name, publisher_name, description,hour(end_time) as end_time "+DATABASE_AND_DATE_RANGES+
					      "  AND promo_id=?3 group by promo_id, campaign_id,publisher_id, to_number, campaign_name, publisher_name, description,end_time";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PROMO_NUMBER_DAYPART)) {
				
				appendQuery = " offerid, advertiser_id, offer_name, advertiser_name, promo_id, to_number, description,hour(end_time) as end_time "+DATABASE_AND_DATE_RANGES+
					      " and offerid=?3 and promo_id=?4 group by offerid, promo_id,advertiser_id,offer_name,advertiser_name,to_number,description, end_time";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.ADVERTISER_DAYPART)) {
				
				appendQuery = " advertiser_id, advertiser_name, hour(end_time) as end_time "+DATABASE_AND_DATE_RANGES+" AND advertiser_id=?3 group by advertiser_id, advertiser_name, end_time ";
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PUBLISHER_DAYPART)) {
				
				appendQuery = " publisher_id, publisher_name,hour(end_time) as end_time "+DATABASE_AND_DATE_RANGES+" and publisher_id=?3 group by publisher_id,publisher_name,end_time";
				
			}
			
			return GENERAL_REPORTS_BASIC_COLUMNS + appendQuery;
		
		}catch(Exception e) {
			context.getLogger().log("Some error in CampaignReportsHandler : " + e.getMessage());
		}
		return null;
	}
	
	
}
