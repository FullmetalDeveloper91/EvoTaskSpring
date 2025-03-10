package ru.fmd.EvoSpring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.fmd.EvoSpring.dto.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
