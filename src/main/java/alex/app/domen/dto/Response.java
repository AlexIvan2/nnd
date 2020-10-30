package alex.app.domen.dto;

import alex.app.common.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Response implements Serializable {

    private String countryCode;
    private ResponseStatus status;

}
