package ru.fmd.EvoSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmd.EvoSpring.dto.Message;
import ru.fmd.EvoSpring.services.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Message> getMessages() {
        return service.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findMessageById(@PathVariable int id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        return service.add(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        return service.updateMessage(id, message);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable int id) {
        service.delete(id);
    }
}