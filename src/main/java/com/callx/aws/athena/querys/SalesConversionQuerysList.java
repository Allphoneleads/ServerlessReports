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
}
