package com.callx.sales.lambda.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.aws.athena.querys.SalesConversionQuerysList;
import com.callx.aws.athena.querys.StaticReports;
import com.callx.aws.lambda.dto.SalesGranularReportDTO;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;
import com.callx.calls.lambda.handlers.Request;


public class SalesGranularHandler implements RequestHandler<Request, List<SalesGranularReportDTO>> {

	@Override
	public List<SalesGranularReportDTO> handleRequest(Request input, Context context) {

		context.getLogger().log("From SaleGranular  Reports Handler : " + input+"\n");

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		List<SalesGranularReportDTO> finalResults = new ArrayList<>();

		try {
			conn  = JDBCConnection.getConnection();
			if(conn != null) {

				statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<SalesGranularReportDTO> resultSetMapper = new ResultSetMapper<SalesGranularReportDTO>();

				//String[] dateRange = CallXDateTimeConverterUtil.getDateRange(input, context);
				Object[] dateRange = CallXDateTimeConverterUtil.getArryOfCalObjects(input.getRef(), input.getRefFrom(), input.getRefTo());
				String query = "";
				context.getLogger().log("Min Date Range ###### : "+dateRange[0].toString() +" Max Date Range : "+dateRange[1].toString() );

				if (input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_CAMPAIGN)) {
					
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.campaignSalesGeoGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getState()+"'")
								                                                       .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.campaignSalesDaypartGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getHour()+"'")
																						   .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.campaignSalesGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+dateRange[0]+"'").replace("?3",  "'"+dateRange[1]+"'");
					}
				
				}else if (input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFER)) {
					
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.offerSalesGeoGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getState()+"'")
								                                                    .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.offerSalesDayprtGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getId()+"'")
								                                                       .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.offerSalesGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+dateRange[0]+"'").replace("?3",  "'"+dateRange[1]+"'");
					}
					
				}else if (input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_PROMONUMBER)) {
					
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.promonumberSalesGeoGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getState()+"'")
								                                                          .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.promonumberSalesDaypartGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getId()+"'")
								                                                              .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.promonumberSalesGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+dateRange[0]+"'").replace("?3",  "'"+dateRange[1]+"'");
					}
				
				}else if (input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_ADVERTISER)) {
					
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.advertiserSalesGeoGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getState()+"'")
								                                                         .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.advertiserSalesDaypartGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getId()+"'")
								                                                             .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.advertiserSalesGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+dateRange[0]+"'").replace("?3",  "'"+dateRange[1]+"'");
					}
				
				}else if (input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_PUBLISHER)) {
					
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.publisherSalesGeoGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getState()+"'")
								                                                         .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.publisherSalesDaypartGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+input.getId()+"'")
								                                                             .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.publisherSalesGranularQuery.replace("?1", "'"+input.getId()+"'").replace("?2", "'"+dateRange[0]+"'").replace("?3",  "'"+dateRange[1]+"'");
					}
					
				}else if (input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_CAMPAIGNSBYPUBLISHER)) {
					
					String[] parts = input.getId().split("-");
					int campaignId = 0;
					int publisherId = 0;
					if (parts.length == 2) {
						campaignId = Integer.parseInt(parts[0]);
						publisherId = Integer.parseInt(parts[1]);
					}
					
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.campaignByPublisherSalesGeoGranularQuery.replace("?1", "'"+campaignId+"'").replace("?2", "'"+publisherId+"'").replace("?3", "'"+input.getState()+"'")
								                                                               .replace("?4", "'"+dateRange[0]+"'").replace("?5",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.campaignByPublisherSalesDaypartGranularQuery.replace("?1", "'"+campaignId+"'").replace("?2", "'"+publisherId+"'").replace("?3", "'"+input.getHour()+"'")
							                                                                   .replace("?4", "'"+dateRange[0]+"'").replace("?5",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.campaignByPublisherSalesGranularQuery.replace("?1", "'"+campaignId+"'").replace("?2", "'"+publisherId+"'")
								                                                               .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");
					}
					
				}else if (input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFERSBYPUBLISHER)) {
					String[] parts = input.getId().split("-");
					int offerId = 0;
					int publisherId = 0;
					if (parts.length == 2) {
						offerId = Integer.parseInt(parts[0]);
						publisherId = Integer.parseInt(parts[1]);
					}
					
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.offerByPublisherSalesGeoGranularQuery.replace("?1", "'"+offerId+"'").replace("?2", "'"+publisherId+"'").replace("?3", "'"+input.getState()+"'")
								                                                               .replace("?4", "'"+dateRange[0]+"'").replace("?5",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.offerByPublisherSalesDaypartGranularQuery.replace("?1", "'"+offerId+"'").replace("?2", "'"+publisherId+"'").replace("?3", "'"+input.getHour()+"'")
							                                                                   .replace("?4", "'"+dateRange[0]+"'").replace("?5",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.offerByPublisherSalesGranularQuery.replace("?1", "'"+offerId+"'").replace("?2", "'"+publisherId+"'")
								                                                            .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");
					}
				
				}else if (input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFERBYPROMONUMBER)) {
					
					String[] parts = input.getId().split("-");
					int offerId = 0;
					String promoNumber = "";
					if (parts.length == 2) {
						offerId = Integer.parseInt(parts[0]);
						promoNumber = parts[1];
					}
					
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.offerByPromonumberSalesGeoGranularQuery.replace("?1", "'"+offerId+"'").replace("?2", "'"+promoNumber+"'").replace("?3", "'"+input.getState()+"'")
								                                                               .replace("?4", "'"+dateRange[0]+"'").replace("?5",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.offerByPromonumberSalesDaypartGranularQuery.replace("?1", "'"+offerId+"'").replace("?2", "'"+promoNumber+"'").replace("?3", "'"+input.getHour()+"'")
							                                                                   .replace("?4", "'"+dateRange[0]+"'").replace("?5",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.offerByPromonumberSalesGranularQuery.replace("?1", "'"+offerId+"'").replace("?2", "'"+promoNumber+"'")
								 .replace("?3", "'"+dateRange[0]+"'").replace("?4",  "'"+dateRange[1]+"'");
					}
					
				
				}
				
				context.getLogger().log("======= Before Executing the Query ================"+"\n");
				context.getLogger().log(query);
				
				rs = statement.executeQuery(query);
				finalResults = resultSetMapper.mapRersultSetToObject(rs, SalesGranularReportDTO.class);
				context.getLogger().log("=====================  final result count :"+finalResults.size());
				
			}
		}catch(Exception e) {
			context.getLogger().log(" Error while fetching the Sales Granular Reports : "+e.getMessage());
			e.printStackTrace();
		}
		return finalResults;

	}
}
