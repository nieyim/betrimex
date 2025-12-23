package com.example.betrimex.service;

import java.util.Map;

public interface ExportFileService {
    byte[] exportPdfReport(String templatePath, Map<String, Object> parameters);
    byte[] exportExcelReport(String templatePath, Map<String, Object> parameters);
}
