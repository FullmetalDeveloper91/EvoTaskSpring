package ru.fmd.EvoSpring.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.EvoSpring.dto.Message;
import ru.fmd.EvoSpring.dto.Person;
import ru.fmd.EvoSpring.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    public ResponseEntity<Person> findById(int personId){
        Person person = repository.findById(personId).orElse(null);

        return person==null
                ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(person, HttpStatus.OK);
    }

    public ResponseEntity<Person> addPerson(Person person) {
        repository.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    public ResponseEntity<Person> updatePerson(int id, Person person) {
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

    @Transactional
    public void delete(int personId) {
        if(repository.findById(personId).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        repository.deleteById(personId);
    }

    public ResponseEntity<Iterable<Message>> getPersonMessages(int personId) {
        Person person = repository.findById(personId).orElse(null);

        return person == null
                ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(person.getMessages(), HttpStatus.OK);
    }

    public ResponseEntity<Message> getPersonMessage(int personId, int messageId) {
        Optional<Person> person = repository.findById(personId);

        if(person.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else{
            Message message = person.get().getMessages().stream()
                    .filter(m -> m.getId() == messageId)
                    .findFirst().orElse(null);
            return message==null
                    ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<Person> addMessageToPerson(int personId, Message message) {
        Person person = repository.findById(personId).orElse(null);

        if(person == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        message.setPerson(person);
        message.setTime(LocalDateTime.now());
        person.addMessage(message);

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @Transactional
    public void delMessageByID(int personId, int messageId){
        Person person = repository.findById(personId).orElse(null);

        if(person == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            if(!person.removeMessage(messageId))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
