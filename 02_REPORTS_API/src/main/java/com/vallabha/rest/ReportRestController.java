package com.vallabha.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.vallabha.request.RequestInfo;
import com.vallabha.response.ResponseInfo;
import com.vallabha.service.EligibilityService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReportRestController {

	@Autowired
	private EligibilityService eligibilityService;
	
	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlans()
	{
		List<String> uniquePlanNames = eligibilityService.getUniquePlanNames();
		return new ResponseEntity<>(uniquePlanNames, HttpStatus.OK);
	}
	
	@GetMapping("/planstatuses")
	public ResponseEntity<List<String>> getPlanStatuses()
	{
		List<String> uniquePlanStatues = eligibilityService.getUniquePlanStatues();
		return new ResponseEntity<>(uniquePlanStatues, HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<ResponseInfo>> search(@RequestBody RequestInfo requestInfo)
	{
		List<ResponseInfo> responseList = eligibilityService.search(requestInfo);
		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws IOException
	{
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.xls";
		
		response.addHeader(headerKey, headerValue);
		
		eligibilityService.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException
	{
		response.setContentType("application/pdf");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=data.pdf";
		
		response.setHeader(headerKey, headerValue);
		
		eligibilityService.generatePdf(response);
	}
}
