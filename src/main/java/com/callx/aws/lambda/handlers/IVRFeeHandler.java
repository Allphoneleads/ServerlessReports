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
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.aws.lambda.util.AppUtils;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;

public class IVRFeeHandler implements RequestHandler<Request, List<GeneralReportDTO>> {

	@Override
	public List<GeneralReportDTO> handleRequest(Request input, Context context) {
		
		context.getLogger().log("Input from Offer By PromoNumbers Handler: " + input+"\n");

		
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
				String query = "";
				if(input.getReportType().equalsIgnoreCase(AppUtils.IVRFEE_REPORT_TYPE_CAMPAIGNS)) {

					query = DynamicQuerysList.IVR_FEES_CAMPAIGNS.replace("?1", dateRange[0]).replace("?2", dateRange[1]);
				
				}else if(input.getReportType().equalsIgnoreCase(AppUtils.IVRFEE_REPORT_TYPE_PROMONUMBERS)) {
				
					query = DynamicQuerysList.IVR_FEES_PROMO_NUMBERS.replace("?1", dateRange[0]).replace("?2", dateRange[1]);
				}

				System.out.println("Executing Query : "+query);

				rs = statement.executeQuery(query);
				results = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
				// print out the list retrieved from database
				if(results != null){
					context.getLogger().log("Size of the OfferByPromoNumbers : "+results.size());
				}
			}

		}catch(Exception e) {
			context.getLogger().log("Some error in OfferByPromoNumbersHandler : " + e.getMessage());
		}finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(statement);
		    DbUtils.closeQuietly(conn);
		}
		
		return results;

	}

}
