package com.callx.sales.lambda.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.aws.athena.querys.SalesCountQuerysList;
import com.callx.aws.athena.querys.StaticReports;
import com.callx.aws.lambda.dto.SaleCountReportDTO;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;
import com.callx.calls.lambda.handlers.Request;

public class SaleCountHandler implements RequestHandler<Request, List<SaleCountReportDTO>> {

	@Override
	public List<SaleCountReportDTO> handleRequest(Request input, Context context) {
		
		
		context.getLogger().log("Input from SaleCount Reports Handler : " + input+"\n");
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		List<SaleCountReportDTO> finalResults = new ArrayList<>();

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
				
					query = SalesCountQuerysList.campaignSalesQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");

				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_CAMPAIGNSBYPUBLISHER)) {
					
					query = SalesCountQuerysList.campaignByPublisherSalesQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					
				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFER)) {
					
					query = SalesCountQuerysList.offerSalesQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
				
				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFERSBYPUBLISHER)) {
					
					query = SalesCountQuerysList.offerByPublisherSalesQuery.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					
				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_PROMONUMBER)) {
					
					query = SalesCountQuerysList.promoNumber.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					
				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_OFFERBYPROMONUMBER)) {
					
					query = SalesCountQuerysList.offerByPromoNumber.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					
				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_ADVERTISER)) {
					
					query = SalesCountQuerysList.advertiser.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
					
				}else if(input.getReportType().equalsIgnoreCase(StaticReports.SALE_TYPE_PUBLISHER)) {
					
					query = SalesCountQuerysList.publisher.replace("?1", "'"+dateRange[0]+"'").replace("?2",  "'"+dateRange[1]+"'");
				}
				context.getLogger().log("======= Before Executing the Query ================"+"\n");
				context.getLogger().log(query);
				
				rs = statement.executeQuery(query);
				finalResults = resultSetMapper.mapRersultSetToObject(rs, SaleCountReportDTO.class);
			}
			
		}catch(Exception e) {
			context.getLogger().log(" Error in SaleCountHandler ");
			e.printStackTrace();
		}
		
		
		
		return finalResults;
	}

}
