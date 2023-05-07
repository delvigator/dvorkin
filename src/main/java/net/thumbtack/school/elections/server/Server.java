package net.thumbtack.school.elections.server;

import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.response.ServerResponse;
import net.thumbtack.school.elections.service.CandidateService;
import net.thumbtack.school.elections.service.SentenceService;
import net.thumbtack.school.elections.service.VoterService;

public class Server {
    private final VoterService voterService = new VoterService();
    private final CandidateService candidateService = new CandidateService();
    private final SentenceService sentenceService = new SentenceService();

    public ServerResponse registerVoter(String requestJsonString) {
        return voterService.registerVoter(requestJsonString);
    }

    public ServerResponse login(String requestJsonString) {
        return voterService.login(requestJsonString);
    }

    public ServerResponse logout(String token, String requestJsonString) {
        return voterService.logout(token, requestJsonString);
    }

    public ServerResponse getAllCandidates(String token, String requestJsonString) {
        return candidateService.getAllCandidates(token, requestJsonString);
    }

    public ServerResponse addCandidate(String token, String requestJsonString) {
        return candidateService.addCandidate(token, requestJsonString);
    }

    public ServerResponse deleteCandidate(String token, String requestJsonString) {
        return candidateService.deleteCandidate(token, requestJsonString);
    }

    public ServerResponse getAllVoters(String token, String requestJsonString) {
        return voterService.getAllVoters(token, requestJsonString);
    }

    public ServerResponse addSentence(String token, String requestJsonString) {
        return sentenceService.addSentence(token, requestJsonString);
    }

    public ServerResponse getAllSentences(String token, String requestJsonString) {
        return sentenceService.getAllSentences(token, requestJsonString);
    }

    public ServerResponse addSentenceInProgram(String token, String requestJsonString)  {
        return sentenceService.addSentenceInProgram(token, requestJsonString);
    }

    public ServerResponse addRating(String token, String requestJsonString) {
        return sentenceService.addRating(token, requestJsonString);
    }

    public ServerResponse deleteRating(String token, String requestJsonString) {
        return sentenceService.deleteRating(token, requestJsonString);
    }

    public ServerResponse deleteSentenceFromProgram(String token, String requestJsonString)  {
        return sentenceService.deleteSentenceFromProgram(token, requestJsonString);
    }

    public ServerResponse changeRating(String token, String requestJsonString) {
        return sentenceService.changeRating(token, requestJsonString);
    }

    public ServerResponse getSentencesByVoter(String token, String requestJsonString) {
        return sentenceService.getSentencesByVoter(token, requestJsonString);
    }
  public ServerResponse changeAgreement(String token,String requestJsonString){
        return candidateService.changeAgreement(token,requestJsonString);
  }
  public ServerResponse leaveServer(String token,String requestJsonString){
      return voterService.leaveServer(token,requestJsonString);
  }
    public ServerResponse returnToServer(String requestJsonString){
        return voterService.returnToServer(requestJsonString);
    }
    public ServerResponse getCandidate(String token,String requestJsonString){
        return candidateService.getCandidate(token,requestJsonString);
    }
    public ServerResponse getVoter(String token,String requestJsonString){
        return voterService.getVoter(token,requestJsonString);
    }
}
