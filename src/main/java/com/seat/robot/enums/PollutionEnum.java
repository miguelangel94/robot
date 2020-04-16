package com.seat.robot.enums;

public enum PollutionEnum {
  GOOD, MODERATE, USG, UNHEALTHY;

  public static PollutionEnum getPollutionByInt(final int number) {

    if (number >= 0 && number <= 50) {
      return GOOD;
    } else if (number >= 51 && number <= 100) {
      return MODERATE;
    } else if (number >= 101 && number <= 150) {
      return USG;
    } else {
      return UNHEALTHY;
    }
  }
}
