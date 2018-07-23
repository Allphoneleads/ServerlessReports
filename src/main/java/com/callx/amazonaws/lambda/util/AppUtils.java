package com.callx.amazonaws.lambda.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.callx.amazonaws.lambda.dto.GeneralReportDTO;

public class AppUtils {
	
	
	//total,paid,unique,repeat,offersnotavailable
	public static String FILTER_TYPE_TOTAL = "total";
	public static String FILTER_TYPE_PAID = "paid";
	public static String FILTER_TYPE_UNIQUE = "unique";
	public static String FILTER_TYPE_REPEAT = "repeat";
	public static String FILTER_TYPE_KEYPRESS = "keypress";
	public static String FILTER_TYPE_OFFERSNOTAVAILABLE = "offersnotavailable";
	
	// Granular Export Reports Scripts Names 
	public static String CAMPAIGN_GRANULAR = "campaigns_granular";
	public static String CAMPAIGNBYPUBLISHER_GRANULAR = "campaignbypublisher_granular";
	public static String OFFERS_GRANULAR = "offers_granular";
	public static String OFFERBYPUBLISHER_GRANULAR = "offerbypublisher_granular";
	public static String PROMONUMBER_GRANULAR = "promonumber_granular";
	public static String OFFERBYPROMONUMBER_GRANULAR = "offerbypromonumber_granular";
	public static String ADVERTISER_GRANULAR = "advertiser_granular";
	public static String PUBLISHER_GRANULAR = "publisher_granular";
	public static String KEYPRESS_GRANULAR = "keypress_granular";
	
	// Geo State and Day Part Granular reports
	public static String CAMPAIGN_STATE_GRANULAR = "campaign_state_granular";
	public static String CAMPAIGN_DAYPART_GRANULAR = "campaign_daypart_granular";
	public static String CAMPAIGNBYPUBLISHER_STATE_GRANULAR = "campaignbypublisher_state_granular";
	public static String CAMPAIGNBYPUBLISHER_DAYPART_GRANULAR = "campaignbypublisher_daypart_granular";
	public static String OFFER_STATE_GRANULAR = "offer_state_granular";
	public static String OFFER_DAYPART_GRANULAR = "offer_daypart_granular";
	public static String OFFERBYPUBLISHER_STATE_GRANULAR = "offerbypublisher_state_granular";
	public static String OFFERBYPUBLISHER_DAYPART_GRANULAR = "offerbypublisher_daypart_granular";
	public static String OFFERBYPROMONUMBER_STATE_GRANULAR = "offerbypromonumber_state_granular";
	public static String OFFERBYPROMONUMBER_DAYPART_GRANULAR = "offerbypromonumber_daypart_granular";
	public static String ADVERTISER_STATE_GRANULAR = "advertiser_state_granular";
	public static String ADVERTISER_DAYPART_GRANULAR = "advertiser_daypart_granular";
	
	
	
	public static String PUBLISHER_STATE_GRANULAR = "publisher_state_granular";
	public static String PUBLISHER_DAYPART_GRANULAR = "publisher_daypart_granular";
	public static String PROMONUMBER_STATE_GRANULAR = "promonumber_state_granular";	
	public static String PROMONUMBER_DAYPART_GRANULAR = "promonumber_daypart_granular";	
	
	
	public static String IVRFEE_REPORT_TYPE_CAMPAIGNS = "campaign";
	public static String IVRFEE_REPORT_TYPE_PROMONUMBERS = "promonumber";
	
	
	public static String getStringObject(Object object){
		if(object != null){
			return object.toString();
		}else{
			return "0";
		}
	}


	public static List<GeneralReportDTO> getFinalResulsAfterConversions(List<GeneralReportDTO> finalResults,
			List<GeneralReportDTO> results, Context context) {
		
		try{
			context.getLogger().log("from getFinalResulsAfterConversions ");
			for(GeneralReportDTO dto : results) {
				dto.setUniqueCalls(new BigDecimal(AppUtils.getStringObject(dto.getTotalCalls())).subtract(new BigDecimal(AppUtils.getStringObject(dto.getRepeatCalls()))));
				dto.setAvgConnectDuration(AppUtils.getStringObject(dto.getTotalCalls()).equals("0") ? new BigDecimal("0").toString() : new BigDecimal(AppUtils.getStringObject(dto.getConnectedDuration()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getTotalCalls())), 2, RoundingMode.HALF_UP).toString());
				dto.setAvgRpc(AppUtils.getStringObject(dto.getTotalCalls()).equals("0") ? new BigDecimal("0") : new BigDecimal(AppUtils.getStringObject(dto.getRevenue()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getTotalCalls())), 2,RoundingMode.HALF_UP));
				dto.setAvgCpc(AppUtils.getStringObject(dto.getTotalCalls()).equals("0") ? new BigDecimal("0") : new BigDecimal(AppUtils.getStringObject(dto.getTotalCost()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getTotalCalls())), 2,RoundingMode.HALF_UP));
				dto.setConv(AppUtils.getStringObject(dto.getTotalCalls()).equals("0") ? new BigDecimal("0") : new BigDecimal(AppUtils.getStringObject(dto.getPaidCalls()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getTotalCalls())), 2,RoundingMode.HALF_UP));
				dto.setUniqueConv(dto.getUniqueCalls().compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal("0") : new BigDecimal(AppUtils.getStringObject(dto.getPaidCalls()))
						.divide(dto.getUniqueCalls(), 2,RoundingMode.HALF_UP));
				
				finalResults.add(dto);
			}
			return finalResults;
		}catch(Exception e) {
			context.getLogger().log("Some error in getFinalResulsAfterConversions : " + e.getMessage());
			context.getLogger().log("Some error in getFinalResulsAfterConversions : " + e);
			System.out.println(e);
		}
		return null;
	}
}
