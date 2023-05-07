package net.thumbtack.school.elections.dto.response;

import net.thumbtack.school.elections.model.Voter;

import java.util.Set;

public class GetAllVotersDtoResponse {
    Set<Voter> voters;
    public GetAllVotersDtoResponse(Set<Voter> voters){
        this.voters=voters;
    }
}
