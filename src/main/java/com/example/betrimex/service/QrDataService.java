package com.example.betrimex.service;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface QrDataService {
    void saveQrData(String qrTextJson) throws IOException, WriterException;
}
