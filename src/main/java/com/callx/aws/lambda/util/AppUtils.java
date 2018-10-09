package com.callx.aws.lambda.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.DbUtils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.calls.lambda.handlers.Request;
import com.google.gson.Gson;

import antlr.StringUtils;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

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
			System.out.println("From First Page Results fucntion call.");
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

			System.out.println("File Upload : "+s3Client.putObject(new PutObjectRequest(System.getenv("S3_BUCKET_NAME"), fileName, new File(file.toString())).withCannedAcl(CannedAccessControlList.PublicRead)));
			System.out.println("After Uploaded the file successfully.");

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
				if(count <= input.getPageSize()) {
					GeneralReportDTO dto = g.fromJson(line, GeneralReportDTO.class);
					listDTO.add(dto);
				}else {
					System.out.println("From Else reached the count or page size  : "+count);
					break;
				}
			}
			reader.close();
			System.out.println("Totla No of Objects : "+listDTO.size());
			response.setData(listDTO);
			int pagesCount = lines/input.getPageSize();

			if(pagesCount != 0 && pagesCount*input.getPageSize() < lines)
				pagesCount = pagesCount + 1;

			System.out.println("Total No of Pages :"+pagesCount);
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
			System.out.println("From Next Page Results Function Call, File Name : "+input.getFileName());
			List<GeneralReportDTO> listDTO = new ArrayList<GeneralReportDTO>();

			S3ObjectInputStream stream = object.getObjectContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			int count = 0, limit = 0;
			Gson g = new Gson(); 
			while ((line = reader.readLine()) != null) {
				count = count+1;
				if(count > (input.getPageNumber() * input.getPageSize())) {
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

	public static ReturnTypeCode getResultsFromExistedFile(Request input, Context context, List<GeneralReportDTO> finalResults, CallXReportsResponseDTO<List<GeneralReportDTO>> response) {


		ReturnTypeCode returnType = new ReturnTypeCode();
		try {

			System.out.println("File existed  Name :"+input.getFileName());

			// Get the file from S3 bucket.
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(System.getenv("ATHENA_USERNAME"), System.getenv("ATHENA_PASSWORD"));
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					.build();

			boolean  isObjectExisted = s3Client.doesObjectExist(System.getenv("S3_BUCKET_NAME"), input.getFileName());

			System.out.println("Is Object Existed in S3 Bucket :"+isObjectExisted);
			if(isObjectExisted) {
				S3Object object = s3Client.getObject(new GetObjectRequest(System.getenv("S3_BUCKET_NAME"), input.getFileName()));						
				response =AppUtils.getNextPageResults(input, context,finalResults, response, object);

				int pagesCount = input.getTotalRecords()/input.getPageSize();
				if(pagesCount != 0 && pagesCount*input.getPageSize() < input.getTotalRecords())
					pagesCount = pagesCount + 1;
				response.setPages(pagesCount);
				response.setTotalRecords(input.getTotalRecords());
				response.setFileName(input.getFileName());
				returnType.setResponse(response);

			}else {

				returnType.setObjectNotExisted(true);
			}
			return returnType;
		}catch(Exception e) {

		}
		return returnType;
	}


	public static CallXReportsResponseDTO<List<GeneralReportDTO>> getFirstPageResultsFromCSV(Request input, Context context, 
			CallXReportsResponseDTO<List<GeneralReportDTO>> response, String fileName, UUID uuid) {

		try {

			System.out.println("#########  FROM getFirstPageResultsFromCSV #############");
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(System.getenv("ATHENA_USERNAME"), System.getenv("ATHENA_PASSWORD"));
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					.build();

			List<GeneralReportDTO> listDTO = new ArrayList<GeneralReportDTO>();

			ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
			strat.setType(GeneralReportDTO.class);
			String[] columns ;

			System.out.println("###################   KEY ##########:"+System.getenv("S3_BUCKET_NAME")+"/"+uuid+"/"+fileName);

			S3Object object = s3Client.getObject(new GetObjectRequest(System.getenv("S3_BUCKET_NAME"), "/"+uuid+"/"+fileName));
			System.out.println("@@@@@@@@@@@ :"+object.getBucketName());
			S3ObjectInputStream stream = object.getObjectContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			System.out.println("############################# @@@@@@@@");
			String line = null;
			int count = 0, firstLine = 0;
			Gson g = new Gson(); 
			while ((line = reader.readLine()) != null) {
				if(firstLine == 0) {
					columns = line.split(",");
					strat.setColumnMapping(columns);
					firstLine = firstLine + 1;
					continue;
				}
				CsvToBean csv = new CsvToBean();
				String csvFilename = fileName;
				CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
				List list = csv.parse(strat, csvReader);
				count = count+1;
				System.out.println("================>>>>>"+line);
				if(count <= input.getPageSize()) {
					GeneralReportDTO dto = g.fromJson(line, GeneralReportDTO.class);
					listDTO.add(dto);
				}else {
					System.out.println("From Else reached the count or page size  : "+count);
					break;
				}
			}
			stream.close();
			reader.close();
			response.setData(listDTO);
			return response;


		}catch(Exception e) {
			System.out.println("Error in First Page Results from CSV : " + e.getMessage());
			context.getLogger().log("Error in First Page Results from CSV : " + e.getMessage());
			context.getLogger().log("Error in First Page Results from CSV : " + e);
		}
		return response;
	}


	public static CallXReportsResponseDTO<List<GeneralReportDTO>> createFolderAndResultsFile(String query, Request input, Context context, CallXReportsResponseDTO<List<GeneralReportDTO>> response) {

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			//Get the MD5 value from the String.
			System.out.println(" Query String  : "+query);
			System.out.println("\n");
			
			String md5Hex = DigestUtils.md5Hex(query);

			System.out.println("=========> MD5HEX Value : "+md5Hex+"\n");
			
			AmazonS3 s3Client =  getS3ClientObject(context);
			
			List<S3ObjectSummary> summaries = new ArrayList<S3ObjectSummary>();
			summaries  = getObjectSummaries(summaries, md5Hex, context);
			
			if (!summaries.isEmpty()) {
				for(S3ObjectSummary obj : summaries) {
					System.out.println("###########  Bucket Name :"+obj.getBucketName());
					System.out.println("@@@@@@@@@@@ Object Name :"+obj.getKey());
					
				}
				
			// Create a new Folder and file with results.
			}else {

				System.out.println("==========  Specified Folder is not existed ========");
				conn  = JDBCConnection.getConnection(md5Hex);
				if(conn != null) {
					statement = conn.createStatement();
					rs = statement.executeQuery(query);
					System.out.println("==========  After executed the query ==============");
				}
				//Create a new .TXT file and save the query into it.
				System.out.println("From put Object :"+s3Client.putObject(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, "query.txt", query));


				//Get the Object from the bucket and fetch the results.
				String fileName = "";
				summaries  = getObjectSummaries(summaries, md5Hex, context);
				if (!summaries.isEmpty()) {
					for(S3ObjectSummary obj : summaries) {
						System.out.println(" ====== Object Name ====== :"+obj.getKey());
						if(obj.getKey().split("/")[1].endsWith(".csv")) {
							fileName = obj.getKey().split("/")[1];
							break;
						}
					}
				}
				// After get the file name, get the object and feth the results data.
				S3Object object = s3Client.getObject(new GetObjectRequest(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, fileName));
				System.out.println(" File Name :"+object.getBucketName()+" :::::"+fileName);
				
	            ColumnPositionMappingStrategy<GeneralReportDTO> mappingStrategy = new ColumnPositionMappingStrategy<GeneralReportDTO>();
	            mappingStrategy.setType(GeneralReportDTO.class);
	          /* String[] columns = new String[]{"id","call_uuid","campaign_id","offerid","publisher_id","advertiser_id","to_number","from_number","offer_name","publisher_name","advertiser_name",
	            								"description","call_type","answer_type","agent_id","from_line_type","from_state","from_city","from_country","from_zip","b_leg_duration","status",
	            								"processed_ivr_keys","filter_id","duration","connected_duration","total_revenue","total_cost","total_profit","publisher_revenue","file_url","algo",
	            								"keyword","keywordmatchtype","offer_not_found","filter_name","campaign_name","selected_ivr_keys","created_at" };*/
	            
	            
	            //mappingStrategy.setColumnMapping(columns);
	            
	            CsvToBean<GeneralReportDTO> ctb = new CsvToBean<GeneralReportDTO>();
	            
				S3ObjectInputStream stream = object.getObjectContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				List<GeneralReportDTO> dtoList = new ArrayList<GeneralReportDTO>();
				String line ="";
				int count = 0, firstLine = 0;
				
				String[] columns = new String[100];
				while((line = reader.readLine()) != null) {
					if(firstLine == 0) {
						firstLine = firstLine + 1;
						System.out.println("==========>>>>>>>====== "+line);
						columns = (String[]) line.split(",");
						System.out.println("########## : "+columns.length);
						mappingStrategy.setColumnMapping(columns);
						System.out.println("=============:::: "+columns[0]+" : "+columns[1]+" : "+columns[2]);
						continue;
					}
					System.out.println("=============>>>>>>>> "+columns[0]+" : "+columns[1]+" : "+columns[2]);
					count = count + 1;
					if(count <= input.getPageSize()) {
						List<GeneralReportDTO> dto = ctb.parse(mappingStrategy,new StringReader(line));
						 System.out.println("=========== Id #####====== : "+dto.get(0).getId()+" ::: "+dto.get(0).getCampaign_id()+" ::: "+dto.get(0).getCampaign_name());
						 dtoList.add(dto.get(0));
						 
					}else {
						System.out.println("Before exist from the loop :"+count+" ::: "+dtoList.size());
						break;
					}
				}
				// Save the List of Objects into Response and send back.
				response.setData(dtoList);
			}
			return response;
			
		}catch(Exception e) {
			System.out.println("Error in createFolderAndResultsFile : " + e.getMessage());
			context.getLogger().log("Error in createFolderAndResultsFile : " + e.getMessage());
			context.getLogger().log("Error in createFolderAndResultsFile : " + e);
		}finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(conn);
		}

		return response;
	}


	private static List<S3ObjectSummary> getObjectSummaries(List<S3ObjectSummary> summaries, String md5Hex,Context context) {

		try {
			
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(System.getenv("S3_BUCKET_NAME")).withPrefix(md5Hex + "/");
			System.out.println("====  List Object request with Folder =====>>>>>.: "+listObjectsRequest.getBucketName());

			AmazonS3 s3Client = getS3ClientObject(context);
			ObjectListing listing = s3Client.listObjects(listObjectsRequest);
			summaries = listing.getObjectSummaries();

			while (listing.isTruncated()) {
				listing = s3Client.listNextBatchOfObjects(listing);
				summaries.addAll(listing.getObjectSummaries());
			}
			return summaries;
		}catch(Exception e) {
			
		}
		return null;
	}


	private static AmazonS3 getS3ClientObject(Context context) {

		try {
			BasicAWSCredentials awsCreds = new BasicAWSCredentials(System.getenv("ATHENA_USERNAME"), System.getenv("ATHENA_PASSWORD"));
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					.build();
			return s3Client;
		}catch(Exception e) {
			System.out.println("Error in getS3ClientObject : " + e.getMessage());
			context.getLogger().log("Error in getS3ClientObject : " + e.getMessage());
			context.getLogger().log("Error in getS3ClientObject : " + e);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
