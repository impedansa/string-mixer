package com.project.stringmixer.features.mix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.stringmixer.features.mix.dto.MixStringsRequestDTO;
import com.project.stringmixer.features.mix.dto.MixStringsResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MixStringsControllerIT {
    private static final String MIX_STRINGS_ENDPOINT = "/api/strings/mix";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testMixStringsControllerWithValidParameters() throws Exception {
        String s1 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
        String s2 = "my frie n d Joh n has ma n y ma n y frie n ds n&";
        String response = "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";
        MixStringsRequestDTO requestDTO = new MixStringsRequestDTO(s1, s2);
        MixStringsResponseDTO responseDTO = new MixStringsResponseDTO(response);

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

        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void testMixStringsControllerWithVeryLongParameters() throws Exception {
        String s1 = "c".repeat(1000);
        String s2 = "d".repeat(1000);
        MixStringsRequestDTO requestDTO = new MixStringsRequestDTO(s1, s2);
        MixStringsResponseDTO responseDTO = new MixStringsResponseDTO("1:" + s1 + "/2:" + s2);

        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    public void testMixStringsControllerWithNullParameter() throws Exception {
        MixStringsRequestDTO requestDTO = new MixStringsRequestDTO(null, "efgh");

        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMixStringsControllerWithMissingParameter() throws Exception {
        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"second\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMixStringsControllerWithInvalidParameterFormat() throws Exception {
        mockMvc.perform(post(MIX_STRINGS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(("Invalid JSON format")))
                .andExpect(status().isBadRequest());
    }
}
