package net.thumbtack.school.colors.v3;

public enum Color {
    RED,
    GREEN,
    BLUE;

    public static Color colorFromString(String colorString) throws ColorException {
        if (colorString == null || colorString.equals("")) throw new ColorException(ColorErrorCode.NULL_COLOR);
        for (Color counter : Color.values()) {
            if (colorString.equalsIgnoreCase(counter.toString())) return counter;
        }
        throw new ColorException(ColorErrorCode.WRONG_COLOR_STRING);
    }
}
