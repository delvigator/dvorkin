package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.SentenceDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Rating;
import net.thumbtack.school.elections.model.Sentence;
import net.thumbtack.school.elections.model.Voter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SentenceDaoImpl implements SentenceDao {
    private final DataBase database;

    public SentenceDaoImpl() {
        database = DataBase.getInstance();
    }

    @Override
    public int addSentence(Sentence sentence) throws ServerException {
        return database.addSentence(sentence);
    }


    @Override
    public List<Sentence> getAllSentences() throws ServerException {
        return database.getAllSentences();
    }

    @Override
    public void addSentenceInProgram(Candidate candidate, Sentence sentence) throws ServerException {
        database.addSentenceInProgram(candidate,sentence);
    }

    @Override
    public int addRating(Voter voter, Rating rating) throws ServerException {
        return database.addRating(voter, rating);
    }

    @Override
    public void deleteRating(Voter voter, Rating rating) throws ServerException {
        database.deleteRating(voter, rating);
    }

    @Override
    public void deleteSentenceFromProgram(Candidate candidate, Sentence sentence) throws ServerException {
    database.deleteSentenceFromProgram(candidate,sentence);
    }

    @Override
    public void changeRating(Voter voter, Rating rating) throws ServerException {
    database.changeRating(voter,rating);
    }

    @Override
    public List<Sentence> getSentencesByVoter(Set<Voter> voters) throws ServerException {
       return database.getSentencesByVoter(voters);
    }

}
