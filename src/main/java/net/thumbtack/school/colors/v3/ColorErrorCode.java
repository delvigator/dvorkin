package net.thumbtack.school.colors.v3;

public enum ColorErrorCode {
    WRONG_COLOR_STRING("wrong color"), NULL_COLOR("null color");
    private String errorString;

    ColorErrorCode(String str) {
        this.errorString = str;
    }

    public String getErrorString() {
        return errorString;
    }
}
