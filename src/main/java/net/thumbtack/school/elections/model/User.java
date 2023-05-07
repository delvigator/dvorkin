package net.thumbtack.school.elections.model;

import lombok.*;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String street;
    private String house;
    private String flat;
    private String login;
    private String password;

    private int id;


    public User(String firstName, String lastName, String patronymic, String street, String house, String flat, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.login = login;
        this.password = password;
    }
public User(String firstName, String lastName, String patronymic, String street,
            String house, String flat, String login, String password,int id){
        this(firstName,lastName,patronymic,street,house,flat,login,password);
        this.id=id;
}

}
