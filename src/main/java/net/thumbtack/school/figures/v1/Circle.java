package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Circle {
    private Point center;
    private int radius;


    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public Circle(int xCenter, int yCenter, int radius) {
        this(new Point(xCenter, yCenter), radius);

    }

    public Circle(int radius) {
        this(new Point(0, 0), radius);
    }

    public Circle() {
        this(new Point(0, 0), 1);
    }


    public Point getCenter() {
        return this.center;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Point center) {
        this.center = center;
    }


    public void moveTo(int x, int y) {
        center.moveTo(x, y);
    }

    public void moveTo(Point point) {
        center.moveTo(point.getX(), point.getY());
    }


    public void moveRel(int dx, int dy) {
        center.moveRel(dx, dy);
    }


    public void resize(double ratio) {
        radius = (int) (radius * ratio);
    }


    public double getArea() {
        return (Math.PI * radius * radius);
    }


    public double getPerimeter() {
        return (2 * Math.PI * radius);
    }

    public boolean isInside(int x, int y) {
        return (x - center.getX()) * (x - center.getX()) +
                (y - center.getY()) * (y - center.getY()) <= radius * radius;
    }

    public boolean isInside(Point point) {
        return (point.getX() - center.getX()) * (point.getX() - center.getX()) +
                (point.getY() - center.getY()) * (point.getY() - center.getY()) <= radius * radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return radius == circle.radius && Objects.equals(center, circle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }
}