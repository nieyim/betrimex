package com.example.betrimex.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class AuditLog extends Auditable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "action")
    private String action;

    @Column(name = "resource")
    private String resource;

    @Column(name = "details")
    private String details;

    @Column(name = "status")
    private String status;

    @Column(name = "current_json")
    private String currentJson;

    @Column(name = "new_json")
    private String newJson;

    @Column(name = "external_response_json")
    private String externalResponseJson;
}
