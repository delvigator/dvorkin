package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.dto.request.LoginDtoRequest;
import net.thumbtack.school.elections.dto.response.IdDto;
import net.thumbtack.school.elections.dto.response.TokenDto;
import net.thumbtack.school.elections.dto.response.GetAllVotersDtoResponse;
import net.thumbtack.school.elections.mapper.VoterMapper;
import net.thumbtack.school.elections.response.ResponseCode;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.response.ResponseErrorData;
import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.model.Voter;
import net.thumbtack.school.elections.daoimpl.VoterDaoImpl;
import net.thumbtack.school.elections.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.service.templ.Service;

public class VoterService {
    private static final Gson gson = new Gson();
    private final VoterDaoImpl voterDao = new VoterDaoImpl();


    public ServerResponse registerVoter(String requestJsonString) {
        try {
            RegisterVoterDtoRequest registerVoterDtoRequest = Service.getObjectFromJson(requestJsonString, RegisterVoterDtoRequest.class);
            validateRegister(registerVoterDtoRequest);
            Voter voter = VoterMapper.MAPPER.toVoter(registerVoterDtoRequest);
            int id = voterDao.register(voter);
            IdDto idDto=new IdDto(id);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(idDto));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse login(String requestJsonString) {
        try {
            LoginDtoRequest loginDtoRequest = Service.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            validateLogin(loginDtoRequest);
            Voter voter=voterDao.searchByLogin(loginDtoRequest.getLogin());
            if(voter==null) throw new ServerException(ResponseErrorData.INVALID_LOGIN);
            if(!voter.getUser().getPassword().equals(loginDtoRequest.getPassword()))
                throw new ServerException(ResponseErrorData.INVALID_PASSWORD);
            String token = voterDao.login(voter);
            TokenDto tokenDto = new TokenDto(token);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(tokenDto));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }


    public ServerResponse logout(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            voterDao.logout(token);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse getAllVoters(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            GetAllVotersDtoResponse getAllVotersDtoResponse = new GetAllVotersDtoResponse(voterDao.getAllVoters());
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(getAllVotersDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }

    }

    public ServerResponse getVoter(String token, String requestJsonString) {
        try {
            if(token.isBlank() || voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            LoginDtoRequest loginDtoRequest = Service.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
           Voter voter= voterDao.searchByLogin(loginDtoRequest.getLogin());
           if(voter==null) throw new ServerException(ResponseErrorData.INVALID_LOGIN);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(voter));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse leaveServer(String token, String requestJsonString) {
        try {
            if(token.isBlank()||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            voterDao.leaveServer(voterDao.searchByTokenVoter(token));
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }

    }

    public ServerResponse returnToServer(String requestJsonString) {
        try {
            LoginDtoRequest loginDtoRequest = Service.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            Voter voter=voterDao.searchByLoginNotActive(loginDtoRequest.getLogin());
            if(voter==null) throw new ServerException(ResponseErrorData.INVALID_LOGIN);
            if(!voter.getUser().getPassword().equals(loginDtoRequest.getPassword()))throw new ServerException(ResponseErrorData.INVALID_PASSWORD);
            voterDao.returnToServer(voter);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }

    }

    private static void validateRegister(RegisterVoterDtoRequest registerVoterDtoRequest) throws ServerException {
        if (registerVoterDtoRequest == null) throw new ServerException(ResponseErrorData.REQUEST_ERROR);
        if (registerVoterDtoRequest.getUser().getFirstName() == null || registerVoterDtoRequest.getUser().getFirstName().equals(""))
            throw new ServerException(ResponseErrorData.INVALID_FIRST_NAME);
        if (registerVoterDtoRequest.getUser().getLastName() == null || registerVoterDtoRequest.getUser().getLastName().equals(""))
            throw new ServerException(ResponseErrorData.INVALID_LAST_NAME);
        if (registerVoterDtoRequest.getUser().getStreet() == null || registerVoterDtoRequest.getUser().getStreet().equals(""))
            throw new ServerException(ResponseErrorData.INVALID_STREET);
        if (registerVoterDtoRequest.getUser().getHouse() == null || registerVoterDtoRequest.getUser().getHouse().equals(""))
            throw new ServerException(ResponseErrorData.INVALID_HOUSE);
        if (registerVoterDtoRequest.getUser().getLogin() == null || registerVoterDtoRequest.getUser().getLogin().equals("")
                || registerVoterDtoRequest.getUser().getLogin().length() < 8)
            throw new ServerException(ResponseErrorData.INVALID_LOGIN);
        if (registerVoterDtoRequest.getUser().getPassword() == null || registerVoterDtoRequest.getUser().getPassword().equals("")
                || registerVoterDtoRequest.getUser().getPassword().length() < 8)
            throw new ServerException(ResponseErrorData.INVALID_PASSWORD);
    }

    private static void validateLogin(LoginDtoRequest loginDtoRequest) throws ServerException {
        if (loginDtoRequest == null) throw new ServerException(ResponseErrorData.REQUEST_ERROR);
        if (loginDtoRequest.getLogin() == null || loginDtoRequest.getLogin().equals(""))
            throw new ServerException(ResponseErrorData.INVALID_LOGIN);
        if (loginDtoRequest.getPassword() == null || loginDtoRequest.getPassword().equals(""))
            throw new ServerException(ResponseErrorData.INVALID_PASSWORD);


    }
}
