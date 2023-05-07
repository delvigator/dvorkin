package net.thumbtack.school.elections.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Sentence {
    private int userId;
    private String sentenceText;


    private int sentenceId;
    private List<Rating> rating;

    double averageRating;

    public Sentence(int userId, String sentenceText) {
        this.userId = userId;
        this.sentenceText = sentenceText;
        this.rating = new ArrayList<>();
        this.averageRating = getAverageRating();

    }

    public void addRating(Rating rating) {
        this.rating.add(rating);
        this.averageRating = getAverageRating();

    }

    public void deleteRating(Rating rating) {
        this.rating.remove(rating);
        this.averageRating = getAverageRating();

    }


    public double getAverageRating() {

        return this.rating.stream()
                .mapToInt(Rating::getNumberOfRating)
                .average().orElse(0);
    }

    public Rating getRatingById(int id) {
        for (Rating i : rating) {
            if (i.getId()==id) return i;
        }
        return null;
    }
    public Rating getRatingByUserId(int id) {
        for (Rating i : rating) {
            if (i.getUserId()==id) return i;
        }
        return null;
    }

}
