package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.dto.request.AddSentenceDtoRequest;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Rating;
import net.thumbtack.school.elections.model.Sentence;
import net.thumbtack.school.elections.model.Voter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SentenceDao {
    int addSentence(Sentence sentence) throws ServerException;
//    void deleteSentence(String token, Sentence sentence) throws ServerException;
    List<Sentence> getAllSentences() throws ServerException;
    void addSentenceInProgram(Candidate candidate,Sentence sentence) throws ServerException;
    int addRating(Voter voter,Rating rating) throws ServerException;
    void deleteRating(Voter voter,Rating rating) throws ServerException;
    void deleteSentenceFromProgram(Candidate candidate,Sentence sentence) throws ServerException;
    void changeRating(Voter voter, Rating rating) throws ServerException;

    List<Sentence> getSentencesByVoter(Set<Voter> voter) throws ServerException;

}
