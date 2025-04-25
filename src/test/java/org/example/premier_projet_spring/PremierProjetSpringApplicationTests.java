package org.example.premier_projet_spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.premier_projet_spring.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PremierProjetSpringApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "b@b.com", roles = {"CLIENT"})
    void getAllLabels_shouldBe200Ok() throws Exception {
        mvc.perform(get("/labels"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "b@b.com", roles = {"CLIENT"})
    void getProduct_shouldBe200Ok() throws Exception {
        mvc.perform(get("/product/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "b@b.com", roles = {"CLIENT"})
    void deleteProductAsClient_shouldBe403Forbidden() throws Exception {
        mvc.perform(delete("/product/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getClientWithId2_shouldNotIncludePasswordKey() throws Exception {
        mvc.perform(get("/client/2"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void getClientWithId2_shouldIncludeOnlyNeededInformations() throws Exception {
        mvc.perform(get("/client/2"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.clientNumber").exists())
        ;
    }

    @Test
    @WithUserDetails("d@d.com")
    void deleteAsSellerButNotOwner_shouldBe403Forbidden() throws Exception {
        mvc.perform(delete("/product/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("a@a.com")
    void addNewProductWithMandatoryFields_shouldBe201created() throws Exception {
        Product product = new Product();
        product.setName("test");
        product.setPrice(100f);
        product.setCode("1234567890");
        String jsonProduct = mapper.writeValueAsString(product);

        mvc.perform(post("/product")
                        .contentType("application/json")
                        .content(jsonProduct))
                .andExpect(status().isCreated());

    }

}
