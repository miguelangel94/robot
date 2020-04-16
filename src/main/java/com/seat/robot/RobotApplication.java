package com.seat.robot;



import java.util.Timer;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.seat.robot.report.task.CO2MeditionTask;
import com.seat.robot.report.task.ReportingTask;
import com.seat.robot.utils.CommonConstants;
import com.seat.robot.utils.ConfigValues;

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class RobotApplication extends SpringBootServletInitializer {

  @Autowired
  private ConfigValues configValues;

  @Autowired
  private CO2MeditionTask co2MeditionTask;

  @Autowired
  private ReportingTask reportingTask;

  public static void main(String[] args) {
    SpringApplication.run(RobotApplication.class, args);

  }

  @PostConstruct
  public void startRobot() {
    final Timer reportTimer = new Timer();
    final Timer co2MeditionTimer = new Timer();
    reportTimer.scheduleAtFixedRate(reportingTask, CommonConstants.MILISECONDS_IN_15_MINUTES,
        CommonConstants.MILISECONDS_IN_15_MINUTES);
    co2MeditionTimer.scheduleAtFixedRate(co2MeditionTask, 0, getCO2MeditionTime());
  }

  private long getCO2MeditionTime() {
    return (long) (100 / configValues.getVelocity()) * 1000;
  }
}
