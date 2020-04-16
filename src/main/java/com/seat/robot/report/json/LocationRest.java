package com.seat.robot.report.json;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationRest implements Serializable {

  private static final long serialVersionUID = 180802329613616000L;

  private double lat;

  private double lng;
}
