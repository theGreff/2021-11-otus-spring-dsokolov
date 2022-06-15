package ru.otus.dsokolov.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.dsokolov.page.BookPageController;
import ru.otus.dsokolov.security.CustomUserDetailsService;
import ru.otus.dsokolov.service.AuthorServiceImpl;
import ru.otus.dsokolov.service.BookService;
import ru.otus.dsokolov.service.GenreServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookPageController.class)
public class BookPageControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorServiceImpl authorService;
    @MockBean
    private GenreServiceImpl genreService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdmin() throws Exception {
        mvc.perform(get("/book-all"))
                .andExpect(status().isOk());
    }

}
