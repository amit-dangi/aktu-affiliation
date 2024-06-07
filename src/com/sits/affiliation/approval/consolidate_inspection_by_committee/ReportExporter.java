	package com.sits.affiliation.approval.consolidate_inspection_by_committee;
	import net.sf.jasperreports.engine.JasperFillManager;
	import net.sf.jasperreports.engine.JasperPrint;
	
	import java.util.HashMap;
	import java.util.Map;
	
	public class ReportExporter {
	    public static void main(String[] args) {
	        String reportFilePath = "D:/17-oct-workspace/aktu-affiliation/src/com/sits/affiliation/approval/consolidate_inspection_by_committee/affiliation_request_main_report_test.jasper"; // The compiled Jasper report file
	        String outputFilePath = "D:/17-oct-workspace/test2.pdf"; // The desired output PDF file name
	
	        // Create parameters map
	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("REPORT_FILE_NAME", "dangibhai.pdf");
	
	        try {
	            // Fill the report
	            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFilePath, parameters);
	
	            // Export the report to PDF
	            CustomPdfExporter exporter = new CustomPdfExporter();
	            exporter.exportToPdf(outputFilePath, jasperPrint);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
