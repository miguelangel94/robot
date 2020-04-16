package com.seat.robot.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.seat.robot.report.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}
