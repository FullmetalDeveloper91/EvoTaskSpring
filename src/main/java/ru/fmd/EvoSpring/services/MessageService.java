package ru.fmd.EvoSpring.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.fmd.EvoSpring.dto.Message;
import ru.fmd.EvoSpring.repository.MessageRepository;

import java.time.LocalDateTime;

@Service
public class MessageService {
    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Iterable<Message> get() {
        return repository.findAll();
    }

    public ResponseEntity<Message> findById(int id) {
        Message message = repository.findById(id).orElse(null);
        return message == null
                ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> add(Message message) {
        message.setTime(LocalDateTime.now());
        repository.save(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Message> updateMessage(int id, Message message) {
        var oldMessage = repository.findById(id).orElse(null);

        if(oldMessage != null){
            oldMessage.setTitle(message.getTitle());
            oldMessage.setText(message.getText());

            return new ResponseEntity<>(repository.save(oldMessage), HttpStatus.OK);
        }else
            return add(message);
    }

    public void delete(int id) {
        if(repository.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        repository.deleteById(id);
    }
}
