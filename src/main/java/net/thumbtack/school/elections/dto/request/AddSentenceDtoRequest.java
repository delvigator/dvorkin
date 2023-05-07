package net.thumbtack.school.elections.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.elections.model.Rating;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class AddSentenceDtoRequest {
    int  userId;
    String sentenceText;
    int sentenceId;
    List<Rating> rating;
    double averageRating;
    public AddSentenceDtoRequest(int userId, String sentenceText,int sentenceId) {
        this.userId = userId;
        this.sentenceText = sentenceText;
        this.rating = new ArrayList<>();
        this.averageRating = getAverageRating();
        if(sentenceId!=0)this.sentenceId=sentenceId;
    }
//    public AddSentenceDtoRequest(String username, String sentenceText,String sentenceId){
//        this(username,sentenceText);
//        this.sentenceId=sentenceId;
//    }
    public void addRating(Rating rating){
        this.averageRating=getAverageRating();
        this.rating.add(rating);
    }
    public double getAverageRating() {

        return this.rating.stream()
                .mapToInt(Rating::getNumberOfRating)
                .average().orElse(0);
    }

}
