package com.dashboard.saas.service;

import com.dashboard.saas.entities.AuditLog;

import java.util.List;

public interface AuditLogService {

    void saveAuditLog(
            Long userId,
            String action,
            String description,
            String ipAddress
    );

    List<AuditLog> getUserAuditLogs(Long userId);

}
