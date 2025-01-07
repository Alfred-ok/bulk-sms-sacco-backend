package com.example.SwizzSoft_Sms_app.groups_members.repo;

import com.example.SwizzSoft_Sms_app.groups_members.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer> {
    // Custom query to find members by organization group code
    List<GroupMember> findByOrgGroupCode(String orgGroupCode);

    // Additional query to find members by groupId
    List<GroupMember> findByGroupId(int groupId);
}
