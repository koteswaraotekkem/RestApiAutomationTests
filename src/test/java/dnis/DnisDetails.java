package dnis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DnisDetails {
    public Id id;
    public StoreId storeId;
    public ArrayList<Object> storeProperties;
    public String tenant;

    public DnisDetails() {
        this.id = new Id();
        this.storeId = new StoreId();
        this.storeProperties = new ArrayList<>();
    }
}