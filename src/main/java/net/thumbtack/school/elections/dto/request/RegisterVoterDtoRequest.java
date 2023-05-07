package net.thumbtack.school.elections.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.elections.model.User;

@Getter
@Setter
@NoArgsConstructor
public class RegisterVoterDtoRequest {
    private User user;

    public RegisterVoterDtoRequest(String firstName, String lastName, String patronymic, String street, String house, String flat,
                                   String login, String password) {
        user = new User(firstName, lastName, patronymic, street, house, flat, login, password);

    }

    public RegisterVoterDtoRequest(User user) {
        this(user.getFirstName(), user.getLastName(), user.getPatronymic(), user.getStreet(),
                user.getHouse(), user.getFlat(), user.getLogin(), user.getPassword());
    }

}