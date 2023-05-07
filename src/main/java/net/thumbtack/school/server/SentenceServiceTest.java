package net.thumbtack.school.server;

import com.google.gson.Gson;
import net.thumbtack.school.elections.dto.request.AddCandidatesDtoRequest;
import net.thumbtack.school.elections.dto.request.AddSentenceDtoRequest;
import net.thumbtack.school.elections.dto.request.LoginDtoRequest;
import net.thumbtack.school.elections.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.dto.response.IdDto;
import net.thumbtack.school.elections.dto.response.TokenDto;
import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.server.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SentenceServiceTest {
    static Gson gson = new Gson();

    private static int register(Server server, RegisterVoterDtoRequest request) {
        ServerResponse serverResponse=server.registerVoter(gson.toJson(request));
        IdDto idDto=gson.fromJson(serverResponse.getResponseData(),IdDto.class);
        return idDto.getID();
    }

    private static String login(Server server, LoginDtoRequest request) {
        ServerResponse responseLogin = server.login(gson.toJson(request));
        TokenDto tokenDto = gson.fromJson(responseLogin.getResponseData(), TokenDto.class);
        return tokenDto.getToken();
    }

    @org.junit.Test
    public void addSentenceTest() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin777", "rightPassword8");

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin777", "rightPassword8");
      int id= register(server,registerVoterDtoRequest);
       String token=login(server,loginDtoRequest);

        AddSentenceDtoRequest addSentenceDtoRequest = new AddSentenceDtoRequest(id, "что-то крутое", 0);
        ServerResponse response = server.addSentence(token, gson.toJson(addSentenceDtoRequest));
        Assertions.assertEquals(200, response.getResponseCode());
        response = server.getAllSentences(token, "");
        Assertions.assertEquals(200, response.getResponseCode());

    }

    @org.junit.jupiter.api.Test
    void addSentenceInProgramTest() {
        Gson gson = new Gson();
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin9", "rightPassword");

        RegisterVoterDtoRequest registerVoterDtoRequest2 = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLoginTwo10", "rightPassword");

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin9", "rightPassword");
        LoginDtoRequest loginDtoRequest2 = new LoginDtoRequest("rightLoginTwo10", "rightPassword");
      int id3=  register(server, registerVoterDtoRequest);
      int id4=  register(server, registerVoterDtoRequest2);
        String token1 = login(server, loginDtoRequest);
        String token2 = login(server, loginDtoRequest2);

        AddCandidatesDtoRequest addCandidatesDtoRequest = new AddCandidatesDtoRequest(
                "Ivan", "Ivanov1",
                "", "Peace", "50", "5",
                "rightLogin9", "rightPassword",id3);
        addCandidatesDtoRequest.setAgreement(true);
        server.addCandidate(token1, gson.toJson(addCandidatesDtoRequest));


        AddSentenceDtoRequest addSentenceDtoRequest2 = new AddSentenceDtoRequest(id4, "что-то крутое", 0);

        ServerResponse serverResponse = server.addSentence(token2, gson.toJson(addSentenceDtoRequest2));
        int id1 = gson.fromJson(serverResponse.getResponseData(), IdDto.class).getID();

        addSentenceDtoRequest2 = new AddSentenceDtoRequest(id4, "что-то крутое", id1);

        ServerResponse response = server.addSentenceInProgram(token1, gson.toJson(addSentenceDtoRequest2));
        Assertions.assertEquals("", response.getResponseData());
        Assertions.assertEquals(200, response.getResponseCode());

        response = server.getAllCandidates(token1, "");

        String expected = "{\"candidates\":[{\"user\":{\"firstName\":\"Ivan\",\"lastName\":\"Ivanov1\"," +
                "\"patronymic\":\"\",\"street\":\"Peace\",\"house\":\"50\",\"flat\":\"5\"," +
                "\"login\":\"rightLogin9\",\"password\":\"rightPassword\",\"id\":"+id3+"}," +
                "\"program\":{\"program\":[{\"userId\":"+id4+",\"sentenceText\":\"что-то крутое\"," +
                "\"sentenceId\":"+id1+",\"rating\":[],\"averageRating\":0.0}]},\"agreement\":true}]}";

        Assertions.assertEquals(expected, response.getResponseData());
        Assertions.assertEquals(200, response.getResponseCode());

        response = server.deleteSentenceFromProgram(token1, gson.toJson(addSentenceDtoRequest2));
        Assertions.assertEquals("", response.getResponseData());
        Assertions.assertEquals(200, response.getResponseCode());

        response = server.getAllCandidates(token1, "");

        expected = "{\"candidates\":[{\"user\":{\"firstName\":\"Ivan\"," +
                "\"lastName\":\"Ivanov1\",\"patronymic\":\"\",\"street\":\"Peace\"," +
                "\"house\":\"50\",\"flat\":\"5\",\"login\":\"rightLogin9\"," +
                "\"password\":\"rightPassword\",\"id\":"+id3+"}," +
                "\"program\":{\"program\":[]},\"agreement\":true}]}";
        Assertions.assertEquals(expected, response.getResponseData());
        Assertions.assertEquals(200, response.getResponseCode());
        server.deleteCandidate(token1,gson.toJson(addCandidatesDtoRequest));
    }

    @Test
    public void getSentencesByVoter() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin5", "rightPassword");

        RegisterVoterDtoRequest registerVoterDtoRequest2 = new RegisterVoterDtoRequest(
                "Elena", "Ivanova", "", "Peace",
                "50", "5", "rightLoginTwo6", "rightPassword");

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin5", "rightPassword");
        LoginDtoRequest loginDtoRequest2 = new LoginDtoRequest("rightLoginTwo6", "rightPassword");

       int id5= register(server, registerVoterDtoRequest);
      int id6=  register(server, registerVoterDtoRequest2);
        String token1 = login(server, loginDtoRequest);
        String token2 = login(server, loginDtoRequest2);


        AddSentenceDtoRequest addSentenceDtoRequest = new AddSentenceDtoRequest(id5, "что-то крутое", 0);
        AddSentenceDtoRequest addSentenceDtoRequest2 = new AddSentenceDtoRequest(id6, "что-то не крутое", 0);
        AddSentenceDtoRequest addSentenceDtoRequest3 = new AddSentenceDtoRequest(id5, "что-то очень крутое", 0);

        ServerResponse serverResponse = server.addSentence(token1, gson.toJson(addSentenceDtoRequest));
        int id1 = gson.fromJson(serverResponse.getResponseData(), IdDto.class).getID();

        serverResponse = server.addSentence(token2, gson.toJson(addSentenceDtoRequest2));
        int id2 = gson.fromJson(serverResponse.getResponseData(), IdDto.class).getID();

        serverResponse = server.addSentence(token1, gson.toJson(addSentenceDtoRequest3));
        int id3 = gson.fromJson(serverResponse.getResponseData(), IdDto.class).getID();

        String request = "{\"voterSet\":[{\"user\":{\"firstName\":\"Elena\",\"lastName\":\"Ivanova\"," +
                "\"patronymic\":\"\",\"street\":\"Peace\",\"house\":\"50\",\"flat\":\"5\",\"login\":\"rightLoginTwo6\"," +
                "\"password\":\"rightPassword\",\"id\":"+id6+"}}]}";
        serverResponse = server.getSentencesByVoter(token2, request);
        String expected = "{\"sentences\":[{\"userId\":"+id6+",\"sentenceText\":\"что-то не крутое\"," +
                "\"sentenceId\":"+id2+",\"rating\":[],\"averageRating\":0.0}]}";

        Assertions.assertEquals(expected, serverResponse.getResponseData());
    }

}
