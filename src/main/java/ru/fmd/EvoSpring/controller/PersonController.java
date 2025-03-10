package ru.fmd.EvoSpring.controller;

import jakarta.servlet.ServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmd.EvoSpring.dto.Message;
import ru.fmd.EvoSpring.dto.Person;
import ru.fmd.EvoSpring.repository.PersonRepository;
import ru.fmd.EvoSpring.services.PersonService;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;
    private final PersonRepository repository;

    public PersonController(PersonService service, PersonRepository repository) {
        this.service = service;
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

    @PostMapping("/{id}/message")
    public ResponseEntity<Person> addMessage(@PathVariable int id, @RequestBody Message message){
        Person person = null;
        HttpStatus status;

        try{
            person = service.addMessageToPerson(id, message);
            status = HttpStatus.OK;
        }catch (NoSuchElementException ex){
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(person,status);
    }

    @DeleteMapping("{p_id/message/{m_id}")
    public void deleteMessage(@PathVariable int personId, @PathVariable int messageId){

    }
}
