package net.thumbtack.school.elections.daoimpl;

import net.thumbtack.school.elections.dao.VoterDao;
import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.model.Candidate;
import net.thumbtack.school.elections.model.User;
import net.thumbtack.school.elections.model.Voter;

import java.util.HashSet;
import java.util.Set;

public class VoterDaoImpl implements VoterDao {

    private final DataBase database;

    public VoterDaoImpl() {
        database = DataBase.getInstance();
    }

    @Override
    public int register(Voter voter) throws ServerException {
        return database.registerVoter(voter);
    }

    @Override
    public String login(Voter voter) throws ServerException {
        return database.login(voter);
    }

    @Override
    public void logout(String token) throws ServerException {
        database.logout(token);
    }


    @Override
    public Voter searchByLogin(String login) throws ServerException {
        return database.searchByLogin(login);
    }

    @Override
    public User searchByToken(String token) throws ServerException {
        return database.searchByToken(token);
    }

    @Override
    public Voter searchByTokenVoter(String token) throws ServerException {
        return database.searchByTokenVoter(token);
    }

    @Override
    public void leaveServer(Voter voter) throws ServerException {
        database.leaveServer(voter);
    }

    @Override
    public Set<Voter> getAllVoters() throws ServerException {
        return database.getVoters();
    }

    @Override
    public void returnToServer(Voter voter) throws ServerException {
        database.returnToServer(voter);
    }

    public Voter searchByLoginNotActive(String login) throws ServerException {
        return database.searchByLoginNotActive(login);
    }

}
