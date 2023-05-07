package net.thumbtack.school.misc.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorErrorCode;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.Point;
import net.thumbtack.school.figures.v3.Square;
import net.thumbtack.school.iface.v3.Colored;

import java.util.Objects;

public class ColoredSquare extends Square implements Colored {
    private Color color;

    public ColoredSquare(Point leftTop, int size, Color color) throws ColorException {
        super(leftTop, size);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredSquare(int xLeft, int yTop, int size, Color color) throws ColorException {
        this(new Point(xLeft, yTop), size, color);
    }

    public ColoredSquare(int size, Color color) throws ColorException {
        this(new Point(0, -size), size, color);
    }

    public ColoredSquare() throws ColorException {
        this(new Point(0, -1), 1, Color.RED);
    }

    public ColoredSquare(int size, String color) throws ColorException {
        this(size, Color.colorFromString(color));
    }

    public ColoredSquare(int xLeft, int yTop, int size, String color) throws ColorException {
        this(xLeft, yTop, size, Color.colorFromString(color));
    }

    public ColoredSquare(Point leftTop, int size, String color) throws ColorException {
        this(leftTop, size, Color.colorFromString(color));
    }

    @Override
    public void setColor(Color color) throws ColorException {
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(String colorString) throws ColorException {
        this.color = Color.colorFromString(colorString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ColoredSquare that = (ColoredSquare) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
