package net.thumbtack.school.elections.dao;

import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.User;
import net.thumbtack.school.elections.model.Voter;

import java.util.Set;

public interface VoterDao {
    int register(Voter voter) throws ServerException;

    String login(Voter voter) throws ServerException;

    void logout(String token) throws ServerException;

    Voter searchByLogin(String login) throws ServerException;

    User searchByToken(String token) throws ServerException;
     Voter searchByTokenVoter(String token) throws ServerException;
    void leaveServer(Voter voter) throws ServerException;

    Set<Voter> getAllVoters() throws ServerException;
    void returnToServer(Voter voter) throws ServerException;
    Voter searchByLoginNotActive(String login) throws ServerException;
}
