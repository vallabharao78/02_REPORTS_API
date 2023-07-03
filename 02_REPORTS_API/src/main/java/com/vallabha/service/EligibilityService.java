package com.vallabha.service;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.vallabha.request.RequestInfo;
import com.vallabha.response.ResponseInfo;

import jakarta.servlet.http.HttpServletResponse;

public interface EligibilityService 
{
	public List<String> getUniquePlanNames();
	public List<String> getUniquePlanStatues();
	public List<ResponseInfo> search(RequestInfo requestInfo);
	public void generateExcel(HttpServletResponse response) throws IOException;
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException;
}
