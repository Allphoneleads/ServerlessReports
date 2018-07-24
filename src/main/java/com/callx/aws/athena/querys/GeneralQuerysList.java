package com.callx.aws.athena.querys;

public class GeneralQuerysList {


	public static final String CAMPAIGN = "SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls, \n" + 
			"	        SUM(total_revenue) AS total_revenue,\n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit, \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found, \n" + 
			"			campaign_id, campaign_name \n" + 
			"			FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 /*AND is_test=0*/ group by campaign_id, campaign_name	";




	public static final String CAMPAIGN_BY_PUBLISHER="SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls, \n" + 
			"			SUM(total_revenue) AS total_revenue,\n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit, \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found, \n" + 
			"			campaign_id, campaign_name, publisher_id, publisher_name \n" + 
			"			FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 /*AND is_test=0*/  group by campaign_id,campaign_name, publisher_id, publisher_name";

	public static final String OFFERS= "SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls, \n" + 
			"			SUM(total_revenue) AS total_revenue,\n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit, \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found, \n" + 
			"			offerid, advertiser_id,offer_name,advertiser_name\n" + 
			"           FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 /*AND is_test=0\n" + 
			"           AND offerid > 0*/ AND offer_name is not null group by offerid,advertiser_id,offer_name,advertiser_name";

	public static final String OFFERS_BY_PUBLISHERS= "SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls, \n" + 
			"			SUM(total_revenue) AS total_revenue,\n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit, \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found, \n" + 
			"			offerid, advertiser_id, offer_name, advertiser_name, publisher_id, publisher_name  \n" + 
			"			FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 /*AND is_test=0\n" + 
			"           AND offerid > 0*/  AND offer_name is not null  group by offerid, publisher_id, advertiser_id,advertiser_name,\n" + 
			"           publisher_name,offer_name";


	public static final String PROMO_NUMBER= " SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls,\n" + 
			"			SUM(total_revenue) AS total_revenue,\n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit, \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found, \n" + 
			"			promo_id, campaign_id,publisher_id, to_number, campaign_name, publisher_name, description  \n" + 
			"			FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 /*AND is_test=0*/ group by  "+
			"           promo_id, campaign_id,publisher_id, to_number, campaign_name, publisher_name, description";

	public static final String OFFERS_BY_PROMO_NUMBER= "SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls, \n" + 
			"			SUM(total_revenue) AS total_revenue,\n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit, \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found, \n" + 
			"			offerid, advertiser_id, offer_name, advertiser_name, promo_id, to_number, description  \n" + 
			"			FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 /*AND is_test=0\n" + 
			"           AND offerid > 0*/  AND offer_name is not null group by offerid, promo_id,advertiser_id,offer_name,advertiser_name,\n" + 
			"           to_number,description";



	public static final String ADVERTISER = "SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls,\n" + 
			"			SUM(total_revenue) AS total_revenue, \n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit,  \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found,\n" + 
			"			advertiser_id, advertiser_name \n" + 
			"			FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 /*AND is_test=0*/ AND "+
			"           advertiser_id > 0  group by advertiser_id,advertiser_name";


	public static final String PUBLISHER= "SELECT count(*) as calls, \n" + 
			"			SUM(CASE WHEN STATUS = 'paid' THEN 1 ELSE 0 END) AS paid_calls, \n" + 
			"			SUM(CASE WHEN repeat = true THEN 1 ELSE 0 END) AS repeat_calls, \n" + 
			"			SUM(total_revenue) AS total_revenue,\n" + 
			"			SUM(publisher_revenue) AS total_cost, \n" + 
			"			SUM(total_profit) AS profit, \n" + 
			"			SUM(connected_duration) AS connected_duration, \n" + 
			"			SUM(CASE WHEN offer_not_found = true THEN 1 ELSE 0 END) AS offer_not_found,\n" + 
			"			publisher_id, publisher_name  \n" + 
			"			FROM calls_reporting.callx_history_parquet  where ymdhm >= ?1 and ymdhm < ?2 "+
			"           /*AND is_test=0*/  group by publisher_id,publisher_name";


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



	public static final String TEST_QUERY="select id as ID, campaign_name as Campaign from calls_reporting.callx_history_parquet order by id desc limit 10";

}
