package com.callx.aws.lambda.util;

import java.util.List;

import com.callx.aws.lambda.dto.CallXReportsResponseDTO;
import com.callx.aws.lambda.dto.GeneralReportDTO;

public class ReturnTypeCode {
	
	private boolean ObjectNotExisted;
	
	private CallXReportsResponseDTO<List<GeneralReportDTO>> response;

	public boolean isObjectNotExisted() {
		return ObjectNotExisted;
	}

	public void setObjectNotExisted(boolean objectNotExisted) {
		ObjectNotExisted = objectNotExisted;
	}

	public CallXReportsResponseDTO<List<GeneralReportDTO>> getResponse() {
		return response;
	}

	public void setResponse(CallXReportsResponseDTO<List<GeneralReportDTO>> response) {
		this.response = response;
	}
	
	

}
