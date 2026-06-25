package com.dashboard.saas.service;

import com.dashboard.saas.entities.AuditLog;
import com.dashboard.saas.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }


    @Override
    public void saveAuditLog(
            Long userId,
            String action,
            String description,
            String ipAddress
    ) {

        AuditLog auditLog =
                new AuditLog();

        auditLog.setUserId(userId);
        auditLog.setAction(action);
        auditLog.setDescription(description);
        auditLog.setIpAddress(ipAddress);
        auditLog.setCreatedAt(
                LocalDateTime.now()
        );

        auditLogRepository.save(
                auditLog
        );
    }

    @Override
    public List<AuditLog> getUserAuditLogs(Long userId) {
        return auditLogRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
