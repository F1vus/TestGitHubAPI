package net.fiv.testgithubapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RouterTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnRepositories() throws Exception {

        String user = "F1vus";

        mockMvc.perform(get("/api/repos/{user}", user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(7))
                .andExpect(jsonPath("$.[0].repositoryName").value("BorukvaInventoryBackup"))
                .andExpect(jsonPath("$.[0].ownerLogin").value("F1vus"))
                .andExpect(jsonPath("$.[0].branches[0].name").value("1.21.4"))
                .andExpect(jsonPath("$.[0].branches[0].lastCommitSha").isNotEmpty());
    }

    @Test
    void thenStatus404() throws Exception {
        String user = "asijsdfgndfubdunifudiuidb21";

        mockMvc.perform(get("/api/repos/{user}", user))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("User not found"));
    }


}


