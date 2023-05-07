package net.thumbtack.school.figures.v3;


import java.util.Objects;


public class Square extends Figure {
    private Point leftTop;
    private int size;

    public Square(Point leftTop, int size) {
        this.leftTop = leftTop;
        this.size = size;
    }

    public Square(int xLeft, int yTop, int size) {
        this(new Point(xLeft, yTop), size);
    }

    public Square(int size) {
        this(new Point(0, -size), size);

    }

    public Square() {
        this(new Point(0, -1), 1);
    }


    public Point getTopLeft() {
        return this.leftTop;
    }

    public Point getBottomRight() {
        return new Point(leftTop.getX() + size, leftTop.getY() + size);
    }

    public void setTopLeft(Point topLeft) {
        this.leftTop = topLeft;

    }


    public int getLength() {
        return size;
    }

    public void moveTo(int x, int y) {
        leftTop.moveTo(x, y);

    }

    public void moveTo(Point point) {
        leftTop.moveTo(point.getX(), point.getY());
    }

    public void moveRel(int dx, int dy) {
        leftTop.moveRel(dx, dy);
    }


    public void resize(double ratio) {
        this.size = (int) (getLength() * ratio);
    }


    public double getArea() {
        return size * size;
    }


    public double getPerimeter() {
        return 4 * size;
    }

    public boolean isInside(int x, int y) {
        return (x <= getBottomRight().getX() && x >= leftTop.getX()
                && y <= getBottomRight().getY() && y >= leftTop.getY());
    }


    public boolean isIntersects(Square square) {
        return !(square.getBottomRight().getX() < leftTop.getX() || getBottomRight().getX() < square.leftTop.getX()
                || square.getBottomRight().getY() < leftTop.getY() || getBottomRight().getY() < square.leftTop.getY());
    }


    public boolean isInside(Square square) {
        return (isInside(square.leftTop.getX(), square.leftTop.getY())
                && isInside(square.getBottomRight().getX(), square.getBottomRight().getY()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return size == square.size && Objects.equals(leftTop, square.leftTop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftTop, size);
    }
}
