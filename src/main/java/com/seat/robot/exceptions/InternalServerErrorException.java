package com.seat.robot.exceptions;

import java.util.Arrays;
import org.springframework.http.HttpStatus;
import com.seat.robot.dto.ErrorDto;

public class InternalServerErrorException extends RobotException {
  private static final long serialVersionUID = -6870732210014274010L;

  public InternalServerErrorException(final String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
  }

  public InternalServerErrorException(final String message, final ErrorDto data) {
    super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, Arrays.asList(data));
  }
}
