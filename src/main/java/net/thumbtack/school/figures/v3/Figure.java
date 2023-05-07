package net.thumbtack.school.figures.v3;


import net.thumbtack.school.figures.v3.Point;
import net.thumbtack.school.iface.v3.HasArea;
import net.thumbtack.school.iface.v3.Movable;
import net.thumbtack.school.iface.v3.Resizable;

import java.io.Serializable;

public abstract class Figure implements Movable, Resizable, HasArea {


    public abstract double getPerimeter();

    public abstract boolean isInside(int x, int y);

    public boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }

}
