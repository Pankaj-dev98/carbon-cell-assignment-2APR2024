package org.orgless.carboncellassignment.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiEntry {
    @JsonProperty("API")
    private String api;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Auth")
    private String auth;

    @JsonProperty("HTTPS")
    private Boolean https;

    @JsonProperty("Cors")
    private String cors;

    @JsonProperty("Link")
    private String link;

    @JsonProperty("Category")
    private String category;
}
