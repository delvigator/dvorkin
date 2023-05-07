package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.Program;
import net.thumbtack.school.elections.model.Sentence;
import net.thumbtack.school.elections.model.Voter;

import java.util.Set;

public interface CandidateDao{
    Set<Candidate> getAllCandidates() throws ServerException;
    Candidate getCandidateByToken(String token);
    Candidate getCandidateByLogin(String login);
    void addCandidate(Voter voter, Candidate candidate) throws ServerException;
    void deleteCandidate(Candidate candidate) throws ServerException;
    void changeAgreement(Candidate candidate) throws ServerException;

}
