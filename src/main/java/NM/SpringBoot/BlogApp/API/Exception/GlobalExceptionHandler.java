package NM.SpringBoot.BlogApp.API.Exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import NM.SpringBoot.BlogApp.API.Response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${app.environment}")
    private String enviroment;

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> ResourceNotFound(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String trace = null;
        String ExceptionType = null;

        if(enviroment.equals("dev")){
            ExceptionType = ex.getClass().getName();
            trace = getStackTrace(ex); 
        }
        ErrorResponse errorResponse= new ErrorResponse(ex.getMessage(), 404 , ExceptionType, trace);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(InvalidPassword.class)
    public ResponseEntity<ErrorResponse> InvalidPassword(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String trace = null;
        String ExceptionType = null;

        if(enviroment.equals("dev")){
            ExceptionType = ex.getClass().getName();
            trace = getStackTrace(ex); 
        }
        ErrorResponse errorResponse= new ErrorResponse(ex.getMessage(), 400 , ExceptionType, trace);

        return new ResponseEntity<>(errorResponse, status);
    }

    private String getStackTrace(Exception ex) {
        StringBuilder trace = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            trace.append(element.toString()).append("\n");
        }
        return trace.toString();
    }
    
}
