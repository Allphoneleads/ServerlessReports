package com.callx.calls.lambda.handlers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.aws.athena.querys.DynamicQuerysList;
import com.callx.aws.athena.querys.GeneralQuerysList;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;

public class KeyPressHandler implements RequestHandler<Request, List<GeneralReportDTO>> {

	@Override
	public List<GeneralReportDTO> handleRequest(Request input, Context context) {
		
		context.getLogger().log("Input from KeyPress Handler: " + input+"\n");

		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		
		List<GeneralReportDTO> results = new ArrayList<>();
		List<GeneralReportDTO> keyPressReportList = new ArrayList<GeneralReportDTO>();
		try {
			conn  = JDBCConnection.getConnection();
			if(conn != null) {
				
				statement = conn.createStatement();
				// Get the result set from the Athena
				ResultSetMapper<GeneralReportDTO> resultSetMapper = new ResultSetMapper<GeneralReportDTO>();
				
				String[] dateRange = CallXDateTimeConverterUtil.getDateRange(input, context);
				
				String query = DynamicQuerysList.KEY_PRESS.replace("?1", dateRange[0]).replace("?2", dateRange[1]);
				
				System.out.println("Executing Query@@@ : "+query);
				
				rs = statement.executeQuery(query);
				results = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				// print out the list retrieved from database
				if(results != null){
					context.getLogger().log("Size of the KeyPress : "+results.size());
				}
				
				// Convert the data properly 
				Map<Integer, Integer> campaignMapData = new HashMap<Integer, Integer>();

				for (GeneralReportDTO report : results) {
					if (campaignMapData.containsKey(report.getCampaign_id())) {
						campaignMapData.put(report.getCampaign_id(),
								campaignMapData.get(report.getCampaign_id()) + report.getKey_calls());
					} else {
						report.setTotal_calls(new BigDecimal(report.getKey_calls()));
						campaignMapData.put(report.getCampaign_id(), report.getKey_calls());
					}
				}

				
				
				for (GeneralReportDTO report : results) {

					GeneralReportDTO keypressReportDto = new GeneralReportDTO();

					keypressReportDto.setCampaign_id(report.getCampaign_id());
					keypressReportDto.setCampaign_name(report.getCampaign_name());
					keypressReportDto.setFilter_name(report.getFilter_name());
					keypressReportDto.setAction(report.getAction());
					keypressReportDto.setTotal_calls(new BigDecimal(campaignMapData.get(report.getCampaign_id())));
					keypressReportDto.setProcessed_ivr_keys(report.getProcessed_ivr_keys());

					keypressReportDto.setKey_calls(report.getKey_calls());
					keypressReportDto.setPaid_calls(report.getPaid_calls());
					keypressReportDto.setTotal_revenue(report.getTotal_revenue());

					BigDecimal percentageKeyPressCalls = (new BigDecimal(report.getKey_calls()))
							.divide(new BigDecimal(campaignMapData.get(report.getCampaign_id())), 2, RoundingMode.FLOOR);
					BigDecimal percentagePaidCalls = (report.getPaid_calls())
							.divide(new BigDecimal(campaignMapData.get(report.getCampaign_id())), 2, RoundingMode.FLOOR);
					BigDecimal percentagePaidVsKeyPress = (report.getPaid_calls())
							.divide(new BigDecimal(report.getKey_calls()), 2, RoundingMode.FLOOR);
					BigDecimal avgRpk = report.getTotal_revenue().divide(new BigDecimal(report.getKey_calls()), 2,
							RoundingMode.FLOOR);

					keypressReportDto.setKeypress_in_percentage(percentageKeyPressCalls);
					keypressReportDto.setPaidCalls_in_percentage(percentagePaidCalls);
					keypressReportDto.setPaid_keypress_rate(percentagePaidVsKeyPress);
					keypressReportDto.setAvg_rpk(avgRpk);
					int avgCallDuration = report.getTotal_duration() / report.getKey_calls();
					keypressReportDto.setAvg_call_duration(CallXDateTimeConverterUtil.convertSecondsToTimeFormat(avgCallDuration));

					keyPressReportList.add(keypressReportDto);

				}
			}
			
		}catch(Exception e) {
			context.getLogger().log("Some error in KeyPressHandler : " + e.getMessage());
		}finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(statement);
		    DbUtils.closeQuietly(conn);
		}
		
		return keyPressReportList;

	}
}
