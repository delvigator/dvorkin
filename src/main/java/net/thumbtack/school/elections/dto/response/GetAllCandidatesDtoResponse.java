package net.thumbtack.school.elections.dto.response;

import net.thumbtack.school.elections.model.Candidate;

import java.util.Set;

public class GetAllCandidatesDtoResponse {
    private Set<Candidate> candidates;

    public GetAllCandidatesDtoResponse(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

}
