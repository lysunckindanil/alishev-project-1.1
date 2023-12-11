package com.lysunkin.project2.util;

import com.lysunkin.project2.DAO.PersonDAO;
import com.lysunkin.project2.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@SuppressWarnings("NullableProblems")
@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (personDAO.findByName(((Person) target).getPerson_name()).isPresent()) {
            errors.rejectValue("person_name", "", "Person with this name already exists!");
        }
    }
}
