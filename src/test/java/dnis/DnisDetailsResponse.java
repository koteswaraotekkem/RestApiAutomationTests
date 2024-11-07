package dnis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DnisDetailsResponse {
    public Id id;
    public String tenant;
    public String storeId;

    public DnisDetailsResponse() {
        this.id = new Id();

    }
}