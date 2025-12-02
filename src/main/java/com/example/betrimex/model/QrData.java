package com.example.betrimex.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString(exclude = "products")
public class QrData extends Auditable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "image_path")
    private String imagePath;

    @Column(columnDefinition = "json")
    private String data;

    @Column(name = "vehicle_card")
    private String vehicleCard;

    @Column(name = "qr_scanner")
    private String qrScanner;

    @Column(name = "factory")
    private String factory;

    @Column(name = "supplier_name_btm")
    private String supplierNameBTM;

    @Column(name = "supplier_name_htx")
    private String supplierNameHTX;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "lot_id")
    private String lotId;

    @Column(name = "lot_id_detail")
    private String lotIdDetail;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "driver_1")
    private String driver1;

    @Column(name = "driver_2")
    private String driver2;

    @Column(name = "driver_3")
    private String driver3;

    @Column(name = "citizen_id_1")
    private String citizenId1;

    @Column(name = "citizen_id_2")
    private String citizenId2;

    @Column(name = "citizen_id_3")
    private String citizenId3;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "estimated_arrival_time")
    private String estimatedArrivalTime;

    @Column(name = "note")
    private String note;

    @Column(name = "status_checkin")
    private boolean statusCheckin;

    @Column(name = "status")
    private String status;

    @Column(name = "is_send_ai")
    private boolean isSendAI;
}
