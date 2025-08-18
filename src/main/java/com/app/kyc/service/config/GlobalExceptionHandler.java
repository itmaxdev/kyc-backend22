package com.app.kyc.service.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.app.kyc.service.common.ErrorCode;
import com.app.kyc.service.common.ErrorResponse;
import com.app.kyc.service.common.FieldError;
import com.app.kyc.service.exception.ConflictExceptionCustom;
import com.app.kyc.service.exception.InvalidDataException;
import com.app.kyc.service.exception.NotFoundExceptionCustom;

/**  
* GlobalExceptionHandler.java - Intercepts all thrown Exceptions, decides the appropriate Http status, ErrorCode and message, builds and return an ErrorResponse.  
*/
@ControllerAdvice
public class GlobalExceptionHandler
{
   private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(ConstraintViolationException.class)
   @ResponseBody
   public ErrorResponse constraintViolationExceptionHandler(ConstraintViolationException ex)
   {
      ErrorResponse errorMessage = new ErrorResponse(ErrorCode.BAD_REQUEST, "Validation error");

      List<FieldError> fieldErrors = new ArrayList<>();

      for(ConstraintViolation<?> violation : ex.getConstraintViolations())
      {
         String path = violation.getPropertyPath().toString();
         String error = violation.getMessage();
         fieldErrors.add(new FieldError(path, error));
      }
      errorMessage.setFieldErrors(fieldErrors);

      log.warn(String.format("constraintViolationExceptionHandler - ErrorCode: %s - ErrorMessage: %s - FieldError: %s", errorMessage.getErrorCode(), errorMessage.getErrorMessage(),
         errorMessage.getFieldErrors()));
      return errorMessage;
   }

   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseBody
   public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex)
   {
      ErrorResponse errorMessage = new ErrorResponse(ErrorCode.BAD_REQUEST, "Validation error");

      List<FieldError> objectErrors = new ArrayList<>();
      List<FieldError> fieldErrors = new ArrayList<>();

      for(org.springframework.validation.ObjectError objectError : ex.getBindingResult().getGlobalErrors())
      {
         if(!ObjectUtils.isEmpty(objectError.getDefaultMessage()))
         {
            String path = objectError.getObjectName();
            String error = Objects.requireNonNull(objectError.getDefaultMessage()).replace("<br>", "");
            objectErrors.add(new FieldError(path, error));
         }
      }

      for(org.springframework.validation.FieldError fieldError : ex.getBindingResult().getFieldErrors())
      {
         String path = fieldError.getField();
         String error = Objects.requireNonNull(fieldError.getDefaultMessage()).replace("<br>", "");
         fieldErrors.add(new FieldError(path, error));
      }
      errorMessage.setObjectErrors(objectErrors);
      errorMessage.setFieldErrors(fieldErrors);
      log.warn(String.format("MethodArgumentNotValidException - ErrorCode: %s - ErrorMessage: %s - FieldError: %s", errorMessage.getErrorCode(), errorMessage.getErrorMessage(),
         errorMessage.getFieldErrors()));
      return errorMessage;

   }

   //   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   //   @ExceptionHandler(BadCredentialsException.class)
   //   @ResponseBody
   //   public ErrorResponse unauthorizedException(BadCredentialsException ex)
   //   {
   //      log.warn("Bad credentials", ex.getMessage());
   //      return createErrorResponse(ex.getMessage(), null, ErrorCode.BAD_CREDENTIAL);
   //   }
   //   
   //   @ResponseStatus(HttpStatus.UNAUTHORIZED)
   //   @ExceptionHandler(UsernameNotFoundException.class)
   //   @ResponseBody
   //   public ErrorResponse userNotFoundException(UsernameNotFoundException ex)
   //   {
   //      log.warn("Invalid user", ex.getMessage());
   //      return createErrorResponse(ex.getMessage(), null, ErrorCode.BAD_CREDENTIAL);
   //   }

   //   @ResponseStatus(HttpStatus.FORBIDDEN)
   //   @ExceptionHandler(AccessDeniedException.class)
   //   @ResponseBody
   //   public ErrorResponse accessdeniedException(AccessDeniedException ex)
   //   {
   //      log.warn("Access denied", ex.getMessage());
   //      return createErrorResponse(ex.getMessage(), null, ErrorCode.ACCESS_DENIED);
   //   }
   //
   //   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   //   @ExceptionHandler(Exception.class)
   //   @ResponseBody
   //   public ErrorResponse globalException(Exception ex)
   //   {
   //      log.error("BCM UnhandledException", ex);
   //      return createErrorResponse(ex.getMessage(), null, ErrorCode.ERROR);
   //   }

   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(InvalidDataException.class)
   @ResponseBody
   public ErrorResponse validationException(InvalidDataException ex)
   {
      return createErrorResponse(ex.getMessage(), ex.getErrorCode(), ErrorCode.BAD_REQUEST);
   }

   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler(NotFoundExceptionCustom.class)
   @ResponseBody
   public ErrorResponse notFoundExceptionHandler(NotFoundExceptionCustom ex)
   {
      return new ErrorResponse(ErrorCode.BAD_REQUEST, ex.getMessage());
   }

   @ResponseStatus(HttpStatus.CONFLICT)
   @ExceptionHandler(ConflictExceptionCustom.class)
   @ResponseBody
   public ErrorResponse conflictExceptionHandler(ConflictExceptionCustom ex)
   {
      return createErrorResponse(ex.getMessage(), ex.getErrorCode(), ErrorCode.BAD_REQUEST);
   }

   //   @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
   //   @ExceptionHandler(ResourcesException.class)
   //   @ResponseBody
   //   public ErrorResponse thirdPartyException(ResourcesException ex)
   //   {
   //      return createErrorResponse(ex.getMessage(), ex.getErrorCode(), ErrorCode.RESOURCES_ERROR);
   //   }
   //   
   //   @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
   //   @ExceptionHandler(ResourceAccessException.class)
   //   @ResponseBody
   //   public ErrorResponse resourceAccessException(ResourceAccessException ex)
   //   {
   //      return createErrorResponse(ex.getMessage(), null, ErrorCode.RESOURCES_ERROR);
   //   }

   private ErrorResponse createErrorResponse(String exceptionMessage, ErrorCode errorCode, ErrorCode defaultErrorCode)
   {
      if(errorCode == null)
      {
         errorCode = defaultErrorCode;
      }
      if(errorCode != null)
      {
         if(exceptionMessage == null)
         {
            exceptionMessage = errorCode.getMessage();
         }
      }

      return new ErrorResponse(errorCode, exceptionMessage);
   }
}
