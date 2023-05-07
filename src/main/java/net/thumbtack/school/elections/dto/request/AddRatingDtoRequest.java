package net.thumbtack.school.elections.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.model.Sentence;
import net.thumbtack.school.elections.response.ResponseErrorData;

@Setter
@Getter
@NoArgsConstructor
public class AddRatingDtoRequest {
    private int numberOfRating;
    private int sentenceId;
    private int id;
    private int userId;
    public AddRatingDtoRequest(int numberOfRating,int userId,int sentenceId) throws ServerException {
        if(numberOfRating<1||numberOfRating>5) throw new ServerException(ResponseErrorData.INVALID_RATING);
        this.userId=userId;
        this.sentenceId=sentenceId;
        this.numberOfRating=numberOfRating;
    }
    public AddRatingDtoRequest(int numberOfRating,int userId,int sentenceId,int id) throws ServerException {
        this(numberOfRating,userId,sentenceId);
        this.id=id;
    }
}
