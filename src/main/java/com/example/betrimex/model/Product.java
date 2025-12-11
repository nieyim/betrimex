package com.example.betrimex.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString(exclude = "qrData")
public class Product extends Auditable{
    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @Column(name = "company")
    private String company;

    @Column(name = "machine_id")
    private String machineId;

    @Column(name = "lot_id")
    private String lotId;

    @Column(name = "lot_id_detail")
    private String lotIdDetail;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "red_card_lot")
    private String redCardLot;

    @Column(name = "vehicle_plate")
    private String vehiclePlate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "count_type")
    private String countType;

    @Column(name = "is_sync")
    private Boolean isSync = false;

    @Column(name = "video_path")
    private String videoPath;

    @Column(name = "video_transfer_path")
    private String videoTransferPath;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "warehouse_staff")
    private String warehouseStaff;

    @Column(name = "status")
    private String status;

    @Column(name = "is_send_to_cloud")
    private Boolean isSendToCloud = false;

    @ManyToOne
    @JoinColumn(name = "qr_data_id")
    @JsonBackReference
    private QrData qrData;
}
