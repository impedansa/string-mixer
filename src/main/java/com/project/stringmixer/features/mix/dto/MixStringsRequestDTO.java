package com.project.stringmixer.features.mix.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MixStringsRequestDTO {
    @NotNull
    private String first;
    @NotNull
    private String second;
}
