package net.fiv.testgithubapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestGitHubApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnRepositories() throws Exception {

        String user = "F1vus";

        mockMvc.perform(get("/api/repos/{user}", user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$.[0].repositoryName").value("BorukvaInventoryBackup"))
                .andExpect(jsonPath("$.[0].ownerLogin").value("F1vus"))
                .andExpect(jsonPath("$.[0].branches[0].name").value("1.21.4"))
                .andExpect(jsonPath("$.[0].branches[0].lastCommitSha").isNotEmpty());
    }


}


