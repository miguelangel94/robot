package com.seat.robot.report.service;

import com.google.maps.model.LatLng;
import com.seat.robot.exceptions.RobotException;
import com.seat.robot.report.json.ReportRest;

public interface ReportService {

  ReportRest createReport() throws RobotException;

  void printReport(LatLng position);

}

