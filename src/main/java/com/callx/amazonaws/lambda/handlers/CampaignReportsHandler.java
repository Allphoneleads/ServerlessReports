package com.callx.amazonaws.lambda.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.amazonaws.lambda.dto.GeneralReportDTO;
import com.callx.amazonaws.lambda.util.AthenaQuerysList;
import com.callx.amazonaws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.amazonaws.lambda.util.JDBCConnection;
import com.callx.amazonaws.lambda.util.ResultSetMapper;

public class CampaignReportsHandler implements RequestHandler<Request, List<GeneralReportDTO>> {

	@Override
	public List<GeneralReportDTO> handleRequest(Request input, Context context) {
		
		context.getLogger().log("Input from Campaign Reports Handler: " + input);

		
		Connection conn = null;
		List<GeneralReportDTO> results = new ArrayList<>();
		try {
			
			conn  = JDBCConnection.getConnection();
			if(conn != null) {
				
				Statement statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<GeneralReportDTO> resultSetMapper = new ResultSetMapper<GeneralReportDTO>();
				
				Object[] calenArray = CallXDateTimeConverterUtil.getArryOfCalObjects(input.getRef(),input.getRefFrom(),input.getRefTo());
				long from = CallXDateTimeConverterUtil.getLongDate(calenArray[0].toString());
				long to = CallXDateTimeConverterUtil.getLongDate(calenArray[1].toString());

				String query = AthenaQuerysList.CAMPAIGN.replace("?1", String.valueOf(from));
				query = query.replace("?2", String.valueOf(to));
				
				System.out.println("Executing Query : "+query);
				
				ResultSet rs1 = statement.executeQuery(query);
				results = resultSetMapper.mapRersultSetToObject(rs1, GeneralReportDTO.class);
				// print out the list retrieved from database
				if(results != null){
					System.out.println("Size of the CampaignReportsHandler : "+results.size());
				}
			}
			
		}catch(Exception e) {
			context.getLogger().log("Some error in CampaignReportsHandler : " + e.getMessage());
		}
		
		return results;

	}

}
