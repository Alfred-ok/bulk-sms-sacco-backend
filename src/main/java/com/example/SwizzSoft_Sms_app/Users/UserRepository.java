package com.example.SwizzSoft_Sms_app.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AspNetUsers, String> {

    List<AspNetUsers> findAllByOrderByIdDesc();

    @Query("SELECT u FROM AspNetUsers u WHERE u.UserName = :userName")
    Optional<AspNetUsers> findByUserName(@Param("userName") String userName);

    // Fetch users by groupID
    List<AspNetUsers> findByGroupID(String groupID);


}
