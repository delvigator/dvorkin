package net.thumbtack.school.server;

import com.google.gson.Gson;
import net.thumbtack.school.elections.dto.request.AddCandidatesDtoRequest;
import net.thumbtack.school.elections.dto.request.LoginDtoRequest;
import net.thumbtack.school.elections.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.dto.response.IdDto;
import net.thumbtack.school.elections.dto.response.TokenDto;
import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.server.Server;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandidateServiceTest {
    static Gson gson = new Gson();

    private static int register(Server server, RegisterVoterDtoRequest request) {

        ServerResponse serverResponse = server.registerVoter(gson.toJson(request));
        System.out.println(serverResponse.getResponseData());
        IdDto idDto = gson.fromJson(serverResponse.getResponseData(), IdDto.class);
        return idDto.getID();

    }

    private static String login(Server server, LoginDtoRequest request) {
        ServerResponse responseLogin = server.login(gson.toJson(request));
        TokenDto tokenDto = gson.fromJson(responseLogin.getResponseData(), TokenDto.class);
        return tokenDto.getToken();
    }

    @org.junit.Test
    public void addCandidateTest() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin45", "rightPassword5");
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin45", "rightPassword5");
        int id = register(server, registerVoterDtoRequest);
        String token = login(server, loginDtoRequest);
        AddCandidatesDtoRequest addCandidatesDtoRequest = new AddCandidatesDtoRequest(
                "Ivan", "Ivanov1",
                "", "Peace", "50", "5",
                "rightLogin45", "rightPassword5", id);
        ServerResponse response = server.addCandidate(token, gson.toJson(addCandidatesDtoRequest));
        Assertions.assertEquals("", response.getResponseData());
        Assertions.assertEquals(200, response.getResponseCode());
        server.deleteCandidate(token, gson.toJson(addCandidatesDtoRequest));
    }

    @org.junit.Test
    public void getAllCandidatesTest() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin4", "rightPassword5");
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin4", "rightPassword5");
        int id = register(server, registerVoterDtoRequest);
        String token = login(server, loginDtoRequest);

        AddCandidatesDtoRequest addCandidatesDtoRequest = new AddCandidatesDtoRequest(
                "Ivan", "Ivanov1",
                "", "Peace", "50", "5",
                "rightLogin4", "rightPassword5", id);
        addCandidatesDtoRequest.setAgreement(true);
        server.addCandidate(token, gson.toJson(addCandidatesDtoRequest));

        ServerResponse serverResponse = server.getAllCandidates(token, "");
        Assertions.assertEquals("{\"candidates\":[" + gson.toJson(addCandidatesDtoRequest) + "]}", serverResponse.getResponseData());
        Assertions.assertEquals(200, serverResponse.getResponseCode());

        server.changeAgreement(token, gson.toJson(addCandidatesDtoRequest));
        serverResponse = server.getAllCandidates(token, "");
        addCandidatesDtoRequest.setAgreement(false);
        Assertions.assertEquals("{\"candidates\":[" + gson.toJson(addCandidatesDtoRequest) + "]}", serverResponse.getResponseData());
        Assertions.assertEquals(200, serverResponse.getResponseCode());
    }

    @Test
    public void deleteCandidateTest() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin56", "rightPassword5");
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin56", "rightPassword5");
        int id = register(server, registerVoterDtoRequest);
        String token = login(server, loginDtoRequest);

        AddCandidatesDtoRequest addCandidatesDtoRequest = new AddCandidatesDtoRequest(
                "Ivan", "Ivanov1",
                "", "Peace", "50", "5",
                "rightLogin56", "rightPassword5", id);
        addCandidatesDtoRequest.setAgreement(true);
        server.addCandidate(token, gson.toJson(addCandidatesDtoRequest));

        ServerResponse serverResponse = server.deleteCandidate(token, gson.toJson(addCandidatesDtoRequest));
        Assertions.assertEquals("", serverResponse.getResponseData());
        Assertions.assertEquals(200, serverResponse.getResponseCode());
        Assertions.assertEquals("{\"candidates\":[]}", server.getAllCandidates(token, "").getResponseData());

    }
}
