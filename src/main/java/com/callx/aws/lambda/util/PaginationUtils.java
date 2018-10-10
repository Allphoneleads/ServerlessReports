package com.callx.aws.lambda.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.DbUtils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.GeneralReportDTO;
import com.callx.calls.lambda.handlers.Request;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

public class PaginationUtils {



	public static CallXReportsResponseDTO<List<GeneralReportDTO>> createFolderAndResultsFile(String query, Request input, Context context, CallXReportsResponseDTO<List<GeneralReportDTO>> response) {

		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			//Get the MD5 value from the String.
			System.out.println(" Query String  : "+query+"\n");
			String md5Hex = DigestUtils.md5Hex(query);
			System.out.println(" MD5HEX Value = "+md5Hex+"\n");

			AmazonS3 s3Client =  getS3ClientObject(context);
			List<S3ObjectSummary> summaries = new ArrayList<S3ObjectSummary>();
			String fileName = "";
			summaries  = getObjectSummaries(summaries, md5Hex, context);

			// If the Object/Folder is already existed, then fetch the results from the file.
			if (!summaries.isEmpty()) {
				for(S3ObjectSummary obj : summaries) {
					//First get the File Name and check for the Last modified date (hour < TTL)
					System.out.println(" ====== Object Name ====== :"+obj.getKey());
					if(obj.getKey().split("/")[1].endsWith(".csv")) {
						fileName = obj.getKey().split("/")[1];
						boolean timemOrQueryNotMatched = true;
						long diff = new Date().getTime() - obj.getLastModified().getTime();
						System.out.println(" Created Time Diffrence : "+ diff / (60 * 1000) % 60);
						if((new Date().getTime() - obj.getLastModified().getTime()) / (60 * 1000) % 60 <= 2) {
							System.out.println(" Files Created time is Less than TTL.");
							// Check for the Query String value
							String existedQuery = s3Client.getObjectAsString(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, HeaderColumnUtils.queryFileName);
							if(existedQuery.equalsIgnoreCase(query)) {
								System.out.println("Both Query's are matching.");
								S3Object object = s3Client.getObject(new GetObjectRequest(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, fileName));
								response.setData(getBeansListFromCSV(object, fileName, input,context, true,md5Hex));
								timemOrQueryNotMatched = false;
							}else {
								System.out.println("Query String is not Matching.");
								timemOrQueryNotMatched = true;
							}
							// Get the file and Read the data.
						}
						if(timemOrQueryNotMatched){
							System.out.println(" TTL is greater than an Hour or Query String are not Matching");
							// If the TTL > 1HR then remove the old file and upload new file.
							//Deleting the old files.
							s3Client.deleteObject(new DeleteObjectRequest(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, fileName));
							s3Client.deleteObject(new DeleteObjectRequest(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, fileName+".metadata"));
							s3Client.deleteObject(new DeleteObjectRequest(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, HeaderColumnUtils.queryFileName));
							System.out.println("Old files are Deleted");

							fileName = runQueryAndUploadFile(md5Hex, conn, query, s3Client, statement, rs ,	summaries, fileName, context);
							S3Object object = s3Client.getObject(new GetObjectRequest(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, fileName));
							response.setData(getBeansListFromCSV(object, fileName, input,context, true,md5Hex));
							System.out.println(" After creating the new files and getting the latest data ");
						}

						break;
					}
					System.out.println("========= File Name :"+fileName);
				}

				// Create a new Folder and file with results.
			}else {

				fileName = runQueryAndUploadFile(md5Hex, conn, query, s3Client, statement, rs ,	summaries, fileName, context);
				// After get the file name, get the object and feth the results data.
				S3Object object = s3Client.getObject(new GetObjectRequest(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, fileName));
				// Save the List of Objects into Response and send back.
				response.setData(getBeansListFromCSV(object, fileName, input,context, false, md5Hex));
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


	private static String runQueryAndUploadFile(String md5Hex, Connection conn, String query, AmazonS3 s3Client, Statement statement, ResultSet rs ,
			List<S3ObjectSummary> summaries, String fileName, Context context) {

		try {
			System.out.println("==========  Specified Folder is not existed ========");
			conn  = JDBCConnection.getConnection(md5Hex);
			if(conn != null) {
				statement = conn.createStatement();
				rs = statement.executeQuery(query);
				System.out.println("==========  After executed the query ==============");
			}
			//Create a new .TXT file and save the query into it.
			System.out.println("From put Object :"+s3Client.putObject(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, HeaderColumnUtils.queryFileName, query));

			//Get the Object from the bucket and fetch the results.
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
			return fileName;
		}catch(Exception e) {
			System.out.println("Error in createFolderAndResultsFile : " + e.getMessage());
			context.getLogger().log("Error in createFolderAndResultsFile : " + e.getMessage());
			context.getLogger().log("Error in createFolderAndResultsFile : " + e);
		}

		return null;
	}


	private static List<GeneralReportDTO> getBeansListFromCSV(S3Object object, String fileName, Request input, Context context, boolean nextPage, String md5Hex){

		try {

			System.out.println(" From getBeansListFromCSV function Call.");

			ColumnPositionMappingStrategy<GeneralReportDTO> mappingStrategy = new ColumnPositionMappingStrategy<GeneralReportDTO>();
			mappingStrategy.setType(GeneralReportDTO.class);

			mappingStrategy.setColumnMapping(HeaderColumnUtils.granularReportHeaderColumns);

			CsvToBean<GeneralReportDTO> ctb = new CsvToBean<GeneralReportDTO>();

			//Get the total number of lines.
			int lines  = getLineNumbersFromS3Object(md5Hex,fileName,context);
			System.out.println("No of Lines Count = "+lines);

			int pagesCount = (lines-1)/input.getPageSize();
			if(pagesCount != 0 && pagesCount*input.getPageSize() < lines -1)
				pagesCount = pagesCount + 1;
			System.out.println("Number of pages : "+pagesCount);

			List<GeneralReportDTO> reportsList = new ArrayList<GeneralReportDTO>();
			int count = 0, firstLine =0, limit = 0;
			String line = "";
			S3ObjectInputStream stream = object.getObjectContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			if(!nextPage) {
				while((line = reader.readLine()) != null) {
					if(firstLine == 0) {
						firstLine = firstLine + 1;
						continue;
					}
					count = count + 1;
					if(count <= input.getPageSize()) {
						List<GeneralReportDTO> dto = ctb.parse(mappingStrategy,new StringReader(line));
						reportsList.add(dto.get(0));

					}else {
						System.out.println("Before exist from the loop :"+count+" Size : "+reportsList.size());
						break;
					}
				}
			}else {
				while((line = reader.readLine()) != null) {
					if(firstLine == 0) {
						firstLine = firstLine + 1;
						continue;
					}
					count = count + 1;
					if(count > (input.getPageNumber() * input.getPageSize())) {
						List<GeneralReportDTO> dto = ctb.parse(mappingStrategy,new StringReader(line));
						reportsList.add(dto.get(0));
						limit = limit+1;
						if(limit == input.getPageSize() ) {
							System.out.println("Before exiting from the Loop :"+limit);
							break;
						}
					}
				}
			}
			reader.close();
			return reportsList;
		}catch(Exception e) {
			System.out.println("Error in getBeansListFromCSV : " + e.getMessage());
			context.getLogger().log("Error in getS3ClientObject : " + e.getMessage());
		}
		return null;
	}
	private static int getLineNumbersFromS3Object(String md5Hex, String fileName,Context context) {

		try {
			System.out.println("From getLineNumbersFromS3Object ");
			AmazonS3 s3Client = getS3ClientObject(context);
			S3Object object = s3Client.getObject(new GetObjectRequest(System.getenv("S3_BUCKET_NAME")+"/"+md5Hex, fileName));
			LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(object.getObjectContent()));
			lineNumberReader.skip(Long.MAX_VALUE);
			return lineNumberReader.getLineNumber();

		}catch(Exception e) {
			System.out.println("Error in getLineNumbersFromS3Object : " + e.getMessage());
			context.getLogger().log("Error in getLineNumbersFromS3Object : " + e.getMessage());
		}
		return 0;
	}


	private static List<S3ObjectSummary> getObjectSummaries(List<S3ObjectSummary> summaries, String md5Hex,Context context) {

		try {
			System.out.println(" From getObjectSummaries function call");
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(System.getenv("S3_BUCKET_NAME")).withPrefix(md5Hex + "/");

			AmazonS3 s3Client = getS3ClientObject(context);
			ObjectListing listing = s3Client.listObjects(listObjectsRequest);
			summaries = listing.getObjectSummaries();

			while (listing.isTruncated()) {
				listing = s3Client.listNextBatchOfObjects(listing);
				summaries.addAll(listing.getObjectSummaries());
			}
			return summaries;
		}catch(Exception e) {
			System.out.println("Error in getObjectSummaries : " + e.getMessage());
			context.getLogger().log("Error in getObjectSummaries : " + e.getMessage());
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
		}
		return null;
	}


	public static String creaeFileAndGetURL(String query, Request input,Context context) {
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String fileURL = "";
		
		try {
			System.out.println(" Query String  : "+query+"\n");
			String md5Hex = DigestUtils.md5Hex(query);
			System.out.println(" MD5HEX Value = "+md5Hex+"\n");

			AmazonS3 s3Client =  getS3ClientObject(context);
			List<S3ObjectSummary> summaries = new ArrayList<S3ObjectSummary>();
			summaries  = getObjectSummaries(summaries, md5Hex, context);
			fileURL = runQueryAndUploadFile(md5Hex, conn, query, s3Client, statement, rs ,summaries, fileURL, context);
			System.out.println(" File URL : "+"https://s3-us-west-2.amazonaws.com/"+System.getenv("S3_BUCKET_NAME")+"/"+md5Hex+fileURL);
			return "https://s3-us-west-2.amazonaws.com/"+System.getenv("S3_BUCKET_NAME")+"/"+md5Hex+fileURL;
		}catch(Exception e) {
				System.out.println("Error in getS3ClientObject : " + e.getMessage());
				context.getLogger().log("Error in getS3ClientObject : " + e.getMessage());
			}finally {
				DbUtils.closeQuietly(rs);
				DbUtils.closeQuietly(statement);
				DbUtils.closeQuietly(conn);
			}
	
		return null;
	}






}
