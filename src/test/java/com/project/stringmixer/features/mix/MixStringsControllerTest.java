package com.project.stringmixer.features.mix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.stringmixer.features.mix.dto.MixStringsRequestDTO;
import com.project.stringmixer.features.mix.dto.MixStringsResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MixStringsController.class)
public class MixStringsControllerTest {
    private static final String MIX_STRINGS_ENDPOINT = "/api/strings/mix";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MixStringsService service;

    @Test
    void testMixStringsControllerWithValidParameters() throws Exception {
        MixStringsRequestDTO requestDTO = new MixStringsRequestDTO("aaaccAAAA111   @@@", "bbbccAAAA111@@@   ");
        MixStringsResponseDTO responseDTO = new MixStringsResponseDTO("1:aaa/2:bbb/=:cc");
        when(service.mixStrings(requestDTO.getFirst(), requestDTO.getSecond())).thenReturn(responseDTO.getResult());

        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void testMixStringsControllerWithEmptyParameters() throws Exception {
        MixStringsRequestDTO requestDTO = new MixStringsRequestDTO("", "");
        MixStringsResponseDTO responseDTO = new MixStringsResponseDTO("");
        when(service.mixStrings(requestDTO.getFirst(), requestDTO.getSecond())).thenReturn(responseDTO.getResult());

        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void testMixStringsControllerWithVeryLongParameters() throws Exception {
        String s1 = "a".repeat(2000);
        String s2 = "b".repeat(1000);
        MixStringsRequestDTO requestDTO = new MixStringsRequestDTO(s1, s2);
        MixStringsResponseDTO responseDTO = new MixStringsResponseDTO("1:" + s1 + "/2:" + s2);
        when(service.mixStrings(requestDTO.getFirst(), requestDTO.getSecond())).thenReturn(responseDTO.getResult());

        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    public void testMixStringsControllerWithNullParameter() throws Exception {
        MixStringsRequestDTO requestDTO = new MixStringsRequestDTO(null, "abcd");

        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMixStringsControllerWithMissingParameter() throws Exception {
        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMixStringsControllerWithInvalidParameterFormat() throws Exception {
        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(("This is not a valid JSON")))
                .andExpect(status().isBadRequest());
    }
}
