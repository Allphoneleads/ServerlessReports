package com.callx.calls.lambda.handlers;

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
import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.aws.lambda.util.AppUtils;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;
import com.callx.aws.lambda.util.ReturnTypeCode;

public class CampaignsHandler implements RequestHandler<Request, CallXReportsResponseDTO<List<GeneralReportDTO>>> {

	
	static String containerId= "";
	
	@Override
	public CallXReportsResponseDTO<List<GeneralReportDTO>> handleRequest(Request input, Context context) {
		
		context.getLogger().log("Input from Campaign Reports Handler @@@@ : " + input+"\n");

		CallXReportsResponseDTO<List<GeneralReportDTO>> response = new CallXReportsResponseDTO<>();
		
		if(!input.getWarmEvent().isEmpty() && input.getWarmEvent() != null ) {
			System.out.println("=============  from warm event =============");
			if(containerId.isEmpty())
				containerId  = context.getAwsRequestId();
			System.out.println("Container Id :"+containerId);
			response.setStatus("From Warm Event: "+containerId);
		}else {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		List<GeneralReportDTO> finalResults = new ArrayList<>();
		boolean fileNotExisted = false;
		
		try {
			// Get the input object and check for File Name. If the file is existed already, then get the data from that file.
			if(!input.getFileName().isEmpty() && input.getFileName() != null) {
				ReturnTypeCode returnObj = AppUtils.getResultsFromExistedFile(input, context, finalResults, response);
				
				if(returnObj.isObjectNotExisted()) {
					fileNotExisted = true;
				}else {
					response = returnObj.getResponse();
				}
			}

			if(input.getFileName().isEmpty() || input.getFileName() == null || fileNotExisted) {

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
					boolean calculateConversions = false;
					String fileName = "";
					if(input.getReportType() != null) {
						if( input.getReportType().equalsIgnoreCase(StaticReports.GEO_TYPE)){
							calculateConversions = true;
							query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_GEO, context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());
							fileName = StaticReports.CAMPAIGN_GEO;

						}else if(input.getReportType().equalsIgnoreCase(StaticReports.DAYPART)) {
							calculateConversions = true;
							query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_DAYPART, context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());
							fileName = StaticReports.CAMPAIGN_DAYPART;

						}else if(input.getReportType().equalsIgnoreCase(StaticReports.GRANULAR)) {
							query = DynamicGranularQuerysList.getGranularReportQuery(StaticReports.CAMPAIGN_GRANULAR, input.getFilterType(), context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());
							fileName = StaticReports.CAMPAIGN_GRANULAR;
						}else if(input.getReportType().equalsIgnoreCase(StaticReports.STATE_GRANULAR)) {
							query = DynamicGranularQuerysList.getStateGranularReportQuery(StaticReports.CAMPAIGN_STATE_GRANULAR, input.getFilterType(), input.getState(), context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId()).replace("?4", input.getState());
							fileName = StaticReports.CAMPAIGN_STATE_GRANULAR;
						}else if(input.getReportType().equalsIgnoreCase(StaticReports.DAYPART_GRANULAR)) {
							query = DynamicGranularQuerysList.getDaypartGranularReportQuery(StaticReports.CAMPAIGN_DAYPART_GRANULAR, input.getFilterType(), input.getHour(), context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId()).replace("?4", String.valueOf(input.getHour()));
							fileName = StaticReports.CAMPAIGN_DAYPART_GRANULAR;
						}

					}else {
						calculateConversions = true;
						query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN, context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]);
						fileName = StaticReports.CAMPAIGN;
					}

					System.out.println("Executing Query : "+query+"\n");
					time = System.currentTimeMillis();

					rs = statement.executeQuery(query);
					context.getLogger().log("After Query Execution : "+(System.currentTimeMillis() -  time)+"\n");
					time = System.currentTimeMillis();
					finalResults = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
					context.getLogger().log("After Mapper : "+(System.currentTimeMillis() -  time)+"\n");
					// Get the Avg values. For Granular reports we don't need these values.
					if(finalResults != null && calculateConversions){
						context.getLogger().log("Size of the CampaignReports : "+finalResults.size()+"\n");
						finalResults = AppUtils.getFinalResulsAfterConversions(finalResults, context);
						context.getLogger().log("After Conversions Size of the CampaignReports : "+finalResults.size()+"\n");

					}

					context.getLogger().log("Before Returning Size of the CampaignReports : "+finalResults.size());
					response  = AppUtils.getFirstPageResults(input, context,finalResults, response, fileName+".json");
				}
			}
			if(finalResults.isEmpty()){
				response.setStatusCode(200);
				response.setTitle("no data");
				response.setStatus("no data");
			}else{
				response.setStatusCode(200);
				response.setTitle("success");
				response.setStatus("success");
			}
			return response;
		}catch(Exception e) {
			context.getLogger().log("Some error in CampaignReportsHandler : " + e.getMessage());
			context.getLogger().log("Some error in CampaignReportsHandler : " + e);
			e.printStackTrace();
		}finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(statement);
		    DbUtils.closeQuietly(conn);
		}
		}
		return response;
	}

}
