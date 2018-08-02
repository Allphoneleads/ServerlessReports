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

public class CampaignsHandler implements RequestHandler<Request, List<GeneralReportDTO>> {

	@Override
	public List<GeneralReportDTO> handleRequest(Request input, Context context) {
		
		context.getLogger().log("Input from Campaign Reports Handler From Jenkins CI/CD Test : " + input+"\n");

		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		List<GeneralReportDTO> results = new ArrayList<>();
		List<GeneralReportDTO> finalResults = new ArrayList<>();
		try {
			long time = System.currentTimeMillis();
			context.getLogger().log("Before getting the JDBC connection : "+time+"\n");
			conn  = JDBCConnection.getConnection();
			if(conn != null) {

				context.getLogger().log("After getting the JDBC connection : "+(System.currentTimeMillis() - time)+"\n");
				time = System.currentTimeMillis();
				statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<GeneralReportDTO> resultSetMapper = new ResultSetMapper<GeneralReportDTO>();

				String[] dateRange = CallXDateTimeConverterUtil.getDateRange(input, context);
				context.getLogger().log("After conversion of params : "+(System.currentTimeMillis() -  time)+"\n");

				String query = "";
				if(input.getReportType() != null) {
					if( input.getReportType().equalsIgnoreCase(StaticReports.GEO_TYPE)){
						query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_GEO, context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());
						
					}else if(input.getReportType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_DAYPART, context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());
						
					}else if(input.getReportType().equalsIgnoreCase(StaticReports.GRANULAR)) {
						
						query = DynamicGranularQuerysList.getGranularReportQuery(StaticReports.CAMPAIGN_GRANULAR, input.getFilterType(), context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());
					}
				}else {
					
					query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN, context)
							.replace("?1", dateRange[0]).replace("?2", dateRange[1]);
				}

				System.out.println("Executing Query : "+query);
				time = System.currentTimeMillis();

				rs = statement.executeQuery(query);
				context.getLogger().log("After Query Execution : "+(System.currentTimeMillis() -  time)+"\n");
				time = System.currentTimeMillis();
				results = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				context.getLogger().log("After Mapper : "+(System.currentTimeMillis() -  time)+"\n");
				// Get the Avg values. For Granular reports we don't need these values.
				if(results != null && !(input.getReportType().equalsIgnoreCase(StaticReports.GRANULAR))){
					context.getLogger().log("Size of the CampaignReports : "+results.size());
					finalResults = AppUtils.getFinalResulsAfterConversions(finalResults, results, context);
					context.getLogger().log("After Conversions Size of the CampaignReports : "+finalResults.size());

				}
			}
			
		}catch(Exception e) {
			context.getLogger().log("Some error in CampaignReportsHandler : " + e.getMessage());
			context.getLogger().log("Some error in CampaignReportsHandler : " + e);
			System.out.println(e);
		}finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(statement);
		    DbUtils.closeQuietly(conn);
		}
		return finalResults;

	}

}
