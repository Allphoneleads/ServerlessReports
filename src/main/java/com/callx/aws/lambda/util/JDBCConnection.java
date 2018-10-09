package com.callx.aws.lambda.util;

import java.sql.Connection;
import java.util.UUID;

import com.simba.athena.jdbc.DataSource;

public class JDBCConnection {

	
	public static Connection getConnection(String  md5Hex) {
		try {
			Connection conn = null;
			Class.forName("com.simba.athena.jdbc.Driver");

			DataSource ds = new com.simba.athena.jdbc.DataSource();
			
			String CONNECTION_URL = "jdbc:awsathena://AwsRegion=us-west-2;UID="+System.getenv("ATHENA_USERNAME")
					+";PWD="+System.getenv("ATHENA_PASSWORD")+";S3OutputLocation=s3://athena-pagination-report-files/"+md5Hex;
			
			ds.setURL(CONNECTION_URL);
			conn = ds.getConnection();
			return conn;
			
		}catch(Exception e) {
		}
		return null;
	}
	
	public static Connection getConnection() {
		try {
			Connection conn = null;
			Class.forName("com.simba.athena.jdbc.Driver");

			DataSource ds = new com.simba.athena.jdbc.DataSource();
			
			String CONNECTION_URL = "jdbc:awsathena://AwsRegion=us-west-2;UID="+System.getenv("ATHENA_USERNAME")
					+";PWD="+System.getenv("ATHENA_PASSWORD")+";S3OutputLocation=s3://athena-pagination-report-files/";
			
			ds.setURL(CONNECTION_URL);
			conn = ds.getConnection();
			return conn;
			
		}catch(Exception e) {
		}
		return null;
	}
	
}
