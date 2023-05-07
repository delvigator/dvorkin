package net.thumbtack.school.elections.dto.response;

import net.thumbtack.school.elections.database.DataBase;
import net.thumbtack.school.elections.model.Sentence;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetAllSentencesDtoResponse {
    private List<Sentence> sentences;
    public GetAllSentencesDtoResponse(List<Sentence> sentences){
        this.sentences=sentences;
    }
}
