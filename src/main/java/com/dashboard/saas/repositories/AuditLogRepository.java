package com.dashboard.saas.repositories;

import com.dashboard.saas.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {

    List<AuditLog> findByUserIdOrderByCreatedAtDesc(Long userId);

}
