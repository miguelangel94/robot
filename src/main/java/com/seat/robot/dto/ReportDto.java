package com.seat.robot.dto;

import java.io.Serializable;


public class ReportDto implements Serializable {

  private static final long serialVersionUID = 180802329613616000L;

  private Long id;

  private Long time;

  private double latitude;

  private double longitude;

  private int metersFromOrigin;

  private int pm;

  private String source;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public int getMetersFromOrigin() {
    return metersFromOrigin;
  }

  public void setMetersFromOrigin(int metersFromOrigin) {
    this.metersFromOrigin = metersFromOrigin;
  }

  public int getPm() {
    return pm;
  }

  public void setPm(int pm) {
    this.pm = pm;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

}
