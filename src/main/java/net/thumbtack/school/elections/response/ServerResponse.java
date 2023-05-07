package net.thumbtack.school.elections.response;

import java.util.Objects;

public class ServerResponse {
    private int responseCode;
    private String responseData;

    public ServerResponse(int responseCode, String responseData) {
        this.responseCode = responseCode;
        this.responseData = responseData;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerResponse that = (ServerResponse) o;
        return responseCode == that.responseCode && Objects.equals(responseData, that.responseData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, responseData);
    }
}
