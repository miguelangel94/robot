package com.seat.robot.report.task;

import java.util.List;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.google.maps.model.LatLng;
import com.seat.robot.report.service.ReportService;
import com.seat.robot.utils.Calculation;
import com.seat.robot.utils.ConfigValues;

@Component
public class ReportingTask extends TimerTask {
  private List<LatLng> positions;
  @Autowired
  private ReportService reportService;

  @Autowired
  private ConfigValues configValues;

  private int counter = 0;

  @Override
  public void run() {
    final List<LatLng> positionsInReportTime = getPositionsInReportTime();
    if (counter < positionsInReportTime.size()) {
      reportService.printReport(positionsInReportTime.get(counter));
      counter++;
    } else {
      cancel();
    }
  }

  private List<LatLng> getPositionsInReportTime() {
    if (CollectionUtils.isEmpty(positions)) {
      positions = Calculation.getPositionsInReportTime(configValues.getVelocity(),
          configValues.getPolyline());
    }
    return positions;
  }
}
