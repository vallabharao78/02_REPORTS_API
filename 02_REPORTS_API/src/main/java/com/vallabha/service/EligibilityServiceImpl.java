package com.vallabha.service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vallabha.entity.EligibilityDetails;
import com.vallabha.repo.EligibilityDetailsRepo;
import com.vallabha.request.RequestInfo;
import com.vallabha.response.ResponseInfo;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class EligibilityServiceImpl implements EligibilityService {
	
	@Autowired
	private EligibilityDetailsRepo eligibilityDetailsRepo;

	@Override
	public List<String> getUniquePlanNames() {
		List<String> uniquePlanNames = eligibilityDetailsRepo.getUniquePlanNames();
		return uniquePlanNames;

		/*
		 * Set<String> uniquePlans = new HashSet(); List<EligibilityDetails> entities =
		 * eligibilityDetailsRepo.findAll(); for(EligibilityDetails entity : entities) {
		 * uniquePlans.add(entity.getPlanName()); }
		 */
	}

	@Override
	public List<String> getUniquePlanStatues() {
		List<String> uniquePlanStatues = eligibilityDetailsRepo.getUniquePlanStatues();
		return uniquePlanStatues;
	}

	@Override
	public List<ResponseInfo> search(RequestInfo requestInfo) {
		List<ResponseInfo> responseInfoList = new ArrayList();

		EligibilityDetails queryBuilder = new EligibilityDetails();

		String planName = requestInfo.getPlanName();
		if (planName != null & !planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}

		String planStatus = requestInfo.getPlanStatus();
		if (planStatus != null & !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}

		LocalDate planStartDate = requestInfo.getPlanStartDate();
		if (planStartDate != null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}

		LocalDate planEndDate = requestInfo.getPlanEndDate();
		if (planEndDate != null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}

		// Query By Example Object
		Example<EligibilityDetails> example = Example.of(queryBuilder);

		List<EligibilityDetails> entities = eligibilityDetailsRepo.findAll(example);
		for (EligibilityDetails entity : entities) {
			ResponseInfo responseInfo = new ResponseInfo();
			BeanUtils.copyProperties(entity, responseInfo);
			responseInfoList.add(responseInfo);
		}
		return responseInfoList;
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws IOException {

		List<EligibilityDetails> entities = eligibilityDetailsRepo.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);

		headerRow.createCell(0).setCellValue("S.NO");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("Mobile");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");
		headerRow.createCell(5).setCellValue("PlanName");
		headerRow.createCell(6).setCellValue("PlanStatus");

		int i = 1;
		for (EligibilityDetails entity : entities) {

			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(entity.getEligId());
			row.createCell(1).setCellValue(entity.getName());
			row.createCell(2).setCellValue(entity.getMobile());
			row.createCell(3).setCellValue(entity.getGender().toString());
			row.createCell(4).setCellValue(entity.getSsn());
			row.createCell(5).setCellValue(entity.getPlanName());
			row.createCell(6).setCellValue(entity.getPlanStatus());
			i++;
		}
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {

		List<EligibilityDetails> entities = eligibilityDetailsRepo.findAll();
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(12);
		font.setColor(Color.WHITE);
		
		Paragraph paragraph = new Paragraph(" REPORTS ",font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(paragraph);
		
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1.0f, 2.8f, 3.3f, 2.5f, 3.0f, 4.0f, 4.0f });
		table.setSpacingBefore(10);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		Font fontt = FontFactory.getFont(FontFactory.HELVETICA);
		fontt.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("NAME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Mobile", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PLAN_NAME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PLAN_STATUS", font));
		table.addCell(cell);
		
		for(EligibilityDetails entity : entities)
		{
			table.addCell(entity.getEligId().toString());   // Using toString() to convert Integer to String
			table.addCell(entity.getName());
			table.addCell(String.valueOf(entity.getMobile())); // Using String.valueOf() to convert as String object
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(entity.getSsn().toString());
			table.addCell(entity.getPlanName());
			table.addCell(entity.getPlanStatus());
		}
		document.add(table);
		document.close();
	}
}
