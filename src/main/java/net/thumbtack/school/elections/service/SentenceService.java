package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpl.CandidateDaoImpl;
import net.thumbtack.school.elections.daoimpl.SentenceDaoImpl;
import net.thumbtack.school.elections.daoimpl.VoterDaoImpl;
import net.thumbtack.school.elections.dto.request.AddRatingDtoRequest;
import net.thumbtack.school.elections.dto.request.AddSentenceDtoRequest;
import net.thumbtack.school.elections.dto.request.GetSentenceByVoterDtoRequest;
import net.thumbtack.school.elections.dto.response.GetAllSentencesDtoResponse;
import net.thumbtack.school.elections.dto.response.IdDto;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.mapper.RatingMapper;
import net.thumbtack.school.elections.mapper.SentenceMapper;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Rating;
import net.thumbtack.school.elections.model.Sentence;
import net.thumbtack.school.elections.model.Voter;
import net.thumbtack.school.elections.response.ResponseCode;
import net.thumbtack.school.elections.response.ResponseErrorData;
import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.service.templ.Service;

import java.util.HashSet;
import java.util.Set;

public class SentenceService {
    public static final SentenceDaoImpl sentenceDao = new SentenceDaoImpl();
    public static final VoterDaoImpl voterDao=new VoterDaoImpl();
    public static final CandidateDaoImpl candidateDao=new CandidateDaoImpl();
    private static final Gson gson = new Gson();

    public ServerResponse addSentence(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            AddSentenceDtoRequest addSentenceDtoRequest = Service.getObjectFromJson(requestJsonString, AddSentenceDtoRequest.class);
            validateAddSentence(addSentenceDtoRequest);
            Sentence sentence = SentenceMapper.MAPPER.toSentence(addSentenceDtoRequest);
           IdDto idDto=new IdDto(sentenceDao.addSentence(sentence));
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(idDto));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }


    public ServerResponse getAllSentences(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            GetAllSentencesDtoResponse getAllSentencesDtoResponse =
                    new GetAllSentencesDtoResponse(sentenceDao.getAllSentences());
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(getAllSentencesDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse addSentenceInProgram(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            Candidate candidate=candidateDao.getCandidateByToken(token);
            if(candidate==null) throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
            AddSentenceDtoRequest addSentenceDtoRequest = Service.getObjectFromJson(requestJsonString, AddSentenceDtoRequest.class);
            Sentence sentence = SentenceMapper.MAPPER.toSentence(addSentenceDtoRequest);
            sentenceDao.addSentenceInProgram(candidate, sentence);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse deleteSentenceFromProgram(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            Candidate candidate=candidateDao.getCandidateByToken(token);
            if(candidate==null) throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
            AddSentenceDtoRequest addSentenceDtoRequest = Service.getObjectFromJson(requestJsonString, AddSentenceDtoRequest.class);
            Sentence sentence = SentenceMapper.MAPPER.toSentence(addSentenceDtoRequest);
            sentenceDao.deleteSentenceFromProgram(candidate, sentence);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse addRating(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
           Voter voter=voterDao.searchByTokenVoter(token);
           if(voter==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);

            AddRatingDtoRequest addRatingDtoRequest = Service.getObjectFromJson(requestJsonString, AddRatingDtoRequest.class);
            validateAddRating(addRatingDtoRequest);
            Rating rating = RatingMapper.MAPPER.toRating(addRatingDtoRequest);
            int id=sentenceDao.addRating(voter, rating);
            IdDto idDto=new IdDto(id);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(idDto));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse changeRating(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            Voter voter=voterDao.searchByTokenVoter(token);
            if(voter==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            AddRatingDtoRequest addRatingDtoRequest = Service.getObjectFromJson(requestJsonString, AddRatingDtoRequest.class);
            Rating rating = RatingMapper.MAPPER.toRating(addRatingDtoRequest);
            sentenceDao.changeRating(voter, rating);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }

    }

    public ServerResponse deleteRating(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            Voter voter=voterDao.searchByTokenVoter(token);
            if(voter==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            AddRatingDtoRequest addRatingDtoRequest = Service.getObjectFromJson(requestJsonString, AddRatingDtoRequest.class);
            Rating rating = RatingMapper.MAPPER.toRating(addRatingDtoRequest);
            sentenceDao.deleteRating(voter, rating);
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), "");
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }
    }

    public ServerResponse getSentencesByVoter(String token, String requestJsonString) {
        try {
            if(token.isBlank() ||voterDao.searchByToken(token)==null) throw new ServerException(ResponseErrorData.WRONG_TOKEN);
            GetSentenceByVoterDtoRequest getSentenceByVoter = Service.getObjectFromJson(requestJsonString, GetSentenceByVoterDtoRequest.class);
            Set<Voter> voters = new HashSet<>(getSentenceByVoter.getVoterSet());
            GetAllSentencesDtoResponse getAllSentencesDtoResponse =
                    new GetAllSentencesDtoResponse(sentenceDao.getSentencesByVoter(voters));
            return new ServerResponse(ResponseCode.ACCEPTED.getResponseCode(), gson.toJson(getAllSentencesDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(ResponseCode.FAILED.getResponseCode(), e.getResponseErrorData().getErrorJson());
        }

    }

    public static void validateAddSentence(AddSentenceDtoRequest addSentenceDtoRequest) throws ServerException {
        if (addSentenceDtoRequest == null) throw new ServerException(ResponseErrorData.REQUEST_ERROR);
        if (addSentenceDtoRequest.getSentenceText().isBlank())
            throw new ServerException(ResponseErrorData.WRONG_SENTENCE);
        if (addSentenceDtoRequest.getUserId()==0)
            throw new ServerException(ResponseErrorData.WRONG_AUTHOR_OF_SENTENCE);
    }

    public static void validateAddRating(AddRatingDtoRequest addRatingDtoRequest) throws ServerException {
        if (addRatingDtoRequest == null) throw new ServerException(ResponseErrorData.REQUEST_ERROR);
        if (addRatingDtoRequest.getNumberOfRating() > 5 || addRatingDtoRequest.getNumberOfRating() < 1)
            throw new ServerException(ResponseErrorData.INVALID_RATING);
        if (addRatingDtoRequest.getSentenceId()==0)
            throw new ServerException(ResponseErrorData.WRONG_SENTENCE);
    }
}
