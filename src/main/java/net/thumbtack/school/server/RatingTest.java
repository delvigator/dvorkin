package net.thumbtack.school.server;

import com.google.gson.Gson;
import net.thumbtack.school.elections.dto.request.AddRatingDtoRequest;
import net.thumbtack.school.elections.dto.request.AddSentenceDtoRequest;
import net.thumbtack.school.elections.dto.request.LoginDtoRequest;
import net.thumbtack.school.elections.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.dto.response.IdDto;
import net.thumbtack.school.elections.dto.response.TokenDto;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.server.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingTest {

    @Test
    public void Rating_Test() throws ServerException {
        Gson gson = new Gson();
        Server server;
        String token;
        String token2;
        server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin88", "rightPassword");
        ServerResponse serverResponse = server.registerVoter(gson.toJson(registerVoterDtoRequest));
        int idUse1 = gson.fromJson(serverResponse.getResponseData(), IdDto.class).getID();

        RegisterVoterDtoRequest registerVoterDtoRequest2 = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLoginTwo88", "rightPassword");

        ServerResponse serverResponse1 = server.registerVoter(gson.toJson(registerVoterDtoRequest2));
        int idUse2 = gson.fromJson(serverResponse1.getResponseData(), IdDto.class).getID();

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin88", "rightPassword");
        LoginDtoRequest loginDtoRequest2 = new LoginDtoRequest("rightLoginTwo88", "rightPassword");

        ServerResponse responseLogin = server.login(gson.toJson(loginDtoRequest));
        TokenDto tokenDto = gson.fromJson(responseLogin.getResponseData(), TokenDto.class);
        token = tokenDto.getToken();

        ServerResponse response2 = server.login(gson.toJson(loginDtoRequest2));
        TokenDto tokenDto2 = gson.fromJson(response2.getResponseData(), TokenDto.class);
        token2 = tokenDto2.getToken();

        AddSentenceDtoRequest addSentenceDtoRequest = new AddSentenceDtoRequest(idUse1, "что-то крутое", 0);
        serverResponse = server.addSentence(token, gson.toJson(addSentenceDtoRequest));
        int id = gson.fromJson(serverResponse.getResponseData(), IdDto.class).getID();

        //добавление
        AddRatingDtoRequest addRatingDtoRequest = new AddRatingDtoRequest(5, idUse2, id);
        ServerResponse response = server.addRating(token2, gson.toJson(addRatingDtoRequest));
        int idRait = gson.fromJson(response.getResponseData(), IdDto.class).getID();
        Assertions.assertEquals(200, response.getResponseCode());

        String request = "{\"voterSet\":[{\"user\":{\"firstName\":\"Ivan\",\"lastName\":\"Ivanov1\"," +
                "\"patronymic\":\"\",\"street\":\"Peace\",\"house\":\"50\",\"flat\":\"5\",\"login\":\"rightLogin88\"," +
                "\"password\":\"rightPassword\",\"id\":" + idUse1 + "}}]}";
        String expected = "{\"sentences\":[{\"userId\":" + idUse1 + ",\"sentenceText\":\"что-то крутое\"," +
                "\"sentenceId\":" + id + ",\"rating\":[{\"numberOfRating\":5,\"sentenceId\":" + id + ",\"id\":" + idRait + "," +
                "\"userId\":" + idUse2 + "}],\"averageRating\":5.0}]}";
        response = server.getSentencesByVoter(token, request);
        Assertions.assertEquals(expected, response.getResponseData());

        //изменение
        addRatingDtoRequest = new AddRatingDtoRequest(3, idUse2, id, idRait);
        response = server.changeRating(token2, gson.toJson(addRatingDtoRequest));
        Assertions.assertEquals("", response.getResponseData());
        response = server.getSentencesByVoter(token, request);
        expected = "{\"sentences\":[{\"userId\":" + idUse1 + ",\"sentenceText\":\"что-то крутое\",\"sentenceId\":" + id + "," +
                "\"rating\":[{\"numberOfRating\":3,\"sentenceId\":" + id + ",\"id\":" + idRait + ",\"userId\":" + idUse2 + "}]," +
                "\"averageRating\":5.0}]}";
        ;
        Assertions.assertEquals(expected, response.getResponseData());

        //удаление
        AddSentenceDtoRequest addSentenceDtoRequest3 = new AddSentenceDtoRequest(idUse1, "что-то крутое", 0);
        ServerResponse serverResponse2 = server.addSentence(token, gson.toJson(addSentenceDtoRequest));
        int idS = gson.fromJson(serverResponse2.getResponseData(), IdDto.class).getID();
        addSentenceDtoRequest3 = new AddSentenceDtoRequest(idUse1, "что-то крутое", idS);

        addRatingDtoRequest = new AddRatingDtoRequest(3, idUse2, idS);
        serverResponse = server.addRating(token2, gson.toJson(addRatingDtoRequest));

        idRait = gson.fromJson(serverResponse.getResponseData(), IdDto.class).getID();
        addRatingDtoRequest = new AddRatingDtoRequest(3, idUse2, idS, idRait);

        response = server.deleteRating(token2, gson.toJson(addRatingDtoRequest));
        Assertions.assertEquals("", response.getResponseData());
        Assertions.assertEquals(200, response.getResponseCode());

        //проверка на сортировку по среднему рейтингу
        serverResponse = server.addSentence(token, gson.toJson(addSentenceDtoRequest));
        int id2 = gson.fromJson(serverResponse.getResponseData(), IdDto.class).getID();

        AddSentenceDtoRequest addSentenceDtoRequest1 = new AddSentenceDtoRequest(idUse1, "что-то не крутое", 0);
        serverResponse1 = server.addSentence(token, gson.toJson(addSentenceDtoRequest1));
        int id3 = gson.fromJson(serverResponse1.getResponseData(), IdDto.class).getID();

        AddRatingDtoRequest addRatingDtoRequest2 = new AddRatingDtoRequest(2, idUse2, id2);
        server.addRating(token2, gson.toJson(addRatingDtoRequest2));

        AddRatingDtoRequest addRatingDtoRequest1 = new AddRatingDtoRequest(5, idUse2, id3);
        server.addRating(token2, gson.toJson(addRatingDtoRequest1));

        response = server.getAllSentences(token, "");
//        expected = "{\"sentences\":[{\"username\":\"rightLogin\",\"sentenceText\":\"что-то не крутое\"," +
//                "\"sentenceId\":\"" + id3 + "\",\"rating\":[{\"numberOfRating\":5," +
//                "\"sentenceId\":\"" + id3 + "\",\"username\":\"rightLoginTwo\"}]," +
//                "\"averageRating\":5.0},{\"username\":\"rightLogin\",\"sentenceText\":\"что-то крутое\"," +
//                "\"sentenceId\":\"" + id2 + "\",\"rating\":[{\"numberOfRating\":2," +
//                "\"sentenceId\":\"" + id2 + "\",\"username\":\"rightLoginTwo\"}]," +
//                "\"averageRating\":2.0},{\"username\":\"rightLogin\",\"sentenceText\":\"что-то крутое\"," +
//                "\"sentenceId\":\"" + id + "\",\"rating\":[],\"averageRating\":0.0},{\"username\":\"rightLogin\"," +
//                "\"sentenceText\":\"что-то крутое\",\"sentenceId\":\""+idS+"\",\"rating\":[],\"averageRating\":0.0}]}";
//        assertEquals(expected, response.getResponseData());
        Assertions.assertEquals(response.getResponseCode(), 200);
    }
}
