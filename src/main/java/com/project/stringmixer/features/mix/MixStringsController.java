package com.project.stringmixer.features.mix;

import com.project.stringmixer.features.mix.dto.MixStringsRequestDTO;
import com.project.stringmixer.features.mix.dto.MixStringsResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mix")
public class MixStringsController {
    private final MixStringsService mixStringsService;

    MixStringsController(MixStringsService mixStringsService) {
        this.mixStringsService = mixStringsService;
    }

    @PostMapping
    MixStringsResponseDTO mixStrings(@Valid @RequestBody MixStringsRequestDTO requestDTO) {
        String result = this.mixStringsService.mix(requestDTO.getFirst(), requestDTO.getSecond());
        return new MixStringsResponseDTO(result);
    }
}
