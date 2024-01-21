package com.project.stringmixer.features.mix.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MixStringsRequestDTO {
    @NotNull
    private String first;
    @NotNull
    private String second;
}
