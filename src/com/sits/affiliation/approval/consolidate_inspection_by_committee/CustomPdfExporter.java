package com.sits.affiliation.approval.consolidate_inspection_by_committee;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

public class CustomPdfExporter {

    public void exportToPdf(String reportFileName, JasperPrint jasperPrint) {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportFileName));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        exporter.setConfiguration(reportConfig);

        try {
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}

