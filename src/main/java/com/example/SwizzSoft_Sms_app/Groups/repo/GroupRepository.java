package com.example.SwizzSoft_Sms_app.Groups.repo;

import com.example.SwizzSoft_Sms_app.Groups.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    // Custom query methods (if needed) can be added here
    List<Group> findByOrganisationGroup(String organisationGroup);

}
