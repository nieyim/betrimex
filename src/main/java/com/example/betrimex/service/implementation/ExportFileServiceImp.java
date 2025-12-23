package com.example.betrimex.service.implementation;

import com.example.betrimex.model.ExportType;
import com.example.betrimex.service.ExportFileService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ExportFileServiceImp implements ExportFileService {
    @Override
    public byte[] exportPdfReport(String templatePath, Map<String, Object> parameters) {
        return exportReport(templatePath, parameters, ExportType.PDF);
    }

    @Override
    public byte[] exportExcelReport(String templatePath, Map<String, Object> parameters) {
        return exportReport(templatePath, parameters, ExportType.EXCEL);
    }

    private byte[] exportReport(String templatePath, Map<String, Object> parameters, ExportType reportType) {
        try {
            var jasperReport = JasperCompileManager.compileReport(templatePath);
//            var jasperReport = (JasperReport) JRLoader.loadObject(new File(templatePath));
            var jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            var outputStream = new ByteArrayOutputStream();

            switch (reportType) {
                case ExportType.PDF -> {
                    var exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                    exporter.exportReport();
                }
                case ExportType.EXCEL -> {
                    var exporter = new JRXlsxExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

                    var reportConfig = new SimpleXlsxReportConfiguration();
                    reportConfig.setDetectCellType(true);
                    reportConfig.setCollapseRowSpan(false);
                    reportConfig.setWhitePageBackground(false);
                    reportConfig.setRemoveEmptySpaceBetweenRows(true);

                    exporter.setConfiguration(reportConfig);
                    exporter.exportReport();
                }
            }
            return outputStream.toByteArray();
        } catch (JRException e) {
            log.error("Failed to export report", e);
//            throw new DomainException(ErrorCode.EXPORT_REPORT_FAILED);
            return null;
        }
    }
}
