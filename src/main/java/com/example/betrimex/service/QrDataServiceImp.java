package com.example.betrimex.service;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.io.IOException;

import static com.example.betrimex.model.Constants.*;


@Service
@RequiredArgsConstructor
@Log4j2
public class QrDataServiceImp implements QrDataService {

    @Override
    public void saveQrData(String qrTextJson) throws IOException, WriterException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, AUTHORIZATION_BASIC + "");
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String apiUrl = "https://dev.it-cpi002-rt.cfapps.ap10.hana.ondemand.com/http/api/shipments" + "?qrcode=" + qrTextJson.trim();

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        }
        catch (HttpClientErrorException e) {

        };

    }
}
