package net.thumbtack.school.elections.database;


import net.thumbtack.school.elections.model.*;
import net.thumbtack.school.elections.response.ResponseErrorData;
import net.thumbtack.school.elections.exception.ServerException;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.text.Bidi;
import java.util.*;

public class DataBase {
    private static final DataBase instance = new DataBase();
    private final BidiMap<String, User> tokens;
    // REVU нет, неудачное решение
    // по токену находим User, и хватит
    // а чтобы по User найти Voter, у Вас есть voters
    private final BidiMap<String, Voter> tokensVoter;
    // REVU тоже не надо по той же причине
    private final Map<String, Voter> votersLog;
    // REVU смените имя. Log - это протоколирование увуих-то действий, обычно в файл
    // userByLogin, например
    private final Map<String, User> usersLog;
    private final Map<Integer, Voter> voters;//userId->voter
    private final Map<Integer, User> users; //userId->user

    // REVU ладно, оставьте. Хотя лучше было бы от них избавиться
    private final Map<Integer, Voter> notActiveVoters; //userId->nAUser
    private final Map<String, Voter> notActiveVotersLog;
    private final Map<Integer, Candidate> candidates; //userId->candidate
    private final List<Sentence> sentences;
    private final List<Rating> ratings;
    private int nextId = 1;
    private int nextIdRating = 1;

    private int nextIdSentence=1;
    Comparator<Sentence> comparator = (s1, s2) -> Double.compare(s1.getAverageRating(), s2.getAverageRating());

    public DataBase() {
        this.users = new HashMap<>();
        this.notActiveVoters = new HashMap<>();
        voters = new HashMap<>();
        tokens = new DualHashBidiMap<>();
        candidates = new HashMap<>();
        sentences = new ArrayList<>();
        ratings = new ArrayList<>();
        votersLog = new HashMap<>();
        usersLog = new HashMap<>();
        notActiveVotersLog = new HashMap<>();
        tokensVoter = new DualHashBidiMap<>();
    }

    public static DataBase getInstance() {
        return instance;
    }

    public int registerVoter(Voter voter) throws ServerException {

        if (usersLog.putIfAbsent(voter.getUser().getLogin(), voter.getUser()) != null
                || votersLog.putIfAbsent(voter.getUser().getLogin(), voter) != null)
            throw new ServerException(ResponseErrorData.INVALID_REGISTER);
        voter.getUser().setId(nextId++);
        voters.put(voter.getUser().getId(), voter);
        users.put(voter.getUser().getId(), voter.getUser());
        return voter.getUser().getId();
    }

    public String login(Voter voter) throws ServerException {
        if (tokens.containsValue(voter.getUser())) return tokens.getKey(voter.getUser());
        String token = UUID.randomUUID().toString();
        tokens.put(token, voter.getUser());
        tokensVoter.put(token, voter);
        return token.toString();
    }

    public void logout(String token) throws ServerException {
        tokens.remove(token);
        tokensVoter.remove(token);
    }

    public Voter searchByLogin(String login) throws ServerException {
        if(!votersLog.containsKey(login)) return null;
        return votersLog.get(login);

    }

    public Candidate getCandidateByToken(String token) {
        User user = tokens.get(token);
        if (!candidates.containsKey(user.getId())) return null;
        return candidates.get(user.getId());
    }
    public Candidate getCandidateByLogin(String login) {
        User user=usersLog.get(login);
        if (!candidates.containsKey(user.getId())) return null;
        return candidates.get(user.getId());
    }
    public Voter searchByLoginNotActive(String login) throws ServerException {
        if(!notActiveVotersLog.containsKey(login)) return null;
        return notActiveVotersLog.get(login);
    }

    public User searchByToken(String token) throws ServerException {
        return tokens.get(token);
    }

    public Voter searchByTokenVoter(String token) throws ServerException {
        return tokensVoter.get(token);
    }


    public HashSet<Candidate> getCandidates() {
        return new HashSet<>(candidates.values());
    }


    public void addCandidate(Voter voter, Candidate candidate) throws ServerException {
        if (!users.containsValue(candidate.getUser())) throw new ServerException(ResponseErrorData.WRONG_CANDIDATE);
        if (voter.getUser().equals(candidate.getUser())) {
            candidate.setUser(users.get(candidate.getUser().getId()));
            candidate.setAgreement(true);
            candidates.put(candidate.getUser().getId(), candidate);
        }
        for (Sentence i : sentences) {
            if (i.getUserId() == candidate.getUser().getId()) candidate.getProgram().addProgram(i);
        }
        candidate.setUser(users.get(candidate.getUser().getId()));
        candidates.put(candidate.getUser().getId(), candidate);

    }


    public int addSentence(Sentence sentence) {
        sentence.setSentenceId(nextIdSentence);
        nextIdSentence++;
        sentences.add(sentence);
        return sentence.getSentenceId();
    }

    public List<Sentence> getAllSentences() {
        List<Sentence> sorted = new ArrayList<>(sentences);
        sorted.sort(comparator.reversed());
        return sorted;
    }

    public HashSet<Voter> getVoters() {
        return new HashSet<>(voters.values());
    }

    public void deleteCandidate(Candidate candidate) throws ServerException {
        try {
            candidates.remove(candidate.getUser().getId());
        } catch (NullPointerException e) {
            throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
        }
    }

    public void addSentenceInProgram(Candidate candidate, Sentence sentence) throws ServerException {
        if (!sentences.contains(sentence)) throw new ServerException(ResponseErrorData.WRONG_SENTENCE);
        if (!candidates.containsValue(candidate)) throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
        candidate.getProgram().addProgram(sentence);
    }

    public int addRating(Voter voter, Rating rating) throws ServerException {
        for (Sentence i : sentences) {
            //если рейтинг от этого автора уже есть
            if (i.getRatingByUserId(voter.getUser().getId()) != null & i.getSentenceId() == rating.getSentenceId())
                throw new ServerException(ResponseErrorData.CANT_BE_ADDED);
            if (i.getSentenceId() == rating.getSentenceId()) i.addRating(rating);
        }
        rating.setId(nextIdRating);
        nextIdRating++;
        ratings.add(rating);
        return rating.getId();
    }

    public void changeRating(Voter voter, Rating rating) throws ServerException {
        if (rating.getUserId() != voter.getUser().getId())
            throw new ServerException(ResponseErrorData.NOT_AUTHOR_OF_RATING);
        for (Rating i : ratings) {
            if (i.getId() == rating.getId())
                i.setNumberOfRating(rating.getNumberOfRating());
        }
    }

    public void deleteRating(Voter voter, Rating rating) throws ServerException {
        for (Sentence i : sentences) {
            //если пытается удалить автор предложения
            if (i.getRatingById(rating.getId()) != null) {
                if (i.getUserId() == rating.getUserId() || voter.getUser().getId() != rating.getUserId())
                    throw new ServerException(ResponseErrorData.CANT_BE_DELETED);

                i.deleteRating(rating);
            }
        }
        ratings.remove(rating);

    }

    public void leaveServer(Voter voter) throws ServerException {
        User user = voter.getUser();
        notActiveVoters.put(user.getId(), voter);
        users.remove(user.getId());
        usersLog.remove(user.getLogin());
        votersLog.remove(user.getLogin());
        voters.remove(user.getId());
        tokens.remove(tokens.getKey(voter.getUser()));
        tokensVoter.remove(tokensVoter.getKey(voter));
        notActiveVoters.put(user.getId(), voter);
        notActiveVotersLog.put(user.getLogin(), voter);
        for (Sentence i : sentences) {
            Rating ratingByUsername = i.getRatingByUserId(user.getId());
            if (ratingByUsername != null) i.deleteRating(ratingByUsername);
        }
        ratings.removeIf(i -> i.getUserId() == user.getId());
    }

    public void returnToServer(Voter voter) {
        Voter voterT = notActiveVoters.get(voter.getUser().getId());
        voters.put(voterT.getUser().getId(), voterT);
        users.put(voterT.getUser().getId(), voterT.getUser());
        votersLog.put(voter.getUser().getLogin(),voter);
        usersLog.put(voter.getUser().getLogin(),voter.getUser());
        notActiveVoters.remove(voter.getUser().getId());
        notActiveVotersLog.remove(voter.getUser().getLogin(),voter);
    }

    public void changeAgreement(Candidate candidate) throws ServerException {
        User user = candidate.getUser();
        if (!candidates.containsKey(user.getId())) throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
        candidates.get(user.getId()).changeAgreement();
    }

    public void deleteSentenceFromProgram(Candidate candidate, Sentence sentence) throws ServerException {
        if (!sentences.contains(sentence)) throw new ServerException(ResponseErrorData.WRONG_SENTENCE);
        User user = candidate.getUser();
        if (!candidates.containsKey(user.getId())) throw new ServerException(ResponseErrorData.CANDIDATE_NOT_FOUND);
        if (sentence.getUserId() == user.getId()) throw new ServerException(ResponseErrorData.CANT_BE_DELETED);
        candidates.get(user.getId()).getProgram().deleteProgram(sentence);
    }

    private Set<Sentence> getSentenceByVoter(Voter voter) {
        Set<Sentence> res = new HashSet<>();
        for (Sentence i : sentences) {
            if (i.getUserId() == voter.getUser().getId()) res.add(i);
        }
        return res;
    }

    public List<Sentence> getSentencesByVoter(Set<Voter> voters) {
        List<Sentence> res = new ArrayList<>();
        for (Voter i : voters) {
            res.addAll(getSentenceByVoter(i));
        }
        return res;
    }


}
