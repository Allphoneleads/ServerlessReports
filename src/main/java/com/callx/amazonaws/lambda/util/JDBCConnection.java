package com.callx.amazonaws.lambda.util;

import java.sql.Connection;

import com.simba.athena.jdbc.DataSource;

public class JDBCConnection {

	
	public static Connection getConnection() {
		try {
			Connection conn = null;
			Class.forName("com.simba.athena.jdbc.Driver");

			DataSource ds = new com.simba.athena.jdbc.DataSource();
			
			String CONNECTION_URL = "jdbc:awsathena://AwsRegion=us-west-2;UID="+System.getenv("ATHENA_USERNAME")
					+";PWD="+System.getenv("ATHENA_PASSWORD")+";S3OutputLocation=s3://aws-athena-query-results-899952997311-us-west-2/test_athena_query_results/";
			
			ds.setURL(CONNECTION_URL);
			conn = ds.getConnection();
			return conn;
			
		}catch(Exception e) {
		}
		return null;
	}
	
}
