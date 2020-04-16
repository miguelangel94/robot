package com.seat.robot.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.seat.robot.response.RobotResponse;

@ControllerAdvice
@SuppressWarnings({"rawtypes", "unchecked"})
public class RobotRestExceptionHandler {

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public RobotResponse unhandledErrors(HttpServletRequest req, Exception ex) {
    return new RobotResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
  }

  @ExceptionHandler({RobotException.class})
  @ResponseBody
  public RobotResponse handleLmException(final HttpServletRequest request,
      final HttpServletResponse response, final RobotException ex) {
    response.setStatus(ex.getCode());
    return new RobotResponse("ERROR", String.valueOf(ex.getCode()), ex.getMessage(),
        ex.getErrorList());
  }
}
