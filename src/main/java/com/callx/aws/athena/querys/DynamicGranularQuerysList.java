package com.callx.aws.athena.querys;

import com.amazonaws.services.lambda.runtime.Context;

public class DynamicGranularQuerysList {
	
	
	public static final String GRANULAR_REPORTS_BASIC_COLUMNS = "SELECT id, call_uuid, campaign_id, offerid, publisher_id, advertiser_id, to_number, "
			                   + "from_number,offer_name, publisher_name, advertiser_name, description, call_type, answer_type, agent_id, from_line_type, "
			                   + "from_state, from_city, from_country, from_zip,b_leg_duration, status, processed_ivr_keys, filter_id, duration,"
			                   + " connected_duration, total_revenue, total_cost, total_profit, publisher_revenue,/*is_repeat,*/ file_url,algo,"
			                   + " keyword, keywordmatchtype,/*ad_group_name,*/  offer_not_found, filter_name, campaign_name, selected_ivr_keys, created_at";
	
	
	public static final String DATABASE_AND_DATE_RANGES = " FROM calls_reporting.callx_calls_athena where ymdhm >= ?1 and ymdhm < ?2 AND test=false  ";
	
	
	
	public static String getGranularReportQuery(String reportType, String filterType, Context context ) {
		try {
			context.getLogger().log(" From getGranularReportQuery : " + reportType);
			String appendQuery = "";
			
			/*Granular Reports*/ 
			 if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_GRANULAR)) {
				
				 if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND campaign_id=?3";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND campaign_id=?3 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND campaign_id=?3 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND campaign_id=?3 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND campaign_id=?3 AND offer_not_found=true";
				 }
			 
			 }else if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_BY_PUBLISHER_GRANULAR)) {
				
				 if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 ";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PUBLISHERS_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 ";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PROMO_NUMBER_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND promo_id=?3";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND promo_id=?3 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND promo_id=?3 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND promo_id=?3 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND promo_id=?3 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PROMO_NUMBER_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3 AND promo_id=?4 ";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.ADVERTISER_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND advertiser_id=?3";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND advertiser_id=?3 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND advertiser_id=?3 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND advertiser_id=?3 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND advertiser_id=?3 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PUBLISHER_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND publisher_id=?3";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND publisher_id=?3 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND publisher_id=?3 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND publisher_id=?3 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND publisher_id=?3 AND offer_not_found=true";
				 }
				
			}
			
			return GRANULAR_REPORTS_BASIC_COLUMNS +" "+DATABASE_AND_DATE_RANGES+ appendQuery;
		
		}catch(Exception e) {
			context.getLogger().log("Some error in getGranularReportQuery : " + e.getMessage());
		}
		return null;
	}



	public static String getStateGranularReportQuery(String reportType, String filterType, String state,
			Context context) {
		try {
			context.getLogger().log(" From getStateGranularReportQuery : " + reportType);
			String appendQuery = "";
			
			/*State Granular Reports*/ 
			 if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_STATE_GRANULAR)) {
				
				 if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND campaign_id=?3 AND from_state='?4'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND campaign_id=?3 AND AND from_state='?4' AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND campaign_id=?3 AND from_state='?4' AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND campaign_id=?3 AND from_state='?4' AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND campaign_id=?3 AND from_state='?4' AND offer_not_found=true";
				 }
			 
			 }else if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_BY_PUBLISHER_STATE_GRANULAR)) {
				
				 if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND from_state='?5' ";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND from_state='?5' AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND from_state='?5' AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND from_state='?5' AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND from_state='?5' AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_STATE_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3 AND from_state='?4'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND from_state='?4' AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND from_state='?4' AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND from_state='?4' AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND from_state='?4' AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PUBLISHERS_STATE_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3 AND publisher_id='?4' AND from_state=?5";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND publisher_id='?4' AND from_state=?5 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND publisher_id='?4' AND from_state=?5 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND publisher_id='?4' AND from_state=?5 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND publisher_id='?4' AND from_state=?5 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PROMO_NUMBER_STATE_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND promo_id=?3 AND from_state='?4'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND promo_id=?3 AND from_state='?4' AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND promo_id=?3 AND from_state='?4' AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND promo_id=?3 AND from_state='?4' AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND promo_id=?3 AND from_state='?4' AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PROMO_NUMBER_STATE_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND from_state='?5'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND from_state='?5' AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND from_state='?5' AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND from_state='?5' AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND from_state='?5' AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.ADVERTISER_STATE_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND advertiser_id=?3 AND from_state='?4'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND advertiser_id=?3 AND from_state='?4' AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND advertiser_id=?3 AND from_state='?4' AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND advertiser_id=?3 AND from_state='?4' AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND advertiser_id=?3 AND from_state='?4' AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PUBLISHER_STATE_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND publisher_id=?3 AND from_state='?4'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND publisher_id=?3 AND from_state='?4' AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND publisher_id=?3 AND from_state='?4' AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND publisher_id=?3 AND from_state='?4' AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND publisher_id=?3 AND from_state='?4' AND offer_not_found=true";
				 }
				
			}
			
			return GRANULAR_REPORTS_BASIC_COLUMNS +" "+DATABASE_AND_DATE_RANGES+ appendQuery;
		
		}catch(Exception e) {
			context.getLogger().log("Some error in getGranularReportQuery : " + e.getMessage());
		}
		return null;
	}



	public static String getDaypartGranularReportQuery(String reportType, String filterType, int hour,
			Context context) {
		try {
			context.getLogger().log(" From getDaypartGranularReportQuery : " + reportType);
			String appendQuery = "";
			
			/*State Granular Reports*/ 
			 if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_DAYPART_GRANULAR)) {
				
				 if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND campaign_id=?3 AND hour(end_time)=?4";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND campaign_id=?3 AND AND hour(end_time)=?4 repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND campaign_id=?3 AND hour(end_time)=?4 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND campaign_id=?3 AND hour(end_time)=?4 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND campaign_id=?3 AND hour(end_time)=?4 AND offer_not_found=true";
				 }
			 
			 }else if(reportType.equalsIgnoreCase(StaticReports.CAMPAIGN_BY_PUBLISHER_DAYPART_GRANULAR)) {
				
				 if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND hour(end_time)=?5 ";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND hour(end_time)=?5 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND hour(end_time)=?5 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND hour(end_time)=?5 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND campaign_id=?3 AND publisher_id=?4 AND hour(end_time)=?5 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_DAYPART_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3 AND hour(end_time)=?4";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND hour(end_time)=?4 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND hour(end_time)=?4 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND hour(end_time)=?4 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND hour(end_time)=?4 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PUBLISHERS_DAYPART_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND hour(end_time)=?5";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND hour(end_time)=?5 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND hour(end_time)=?5 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND hour(end_time)=?5 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND publisher_id=?4 AND hour(end_time)=?5 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PROMO_NUMBER_DAYPART_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND promo_id=?3 AND hour(end_time)=?4";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND promo_id=?3 AND hour(end_time)=?4 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND promo_id=?3 AND hour(end_time)=?4 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND promo_id=?3 AND hour(end_time)=?4 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND promo_id=?3 AND hour(end_time)=?4 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.OFFERS_BY_PROMO_NUMBER_DAYPART_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND hour(end_time)=?5";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND hour(end_time)=?5 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND hour(end_time)=?5 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND hour(end_time)=?5 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND offerid=?3 AND promo_id=?4 AND hour(end_time)=?5 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.ADVERTISER_DAYPART_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND advertiser_id=?3 AND hour(end_time)=?4";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND advertiser_id=?3 AND hour(end_time)=?4 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND advertiser_id=?3 AND hour(end_time)=?4 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND advertiser_id=?3 AND hour(end_time)=?4 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND advertiser_id=?3 AND hour(end_time)=?4 AND offer_not_found=true";
				 }
				
			}else if(reportType.equalsIgnoreCase(StaticReports.PUBLISHER_DAYPART_GRANULAR)) {
				
				if(filterType.equalsIgnoreCase(StaticReports.TOTAL)) {
					 appendQuery = " AND publisher_id=?3 AND hour(end_time)=?4";
				 }else if(filterType.equalsIgnoreCase(StaticReports.REPEAT)){
					 appendQuery = " AND publisher_id=?3 AND hour(end_time)=?4 AND repeat=true";
				 }else if(filterType.equalsIgnoreCase(StaticReports.UNIQUE)){
					 appendQuery = " AND publisher_id=?3 AND hour(end_time)=?4 AND repeat=false";
				 }else if(filterType.equalsIgnoreCase(StaticReports.PAID)){
					 appendQuery = " AND publisher_id=?3 AND hour(end_time)=?4 AND status='paid'";
				 }else if(filterType.equalsIgnoreCase(StaticReports.OFFERS_NOT_AVAILABLE)){
					 appendQuery = " AND publisher_id=?3 AND hour(end_time)=?4 AND offer_not_found=true";
				 }
				
			}
			
			return GRANULAR_REPORTS_BASIC_COLUMNS +" "+DATABASE_AND_DATE_RANGES+ appendQuery;
		
		}catch(Exception e) {
			context.getLogger().log("Some error in getGranularReportQuery : " + e.getMessage());
		}
		return null;
	}

}
