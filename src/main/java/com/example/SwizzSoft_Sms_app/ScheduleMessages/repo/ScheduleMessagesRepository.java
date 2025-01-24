package com.example.SwizzSoft_Sms_app.ScheduleMessages.repo;

import com.example.SwizzSoft_Sms_app.Messagein.dbo.Messagein;
import com.example.SwizzSoft_Sms_app.ScheduleMessages.dbo.ScheduleMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface ScheduleMessagesRepository  extends JpaRepository<ScheduleMessages, Integer> {

    Page<ScheduleMessages> findByCode(Integer code, Pageable pageable);

    Page<ScheduleMessages> findByCodeAndAuditDateBetween(Integer code, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);


}
