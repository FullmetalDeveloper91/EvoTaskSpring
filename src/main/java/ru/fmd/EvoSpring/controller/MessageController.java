package ru.fmd.EvoSpring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmd.EvoSpring.dto.Message;
import ru.fmd.EvoSpring.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Message> getMessages() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Message> findMessageById(@PathVariable int id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        message.setTime(LocalDateTime.now());
        repository.save(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        var oldMessage = repository.findById(id).orElse(null);

        if(oldMessage != null){
            oldMessage.setTitle(message.getTitle());
            oldMessage.setText(message.getText());

            return new ResponseEntity<>(repository.save(oldMessage), HttpStatus.OK);
        }else
            return addMessage(message);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable int id) {
        repository.deleteById(id);
    }
}