package com.callx.sales.lambda.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;
import com.callx.calls.lambda.handlers.Request;

public class SearchHandler implements RequestHandler<Request, CallXReportsResponseDTO<List<GeneralReportDTO>>> {
	
	@Override
	public CallXReportsResponseDTO<List<GeneralReportDTO>> handleRequest(Request input, Context context) {

		context.getLogger().log("From Search Handler : " + input+"\n");
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		List<GeneralReportDTO> finalResults = new ArrayList<>();
		CallXReportsResponseDTO<List<GeneralReportDTO>> response = new CallXReportsResponseDTO<>();
		
		try {
			
			conn  = JDBCConnection.getConnection();
			if(conn != null) {
				statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<GeneralReportDTO> resultSetMapper = new ResultSetMapper<GeneralReportDTO>();
				
				context.getLogger().log(" Search Term :"+input.getSearchTerm());
				
				String query = "select * FROM calls_reporting.callx_calls_athena_merged_parquet where " 
						      +" CONCAT(coalesce(campaign_name,''),coalesce(offer_name,''),coalesce(ivr_key,''),coalesce(call_uuid,''),coalesce(to_number,''),coalesce(description,''),"
						      +" coalesce(call_type,''),coalesce(answer_type,''),coalesce(from_number,''),coalesce(from_caller_name,''),"
						      +" coalesce(from_line_type,''),coalesce(from_state,''),coalesce(from_city,''),coalesce(from_country,''),coalesce(from_zip,''),"
						      +" coalesce(hangup_cause,''),coalesce(start_time,''),coalesce(answer_time,''),coalesce(status,''),coalesce(selected_ivr_keys,''),"
						      +" coalesce(processed_ivr_keys,''),coalesce(filter_name,''),coalesce(ivr_action,''),coalesce(selected_zip_code,''),coalesce(processed_zip_code,''),"
						      +" coalesce(publisher_name,''),coalesce(advertiser_name,''),coalesce(file_url,''),coalesce(algo,''),coalesce(sms_uuid,''),"
						      +" coalesce(number_name,''),coalesce(keyword,''),coalesce(keywordmatchtype,''),coalesce(created_at,''),coalesce(updated_at,'')) LIKE '%?1%' ";
				
				query = query.replace("?1", input.getSearchTerm());
				
				context.getLogger().log("============= Before executing the Query ==============="+"\n");
				context.getLogger().log(query);
				context.getLogger().log("=======================");
				rs = statement.executeQuery(query);
				
				finalResults = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				context.getLogger().log("Before Returning Size of the Search Term Reports : "+finalResults.size());

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
			context.getLogger().log("Error while fetching the Search term results :"+e.getMessage());
			e.printStackTrace();
		}
		
		
		return response;
	}

}
