package com.callx.aws.lambda.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.calls.lambda.handlers.Request;
import com.google.gson.Gson;

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


	public static List<GeneralReportDTO> getFinalResulsAfterConversions(List<GeneralReportDTO> finalResults, Context context) {

		try{
			context.getLogger().log("from getFinalResulsAfterConversions ");
			for(GeneralReportDTO dto : finalResults) {
				dto.setUnique_calls(new BigDecimal(AppUtils.getStringObject(dto.getTotal_calls())).subtract(new BigDecimal(AppUtils.getStringObject(dto.getRepeat_calls()))));
				dto.setAvg_connect_duration(AppUtils.getStringObject(dto.getTotal_calls()).equals("0") ? new BigDecimal("0").toString() : new BigDecimal(AppUtils.getStringObject(dto.getConnected_duration()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getTotal_calls())), 2, RoundingMode.HALF_UP).toString());
				dto.setAvg_rpc(AppUtils.getStringObject(dto.getTotal_calls()).equals("0") ? new BigDecimal("0") : new BigDecimal(AppUtils.getStringObject(dto.getRevenue()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getTotal_calls())), 2,RoundingMode.HALF_UP));
				dto.setAvg_cpc(AppUtils.getStringObject(dto.getTotal_calls()).equals("0") ? new BigDecimal("0") : new BigDecimal(AppUtils.getStringObject(dto.getCost()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getTotal_calls())), 2,RoundingMode.HALF_UP));
				dto.setConv(AppUtils.getStringObject(dto.getTotal_calls()).equals("0") ? new BigDecimal("0") : new BigDecimal(AppUtils.getStringObject(dto.getPaid_calls()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getTotal_calls())), 2,RoundingMode.HALF_UP));
				dto.setUnique_conv(dto.getUnique_calls().compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal("0") : new BigDecimal(AppUtils.getStringObject(dto.getPaid_calls()))
						.divide(dto.getUnique_calls(), 2,RoundingMode.HALF_UP));
				dto.setAvg_connect_duration_paid_calls(AppUtils.getStringObject(dto.getPaid_calls()).equals("0") ? "0" :new BigDecimal(AppUtils.getStringObject(dto.getConnected_duration()))
						.divide(new BigDecimal(AppUtils.getStringObject(dto.getPaid_calls())), 2, RoundingMode.HALF_UP).toString());

			}
			return finalResults;
		}catch(Exception e) {
			context.getLogger().log("Some error in getFinalResulsAfterConversions : " + e.getMessage());
			context.getLogger().log("Some error in getFinalResulsAfterConversions : " + e);
			System.out.println(e);
		}
		return null;
	}


	public static CallXReportsResponseDTO<List<GeneralReportDTO>> getFirstPageResults(Request input, Context context, 
			List<GeneralReportDTO> finalResults, CallXReportsResponseDTO<List<GeneralReportDTO>> response, String fileName) {


		try {
			System.out.println("============== This is a new report request =======================");
			File file = new File("/tmp/"+fileName);
			FileWriter writer = new FileWriter(file); 
			Gson gson = new Gson();
			for(GeneralReportDTO obj : finalResults) {
				String jsonString = gson.toJson(obj);
				writer.write(jsonString+"\n");
			}
			writer.close();

			BasicAWSCredentials awsCreds = new BasicAWSCredentials(System.getenv("ATHENA_USERNAME"), System.getenv("ATHENA_PASSWORD"));
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					.build();

			System.out.println("%%%% : "+s3Client.putObject(new PutObjectRequest("lambda-fission", fileName, new File(file.toString())).withCannedAcl(CannedAccessControlList.PublicRead)));
			System.out.println("%%%%%%%%%%%%%%%   after upload the file successfully %%%%%%%%%%%%%%%%%%%%%");

			LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
			lineNumberReader.skip(Long.MAX_VALUE);
			int lines = lineNumberReader.getLineNumber();
			lineNumberReader.close();

			List<GeneralReportDTO> listDTO = new ArrayList<GeneralReportDTO>();
			String line = null;
			int count = 0;
			Gson g = new Gson(); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while ((line = reader.readLine()) != null) {
				count = count+1;
				System.out.println("Count of Line Number : "+count);
				if(count <= input.getPageSize()) {
					System.out.println("Count ::::"+count);
					GeneralReportDTO dto = g.fromJson(line, GeneralReportDTO.class);
					listDTO.add(dto);
				}else {
					System.out.println("From Else reached the count or page size  : "+count);
					break;
				}
			}
			reader.close();
			System.out.println("######## : "+listDTO.size());
			response.setData(listDTO);
			int pagesCount = lines/input.getPageSize();

			if(pagesCount != 0 && pagesCount*input.getPageSize() < lines)
				pagesCount = pagesCount + 1;

			System.out.println(" ====== Total No of Pages :"+pagesCount);
			response.setPages(pagesCount);
			response.setTotalRecords(lines);
			response.setFileName(fileName);
			
			System.out.println("File is Deleted :"+file.delete());
			
			return response;

		}catch(Exception e) {
			context.getLogger().log("Some error in First page resuts : " + e.getMessage());
			context.getLogger().log("Some error in First page resuts : " + e);
		}
		return response;
	}



	public static CallXReportsResponseDTO<List<GeneralReportDTO>> getNextPageResults(Request input, Context context, 
			List<GeneralReportDTO> finalResults, CallXReportsResponseDTO<List<GeneralReportDTO>> response, S3Object object){

		try {
			System.out.println("==========  from getNextPageResults ======"+input.getFileName());
			List<GeneralReportDTO> listDTO = new ArrayList<GeneralReportDTO>();

			S3ObjectInputStream stream = object.getObjectContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			int count = 0, limit = 0;
			Gson g = new Gson(); 
			while ((line = reader.readLine()) != null) {
				count = count+1;
				System.out.println("@@@@@@@@@@@@@@@ : "+count);
				if(count > (input.getPageNumber() * input.getPageSize())) {
					System.out.println("::::"+count);
					GeneralReportDTO dto = g.fromJson(line, GeneralReportDTO.class);
					listDTO.add(dto);
					limit = limit+1;
					if(limit == input.getPageSize() ) {
						System.out.println("Before exiting from the Loop :"+limit);
						break;
					}
				}
			}
			stream.close();
			reader.close();
			response.setData(listDTO);
			return response;
		}catch(Exception e) {
			context.getLogger().log("Some error in Next page resuts : " + e.getMessage());
			context.getLogger().log("Some error in Next page resuts : " + e);
		}
		return response;
	}
	
	/*public static int getTotalPages(Request input, Context context, AmazonS3 s3Client) {
		int pagesCount = 0;
		try {
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			S3ObjectInputStream stream = s3Client.getObject(new GetObjectRequest("lambda-fission", input.getFileName())).getObjectContent();
			LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(stream));
			lineNumberReader.skip(Long.MAX_VALUE);
			int lines = lineNumberReader.getLineNumber();
			lineNumberReader.close();
			stream.abort();
			System.out.println("$$$$$$$$$$$$$$$$$$!!!! : "+lines);
			pagesCount = lines/input.getPageSize();

			if(pagesCount != 0 && pagesCount*input.getPageSize() < lines)
				pagesCount = pagesCount + 1;

			System.out.println(" ====== Total No of Pages :"+pagesCount);
			return pagesCount;
		}catch(Exception e) {
			context.getLogger().log("Some error in Get Total Pages : " + e.getMessage());
			context.getLogger().log("Some error in Get Total Pages : " + e);

		}
		return pagesCount;

	}*/

}
