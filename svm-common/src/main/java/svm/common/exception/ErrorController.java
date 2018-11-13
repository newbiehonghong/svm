package svm.common.exception;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ErrorController extends BasicErrorController {

    public ErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Object ex = webRequest.getAttribute(DefaultErrorAttributes.class.getName() + ".ERROR", 0);
        String message = "基础框架异常";
        if(ex instanceof BaseRuntimeException) {
            message = ((BaseRuntimeException) ex).getMessage();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", "OK");
        map.put("message", message);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
