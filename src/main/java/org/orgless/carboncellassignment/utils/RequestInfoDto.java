package org.orgless.carboncellassignment.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestInfoDto {
    @NotBlank(message = "Category name can't be blank")
    private String category;

    private Integer maxEntries;
}
