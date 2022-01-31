package ru.otus.dsokolov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.dsokolov.domain.Author;
import ru.otus.dsokolov.dto.AuthorDto;
import ru.otus.dsokolov.repository.AuthorRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public String authorsPage(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("authorId", 1);

        return "authorsList";
    }

    @GetMapping("/edit")
    public String editAuthorPage(@RequestParam("id") long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("author", author);

        return "authorEdit";
    }

    @Validated
    @PostMapping("/edit")
    public String saveAuthor(@Valid @ModelAttribute("author") AuthorDto authorDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "authorEdit";
        }
        authorRepository.save(authorDto.toDomainObject());

        return "redirect:/authors";
    }
}
