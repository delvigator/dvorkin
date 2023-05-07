package net.thumbtack.school.misc.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorErrorCode;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.Point;
import net.thumbtack.school.iface.v3.Colored;

import java.util.Objects;

public class ColoredPoint extends Point implements Colored {
    private Color color;

    public ColoredPoint(int x, int y, Color color) throws ColorException {
        super(x, y);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredPoint(Color color) throws ColorException {
        this(0, 0, color);
    }

    public ColoredPoint(String color) throws ColorException {
        this(Color.colorFromString(color));
    }

    public ColoredPoint(int x, int y, String color) throws ColorException {
        this(x, y, Color.colorFromString(color));
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
        ColoredPoint that = (ColoredPoint) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
