package ru.fmd.EvoSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmd.EvoSpring.dto.Message;
import ru.fmd.EvoSpring.dto.Person;
import ru.fmd.EvoSpring.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Person> getPerson() {
        return service.findAll();
    }

    @GetMapping("/{p_id}")
    public ResponseEntity<Person> findPersonById(@PathVariable("p_id") int personId) {
        return service.findById(personId);
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return service.addPerson(person);
    }

    @PutMapping("/{p_id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("p_id") int id, @RequestBody Person person) {
        return service.updatePerson(id, person);
    }

    @DeleteMapping("/{p_id}")
    public void deletePerson(@PathVariable("p_id") int personId) {
        service.delete(personId);
    }

    @GetMapping("/{p_id}/message")
    public ResponseEntity<Iterable<Message>> getPersonMessages(@PathVariable("p_id") int personId){
        return service.getPersonMessages(personId);
    }

    @GetMapping("/{p_id}/message/{m_id}")
    public ResponseEntity<Message> getPersonMessage(
            @PathVariable("p_id") int personId,
            @PathVariable("m_id") int messageId){
        return service.getPersonMessage(personId, messageId);
    }

    @PostMapping("/{p_id}/message")
    public ResponseEntity<Person> addMessage(@PathVariable("p_id") int personId, @RequestBody Message message){
        return service.addMessageToPerson(personId, message);
    }

    @DeleteMapping("{p_id}/message/{m_id}")
    public void deleteMessage(@PathVariable("p_id") int personId, @PathVariable("m_id") int messageId){
       service.delMessageByID(personId, messageId);
    }
}
