package com.spotify.oath2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    public InnerError getError() {
        return error;
    }

    public void setError(InnerError error) {
        this.error = error;
    }

    @JsonProperty("error")
    private InnerError error;
}
