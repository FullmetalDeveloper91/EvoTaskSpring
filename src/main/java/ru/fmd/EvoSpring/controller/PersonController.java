package ru.fmd.EvoSpring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmd.EvoSpring.dto.Person;
import ru.fmd.EvoSpring.repository.PersonRepository;

import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Person> getPerson() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findPersonById(@PathVariable int id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        repository.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        var oldPerson = repository.findById(id).orElse(null);

        if(oldPerson != null){
            oldPerson.setFirstname(person.getFirstname());
            oldPerson.setSurname(person.getSurname());
            oldPerson.setLastname(person.getLastname());
            oldPerson.setBirthday(person.getBirthday());

            return new ResponseEntity<>(repository.save(oldPerson), HttpStatus.OK);
        }else
            return addPerson(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        repository.deleteById(id);
    }
}
