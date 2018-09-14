package com.callx.aws.athena.querys;

public class SalesConversionQuerysList {



	public static String campaignSalesQuery = "select count(*) as sale_count, campaign_id, campaign_name from calls_reporting.callx_sales_athena"
			+ " where timestamp between ?1 and ?2 group by campaign_id,campaign_name;";


	public static String campaignByPublisherSalesQuery = "select count(*) as sale_count, campaign_id,  campaign_name,publisher_id, publisher_name "
			+ " from calls_reporting.callx_sales_athena"
			+ " where timestamp between ?1 and ?2 group by campaign_id,publisher_id, campaign_name, publisher_name ";

	public static String offerSalesQuery = "select count(*) as sale_count, offer_id, offer_name from calls_reporting.callx_sales_athena"
			+ " where timestamp between ?1 and ?2 group by offer_id, offer_name;";

	public static String offerByPublisherSalesQuery = "select count(*) as sale_count, offer_id, offer_name,publisher_id,publisher_name from calls_reporting.callx_sales_athena"
			+ " where timestamp between ?1 and ?2 group by offer_id, publisher_id, offer_name, publisher_name";

	public static String promoNumber ="select count(*) as sale_count, promo_number from calls_reporting.callx_sales_athena"
			+ " where timestamp between ?1 and ?2 group by promo_number";


	public static String offerByPromoNumber = "select count(*) as sale_count, offer_id,  offer_name, promo_number from calls_reporting.callx_sales_athena"
			+ " where timestamp between ?1 and ?2 group by offer_id, promo_number, offer_name";

	public static String advertiser = "select count(*) as sale_count,advertiser_id, advertiser_name  from calls_reporting.callx_sales_athena"
			+ " where timestamp between ?1 and ?2 group by advertiser_id, advertiser_name";

	public static String publisher = "select count(*), publisher_id, publisher_name from calls_reporting.callx_sales_athena"
			+ " where timestamp between ?1 and ?2 group by publisher_id, publisher_name;";



	public static String campaignSalesGranularQuery = " select * from calls_reporting.callx_sales_athena where campaign_id=?1 and timestamp between ?2 and ?3;";

	public static String offerSalesGranularQuery = "select * from calls_reporting.callx_sales_athena where offer_id =?1 and timestamp between ?2 and ?3;";

	public static String promonumberSalesGranularQuery = "select * from calls_reporting.callx_sales_athena where promo_number =?1 and timestamp between ?2 and ?3;";

	public static String advertiserSalesGranularQuery = " select * from calls_reporting.callx_sales_athena where advertiser_id =?1 and timestamp between ?2 and ?3";

	public static String publisherSalesGranularQuery = " select * from calls_reporting.callx_sales_athena where publisher_id =?1 and timestamp between ?2 and ?3";

	public static String campaignByPublisherSalesGranularQuery = " select * from calls_reporting.callx_sales_athena where campaign_id =?1 and publisher_id=?2 and timestamp between ?3 and ?4 ";

	public static String offerByPublisherSalesGranularQuery = " select * from calls_reporting.callx_sales_athena where offer_id =?1 and publisher_id=?2 and timestamp between ?3 and ?4";

	public static String offerByPromonumberSalesGranularQuery = " select * from calls_reporting.callx_sales_athena where offer_id =?1 and promo_number =?2 and timestamp between ?3 and ?4";


	// Sales Geo Query.
	public static String campaignSalesGeoQuery = "select count(*) as sale_count, campaign_id, campaign_name, state from calls_reporting.callx_sales_athena"
			+ " where campaign_id=?1 AND timestamp between ?2 and ?3 group by campaign_id,campaign_name, state;";

	public static String campaignByPublisherSalesGeoQuery = "select count(*) as sale_count, campaign_id,  campaign_name,publisher_id, publisher_name, state "
			+ " from calls_reporting.callx_sales_athena"
			+ " where campaign_id=?1 AND publisher_id=?2 AND timestamp between ?3 and ?4 group by campaign_id,publisher_id, campaign_name, publisher_name, state ";

	public static String offerSalesGeoQuery = "select count(*) as sale_count, offer_id, offer_name,state from calls_reporting.callx_sales_athena"
			+ " where offer_id=?1 AND timestamp between ?2 and ?3 group by offer_id, offer_name,state;";

	public static String offerByPublisherSalesGeoQuery = "select count(*) as sale_count, offer_id, offer_name,publisher_id,publisher_name, state from calls_reporting.callx_sales_athena"
			+ " where offer_id=?1 AND publisher_id=?2 AND timestamp between ?3 and ?4 group by offer_id, publisher_id, offer_name, publisher_name,state";

	public static String promoNumberGeoQuery ="select count(*) as sale_count, promo_number,state from calls_reporting.callx_sales_athena"
			+ " where promo_id=?1 AND timestamp between ?2 and ?3 group by promo_number,state";


	public static String offerByPromoNumberGeoQuery = "select count(*) as sale_count, offer_id,  offer_name, promo_number,state from calls_reporting.callx_sales_athena"
			+ " where offer_id=?1 AND promo_id=?2 AND timestamp between ?3 and ?4 group by offer_id, promo_number, offer_name,state";

	public static String advertiserGeoQuery = "select count(*) as sale_count,advertiser_id, advertiser_name,state  from calls_reporting.callx_sales_athena"
			+ " where advertiser_id=?1 AND timestamp between ?2 and ?3 group by advertiser_id, advertiser_name,state";

	public static String publisherGeoQuery = "select count(*), publisher_id, publisher_name,state from calls_reporting.callx_sales_athena"
			+ " where publisher_id=?1 AND timestamp between ?2 and ?3 group by publisher_id, publisher_name,state;";


	// Sales Day Part Query 
	public static String campaignSalesDaypartQuery ="select count(*) as sale_count, campaign_id, campaign_name, end_time from calls_reporting.callx_sales_athena"
												  + " where campaign_id=?1 AND timestamp between ?2 and ?3 group by campaign_id,campaign_name, end_time;";

	public static String campaignByPublisherSalesDaypartQuery = "select count(*) as sale_count, campaign_id,  campaign_name,publisher_id, publisher_name, end_time "
															  + " from calls_reporting.callx_sales_athena"
															  + " where campaign_id=?1 AND publisher_id=?2 AND timestamp between ?3 and ?4 group by campaign_id,publisher_id, campaign_name, publisher_name, end_time ";

	public static String offerSalesDaypartQuery = "select count(*) as sale_count, offer_id, offer_name,end_time from calls_reporting.callx_sales_athena"
												+ " where offer_id=?1 AND timestamp between ?2 and ?3 group by offer_id, offer_name,end_time;";

	public static String offerByPublisherSalesDaypartQuery = "select count(*) as sale_count, offer_id, offer_name,publisher_id,publisher_name, end_time from calls_reporting.callx_sales_athena"
														   + " where offer_id=?1 AND publisher_id=?2 AND timestamp between ?3 and ?4 group by offer_id, publisher_id, offer_name, publisher_name,end_time";

	public static String promoNumberDaypartQuery ="select count(*) as sale_count, promo_number,end_time from calls_reporting.callx_sales_athena"
												+ " where promo_id=?1 AND timestamp between ?2 and ?3 group by promo_number,end_time";


	public static String offerByPromoNumberDaypartQuery = "select count(*) as sale_count, offer_id,  offer_name, promo_number,end_time from calls_reporting.callx_sales_athena"
														+ " where offer_id=?1 AND promo_id=?2 AND timestamp between ?3 and ?4 group by offer_id, promo_number, offer_name,end_time";

	public static String advertiserDaypartQuery = "select count(*) as sale_count,advertiser_id, advertiser_name,end_time  from calls_reporting.callx_sales_athena"
												+ " where advertiser_id=?1 AND timestamp between ?2 and ?3 group by advertiser_id, advertiser_name,end_time";

	public static String publisherDaypartQuery = "select count(*), publisher_id, publisher_name,end_time from calls_reporting.callx_sales_athena"
											   + " where publisher_id=?1 AND timestamp between ?2 and ?3 group by publisher_id, publisher_name,end_time;";
	
	
	
	//Sales Geo Granular Query 
	public static String campaignSalesGeoGranularQuery = "select * from calls_reporting.callx_sales_athena where campaign_id=?1 AND state=?2 AND timestamp between ?3 and ?4 ";
	
	public static String offerSalesGeoGranularQuery = "select * from calls_reporting.callx_sales_athena where offer_id =?1 AND state=?2 AND timestamp between ?3 and ?4;";
	
	public static String promonumberSalesGeoGranularQuery = "select * from calls_reporting.callx_sales_athena where promo_number =?1 AND state=?2 AND timestamp between ?3 and ?4;";
	
	public static String advertiserSalesGeoGranularQuery = "select * from calls_reporting.callx_sales_athena where advertiser_id =?1 AND state=?2 AND timestamp between ?3 and ?4";
	
	public static String publisherSalesGeoGranularQuery = "select * from calls_reporting.callx_sales_athena where publisher_id =?1 AND state=?2 AND timestamp between ?3 and ?4";
	
	public static String campaignByPublisherSalesGeoGranularQuery = "select * from calls_reporting.callx_sales_athena where campaign_id =?1 AND publisher_id=?2 AND state=?3 AND timestamp between ?4 and ?5 ";
	
	public static String offerByPublisherSalesGeoGranularQuery = "select * from calls_reporting.callx_sales_athena where offer_id =?1 AND publisher_id=?2 AND state=?3 AND timestamp between ?4 and ?5";
	
	public static String offerByPromonumberSalesGeoGranularQuery = "select * from calls_reporting.callx_sales_athena where offer_id =?1 AND promo_number =?2 AND state=?3 AND timestamp between ?4 and ?5";
	
	
	//Sales Daypart Granular Query
	public static String campaignSalesDaypartGranularQuery = "select * from calls_reporting.callx_sales_athena where campaign_id=?1 AND hour(end_time)=?2 AND timestamp between ?3 and ?4 ";

	public static String offerSalesDayprtGranularQuery = "select * from calls_reporting.callx_sales_athena where offer_id =?1 AND hour(end_time)=?2 AND timestamp between ?3 and ?4;";
	
	public static String promonumberSalesDaypartGranularQuery = "select * from calls_reporting.callx_sales_athena where promo_number =?1 AND hour(end_time)=?2 AND timestamp between ?3 and ?4;";

	public static String advertiserSalesDaypartGranularQuery = "select * from calls_reporting.callx_sales_athena where advertiser_id =?1 AND hour(end_time)=?2 AND timestamp between ?3 and ?4";
	
	public static String publisherSalesDaypartGranularQuery = "select * from calls_reporting.callx_sales_athena where publisher_id =?1 AND hour(end_time)=?2 AND timestamp between ?3 and ?4";
	
	public static String campaignByPublisherSalesDaypartGranularQuery = "select * from calls_reporting.callx_sales_athena where campaign_id =?1 AND publisher_id=?2 AND hour(end_time)=?3 AND timestamp between ?4 and ?5 ";
	
	public static String offerByPublisherSalesDaypartGranularQuery = "select * from calls_reporting.callx_sales_athena where offer_id =?1 AND publisher_id=?2 AND hour(end_time)=?3 AND timestamp between ?4 and ?5";
	
	public static String offerByPromonumberSalesDaypartGranularQuery = "select * from calls_reporting.callx_sales_athena where offer_id =?1 AND promo_number =?2 AND hour(end_time)=?3 AND timestamp between ?4 and ?5";
	
	
	
}
