package net.thumbtack.school.server;

import com.google.gson.Gson;
import net.thumbtack.school.elections.dto.request.LoginDtoRequest;
import net.thumbtack.school.elections.dto.request.RegisterVoterDtoRequest;

import net.thumbtack.school.elections.dto.response.IdDto;
import net.thumbtack.school.elections.dto.response.TokenDto;

import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.server.Server;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 public class VoterServiceTest {
    static Gson gson = new Gson();
     private static int register(Server server, RegisterVoterDtoRequest request) {
        return gson.fromJson(server.registerVoter(gson.toJson(request)).getResponseData(), IdDto.class).getID();
     }

     private static String login(Server server,LoginDtoRequest request) {
         ServerResponse responseLogin = server.login(gson.toJson(request));
         TokenDto tokenDto = gson.fromJson(responseLogin.getResponseData(), TokenDto.class);
         return tokenDto.getToken();
     }

    @Test
    public void getAllVotersTest() {
        Server server = new Server();
        RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                "Ivan", "Ivanov1", "", "Peace",
                "50", "5", "rightLogin78", "rightPassword");
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin78", "rightPassword");

        register(server,registerVoterDtoRequest);
        String token = login(server,loginDtoRequest);

        ServerResponse response = server.getAllVoters(token, "");
        Assertions.assertEquals(200, response.getResponseCode());
    }
     @Test
     public void leaveServerTest() {
         Server server = new Server();
         RegisterVoterDtoRequest registerVoterDtoRequest = new RegisterVoterDtoRequest(
                 "Ivan", "Ivanov1", "", "Peace",
                 "50", "5", "rightLogin3", "rightPassword");

         RegisterVoterDtoRequest registerVoterDtoRequest2 = new RegisterVoterDtoRequest(
                 "Elena", "Ivanova", "", "Peace",
                 "50", "5", "rightLoginTwo", "rightPassword");

         LoginDtoRequest loginDtoRequest = new LoginDtoRequest("rightLogin3", "rightPassword");
         LoginDtoRequest loginDtoRequest2 = new LoginDtoRequest("rightLoginTwo", "rightPassword");

       int id=  register(server,registerVoterDtoRequest);
         register(server,registerVoterDtoRequest2);
        String token= login(server,loginDtoRequest);
        String token2= login(server,loginDtoRequest2);


        ServerResponse response = server.leaveServer(token, "");
         Assertions.assertEquals("", response.getResponseData());
         response = server.getAllVoters(token, "");
         Assertions.assertEquals(400, response.getResponseCode());
         response = server.getVoter(token2,gson.toJson(loginDtoRequest));

         Assertions.assertEquals(400, response.getResponseCode());

         response = server.returnToServer(gson.toJson(loginDtoRequest));
         Assertions.assertEquals("", response.getResponseData());

         response = server.getVoter(token2,gson.toJson(loginDtoRequest));
         String expected = "{\"user\":{\"firstName\":\"Ivan\",\"lastName\":\"Ivanov1\"," +
                 "\"patronymic\":\"\",\"street\":\"Peace\",\"house\":\"50\",\"flat\":\"5\"," +
                 "\"login\":\"rightLogin3\",\"password\":\"rightPassword\",\"id\":"+id+"}}";
         Assertions.assertEquals(expected, response.getResponseData());
     }

}
