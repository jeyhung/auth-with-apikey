package io.github.jeyhung.shared;

import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.valueOf;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(value = "error")
    @ResponseBody
    public ApiError error(WebRequest webRequest, HttpServletResponse response) {
        log.error("Error response {}",response.getStatus());
        HttpStatus status = valueOf(response.getStatus());
        return new ApiError(status);
    }
}
