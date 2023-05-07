package net.thumbtack.school.figures.v3;

import net.thumbtack.school.iface.v3.Stretchable;

import java.io.*;
import java.util.Objects;

public class Rectangle extends Figure implements Stretchable, Serializable {
    private Point leftTop;
    private Point rightBottom;

    public Rectangle(Point leftTop, Point rightBottom) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom) {
        this(new Point(xLeft, yTop), new Point(xRight, yBottom));
    }

    public Rectangle(int length, int width) {
        this(new Point(0, -width), new Point(length, 0));
    }

    public Rectangle() {
        this(new Point(0, -1), new Point(1, 0));
    }

    public Point getTopLeft() {
        return leftTop;
    }

    public Point getBottomRight() {
        return rightBottom;
    }

    public void setTopLeft(Point topLeft) {
        this.leftTop = topLeft;
    }

    public void setBottomRight(Point bottomRight) {
        this.rightBottom = bottomRight;
    }

    public int getLength() {
        return (rightBottom.getX() - leftTop.getX());
    }

    public int getWidth() {
        return (rightBottom.getY() - leftTop.getY());
    }

    public void moveTo(int x, int y) {
        int length = getLength();
        int width = getWidth();
        leftTop.moveTo(x, y);
        rightBottom.moveTo(x + length, y + width);
    }


    public void moveRel(int dx, int dy) {
        leftTop.moveRel(dx, dy);
        rightBottom.moveRel(dx, dy);
    }

    public void resize(double ratio) {
        int length = (int) (getLength() * ratio);
        int width = (int) (getWidth() * ratio);
        rightBottom.moveTo(leftTop.getX() + length, leftTop.getY() + width);
    }

    public void stretch(double xRatio, double yRatio) {
        int length = (int) (getLength() * xRatio);
        int width = (int) (getWidth() * yRatio);
        rightBottom.moveTo(leftTop.getX() + length, leftTop.getY() + width);
    }

    public double getArea() {
        return getLength() * getWidth();
    }

    public double getPerimeter() {
        return (2 * (getWidth() + getLength()));
    }

    public boolean isInside(int x, int y) {
        return (x <= rightBottom.getX() && x >= leftTop.getX() && y <= rightBottom.getY() && y >= leftTop.getY());
    }


    public boolean isIntersects(Rectangle rectangle) {

        return !(rectangle.rightBottom.getX() < leftTop.getX() || rightBottom.getX() < rectangle.leftTop.getX()
                || rectangle.rightBottom.getY() < leftTop.getY() || rightBottom.getY() < rectangle.leftTop.getY());

    }

    public boolean isInside(Rectangle rectangle) {
        return (isInside(rectangle.leftTop.getX(), rectangle.leftTop.getY())
                && isInside(rectangle.rightBottom.getX(), rectangle.rightBottom.getY()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(leftTop, rectangle.leftTop) && Objects.equals(rightBottom, rectangle.rightBottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftTop, rightBottom);
    }


}
