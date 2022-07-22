package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUser {
    @JsonProperty("name")
    private String name;
    @JsonProperty("job")
    private String job;
}
