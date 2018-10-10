package com.callx.calls.lambda.handlers;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.callx.aws.athena.querys.DynamicGranularQuerysList;
import com.callx.aws.athena.querys.DynamicQuerysList;
import com.callx.aws.athena.querys.StaticReports;
import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.aws.lambda.util.AppUtils;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.PaginationUtils;

public class CampaignsHandler implements RequestHandler<Request, CallXReportsResponseDTO<List<GeneralReportDTO>>> {


	static String containerId= "";

	@Override
	public CallXReportsResponseDTO<List<GeneralReportDTO>> handleRequest(Request input, Context context) {

		context.getLogger().log("Input from Campaign Reports Handler : [" + input+"]\n");

		CallXReportsResponseDTO<List<GeneralReportDTO>> response = new CallXReportsResponseDTO<>();

		// To make the Lambda function warm, we are invoking dummy call and exiting the function immediately.
		if(!input.getWarmEvent().isEmpty() && input.getWarmEvent() != null ) {
			System.out.println("From Keep Lambda Warm ");
			if(containerId.isEmpty())
				containerId  = context.getAwsRequestId();
			System.out.println("Container Id :"+containerId);
			response.setStatus("From Keep Lambda Warm : "+containerId);
		}else {
			// This Code will execute when there is a real time API request is hit.

			try {
					String[] dateRange = CallXDateTimeConverterUtil.getDateRange(input, context);
					String query = "";
					boolean calculateConversions = false;
					
					if(input.getReportType() != null) {
						if( input.getReportType().equalsIgnoreCase(StaticReports.GEO_TYPE)){
							calculateConversions = true;
							query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_GEO, context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());

						}else if(input.getReportType().equalsIgnoreCase(StaticReports.DAYPART)) {
							calculateConversions = true;
							query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN_DAYPART, context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());

						}else if(input.getReportType().equalsIgnoreCase(StaticReports.GRANULAR)) {
							query = DynamicGranularQuerysList.getGranularReportQuery(StaticReports.CAMPAIGN_GRANULAR, input.getFilterType(), context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId());
						}else if(input.getReportType().equalsIgnoreCase(StaticReports.STATE_GRANULAR)) {
							query = DynamicGranularQuerysList.getStateGranularReportQuery(StaticReports.CAMPAIGN_STATE_GRANULAR, input.getFilterType(), input.getState(), context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId()).replace("?4", input.getState());
						}else if(input.getReportType().equalsIgnoreCase(StaticReports.DAYPART_GRANULAR)) {
							query = DynamicGranularQuerysList.getDaypartGranularReportQuery(StaticReports.CAMPAIGN_DAYPART_GRANULAR, input.getFilterType(), input.getHour(), context)
									.replace("?1", dateRange[0]).replace("?2", dateRange[1]).replace("?3", input.getCampaignId()).replace("?4", String.valueOf(input.getHour()));
						}

					}else {
						calculateConversions = true;
						query = DynamicQuerysList.getGeneralReportQuery(StaticReports.CAMPAIGN, context)
								.replace("?1", dateRange[0]).replace("?2", dateRange[1]);
					}

					System.out.println(" Export Value =======: "+input.isExport());
					if(!input.isExport()) {
						response = PaginationUtils.createFolderAndResultsFile(query, input, context, response);	
					}else {
						System.out.println(" ==== From is Export true =======");
						String fileURL  = PaginationUtils.creaeFileAndGetURL(query, input, context);	
						response.setFileURL(fileURL);
					}

					// Other than Granular reports, we need to calculate the Avg values. 
					if(calculateConversions){
						context.getLogger().log("Before calcuating the Avg : "+response.getData().size()+"\n");
						List<GeneralReportDTO> finalResults = AppUtils.getFinalResulsAfterConversions(response.getData(), context);
						context.getLogger().log("After Conversions Size of the CampaignReports : "+finalResults.size()+"\n");
						response.setData(finalResults);
					}

					//context.getLogger().log("Before Returning Size of the CampaignReports : "+response.getData().size());
				if(response.getData().isEmpty()){
					response.setStatusCode(200);
					response.setTitle("no data");
					response.setStatus("no data");
				}else{
					response.setStatusCode(200);
					response.setTitle("success");
					response.setStatus("success");
				}
				return response;
			}catch(Exception e) {
				context.getLogger().log("Some error in CampaignReportsHandler : " + e.getMessage());
				context.getLogger().log("Some error in CampaignReportsHandler : " + e);
				e.printStackTrace();
			}
		}
		return response;
	}

}
