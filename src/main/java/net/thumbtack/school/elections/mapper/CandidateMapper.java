package net.thumbtack.school.elections.mapper;

import net.thumbtack.school.elections.dto.request.AddCandidatesDtoRequest;

import net.thumbtack.school.elections.model.Candidate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CandidateMapper {
    CandidateMapper MAPPER = Mappers.getMapper(CandidateMapper.class);
    Candidate toCandidate(AddCandidatesDtoRequest addCandidatesDtoRequest);

    @InheritInverseConfiguration
    AddCandidatesDtoRequest fromCandidate(Candidate candidate);
}
