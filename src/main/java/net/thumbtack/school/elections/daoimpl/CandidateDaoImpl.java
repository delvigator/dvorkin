package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.CandidateDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Program;
import net.thumbtack.school.elections.model.Sentence;
import net.thumbtack.school.elections.model.Voter;

import java.util.Collections;
import java.util.HashSet;

public class CandidateDaoImpl implements CandidateDao
{
    private final DataBase database;

    public CandidateDaoImpl() {
        database = DataBase.getInstance();
    }
    @Override
    public HashSet<Candidate> getAllCandidates() throws ServerException {
        return database.getCandidates();
    }

    @Override
    public Candidate getCandidateByToken(String token) {
        return database.getCandidateByToken(token);
    }

    @Override
    public Candidate getCandidateByLogin(String login) {
        return null;
    }


    @Override
    public void addCandidate(Voter voter, Candidate candidate) throws ServerException {
        database.addCandidate(voter, candidate);
    }

    @Override
    public void deleteCandidate(Candidate candidate) throws ServerException {
        database.deleteCandidate(candidate);
    }

    @Override
    public void changeAgreement(Candidate candidate) throws ServerException {
    database.changeAgreement(candidate);
    }


}
