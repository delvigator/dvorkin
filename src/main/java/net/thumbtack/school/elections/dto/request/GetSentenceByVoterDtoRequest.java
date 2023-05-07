package net.thumbtack.school.elections.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.elections.model.Voter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class GetSentenceByVoterDtoRequest {
    Set<Voter> voterSet;

    public GetSentenceByVoterDtoRequest(Set<Voter> voterSet) {
        this.voterSet = voterSet;
    }
}
