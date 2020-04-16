package com.seat.robot.report.service.impl;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.model.LatLng;
import com.seat.robot.enums.PollutionEnum;
import com.seat.robot.exceptions.RobotException;
import com.seat.robot.report.entity.Report;
import com.seat.robot.report.json.LocationRest;
import com.seat.robot.report.json.ReportRest;
import com.seat.robot.report.repository.ReportRepository;
import com.seat.robot.report.service.ReportService;
import com.seat.robot.utils.Calculation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

  @Autowired
  private ReportRepository reportRepository;


  @Override
  public ReportRest createReport() throws RobotException {
    final Report report = new Report();
    report.setTime(getCurrentTime().getTime());
    report.setPm(Calculation.getRandomPmNumber());
    return createReportRest(reportRepository.save(report));
  }

  private ReportRest createReportRest(final Report report) {
    final ReportRest reportRest = new ReportRest();
    reportRest.setLevel(String.valueOf(report.getPm()));
    reportRest.setTimestamp(getCurrentTime().getTime());
    return reportRest;
  }

  private Timestamp getCurrentTime() {
    return new Timestamp(System.currentTimeMillis());
  }

  @Override
  public void printReport(LatLng position) {
    final List<Report> reportList = reportRepository.findAll();
    final ReportRest reportRest = new ReportRest();
    final LocationRest locationRest = new LocationRest();

    reportRest.setLevel(String
        .valueOf(PollutionEnum.getPollutionByInt(Calculation.calculateAvgPollution(reportList))));
    locationRest.setLat(position.lat);
    locationRest.setLng(position.lng);
    reportRest.setLocation(locationRest);
    reportRest.setTimestamp(getCurrentTime().getTime());
    final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    log.info(gson.toJson(reportRest));
    reportRepository.deleteAll();
  }
}
