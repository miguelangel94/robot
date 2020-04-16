package com.seat.robot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Getter
@Component
public class ConfigValues {
  @Value("${polyline}")
  private String polyline;
  private final Double velocity = Math.random() * 3;

}
