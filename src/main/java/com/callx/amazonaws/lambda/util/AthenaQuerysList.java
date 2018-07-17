package com.callx.amazonaws.lambda.util;

public class AthenaQuerysList {
	
	
	public static final String CAMPAIGN = "select  campaign_name, count(*) AS calls,  \n" + 
			"COUNT(CASE WHEN repeat=true THEN repeat END) AS repeat_calls,\n" + 
			"ROUND(CAST(COUNT(CASE WHEN repeat=true THEN repeat END) AS DOUBLE)/count(*)* 100) AS repeat_calls_per,\n" + 
			"COUNT(CASE WHEN repeat=false THEN repeat END) AS unique_calls,\n" + 
			"COUNT(CASE WHEN status= 'paid' THEN status END) AS paid_calls,\n" + 
			"/*COUNT(CASE WHEN is_sale= 1 THEN is_sale ELSE 0 END) AS sale,*/\n" + 
			"COUNT(CASE WHEN offer_not_found= true THEN offer_not_found END) AS offer_not_found,\n" + 
			"ROUND(CAST(COUNT(CASE WHEN status='paid' THEN status END) AS DOUBLE)/count(*)* 100) AS conv_rate,\n" + 
			"ROUND(CAST(COUNT(CASE WHEN status= 'paid' THEN status END) AS DOUBLE)/COUNT(CASE WHEN repeat=false THEN repeat END) * 100) AS unique_conv_rate,\n" + 
			"cast(round(SUM(CASE WHEN status='paid' THEN total_revenue ELSE 0 END),2) AS Varchar) AS total_revenue,\n" + 
			"cast(round(SUM(CASE WHEN status= 'paid' THEN publisher_revenue ELSE 0 END),2) AS Varchar) AS publisher_revenue,\n" + 
			"cast(round(SUM(CASE WHEN status= 'paid' THEN total_revenue - publisher_revenue ELSE 0 END),2) AS VArchar) AS profit,\n" + 
			"cast(round(AVG(total_revenue),2) AS Varchar) AS avg_rpc,\n" + 
			"cast(round(AVG(publisher_revenue),2) AS Varchar) AS avg_cpc,\n" + 
			" CONCAT(CAST(FLOOR(CAST(AVG(connected_duration) AS INTEGER)/60) AS Varchar), ':',\n" + 
			"        LPAD(CAST(CAST(AVG(connected_duration) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration\n" + 
			"FROM calls_reporting.callx_history_parquet\n" + 
			"WHERE campaign_id is not null\n" + 
			"and ymdhm >= ?1 and ymdhm < ?2\n" + 
			"GROUP BY campaign_name";
	
	
	
	public static final String CAMPAIGN_BY_PUBLISHER="select  campaign_name , publisher_name , count(*) AS calls,  \n" + 
			"COUNT(CASE WHEN repeat=true THEN repeat END) AS repeat_calls,\n" + 
			"ROUND(CAST(COUNT(CASE WHEN repeat=true THEN repeat END) AS DOUBLE)/count(*)* 100) AS repeat_calls_per,\n" + 
			"COUNT(CASE WHEN repeat=false THEN repeat END) AS unique_calls,\n" + 
			"COUNT(CASE WHEN status= 'paid' THEN status END) AS paid_calls,\n" + 
			"/*COUNT(CASE WHEN is_sale= 1 THEN is_sale ELSE 0 END) AS sale,*/\n" +
			"COUNT(CASE WHEN offer_not_found= true THEN offer_not_found END) AS offer_not_found,\n" + 
			"ROUND(CAST(COUNT(CASE WHEN status='paid' THEN status END) AS DOUBLE)/count(*)* 100) AS conv_rate,\n" + 
			"ROUND(CAST(COUNT(CASE WHEN status= 'paid' THEN status END) AS DOUBLE)/COUNT(CASE WHEN repeat=false THEN repeat END) * 100) AS unique_conv_rate,\n" + 
			"cast(round(SUM(CASE WHEN status='paid' THEN total_revenue ELSE 0 END),2) AS Varchar) AS total_revenue,\n" + 
			"cast(round(SUM(CASE WHEN status= 'paid' THEN publisher_revenue ELSE 0 END),2) AS Varchar) AS publisher_revenue,\n" + 
			"cast(round(SUM(CASE WHEN status= 'paid' THEN total_revenue - publisher_revenue ELSE 0 END),2) AS VArchar) AS profit,\n" + 
			"cast(round(AVG(total_revenue),2) AS Varchar) AS avg_rpc,\n" + 
			"cast(round(AVG(publisher_revenue),2) AS Varchar) AS avg_cpc,\n" + 
			"  CONCAT(CAST(FLOOR(CAST(AVG(connected_duration) AS INTEGER)/60) AS Varchar), ':',\n" + 
			"        LPAD(CAST(CAST(AVG(connected_duration) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration\n" + 
			"FROM calls_reporting.callx_history_parquet\n" + 
			"WHERE campaign_id is not null\n" + 
			"and ymdhm >= ?1 and ymdhm < ?2\n" + 
			"GROUP BY campaign_name, publisher_name";
	
	public static final String OFFERS= "select  offer_name , advertiser_name , count(*) AS calls,  \n" + 
			" COUNT(CASE WHEN repeat=true THEN repeat END) AS repeat_calls,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN repeat=true THEN repeat END) AS DOUBLE)/count(*) * 100) AS repeat_calls_per,\n" + 
			" COUNT(CASE WHEN repeat=false THEN repeat END) AS unique_calls,\n" + 
			" COUNT(CASE WHEN status= 'paid' THEN status END) AS paid_calls,\n" + 
			" /*COUNT(CASE WHEN is_sale= 1 THEN is_sale ELSE 0 END) AS Sale,*/\n" + 
			" COUNT(CASE WHEN offer_not_found= true THEN offer_not_found END) AS offer_not_found,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status='paid' THEN status END) AS DOUBLE)/count(*) * 100) AS conv_rate,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status= 'paid' THEN status END) AS DOUBLE)/COUNT(CASE WHEN repeat=false THEN repeat END) * 100) AS unique_conv_rate,\n" + 
			"  cast(round(SUM(CASE WHEN status='paid' THEN total_revenue ELSE 0 END),2) AS Varchar) AS total_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN publisher_revenue ELSE 0 END),2) AS Varchar) AS publisher_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN total_revenue - publisher_revenue ELSE 0 END),2) AS VArchar) AS profit,\n" + 
			"  cast(round(AVG(total_revenue),2) AS Varchar) AS avg_rpc,\n" + 
			"  cast(round(AVG(publisher_revenue),2) AS Varchar) AS avg_cpc,\n" + 
			"  CONCAT(CAST(FLOOR(CAST(AVG(connected_duration) AS INTEGER)/60) AS Varchar), ':', \n" + 
			"         LPAD(CAST(CAST(AVG(connected_duration) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration\n" + 
			" FROM calls_reporting.callx_history_parquet\n" + 
			" WHERE offer_name is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY offer_name, advertiser_name";
	
	public static final String OFFERS_BY_PUBLISHERS= "select  offer_name , publisher_name , advertiser_name , count(*) AS calls,  \n" + 
			" COUNT(CASE WHEN repeat=true THEN repeat END) AS repeat_calls,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN repeat=true THEN repeat END) AS DOUBLE)/count(*) * 100) AS repeat_calls_per,\n" + 
			" COUNT(CASE WHEN repeat=false THEN repeat END) AS unique_calls,\n" + 
			" COUNT(CASE WHEN status= 'paid' THEN status END) AS paid_calls,\n" + 
			" /*COUNT(CASE WHEN is_sale= 1 THEN is_sale ELSE 0 END) AS Sale,*/\n" + 
			" COUNT(CASE WHEN offer_not_found= true THEN offer_not_found END) AS offer_not_found,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status='paid' THEN status END) AS DOUBLE)/count(*) * 100) AS conv_rate,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status= 'paid' THEN status END) AS DOUBLE)/COUNT(CASE WHEN repeat=false THEN repeat END) * 100) AS unique_conv_rate,\n" + 
			"  cast(round(SUM(CASE WHEN status='paid' THEN total_revenue ELSE 0 END),2) AS Varchar) AS total_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN publisher_revenue ELSE 0 END),2) AS Varchar) AS publisher_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN total_revenue - publisher_revenue ELSE 0 END),2) AS VArchar) AS profit,\n" + 
			"  cast(round(AVG(total_revenue),2) AS Varchar) AS avg_rpc,\n" + 
			"  cast(round(AVG(publisher_revenue),2) AS Varchar) AS avg_cpc,\n" + 
			"   CONCAT(CAST(FLOOR(CAST(AVG(connected_duration) AS INTEGER)/60) AS Varchar), ':', \n" + 
			"         LPAD(CAST(CAST(AVG(connected_duration) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration\n" + 
			" FROM calls_reporting.callx_history_parquet\n" + 
			" WHERE offer_name is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY offer_name, publisher_name,advertiser_name";
	
	
	public static final String PROMO_NUMBER= "select promo_id, to_number, campaign_name, publisher_name , description, count(*) AS calls,  \n" + 
			" COUNT(CASE WHEN repeat=true THEN repeat END) AS repeat_calls,\n" + 
			"ROUND(CAST(COUNT(CASE WHEN repeat=true THEN repeat END) AS DOUBLE)/count(*) * 100) AS repeat_calls_per,\n" + 
			" COUNT(CASE WHEN repeat=false THEN repeat END) AS unique_calls,\n" + 
			" COUNT(CASE WHEN status= 'paid' THEN status END) AS paid_calls,\n" + 
			" /*COUNT(CASE WHEN is_sale= 1 THEN is_sale ELSE 0 END) AS Sale,*/\n" + 
			" COUNT(CASE WHEN offer_not_found= true THEN offer_not_found END) AS offer_not_found,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status='paid' THEN status END) AS DOUBLE)/count(*) * 100) AS conv_rate,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status= 'paid' THEN status END) AS DOUBLE)/COUNT(CASE WHEN repeat=false THEN repeat END) * 100) AS unique_conv_rate,\n" + 
			"  cast(round(SUM(CASE WHEN status='paid' THEN total_revenue ELSE 0 END),2) AS Varchar) AS total_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN publisher_revenue ELSE 0 END),2) AS Varchar) AS publisher_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN total_revenue - publisher_revenue ELSE 0 END),2) AS VArchar) AS profit,\n" + 
			"  cast(round(AVG(total_revenue),2) AS Varchar) AS avg_rpc,\n" + 
			"  cast(round(AVG(publisher_revenue),2) AS Varchar) AS avg_cpc,\n" + 
			"  CONCAT(CAST(FLOOR(CAST(AVG(connected_duration) AS INTEGER)/60) AS Varchar), ':', \n" + 
			"         LPAD(CAST(CAST(AVG(connected_duration) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration,\n" + 
			"  CONCAT(CAST(FLOOR(CAST(AVG(CASE WHEN status='paid' THEN connected_duration END) AS INTEGER)/60) AS Varchar), ':', \n" + 
			"         LPAD(CAST(CAST(AVG(CASE WHEN status='paid' THEN connected_duration END) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration_paid_calls\n" + 
			" FROM calls_reporting.callx_history_parquet\n" + 
			" WHERE to_number is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY promo_id, to_number, campaign_name, publisher_name, description";
	
	public static final String OFFERS_BY_PROMO_NUMBER= "select offer_name , to_number, description, count(*) AS calls,  \n" + 
			"COUNT(CASE WHEN repeat=true THEN repeat END) AS repeat_calls,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN repeat=true THEN repeat END) AS DOUBLE)/count(*) * 100) AS repeat_calls_per,\n" + 
			" COUNT(CASE WHEN repeat=false THEN repeat END) AS unique_calls,\n" + 
			" COUNT(CASE WHEN status= 'paid' THEN status END) AS paid_calls,\n" + 
			" /*COUNT(CASE WHEN is_sale= 1 THEN is_sale ELSE 0 END) AS Sale,*/\n" + 
			" COUNT(CASE WHEN offer_not_found= true THEN offer_not_found END) AS offer_not_found,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status='paid' THEN status END) AS DOUBLE)/count(*) * 100) AS conv_rate,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status= 'paid' THEN status END) AS DOUBLE)/COUNT(CASE WHEN repeat=false THEN repeat END) * 100) AS unique_conv_rate,\n" + 
			"  cast(round(SUM(CASE WHEN status='paid' THEN total_revenue ELSE 0 END),2) AS Varchar) AS total_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN publisher_revenue ELSE 0 END),2) AS Varchar) AS publisher_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN total_revenue - publisher_revenue ELSE 0 END),2) AS VArchar) AS profit,\n" + 
			"  cast(round(AVG(total_revenue),2) AS Varchar) AS avg_rpc,\n" + 
			"  cast(round(AVG(publisher_revenue),2) AS Varchar) AS avg_cpc,\n" + 
			"  CONCAT(CAST(FLOOR(CAST(AVG(connected_duration) AS INTEGER)/60) AS Varchar), ':', \n" + 
			"         LPAD(CAST(CAST(AVG(connected_duration) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration,\n" + 
			"  CONCAT(CAST(FLOOR(CAST(AVG(CASE WHEN status='paid' THEN connected_duration END) AS INTEGER)/60) AS Varchar), ':', \n" + 
			"         LPAD(CAST(CAST(AVG(CASE WHEN status='paid' THEN connected_duration END) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration_paid_calls\n" + 
			" FROM calls_reporting.callx_history_parquet\n" + 
			" WHERE offer_name is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY offer_name, to_number, description";
	
	
	
	public static final String ADVERTISER = "select  advertiser_name , count(*) AS calls,  \n" + 
			" COUNT(CASE WHEN repeat=true THEN repeat END) AS repeat_calls,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN repeat=true THEN repeat END) AS DOUBLE)/count(*) * 100) AS repeat_calls_per,\n" + 
			" COUNT(CASE WHEN repeat=false THEN repeat END) AS unique_calls,\n" + 
			" COUNT(CASE WHEN status= 'paid' THEN status END) AS paid_calls,\n" + 
			" /*COUNT(CASE WHEN is_sale= 1 THEN is_sale ELSE 0 END) AS Sale,*/\n" + 
			" COUNT(CASE WHEN offer_not_found= true THEN offer_not_found END) AS offer_not_found,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status='paid' THEN status END) AS DOUBLE)/count(*) * 100) AS conv_rate ,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status= 'paid' THEN status END) AS DOUBLE)/COUNT(CASE WHEN repeat=false THEN repeat END) * 100) AS unique_conv_rate,\n" + 
			"  cast(round(SUM(CASE WHEN status='paid' THEN total_revenue ELSE 0 END),2) AS Varchar) AS total_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN publisher_revenue ELSE 0 END),2) AS Varchar) AS publisher_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN total_revenue - publisher_revenue ELSE 0 END),2) AS VArchar) AS profit,\n" + 
			"  cast(round(AVG(total_revenue),2) AS Varchar) AS avg_rpc,\n" + 
			"  cast(round(AVG(publisher_revenue),2) AS Varchar) AS avg_cpc,\n" + 
			"    CONCAT(CAST(FLOOR(CAST(AVG(connected_duration) AS INTEGER)/60) AS Varchar), ':', \n" + 
			"         LPAD(CAST(CAST(AVG(connected_duration) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration\n" + 
			" FROM calls_reporting.callx_history_parquet\n" + 
			" WHERE advertiser_id is not null and advertiser_name is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY advertiser_name";
	
	
	public static final String PUBLISHER= "select  publisher_name, count(*) AS calls,  \n" + 
			" COUNT(CASE WHEN repeat=true THEN repeat END) AS repeat_calls,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN repeat=true THEN repeat END) AS DOUBLE)/count(*) * 100) AS repeat_calls_per,\n" + 
			" COUNT(CASE WHEN repeat=false THEN repeat END) AS unique_calls,\n" + 
			" COUNT(CASE WHEN status= 'paid' THEN status END) AS paid_calls,\n" + 
			" /*COUNT(CASE WHEN is_sale= 1 THEN is_sale ELSE 0 END) AS Sale,*/\n" + 
			" COUNT(CASE WHEN offer_not_found= true THEN offer_not_found END) AS offer_not_found,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status='paid' THEN status END) AS DOUBLE)/count(*) * 100) AS conv_rate ,\n" + 
			" ROUND(CAST(COUNT(CASE WHEN status= 'paid' THEN status END) AS DOUBLE)/COUNT(CASE WHEN repeat=false THEN repeat END) * 100) AS unique_conv_rate,\n" + 
			"  cast(round(SUM(CASE WHEN status='paid' THEN total_revenue ELSE 0 END),2) AS Varchar) AS total_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN publisher_revenue ELSE 0 END),2) AS Varchar) AS publisher_revenue,\n" + 
			"  cast(round(SUM(CASE WHEN status= 'paid' THEN total_revenue - publisher_revenue ELSE 0 END),2) AS VArchar) AS profit,\n" + 
			"  cast(round(AVG(total_revenue),2) AS Varchar) AS avg_rpc,\n" + 
			"  cast(round(AVG(publisher_revenue),2) AS Varchar) AS avg_cpc,\n" + 
			"  CONCAT(CAST(FLOOR(CAST(AVG(connected_duration) AS INTEGER)/60) AS Varchar), ':', \n" + 
			" LPAD(CAST(CAST(AVG(connected_duration) AS INTEGER)%60 AS Varchar),2,'0' )) AS avg_connected_duration\n" + 
			" FROM calls_reporting.callx_history_parquet\n" + 
			" WHERE publisher_id is not null and publisher_name is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY publisher_name";

	
	public static final String IVR_FEES_CAMPAIGNS="select campaign_name, \n" + 
			"       SUM(duration - connected_duration) AS inbound_duration,\n" + 
			"       SUM(connected_duration) AS outbound_duration,\n" + 
			"       SUM(duration) AS total_duration,\n" + 
			"       ROUND(SUM(provider_cost),2) as inbound_cost, \n" + 
			"       ROUND(SUM(b_leg_total_cost),2) AS outbound_cost,\n" + 
			"       SUM(total_cost) AS total_cost\n" + 
			"from calls_reporting.callx_history_parquet\n" + 
			"WHERE campaign_id is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY campaign_name";
	
	public static final String IVR_FEES_PROMO_NUMBERS="select to_number, \n" + 
			"       SUM(duration - connected_duration) AS inbound_duration,\n" + 
			"       SUM(connected_duration) AS outbound_duration,\n" + 
			"       SUM(duration) AS total_duration,\n" + 
			"       ROUND(SUM(provider_cost),2) as inbound_cost, \n" + 
			"	    ROUND(SUM(b_leg_total_cost),2) AS outbound_cost,\n" + 
			"       SUM(total_cost) AS total_cost\n" + 
			"from calls_reporting.callx_history_parquet\n" + 
			"WHERE to_number is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY to_number";
	
	public static final String KEY_PRESS= " select campaign_name, COUNT(*) AS calls,\n" + 
			" processed_ivr_keys AS processed_ivr_keys,\n" + 
			" ivr_action AS   ivr_action,\n" + 
			" filter_name AS filter_name,\n" + 
			" SUM(CASE WHEN STATUS = 'unpaid' OR STATUS='Unpaid - Daily Budget Met' THEN 0 ELSE 1 END) AS paid_calls,\n" + 
			" SUM(total_revenue) AS total_revenue,\n" + 
			" AVG(total_revenue) AS avg_rpk,\n" + 
			" AVG(duration) AS avg_call_duration\n" + 
			" from calls_reporting.callx_history_parquet\n" + 
			" WHERE campaign_id is not null\n" + 
			" and ymdhm >= ?1 and ymdhm < ?2\n" + 
			" GROUP BY campaign_name, processed_ivr_keys, ivr_action, filter_name\n" + 
			" ORDER BY campaign_name, processed_ivr_keys, ivr_action, filter_name";
	
	public static final String TEST_QUERY="select id as ID, campaign_name as Campaign from calls_reporting.callx_history_parquet order by id desc limit 10";

}
