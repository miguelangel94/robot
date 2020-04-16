package com.seat.robot.report.json;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportRest implements Serializable {

  private static final long serialVersionUID = 180802329613616000L;

  private Long timestamp;

  private Long id;

  private LocationRest location;

  private String level;

  private String source;

  @Override
  public String toString() {
    return "ReportRest [time=" + timestamp + ", location=" + location + ", level=" + level
        + ", source=" + source + "]";
  }


}
