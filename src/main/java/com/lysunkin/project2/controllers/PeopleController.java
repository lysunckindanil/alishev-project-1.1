package com.lysunkin.project2.controllers;


import com.lysunkin.project2.DAO.BookDAO;
import com.lysunkin.project2.DAO.PersonDAO;
import com.lysunkin.project2.models.Person;
import com.lysunkin.project2.util.PersonValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String person(@PathVariable int id, Model model) {
        model.addAttribute("person", personDAO.findById(id));
        model.addAttribute("books", bookDAO.findAllByPersonId(id));
        return "people/profile";
    }

    @GetMapping("/new")
    public String newPersonGet(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping("/new")
    public String newPersonPost(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonGet(@PathVariable int id, Model model) {
        model.addAttribute("person", personDAO.findById(id));
        return "people/edit";
    }

    @PostMapping("/{id}/edit")
    public String editPersonPost(@PathVariable int id, @ModelAttribute @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        Person person_original = personDAO.findById(id);
        person_original.setPerson_name(person.getPerson_name());
        person_original.setBirthday(person.getBirthday());
        personDAO.update(person_original);
        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable int id) {
        personDAO.deleteById(id);
        return "redirect:/people";
    }

}
