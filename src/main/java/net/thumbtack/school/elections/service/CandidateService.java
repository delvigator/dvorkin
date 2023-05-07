
package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpl.CandidateDaoImpl;
import net.thumbtack.school.elections.daoimpl.VoterDaoImpl;
import net.thumbtack.school.elections.dto.request.AddCandidatesDtoRequest;

import net.thumbtack.school.elections.dto.request.LoginDtoRequest;
import net.thumbtack.school.elections.dto.response.GetAllCandidatesDtoResponse;
import net.thumbtack.school.elections.dto.response.GetAllVotersDtoResponse;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.mapper.CandidateMapper;

import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Voter;
import net.thumbtack.school.elections.response.ResponseCode;
import net.thumbtack.school.elections.response.ResponseErrorData;
import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.service.templ.Service;

public class CandidateService {
    private static final Gson gson = new Gson();
    private static final CandidateDaoImpl candidateDao = new CandidateDaoImpl();
    private final VoterDaoImpl voterDao = new VoterDaoImpl();
    public ServerResponse addCandidate(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            Voter voter=voterDao.searchByTokenVoter(token);
            if(voter==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            AddCandidatesDtoRequest addCandidatesDtoRequest = Service.getObjectFromJson(requestJsonString, AddCandidatesDtoRequest.class);
            validateAddCandidate(addCandidatesDtoRequest);
            candidateDao.addCandidate(voter, CandidateMapper.MAPPER.toCandidate(addCandidatesDtoRequest));
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }

    }

    public ServerResponse deleteCandidate(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            Candidate candidate=candidateDao.getCandidateByToken(token);
            if(candidate==null) throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
            candidateDao.deleteCandidate(candidate);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse getAllCandidates(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            GetAllCandidatesDtoResponse getAllCandidatesDtoResponse = new GetAllCandidatesDtoResponse(
                    candidateDao.getAllCandidates()
            );
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(getAllCandidatesDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse changeAgreement(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            Candidate candidate=candidateDao.getCandidateByToken(token);
            if(candidate==null) throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
            candidateDao.changeAgreement(candidate);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }
    public ServerResponse getCandidate(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            LoginDtoRequest loginDtoRequest=Service.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            Candidate candidate=candidateDao.getCandidateByLogin(loginDtoRequest.getLogin());
            if(candidate==null) throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(),
                    gson.toJson(candidate));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }

    }

    private static void validateAddCandidate(AddCandidatesDtoRequest addCandidatesDtoRequest) throws ServerException {
        if (addCandidatesDtoRequest == null) throw new ServerException(ResponseErrorData.REQUEST_ERROR);
        if (addCandidatesDtoRequest.getUser().getLogin().isBlank())
            throw new ServerException(ResponseErrorData.INVALID_LOGIN);
        if (addCandidatesDtoRequest.getUser().getPassword().isBlank())
            throw new ServerException(ResponseErrorData.INVALID_PASSWORD);
        if (addCandidatesDtoRequest.getUser().getLastName().isBlank())
            throw new ServerException(ResponseErrorData.INVALID_LAST_NAME);
        if (addCandidatesDtoRequest.getUser().getStreet().isBlank())
            throw new ServerException(ResponseErrorData.INVALID_STREET);
        if (addCandidatesDtoRequest.getUser().getHouse().isBlank())
            throw new ServerException(ResponseErrorData.INVALID_HOUSE);
    }

}
