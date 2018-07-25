package com.callx.aws.lambda.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.aws.athena.querys.DynamicQuerysList;
import com.callx.aws.athena.querys.GeneralQuerysList;
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
				if(input.getGeoType() != null) {
					if(input.getGeoType().equalsIgnoreCase(StaticReports.GEO_TYPE)) {
						query = DynamicQuerysList.getExtraColumnsBasedOnReport(StaticReports.OFFERS_GEO, context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getOfferId());
					}else if(input.getGeoType().equalsIgnoreCase(StaticReports.DAYPART)) {
						query = DynamicQuerysList.getExtraColumnsBasedOnReport(StaticReports.OFFERS_DAYPART, context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getOfferId());
					}
				}else {
					query = DynamicQuerysList.getExtraColumnsBasedOnReport(StaticReports.OFFERS, context)
							.replace("?1", dateRange[0]).replace("?2", dateRange[1]);

				}

				System.out.println("Executing Query : "+query);

				rs = statement.executeQuery(query);
				results = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				// print out the list retrieved from database
				if(results != null){
					context.getLogger().log("Size of the OffersReports : "+results.size());
					finalResults = AppUtils.getFinalResulsAfterConversions(finalResults, results, context);
					context.getLogger().log("After Conversions Size of the OffersReports : "+finalResults.size());

				}
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
