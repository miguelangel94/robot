package com.seat.robot.report.task;

import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.seat.robot.exceptions.RobotException;
import com.seat.robot.report.service.ReportService;
import com.seat.robot.utils.Calculation;
import com.seat.robot.utils.ConfigValues;
import com.seat.robot.utils.ErrorConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CO2MeditionTask extends TimerTask {

  private int counter = 0;
  private Integer reportTimes;
  @Autowired
  private ConfigValues configValues;

  @Autowired
  private ReportService reportService;

  @Override
  public void run() {
    if (counter < getTotalReportTimes()) {
      createReport();
      counter++;
    } else {
      cancel();
    }
  }

  private void createReport() {
    try {
      reportService.createReport();
    } catch (final RobotException e) {
      log.error(ErrorConstants.PERSISTENCE_ERROR, e);
    }
  }

  private int getTotalReportTimes() {
    if (reportTimes == null) {
      reportTimes = (int) (Calculation.calculateTotalDistance(configValues.getPolyline()) / 100);
    }
    return reportTimes;
  }

}
