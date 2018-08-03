package com.callx.aws.lambda.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.aws.athena.querys.DynamicGranularQuerysList;
import com.callx.aws.athena.querys.DynamicQuerysList;
import com.callx.aws.athena.querys.StaticReports;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.aws.lambda.util.AppUtils;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;

public class OffersHandler implements RequestHandler<Request, List<GeneralReportDTO>>{

	@Override
	public List<GeneralReportDTO> handleRequest(Request input, Context context) {
		
		context.getLogger().log("Input From Offers Report Handler : " + input);

		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		List<GeneralReportDTO> finalResults = new ArrayList<>();
		try {
			
			conn  = JDBCConnection.getConnection();
			if(conn != null) {

				statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<GeneralReportDTO> resultSetMapper = new ResultSetMapper<GeneralReportDTO>();

				String[] dateRange = CallXDateTimeConverterUtil.getDateRange(input, context);
				String query = "";
				boolean calculateConversions = false;
				
				if(input.getReportType() != null) {
					if(input.getReportType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						calculateConversions = true;
						query = DynamicQuerysList.getGeneralReportQuery(StaticReports.OFFERS_GEO, context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getOfferId());
					}else if(input.getReportType().equalsIgnoreCase(StaticReports.DAYPART)) {
						calculateConversions = true;
						query = DynamicQuerysList.getGeneralReportQuery(StaticReports.OFFERS_DAYPART, context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getOfferId());
					}else if(input.getReportType().equalsIgnoreCase(StaticReports.GRANULAR)) {
						query = DynamicGranularQuerysList.getGranularReportQuery(StaticReports.OFFERS_GRANULAR, input.getFilterType(), context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getOfferId());
					}else if(input.getReportType().equalsIgnoreCase(StaticReports.STATE_GRANULAR)) {
						query = DynamicGranularQuerysList.getStateGranularReportQuery(StaticReports.OFFERS_STATE_GRANULAR, input.getFilterType(),input.getState(), context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getOfferId()).replace("?4", input.getState());
					}else if(input.getReportType().equalsIgnoreCase(StaticReports.DAYPART_GRANULAR)) {
						query = DynamicGranularQuerysList.getDaypartGranularReportQuery(StaticReports.OFFERS_DAYPART_GRANULAR, input.getFilterType(),input.getHour(), context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getOfferId()).replace("?4", String.valueOf(input.getHour()));
					}
				}else {
					calculateConversions = true;
					query = DynamicQuerysList.getGeneralReportQuery(StaticReports.OFFERS, context)
							.replace("?1", dateRange[0]).replace("?2", dateRange[1]);

				}

				System.out.println("Executing Query : "+query+"\n");

				rs = statement.executeQuery(query);
				finalResults = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				// Get the Avg values. For Granular reports we don't need these values.
				if(finalResults != null && calculateConversions){
					context.getLogger().log("Size of the OffersReports : "+finalResults.size()+"\n");
					finalResults = AppUtils.getFinalResulsAfterConversions(finalResults, context);
					context.getLogger().log("After Conversions Size of the OffersReports : "+finalResults.size()+"\n");
				}
				context.getLogger().log("Before Returning Size of the OffersReports : "+finalResults.size());
			}
			
		}catch(Exception e) {
			context.getLogger().log("Some error in OffersReportsHandler : " + e.getMessage());
		}finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(statement);
		    DbUtils.closeQuietly(conn);
		}
		
		return finalResults;

	}

}
