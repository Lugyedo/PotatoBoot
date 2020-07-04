package com.potato.boot.controller.advice;

import com.potato.boot.controller.advice.exception.BizException;
import com.potato.boot.controller.advice.exception.EnumExceptionDefine;
import com.potato.boot.pojo.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * @author dehuab
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 * 　　　　　　　NoSuchMethodException,IOException,IndexOutOfBoundsException
 * 　　　　　　　以及springmvc自定义异常等，如下：
 * SpringMVC自定义异常对应的status code
 * Exception                               HTTP Status Code
 * ConversionNotSupportedException         500 (Internal Server Error)
 * HttpMessageNotWritableException         500 (Internal Server Error)
 * HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 * HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 * HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 * NoSuchRequestHandlingMethodException    404 (Not Found)
 * TypeMismatchException                   400 (Bad Request)
 * HttpMessageNotReadableException         400 (Bad Request)
 * MissingServletRequestParameterException 400 (Bad Request)
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionAdvice {
    /**
     * 业务异常
     *
     * @param request
     * @param bizException
     * @return
     */
    @ExceptionHandler(BizException.class)
    public APIResponse handleBizException(HttpServletRequest request, BizException bizException) {
        //业务异常不打堆栈
        logExceptionInfo(request, bizException);
        return APIResponse.returnFail(bizException.getErrorCode(), bizException.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public APIResponse handleBindException(HttpServletRequest request, BindException ex) {
        logExceptionInfo(request, ex);
        List<FieldError> fieldErrors = ex.getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (FieldError fieldError : fieldErrors) {
                msgBuilder.append(fieldError.getField()).append("-").append(fieldError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return APIResponse.returnFail(errorMessage);
        }
        return APIResponse.returnFail(ex.getMessage());
    }

    /**
     * 用来处理bean validation异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public APIResponse handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        logExceptionInfo(request, ex);
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return APIResponse.returnFail(errorMessage);
        }
        return APIResponse.returnFail(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public APIResponse handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        logExceptionInfo(request, ex);
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            return APIResponse.returnFail(errorMessage);
        }
        return APIResponse.returnFail(ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public APIResponse handleSQLException(HttpServletRequest request, SQLException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(EnumExceptionDefine.UNKNOW.getErrorMsg());
    }

    @ExceptionHandler(NullPointerException.class)
    public APIResponse handleNullPointerException(HttpServletRequest request, NullPointerException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(EnumExceptionDefine.UNKNOW.getErrorMsg());
    }

    @ExceptionHandler(ClassCastException.class)
    public APIResponse handleClassCastException(HttpServletRequest request, ClassCastException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(EnumExceptionDefine.UNKNOW.getErrorMsg());
    }

    @ExceptionHandler(IOException.class)
    public APIResponse handleIOException(HttpServletRequest request, IOException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(EnumExceptionDefine.UNKNOW.getErrorMsg());
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public APIResponse handleNoSuchMethodException(HttpServletRequest request, NoSuchMethodException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(EnumExceptionDefine.UNKNOW.getErrorMsg());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public APIResponse handleIndexOutOfBoundsException(HttpServletRequest request, IndexOutOfBoundsException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(EnumExceptionDefine.UNKNOW.getErrorMsg());
    }

    /**
     * 400错误
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public APIResponse handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(EnumExceptionDefine.UNKNOW.getErrorMsg());
    }

    /**
     * 400错误
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(TypeMismatchException.class)
    public APIResponse handleTypeMismatchException(HttpServletRequest request, TypeMismatchException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(400, EnumExceptionDefine.REQ_ARGUMENT_ERROR.getErrorMsg());
    }

    /**
     * 400错误
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public APIResponse handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(400, EnumExceptionDefine.REQ_ARGUMENT_ERROR.getErrorMsg());
    }

    /**
     * 405错误
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public APIResponse handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(405, EnumExceptionDefine.REQ_ARGUMENT_ERROR.getErrorMsg());
    }

    /**
     * 406错误
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public APIResponse handleHttpMediaTypeNotAcceptableException(HttpServletRequest request, HttpMediaTypeNotAcceptableException ex) {
        logExceptionInfo(request, ex);
        return APIResponse.returnFail(406, EnumExceptionDefine.REQ_ARGUMENT_ERROR.getErrorMsg());
    }

    /**
     * 其它所有异常
     *
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public APIResponse handleException(HttpServletRequest request, Exception exception) {
        logExceptionInfo(request, exception);
        return APIResponse.returnFail(exception.getMessage());
    }

    private void logExceptionInfo(HttpServletRequest request, Exception e) {
        log.error("请求出现异常RequestURL={}，ErrorMsg={}", request.getRequestURI(), e.getMessage(), e);
    }
}
