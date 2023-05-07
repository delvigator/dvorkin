package net.thumbtack.school.misc.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorErrorCode;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.Ellipse;
import net.thumbtack.school.figures.v3.Point;
import net.thumbtack.school.iface.v3.Colored;

import java.util.Objects;

public class ColoredEllipse extends Ellipse implements Colored {
    private Color color;

    public ColoredEllipse(Point center, int xAxis, int yAxis, Color color) throws ColorException {
        super(center, xAxis, yAxis);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredEllipse(int xCenter, int yCenter, int xAxis, int yAxis, Color color) throws ColorException {
        this(new Point(xCenter, yCenter), xAxis, yAxis, color);
    }

    public ColoredEllipse(int xAxis, int yAxis, Color color) throws ColorException {
        this(new Point(0, 0), xAxis, yAxis, color);
    }

    public ColoredEllipse() throws ColorException {
        this(new Point(0, 0), 1, 1, Color.RED);
    }

    public ColoredEllipse(Color color) throws ColorException {
        this(new Point(0, 0), 1, 1, color);
    }

    public ColoredEllipse(String str) throws ColorException {
        this(Color.colorFromString(str));
    }

    public ColoredEllipse(int xAxis, int yAxis, String str) throws ColorException {
        this(xAxis, yAxis, Color.colorFromString(str));
    }

    public ColoredEllipse(int xCenter, int yCenter, int xAxis, int yAxis, String color) throws ColorException {
        this(xCenter, yCenter, xAxis, yAxis, Color.colorFromString(color));
    }

    public ColoredEllipse(Point center, int xAxis, int yAxis, String color) throws ColorException {
        this(center, xAxis, yAxis, Color.colorFromString(color));
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
        ColoredEllipse that = (ColoredEllipse) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
