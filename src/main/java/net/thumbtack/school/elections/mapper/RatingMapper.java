package net.thumbtack.school.elections.mapper;

import net.thumbtack.school.elections.dto.request.AddRatingDtoRequest;
import net.thumbtack.school.elections.dto.request.RegisterVoterDtoRequest;
import net.thumbtack.school.elections.model.Rating;
import net.thumbtack.school.elections.model.Voter;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface RatingMapper {
    RatingMapper MAPPER = Mappers.getMapper(RatingMapper.class);
    Rating toRating(AddRatingDtoRequest addRatingDtoRequest);

    @InheritInverseConfiguration
    AddRatingDtoRequest fromRating(Rating rating);
}
