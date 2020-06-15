package net.kuper.jftp.server.config;

//import com.sun.jdi.VMOutOfMemoryException;

import lombok.extern.slf4j.Slf4j;
import net.kuper.jftp.server.exception.ResException;
import net.kuper.jftp.server.message.Res;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ResException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Res handleRRException(ResException ex) {
        log.error(ex.getMessage(), ex);
        return new Res().error().msg(ex.getMessage());
    }

    //其他错误
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Order(1000)
    public Res handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new Res().error().msg(ex.getMessage());
    }
}
