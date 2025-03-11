package ru.fmd.EvoSpring.controller;

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

    @GetMapping("/{p_id}")
    public Optional<Person> findPersonById(@PathVariable("p_id") int id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        repository.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/{p_id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("p_id") int id, @RequestBody Person person) {
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

    @DeleteMapping("/{p_id}")
    public void deletePerson(@PathVariable("p_id") int id) {
        repository.deleteById(id);
    }

    @GetMapping("/{p_id}/message")
    public Iterable<Message> getPersonMessage(@PathVariable("p_id") int personId){
        return repository.findById(personId).get().getMessages();
    }

    @PostMapping("/{p_id}/message")
    public ResponseEntity<Person> addMessage(@PathVariable("p_id") int id, @RequestBody Message message){
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

    @DeleteMapping("{p_id}/message/{m_id}")
    public HttpStatus deleteMessage(@PathVariable("p_id") int personId, @PathVariable("m_id") int messageId){
        HttpStatus status;

        try{
            if(service.delMessageByID(personId, messageId))
                status = HttpStatus.OK;
            else
                status = HttpStatus.BAD_REQUEST;
        }catch (NoSuchElementException ex){
            status = HttpStatus.BAD_REQUEST;
        }
        return status;
    }
}
