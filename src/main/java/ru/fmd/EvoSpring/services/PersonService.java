package ru.fmd.EvoSpring.services;

import org.springframework.stereotype.Service;
import ru.fmd.EvoSpring.dto.Message;
import ru.fmd.EvoSpring.dto.Person;
import ru.fmd.EvoSpring.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person addMessageToPerson(int personId, Message message) throws NoSuchElementException {
        Person person = repository.findById(personId).orElseThrow();
        message.setPerson(person);
        message.setTime(LocalDateTime.now());
        person.addMessage(message);
        return repository.save(person);
    }

    public boolean delMessageByID(int personId, int messageId){
        Person person = repository.findById(personId).orElseThrow();
        boolean result = person.removeMessage(messageId);
        repository.save(person);
        return result;
    }
}
