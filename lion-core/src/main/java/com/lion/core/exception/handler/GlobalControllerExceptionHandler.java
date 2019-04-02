package com.lion.core.exception.handler;

import com.lion.core.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Lianhong
 * @since 2018-08-21
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    /**
     * 500 Internal Server Error 异常捕获
     *
     * @param e Exception
     * @return RestServiceError
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestServiceError handleValidationException(Exception e) {
        logger.error("系统异常：", e);
        return RestServiceError.build(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "系统异常。");
    }

    /**
     * 500 Internal Server Error 自定义异常捕获
     *
     * @param e ApplicationException
     * @return RestServiceError
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public RestServiceError handleValidationException(ApplicationException e) {
        logger.error(String.format("业务异常：%s", e.getCode()), e);
        return RestServiceError.build(e.getCode(), e.getMessage());
    }

}
