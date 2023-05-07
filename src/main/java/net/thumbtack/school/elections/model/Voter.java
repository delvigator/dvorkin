package net.thumbtack.school.elections.model;

import lombok.*;

import java.util.Objects;
@EqualsAndHashCode
@Data
@NoArgsConstructor
public class Voter {
    private User user;


    public Voter(String firstName, String lastName, String patronymic, String street, String house, String flat,
                 String login, String password) {
        user = new User(firstName, lastName, patronymic, flat, house, street, login, password);

    }

    public Voter(User user) {
        this(user.getFirstName(), user.getLastName(), user.getPatronymic(), user.getStreet(),
                user.getHouse(), user.getFlat(), user.getLogin(), user.getPassword());
    }

}
