package net.thumbtack.school.elections.exception;

import net.thumbtack.school.elections.response.ResponseErrorData;

public class ServerException extends Exception{
    private final ResponseErrorData responseErrorData;
    public ServerException(ResponseErrorData code) {
        this.responseErrorData=code;
    }

    public ResponseErrorData getResponseErrorData() {
        return responseErrorData;
    }
}
