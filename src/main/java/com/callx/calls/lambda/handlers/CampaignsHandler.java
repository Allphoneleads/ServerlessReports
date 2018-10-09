package com.callx.calls.lambda.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.dbutils.DbUtils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.callx.aws.athena.querys.DynamicGranularQuerysList;
import com.callx.aws.athena.querys.DynamicQuerysList;
import com.callx.aws.athena.querys.StaticReports;
import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.aws.lambda.util.AppUtils;
import com.callx.aws.lambda.util.CallXDateTimeConverterUtil;
import com.callx.aws.lambda.util.JDBCConnection;
import com.callx.aws.lambda.util.ResultSetMapper;
import com.callx.aws.lambda.util.ReturnTypeCode;

public class CampaignsHandler implements RequestHandler<Request, CallXReportsResponseDTO<List<GeneralReportDTO>>> {

	
	static String containerId= "";
	
	@Override
	public CallXReportsResponseDTO<List<GeneralReportDTO>> handleRequest(Request input, Context context) {

		context.getLogger().log("Input from Campaign Reports Handler @@@@: " + input+"\n");

		CallXReportsResponseDTO<List<GeneralReportDTO>> response = new CallXReportsResponseDTO<>();

		if(!input.getWarmEvent().isEmpty() && input.getWarmEvent() != null ) {
			System.out.println("From Keep Lambda Warm ");
			if(containerId.isEmpty())
				containerId  = context.getAwsRequestId();
			System.out.println("Container Id :"+containerId);
			response.setStatus("From Keep Lambda Warm : "+containerId);
		}else {
			

			List<GeneralReportDTO> finalResults = new ArrayList<>();
			boolean fileNotExisted = false;

			try {
				// Get the input object and check for File Name. If the file is existed already, then get the data from that file.
				if(!input.getFileName().isEmpty() && input.getFileName() != null) {/*
					ReturnTypeCode returnObj = AppUtils.getResultsFromExistedFile(input, context, finalResults, response);

					if(returnObj.isObjectNotExisted()) {
						fileNotExisted = true;
					}else {
						response = returnObj.getResponse();
					}
				*/}

				if(input.getFileName().isEmpty() || input.getFileName() == null || fileNotExisted) {

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

						
						// Get the MD5 Code from the Query String.
						
						
						// Create the Folder URL.
						//Check the Folder URL is Existed or not.
						// IF the Folder is not existed, then create a new folder and result file./
						//Then create new .txt file and store the query into it.
						// Fetch the file and get the results based on page no and page size.
						
						response = AppUtils.createFolderAndResultsFile(query, input, context, response);
						
						// ELSE
						// If the Folder is existed, then check for the file and get the last modified date.
						
						// if the time > threshold time ( 1 hr) then the file is older.
						// Overwrite the results into new file.
						//Check the result file is there and having latest date.
						
						// --- ELSE 
						   // threshold is < 1 hr.
						      // check for the file and query string from txt file to make sure the md5 is same.
						//  get the result from there
					  	   
						
						
						/*conn  = JDBCConnection.getConnection(uuid);
						if(conn != null) {
							statement = conn.createStatement();
							// Get the result set from the Athena
							ResultSetMapper<GeneralReportDTO> resultSetMapper = new ResultSetMapper<GeneralReportDTO>();

						rs = statement.executeQuery(query);
						context.getLogger().log("After Query Execution ########## : "+(System.currentTimeMillis() -  time)+"\n");
						
						
						System.out.println("Before Processing the list of objects from the S3 Bucket and getting Date :");
						time = System.currentTimeMillis();
						
						BasicAWSCredentials awsCreds = new BasicAWSCredentials(System.getenv("ATHENA_USERNAME"), System.getenv("ATHENA_PASSWORD"));
						AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
								.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
								.build();
						
						
						 ListObjectsRequest listObjectsRequest = 
	                                new ListObjectsRequest()
	                                      .withBucketName(System.getenv("S3_BUCKET_NAME"))
	                                      .withPrefix(uuid + "/");
						 
						System.out.println("################ Bucket Name : "+System.getenv("S3_BUCKET_NAME")+"/"+uuid+"/");
						ObjectListing listing = s3Client.listObjects(listObjectsRequest);
						
						List<S3ObjectSummary> summaries = listing.getObjectSummaries();

						 Store all S3 files and folders in List summaries 
						while (listing.isTruncated()) {
							listing = s3Client.listNextBatchOfObjects(listing);
							
							summaries.addAll(listing.getObjectSummaries());

						}
						
						Map<Date, String> objectKeysMap = new HashMap<Date, String>();
						if (!summaries.isEmpty()) {
							System.out.println("S3 folder Exists");

							for(S3ObjectSummary obj : summaries) {
								System.out.println("###########  Bucket Name :"+obj.getBucketName());
								objectKeysMap.put(obj.getLastModified(), obj.getKey().split("/")[1]);
							}
						}
						
						System.out.println("########### : "+objectKeysMap.size());
						TreeMap<Date, String> sorted = new TreeMap<Date,String>(); 
						sorted.putAll(objectKeysMap);
						
						System.out.println("########### : "+sorted.lastKey());
						System.out.println("########### @@@@@@ : "+sorted.lastEntry().getValue());
						
						fileName = sorted.lastEntry().getValue().replaceAll("csv.metadata", "csv");
						
						System.out.println("After Getting the File Name ######### : "+(System.currentTimeMillis() -  time)+"\n");
						System.out.println("########### File Name ############: "+fileName);	
						
						
						response  = AppUtils.getFirstPageResultsFromCSV(input, context,response, fileName, uuid);
						
						System.out.println("============================================");*/
						
						
						//finalResults = resultSetMapper.mapRersultSetToObject(rs, GeneralReportDTO.class);
						//context.getLogger().log("After Mapper : "+(System.currentTimeMillis() -  time)+"\n");
						// Get the Avg values. For Granular reports we don't need these values.
						/*if(finalResults != null && calculateConversions){
							context.getLogger().log("Size of the CampaignReports : "+finalResults.size()+"\n");
							finalResults = AppUtils.getFinalResulsAfterConversions(finalResults, context);
							context.getLogger().log("After Conversions Size of the CampaignReports : "+finalResults.size()+"\n");

						}

						context.getLogger().log("Before Returning Size of the CampaignReports : "+finalResults.size());
						response  = AppUtils.getFirstPageResults(input, context,finalResults, response, fileName+".json");
					}*/
				}
				if(finalResults.isEmpty()){
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
