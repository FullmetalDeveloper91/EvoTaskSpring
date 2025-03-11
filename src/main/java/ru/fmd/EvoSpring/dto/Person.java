package ru.fmd.EvoSpring.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String surname;
    private String lastname;
    private LocalDate birthday;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    public Person() {
    }

    public Person(String firstname, String surname, String lastname, LocalDate birthday) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
    }

    public Person(String firstname, String surname, String lastname, LocalDate birthday, List<Message> messages) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Message> getMessages() {
        return List.copyOf(messages);
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public boolean removeMessage(int id){
        return messages.removeIf(m -> m.getId() == id);
    }
}