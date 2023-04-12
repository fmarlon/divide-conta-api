package me.frankmms.divideconta.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorDTO {

    private HttpStatus status;
    private String message;
    private List<String> causes = Collections.emptyList();

    public ErrorDTO(HttpStatus status, String message) {
        this(status, message, "");
    }

    public ErrorDTO(HttpStatus status, String message, String cause) {
        super();
        this.status = status;
        this.message = message;
        if (StringUtils.isNotBlank(cause)) {
            causes = Arrays.asList(cause);
        }
    }

    public int getStatusCode() {
        return status.value();
    }

}
