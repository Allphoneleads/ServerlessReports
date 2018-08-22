package com.callx.aws.athena.querys;

public class SalesCountQuerysList {

	
	
	public static String campaignSalesQuery = "select count(*) as sale_count, campaign_id, campaign_name from calls_reporting.callx_sales_athena"
			                                + " where timestamp between ?1 and ?2 group by campaign_id,campaign_name;";
	
	
	public static String campaignByPublisherSalesQuery = "select count(*) as sale_count, campaign_id,  campaign_name,publisher_id, publisher_name "
			                                           + " from calls_reporting.callx_sales_athena"
			    									   + " where timestamp between ?1 and ?2 group by campaign_id,publisher_id, campaign_name, publisher_name ";
	
	public static String offerSalesQuery = "select count(*) as sale_count, offer_id, offer_name from calls_reporting.callx_sales_athena"
										 + " where timestamp between ?1 and ?2 group by offer_id, offer_name;";
	
	public static String offerByPublisherSalesQuery = "select count(*)as sale_count, offer_id, offer_name,publisher_id,publisher_name from calls_reporting.callx_sales_athena"
													+ " where timestamp between ?1 and ?2 group by offer_id, publisher_id, offer_name, publisher_name";
	
	public static String promoNumber ="select count(*) as sale_count, promo_number from calls_reporting.callx_sales_athena"
								    + " where timestamp between ?1 and ?2 group by promo_number";
	
	
	public static String offerByPromoNumber = "select count(*) as sale_count, offer_id,  offer_name, promo_number from calls_reporting.callx_sales_athena"
											+ " where timestamp between ?1 and ?2 group by offer_id, promo_number, offer_name";
	
	public static String advertiser = "select count(*) as sale_count,advertiser_id, advertiser_name  from calls_reporting.callx_sales_athena"
									+ " where timestamp between ?1 and ?2 group by advertiser_id, advertiser_name";
	
	public static String publisher = "select count(*), publisher_id, publisher_name from calls_reporting.callx_sales_athena"
								   + " where timestamp between ?1 and ?2 group by publisher_id, publisher_name;";
	
	

}
