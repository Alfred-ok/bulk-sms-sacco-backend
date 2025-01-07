package com.example.SwizzSoft_Sms_app.AuditTrail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {
    List<AuditTrail> findByGroupID(Integer groupID);
}
