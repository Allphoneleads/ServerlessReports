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

public class OffersHandler implements RequestHandler<Request, List<GeneralReportDTO>>{

	@Override
	public List<GeneralReportDTO> handleRequest(Request input, Context context) {
		
		context.getLogger().log("Input From Offers Report Handler : " + input);

		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		List<GeneralReportDTO> results = new ArrayList<>();
		try {
			
			conn  = JDBCConnection.getConnection();
			if(conn != null) {
				
				statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<GeneralReportDTO> resultSetMapper = new ResultSetMapper<GeneralReportDTO>();
				
				String[] dateRange = CallXDateTimeConverterUtil.getDateRange(input, context);
				String query = AthenaQuerysList.OFFERS.replace("?1", dateRange[0]);
				query = query.replace("?2", dateRange[1]);
				
				System.out.println("Executing Query : "+query);
				
				rs = statement.executeQuery(query);
				results = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				// print out the list retrieved from database
				if(results != null){
					System.out.println("Size of the OffersReports : "+results.size());
				}
			}
			
		}catch(Exception e) {
			context.getLogger().log("Some error in OffersReportsHandler : " + e.getMessage());
		}finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(statement);
		    DbUtils.closeQuietly(conn);
		}
		
		return results;

	}

}
