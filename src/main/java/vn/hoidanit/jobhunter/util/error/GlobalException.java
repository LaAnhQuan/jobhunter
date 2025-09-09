package vn.hoidanit.jobhunter.util.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import vn.hoidanit.jobhunter.domain.response.RestResponse;

import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalException {

    // 400 - Bad Request (input sai, request không hợp lệ)
    @ExceptionHandler({
            IllegalArgumentException.class,
            BadCredentialsException.class,
            NullPointerException.class,
            IdInvalidException.class
    })
    public ResponseEntity<RestResponse<Object>> handleBadRequest(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getMessage());
        res.setMessage("Exception occurs");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // 404 - Not Found (không tìm thấy resource)
    @ExceptionHandler({
            UsernameNotFoundException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<RestResponse<Object>> handleNotFound(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setError(ex.getMessage());
        res.setMessage("404 Not Found. URL may not exist...");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    // 400 - Validation Error (lỗi @Valid / @Validated)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Validation failed");
        res.setMessage(errors); // luôn trả về list cho đồng nhất
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // 500 - Server error (bắt tất cả còn lại)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<Object>> handleServerError(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setError(ex.getMessage());
        res.setMessage("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    @ExceptionHandler({
            StorageException.class
    })
    public ResponseEntity<RestResponse<Object>> handleFileUploadRequest(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getMessage());
        res.setMessage("Exception upload file ...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
