package net.thumbtack.school.elections.response;

import com.google.gson.Gson;

public enum ResponseErrorData {
    INVALID_FIRST_NAME("Невалидная фамилия"),
    INVALID_LAST_NAME("Невалидное имя"),
    INVALID_STREET("Невалидная улица"),
    INVALID_HOUSE("Невалидный дом"),
    INVALID_LOGIN("Невалидный логин"),
    INVALID_PASSWORD("Невалидный пароль"),

    WRONG_TOKEN("Невалидный токен"),
    INVALID_REGISTER("Этот пользователь уже зарегистрирован"),
    INVALID_RATING("Невалидный рейтинг"),
    WRONG_CANDIDATE("Ошибка при добавлении кандидата"),
    WRONG_JSON("Неверный json"),
    CANDIDATE_NOT_FOUND("Кандидатура не найдена"),
    CANT_BE_DELETED("Ошибка удаления"),
    WRONG_VOTER("Неверный избиратель"),
    CANDIDATE_LEAVE_ERROR("Сначала необходимо снять кандидатуру"),
    WRONG_SENTENCE("Невалидное предложение"),
    NOT_AUTHOR_OF_RATING("Пользователь не является автором оценки"),
    CANT_BE_CHANGED("Не может быть изменено"),
    CANT_BE_ADDED("Не может быть добавлено"),
    WRONG_AUTHOR_OF_SENTENCE("Невалидный автор предложения"),
    WRONG_AUTHOR_OF_RATING("Невалидный автор оценки"),
    REQUEST_ERROR("Ошибка запроса");

    private final String error;

    ResponseErrorData(String error) {
        this.error = error;
    }

    public String getErrorJson() {
        Gson gson = new Gson();
        return this.error;
        //return gson.toJson(this.error);
    }

    public String getError() {
        return error;
    }
}
