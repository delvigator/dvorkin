package net.thumbtack.school.elections.model;

import lombok.*;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.response.ResponseErrorData;
@NoArgsConstructor
@Data
@Getter
@Setter
@EqualsAndHashCode
public class Rating {
    private int numberOfRating;
    private int sentenceId;
    private int id;
    private int userId;
    public Rating(int numberOfRating,int userId,int sentenceId) throws ServerException {
        if(numberOfRating<1||numberOfRating>5) throw new ServerException(ResponseErrorData.INVALID_RATING);
        this.userId=userId;
        this.sentenceId=sentenceId;
        this.numberOfRating=numberOfRating;
    }

    public Rating(int numberOfRating,int userId,int sentenceId,int id) throws ServerException {
        this(numberOfRating,userId,sentenceId);
        this.id=id;
    }

}
