package net.thumbtack.school.elections.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.model.Program;
import net.thumbtack.school.elections.model.User;
import net.thumbtack.school.elections.model.Voter;
import net.thumbtack.school.elections.response.ResponseErrorData;
@Getter
@Setter
@NoArgsConstructor
public class AddCandidatesDtoRequest {
    private User user;
    private Program program;
    private boolean agreement;

    public AddCandidatesDtoRequest(String firstName, String lastName, String patronymic,
                     String street, String house, String flat, String login, String password,int id) {
        user = new User(firstName, lastName, patronymic, street, house, flat, login, password,id);
        this.program = new Program();
        this.agreement = false;
    }
public AddCandidatesDtoRequest(User user){
        this.user=user;
        this.program=new Program();
        this.agreement=false;
}
}