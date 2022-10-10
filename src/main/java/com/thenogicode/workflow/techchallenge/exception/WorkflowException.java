package com.thenogicode.workflow.techchallenge.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkflowException extends Exception {
  private final String exception;

  private HttpStatus status;
  private List<String> exceptionParameters = new ArrayList<>();

  public WorkflowException(String message, Throwable throwable) {
    super(message, throwable);
    this.exception = throwable.getClass().getName();
  }

  public WorkflowException(String exception) {
    this.exception = exception;
  }

  public WorkflowException(String message, String exception) {
    super(message);
    this.exception = exception;
  }

  public WorkflowException(HttpStatus status, String message, String exception) {
    super(message);
    this.status = status;
    this.exception = exception;
  }

  public WorkflowException(
      HttpStatus status, String message, String exception, List<String> exceptionParameters) {
    super(message);
    this.status = status;
    this.exception = exception;
    this.exceptionParameters = exceptionParameters;
  }

  public static WorkflowException exception(String message, Throwable exception) {
    return new WorkflowException(message, exception);
  }

  public static WorkflowException notFound(
      @NonNull final String message, @NonNull final String exception) {
    return new WorkflowException(HttpStatus.NOT_FOUND, message, exception);
  }

  public static WorkflowException internalServerError(
      @NonNull final String message, @NonNull final String exception) {
    return new WorkflowException(HttpStatus.INTERNAL_SERVER_ERROR, message, exception);
  }
}
