package com.example.SwizzSoft_Sms_app.AuditTrail;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/audit-trail")
public class AuditTrailController {

    private final AuditTrailRepository auditTrailRepository;

    public AuditTrailController(AuditTrailRepository auditTrailRepository) {
        this.auditTrailRepository = auditTrailRepository;
    }

    // Endpoint to log an audit trail entry
    @PostMapping
    public ResponseEntity<AuditTrail> logAudit(@RequestBody AuditTrail auditTrail) {
        auditTrail.setTimestamp(LocalDateTime.now()); // Automatically set the current timestamp
        AuditTrail savedAudit = auditTrailRepository.save(auditTrail);
        return ResponseEntity.ok(savedAudit);
    }

    // Endpoint to fetch all audit trail entries
    @GetMapping
    public ResponseEntity<List<AuditTrail>> getAllAuditTrails() {
        List<AuditTrail> auditTrails = auditTrailRepository.findAll();
        return ResponseEntity.ok(auditTrails);
    }


    @GetMapping("/auditGroup/{groupID}")
    public List<AuditTrail> getAuditTrailsByGroupID(@PathVariable Integer groupID) {
        return auditTrailRepository.findByGroupID(groupID);
    }


}
