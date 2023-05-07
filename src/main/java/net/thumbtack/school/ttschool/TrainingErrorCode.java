package net.thumbtack.school.ttschool;

public enum TrainingErrorCode {
    TRAINEE_WRONG_RATING("rating must be in interval 1-5"),
    TRAINEE_WRONG_FIRSTNAME("firstname is wrong"),
    TRAINEE_WRONG_LASTNAME("lastname is wrong"),
    GROUP_WRONG_NAME("group name is wrong"),
    GROUP_WRONG_ROOM("room is wrong"),
    TRAINEE_NOT_FOUND("trainee not found"),
    SCHOOL_WRONG_NAME("school name is wrong"),
    DUPLICATE_GROUP_NAME("has a duplicate"),
    GROUP_NOT_FOUND("group not found"),
    DUPLICATE_TRAINEE("has a duplicate"),
    EMPTY_TRAINEE_QUEUE("que is empty");
    private String errorString;

    TrainingErrorCode(String str) {
        this.errorString = str;
    }

    public String getErrorString() {
        return errorString;
    }
}
