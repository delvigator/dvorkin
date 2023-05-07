package net.thumbtack.school.elections.service.templ;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.elections.exception.ServerException;
import net.thumbtack.school.elections.response.ResponseErrorData;

public class Service {
    public static <T> T getObjectFromJson(String requestJsonString, Class<T> classObj) throws ServerException {
        try {
            Gson gson = new Gson();
            if ((requestJsonString.isBlank())) {
                throw new JsonSyntaxException("");
            }
            return gson.fromJson(requestJsonString, classObj);
        } catch (JsonSyntaxException ex) {
            throw new ServerException(ResponseErrorData.WRONG_JSON);
        }
    }
}
