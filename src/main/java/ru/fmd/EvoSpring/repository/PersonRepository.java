package ru.fmd.EvoSpring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fmd.EvoSpring.dto.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
