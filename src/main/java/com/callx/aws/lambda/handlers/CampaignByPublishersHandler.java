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

public class CampaignByPublishersHandler implements RequestHandler<Request, List<GeneralReportDTO>> {

	@Override
	public List<GeneralReportDTO> handleRequest(Request input, Context context) {

		context.getLogger().log("Input from Campaign By Publishers Handler: " + input+"\n");


		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		List<GeneralReportDTO> results = new ArrayList<>();
		List<GeneralReportDTO> finalResults = new ArrayList<>();
		try {
			conn  = JDBCConnection.getConnection();
			if(conn != null) {

				statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<GeneralReportDTO> resultSetMapper = new ResultSetMapper<GeneralReportDTO>();

				String[] dateRange = CallXDateTimeConverterUtil.getDateRange(input, context);

				String query = "";
				if(input.getReportType() != null) {

					if (input.getCampPubId() != null && !input.getCampPubId().isEmpty()) {
						String[] parts = input.getCampPubId().split("-");
						if(input.getReportType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
							query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_BY_PUBLISHER_GEO, context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", parts[0]).replace("?4", parts[1]);	
						}else if(input.getReportType().equalsIgnoreCase(StaticReports.DAYPART)){
							query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_BY_PUBLISHER_DAYPART, context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", parts[0]).replace("?4", parts[1]);
						}else if(input.getReportType().equalsIgnoreCase(StaticReports.GRANULAR)){
							query = DynamicGranularQuerysList.getGranularReportQuery(StaticReports.CAMPAIGN_BY_PUBLISHER_GRANULAR, input.getFilterType(), context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", parts[0]).replace("?4", parts[1]);
						}else if(input.getReportType().equalsIgnoreCase(StaticReports.STATE_GRANULAR)){
							query = DynamicGranularQuerysList.getStateGranularReportQuery(StaticReports.CAMPAIGN_BY_PUBLISHER_STATE_GRANULAR, input.getFilterType(), input.getState(), context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", parts[0]).replace("?4", parts[1]).replace("?5", input.getState());
						}
					}
				}else {
					query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_BY_PUBLISHER, context)
							.replace("?1", dateRange[0]).replace("?2", dateRange[1]);

				}

				System.out.println("Executing Query : "+query);

				rs = statement.executeQuery(query);
				results = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				// Get the Avg values. For Granular reports we don't need these values.
				if(results != null && !(input.getReportType().contains(StaticReports.GRANULAR))){
					context.getLogger().log("Size of the CampaignByPublishers : "+results.size());
					finalResults = AppUtils.getFinalResulsAfterConversions(finalResults, results, context);
					context.getLogger().log("After Conversions Size of the CampaignByPublishers : "+finalResults.size());
				}
			}

		}catch(Exception e) {
			context.getLogger().log("Some error in CampaignByPublishers : " + e.getMessage());
		}finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(conn);
		}

		return results;

	}

}
