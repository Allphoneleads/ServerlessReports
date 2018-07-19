package com.callx.amazonaws.lambda.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.amazonaws.lambda.dto.GeneralReportDTO;
import com.callx.amazonaws.lambda.util.AthenaQuerysList;
import com.callx.amazonaws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.amazonaws.lambda.util.JDBCConnection;
import com.callx.amazonaws.lambda.util.ResultSetMapper;

public class CampaignsHandler implements RequestHandler<Request, List<GeneralReportDTO>> {

	@Override
	public List<GeneralReportDTO> handleRequest(Request input, Context context) {
		
		context.getLogger().log("Input from Campaign Reports Handler: " + input+"\n");

		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		List<GeneralReportDTO> results = new ArrayList<>();
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
				
				String query = AthenaQuerysList.CAMPAIGN.replace("?1", dateRange[0]);
				query = query.replace("?2", dateRange[1]);
				
				System.out.println("Executing Query : "+query);
				time = System.currentTimeMillis();
				
				rs = statement.executeQuery(query);
				context.getLogger().log("After Query Execution : "+(System.currentTimeMillis() -  time)+"\n");
				time = System.currentTimeMillis();
				results = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				context.getLogger().log("After Mapper : "+(System.currentTimeMillis() -  time)+"\n");
				// print out the list retrieved from database
				if(results != null){
					context.getLogger().log("Size of the CampaignReports : "+results.size());
				}
			}
			
		}catch(Exception e) {
			context.getLogger().log("Some error in CampaignReportsHandler : " + e.getMessage());
		}finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(statement);
		    DbUtils.closeQuietly(conn);
		}
		
		return results;

	}

}
