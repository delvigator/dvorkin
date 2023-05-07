package net.thumbtack.school.elections.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode
@Data
@NoArgsConstructor
public class Candidate {
    private User user;
    private Program program;
    private boolean agreement;

    public Candidate(String firstName, String lastName, String patronymic,
                     String street, String house, String flat, String login, String password,int id) {
        user = new User(firstName, lastName, patronymic, street, house, flat, login, password,id);
        this.program = new Program();
        this.agreement = false;
    }

    public Candidate(User user) {
        this.user = user;
        agreement = false;
        program = new Program();
    }
    public void changeAgreement(){
        agreement= !agreement;
    }
}