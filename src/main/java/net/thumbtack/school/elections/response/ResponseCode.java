package net.thumbtack.school.elections.response;

public enum ResponseCode {

    ACCEPTED(200),
    FAILED(400);

    private int responseCode;
    ResponseCode(int code){
        this.responseCode=code;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
