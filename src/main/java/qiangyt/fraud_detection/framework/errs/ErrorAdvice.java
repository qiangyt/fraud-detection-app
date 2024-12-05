/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package qiangyt.fraud_detection.framework.errs;

import com.fasterxml.jackson.core.JacksonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import qiangyt.fraud_detection.framework.misc.ExceptionHelper;
import qiangyt.fraud_detection.framework.misc.StringHelper;
import qiangyt.fraud_detection.framework.misc.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Intercept and handle exceptions thrown by RestController. Output the thrown exceptions as HTTP
 * response body.
 */
@lombok.extern.slf4j.Slf4j
@lombok.Getter
@lombok.Setter
@ControllerAdvice // (annotations = {RestController.class})
public class ErrorAdvice {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleError(Throwable ex, HttpServletRequest req) {
        var err = normalizeError(ex);
        var status = err.getStatus();
        if (err.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error(ex.getMessage(), ex);
        } else {
            log.warn(ex.getMessage(), ex);
        }

        return new ResponseEntity<>(err.toResponse(req.getRequestURI()), status);
    }

    public static BaseError normalizeError(Throwable ex) {
        ex = ExceptionHelper.getRootCause(ex);

        if (ex instanceof BaseError) {
            return ((BaseError) ex);
        }

        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> objErrors =
                    ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            var errors = new ArrayList<String>();
            for (var objErr : objErrors) {
                var msg = objErr.getDefaultMessage();
                if (objErr instanceof FieldError) {
                    var fieldErr = (FieldError) objErr;
                    var fieldName = ValidationHelper.hackJsonFieldName(fieldErr);
                    errors.add(String.format("'%s' %s", fieldName, msg));
                } else {
                    errors.add(msg);
                }
            }

            return new BadRequest(
                    ErrorCode.PARAMETER_NOT_VALID, ex, "%s", StringHelper.join(", ", errors));
        }

        if (ex instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations =
                    ((ConstraintViolationException) ex).getConstraintViolations();

            var errors =
                    violations.stream()
                            .map(
                                    violation -> {
                                        return String.format(
                                                "%s: %s",
                                                violation.getPropertyPath(),
                                                violation.getMessage());
                                    })
                            .collect(Collectors.toList());

            return new BadRequest(
                    ErrorCode.CONSTRAINT_VIOLATION, ex, "constraint violated", errors);
        }

        if (ex instanceof HttpMessageNotReadableException) {
            var msg = ex.getMessage();
            var posOfSep = msg.indexOf(":");
            if (posOfSep >= 0) {
                msg = msg.substring(0, posOfSep);
            }
            return new BadRequest(ErrorCode.NONE, ex, msg);
        }

        if (ex instanceof BindException
                || ex instanceof MissingServletRequestParameterException
                || ex instanceof MissingServletRequestPartException
                || ex instanceof TypeMismatchException) {
            return new BadRequest(ErrorCode.NONE, ex, ex.getMessage());
        }
        if (ex instanceof JacksonException) {
            return new BadRequest(ErrorCode.NONE, ex, ex.getMessage());
        }
        if (ex instanceof IllegalArgumentException) {
            return new BadRequest(ErrorCode.NONE, ex, ex.getMessage());
        }

        if (ex instanceof HttpMediaTypeNotAcceptableException) {
            return new NotAcceptable(ex, ex.getMessage());
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return new MethodNotAllowed(ex, ex.getMessage());
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            return new UnsupportedMediaType(ex, ex.getMessage());
        }

        if (ex instanceof AsyncRequestTimeoutException) {
            return new RequestTimeout(ex, ex.getMessage());
        }

        if (ex instanceof NoHandlerFoundException) {
            return new NotFound(ErrorCode.PATH_NOT_FOUND, ex, ex.getMessage());
        }

        return new Internal(ex);
    }
}
