package net.thumbtack.school.elections.mapper;

import net.thumbtack.school.elections.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.model.Voter;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VoterMapper  {
    VoterMapper MAPPER = Mappers.getMapper(VoterMapper.class);
    Voter toVoter(RegisterVoterDtoRequest registerVoterDtoRequest);

    @InheritInverseConfiguration
    RegisterVoterDtoRequest fromVoter(Voter voter);
}
