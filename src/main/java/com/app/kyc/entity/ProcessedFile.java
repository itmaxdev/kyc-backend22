package com.app.kyc.entity;

import com.app.kyc.service.FileStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Setter
@Getter
public class ProcessedFile {

    @Id
    @GeneratedValue
    private UUID id;

    private String operator;
    private String filename;
    private String filenameNew;
    private Integer totalRecords;
    private Integer recordsProcessed = 0;
    @Enumerated(EnumType.STRING) // 🔥 This maps enum to a readable String column
    private FileStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    @Column(columnDefinition = "TEXT")
    private String lastError;
    private LocalDateTime lastUpdated;
}