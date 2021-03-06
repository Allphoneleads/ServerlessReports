package com.callx.calls.lambda.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.simba.athena.amazonaws.AmazonServiceException;

public class MergeCallsToS3 implements RequestHandler<ScheduledEvent, String> {

	@Override
	public String handleRequest(ScheduledEvent text, Context arg1) {

		System.out.println("Scheduled Event Data : "+text);
		String sourceBucket = "callx-calls-athena";
		String destinationBucket = "callx-calls-athena-merged";

		/* Build S3 client object */
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
		ObjectListing listing = null;
		List<S3ObjectSummary> summaries = new ArrayList<S3ObjectSummary>();
		String prefix = "";

		for(int i=0; i < 4 ; i++ ) {
			prefix = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH").format(LocalDateTime.now().minusHours(4 - i)).toString();
			System.out.println(" Bucket Prefix : "+prefix);
			listing = s3Client.listObjects(sourceBucket, prefix);
			summaries.addAll(listing.getObjectSummaries());
			System.out.println(" Listing Value : "+listing.getBucketName()+"/"+listing.getPrefix());
		}

		try {

			/* If source S3 folder is not found */
			if (summaries.isEmpty()) {
				System.out.println("S3 folder doesn't exist or is empty");
				System.exit(1);
			}

			/* Declare output file name to be merged to */
			File file = new File("/tmp/merged-file");

			OutputStream out = new FileOutputStream(file);
			/* Ignore folder names, only pick files */
			for (S3ObjectSummary summary : summaries) {
				System.out.println("=============   Summary Iteration : "+summary.getKey()+" ===== "+summary.getBucketName());
				if (summary.getKey().endsWith("/"))
					continue;
				S3Object s3Object = s3Client.getObject(new GetObjectRequest(sourceBucket, summary.getKey()));
				InputStream in = s3Object.getObjectContent();
				byte[] buf = new byte[1024];

				int count;
				while ((count = in.read(buf)) != -1) {
					if (Thread.interrupted()) {
						throw new InterruptedException();
					}
					out.write(buf, 0, count);
				}

				in.close();
			}
			out.close();

			String dateSuffix = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm").format(LocalDateTime.now()).toString();
			
			System.out.println(" dateSuffix : "+dateSuffix);
			
			String file_path = file.toString();
			String key_name = Paths.get(file_path).getFileName().toString() + "-" + dateSuffix;
			System.out.println(" key_name : "+key_name);
			/* Copy merged file to a destination folder */
			System.out.format("Uploading %s to S3 bucket %s...\n", file, destinationBucket);
			final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
			
			s3.putObject(destinationBucket, key_name, new File(file.toString()));
			//merged_file
			System.out.println("Before uploading the copy of the same file.");
			System.out.println(" destinationBucket : "+destinationBucket+"/merged_file");
			s3.putObject(destinationBucket+"/merged_file", "final_merged_file", new File(file.toString()));
			System.out.println("After copy the same file in new folder for EMR.");

		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (FileNotFoundException e) {
			 e.printStackTrace(); 
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return "Merge and Copy complete...";
	}
	
	/*public static void main(String a[]) {
		
		System.out.println("#######################");
		String prefix = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH").format(LocalDateTime.now().minusHours(4)).toString();
		
		System.out.println("@@@@@@@@@@ : "+prefix);
		
		
	}*/
}
