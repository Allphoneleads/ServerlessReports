package com.callx.amazonaws.lambda.handlers;

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
import com.callx.amazonaws.lambda.dto.GeneralReportDTO;
import com.callx.amazonaws.lambda.util.AthenaQuerysList;
import com.callx.amazonaws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.amazonaws.lambda.util.JDBCConnection;
import com.callx.amazonaws.lambda.util.ResultSetMapper;

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
				
				String query = AthenaQuerysList.KEY_PRESS.replace("?1", dateRange[0]);
				query = query.replace("?2", dateRange[1]);
				
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
					if (campaignMapData.containsKey(report.getCampaignId())) {
						campaignMapData.put(report.getCampaignId(),
								campaignMapData.get(report.getCampaignId()) + report.getKeyCalls());
					} else {
						report.setTotalCalls(new BigDecimal(report.getKeyCalls()));
						campaignMapData.put(report.getCampaignId(), report.getKeyCalls());
					}
				}

				
				
				for (GeneralReportDTO report : results) {

					GeneralReportDTO keypressReportDto = new GeneralReportDTO();

					keypressReportDto.setCampaignId(report.getCampaignId());
					keypressReportDto.setCampaignName(report.getCampaignName());
					keypressReportDto.setIvrFilter(report.getIvrFilter());
					keypressReportDto.setAction(report.getAction());
					keypressReportDto.setTotalCalls(new BigDecimal(campaignMapData.get(report.getCampaignId())));
					keypressReportDto.setKeypress(report.getKeypress());

					keypressReportDto.setKeyCalls(report.getKeyCalls());
					keypressReportDto.setPaidCalls(report.getPaidCalls());
					keypressReportDto.setRevenue(report.getRevenue());

					BigDecimal percentageKeyPressCalls = (new BigDecimal(report.getKeyCalls()))
							.divide(new BigDecimal(campaignMapData.get(report.getCampaignId())), 2, RoundingMode.FLOOR);
					BigDecimal percentagePaidCalls = (report.getPaidCalls())
							.divide(new BigDecimal(campaignMapData.get(report.getCampaignId())), 2, RoundingMode.FLOOR);
					BigDecimal percentagePaidVsKeyPress = (report.getPaidCalls())
							.divide(new BigDecimal(report.getKeyCalls()), 2, RoundingMode.FLOOR);
					BigDecimal avgRpk = report.getRevenue().divide(new BigDecimal(report.getKeyCalls()), 2,
							RoundingMode.FLOOR);

					keypressReportDto.setKeypressInPercentage(percentageKeyPressCalls);
					keypressReportDto.setPaidCallsInPercentage(percentagePaidCalls);
					keypressReportDto.setPaidKeypressRate(percentagePaidVsKeyPress);
					keypressReportDto.setAvgRpk(avgRpk);
					int avgCallDuration = report.getTotalDuration() / report.getKeyCalls();
					keypressReportDto.setAvgCallDuration(CallXDateTimeConverterUtil.convertSecondsToTimeFormat(avgCallDuration));

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
