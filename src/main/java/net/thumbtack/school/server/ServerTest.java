package net.thumbtack.school.server;

import com.google.gson.Gson;
import net.thumbtack.school.elections.dto.request.LoginDtoRequest;
import net.thumbtack.school.elections.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.dto.response.IdDto;
import net.thumbtack.school.elections.dto.response.TokenDto;
import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    Gson gson = new Gson();

    @Test
    public void registerVoterTest() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan1", "Ivanov1", "", "Peace",
                "50", "5", "rightLoginn", "rightPassword");
        ServerResponse response = server.registerVoter(gson.toJson(registerVoterDtoRequest));
        int id=gson.fromJson(response.getResponseData(), IdDto.class).getID();
        Assertions.assertEquals("{\"id\":"+id+"}", response.getResponseData());
        Assertions.assertEquals(200, response.getResponseCode());
    }

    @org.junit.Test
    public void registerVoterTest_notValid() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan2", "Ivanov1", "", "Peace",
                "50", "5", "wrong", "wrong");
        ServerResponse response = server.registerVoter(gson.toJson(registerVoterDtoRequest));
        Assertions.assertEquals(400, response.getResponseCode());
        Assertions.assertEquals("Невалидный логин", response.getResponseData());

    }

    @org.junit.Test
    public void registerVoterTest_notValidPassword() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan3", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin", "wrong");
        ServerResponse response = server.registerVoter(gson.toJson(registerVoterDtoRequest));
        Assertions.assertEquals(400, response.getResponseCode());
        Assertions.assertEquals("Невалидный пароль", response.getResponseData());

    }

    @org.junit.Test
    public void registerVoterTest_twoTimes() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan4", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin", "rightPassword");
        server.registerVoter(gson.toJson(registerVoterDtoRequest));
        ServerResponse response = server.registerVoter(gson.toJson(registerVoterDtoRequest));
        Assertions.assertEquals(400, response.getResponseCode());
        Assertions.assertEquals("Этот пользователь уже зарегистрирован", response.getResponseData());

    }

    @org.junit.Test
    public void loginVoterTest() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan5", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin", "rightPassword");
        server.registerVoter(gson.toJson(registerVoterDtoRequest));

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin", "rightPassword");
        ServerResponse response = server.login(gson.toJson(loginDtoRequest));
        Assertions.assertEquals(200, response.getResponseCode());
        response = server.logout(gson.fromJson(response.getResponseData(), TokenDto.class).getToken(), "");
        Assertions.assertEquals(200, response.getResponseCode());
        response = server.login(gson.toJson(loginDtoRequest));
        Assertions.assertEquals(200, response.getResponseCode());
    }

    @org.junit.Test
    public void loginVoterTest_notValid() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan6", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin", "rightPassword");
        server.registerVoter(gson.toJson(registerVoterDtoRequest));

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("wrongLogin", "rightPassword");
        ServerResponse response = server.login(gson.toJson(loginDtoRequest));
        Assertions.assertEquals(400, response.getResponseCode());
    }



}