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
import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.SaleCountReportDTO;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;
import com.callx.calls.lambda.handlers.Request;

public class SaleCountHandler implements RequestHandler<Request, CallXReportsResponseDTO<List<SaleCountReportDTO>>> {

	@Override
	public CallXReportsResponseDTO<List<SaleCountReportDTO>> handleRequest(Request input, Context context) {


		context.getLogger().log("Input from SaleCount Reports Handler : " + input+"\n");

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		List<SaleCountReportDTO> finalResults = new ArrayList<>();
		CallXReportsResponseDTO<List<SaleCountReportDTO>> response = new CallXReportsResponseDTO<>();

		try {

			conn  = JDBCConnection.getConnection();
			if(conn != null) {

				statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<SaleCountReportDTO> resultSetMapper = new ResultSetMapper<SaleCountReportDTO>();

				//String[] dateRange = CallXDateTimeConverterUtil.getDateRange(input, context);
				Object[] dateRange = CallXDateTimeConverterUtil.getArryOfCalObjects(input.getRef(), input.getRefFrom(), input.getRefTo());
				String query = "";

				if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_CAMPAIGN)) {

					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.campaignSalesGeoQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.campaignSalesDaypartQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");

					}else {
						query = SalesConversionQuerysList.campaignSalesQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}



				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_CAMPAIGNSBYPUBLISHER)) {

					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.campaignByPublisherSalesGeoQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.campaignByPublisherSalesDaypartQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					
					}else {
						query = SalesConversionQuerysList.campaignByPublisherSalesQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}


				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFER)) {
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.offerSalesGeoQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.offerSalesDaypartQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}else {
						query = SalesConversionQuerysList.offerSalesQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}


				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFERSBYPUBLISHER)) {
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.offerByPublisherSalesGeoQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.offerByPublisherSalesDaypartQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}else {
						query = SalesConversionQuerysList.offerByPublisherSalesQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}


				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_PROMONUMBER)) {
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.promoNumberGeoQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.promoNumberDaypartQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}else {
						query = SalesConversionQuerysList.promoNumber.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}


				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFERBYPROMONUMBER)) {
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.offerByPromoNumberGeoQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.offerByPromoNumberDaypartQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}else {
						query = SalesConversionQuerysList.offerByPromoNumber.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}


				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_ADVERTISER)) {
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.advertiserGeoQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.advertiserDaypartQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}else {
						query = SalesConversionQuerysList.advertiser.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}


				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_PUBLISHER)) {
					if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = SalesConversionQuerysList.publisherGeoQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");

					}else if(input.getFilterType() != null && input.getFilterType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = SalesConversionQuerysList.publisherDaypartQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}else {
						query = SalesConversionQuerysList.publisher.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					}

				}
				context.getLogger().log("======= Before Executing the Query ================"+"\n");
				context.getLogger().log(query);

				rs = statement.executeQuery(query);
				finalResults = resultSetMapper.mapRersultSetToObject(rs, SaleCountReportDTO.class);

				if(finalResults.isEmpty()){
					response.setStatusCode(200);
					response.setTitle("no data");
					response.setStatus("no data");
				}else{
					response.setStatusCode(200);
					response.setTitle("success");
					response.setStatus("success");
				}

				response.setData(finalResults);	
			}
			return response;

		}catch(Exception e) {
			context.getLogger().log(" Error in SaleCountHandler ");
			e.printStackTrace();
		}

		return response;
	}

}
