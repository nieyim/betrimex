package com.example.betrimex.service.implementation;

import com.example.betrimex.model.dto.response.DashboardStatResponse;
import com.example.betrimex.repository.ProductRepository;
import com.example.betrimex.repository.QrDataRepository;
import com.example.betrimex.repository.UserRepository;
import com.example.betrimex.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImp implements DashboardService {

    private final ProductRepository productRepository;
    private final QrDataRepository  qrDataRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardStatResponse getDashboardStat() {

        long totalProductToday = productRepository.countProductsCreatedToday();
        long totalProductQuantity = productRepository.sumAllQuantity();
        long totalQRScan = qrDataRepository.count();
        long totalUser = userRepository.count();

        DashboardStatResponse response = new DashboardStatResponse();
        response.setTotalProductQuantity((int) totalProductQuantity);
        response.setTotalProductToday((int) totalProductToday);
        response.setTotalQRScan((int) totalQRScan);
        response.setTotalUser((int) totalUser);

        return response;
    }
}
