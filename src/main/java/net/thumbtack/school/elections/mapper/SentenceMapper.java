package net.thumbtack.school.elections.mapper;


import net.thumbtack.school.elections.dto.request.AddSentenceDtoRequest;

import net.thumbtack.school.elections.model.Sentence;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SentenceMapper {

        SentenceMapper MAPPER = Mappers.getMapper(SentenceMapper.class);
        Sentence toSentence(AddSentenceDtoRequest addSentenceDtoRequest);

        @InheritInverseConfiguration
        AddSentenceDtoRequest fromSentence(Sentence sentence);
}