package net.thumbtack.school.boxes.v3;

import net.thumbtack.school.figures.v3.Figure;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.iface.v3.HasArea;

public class Box<T extends HasArea> implements HasArea {
    private T content;

    public Box(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public double getArea() {
        return content.getArea();
    }

    public boolean isAreaEqual(Box<? extends HasArea> box) {
        return Math.abs(this.getArea() - box.getArea()) < 1E-6;
    }

    public static boolean isAreaEqual(Box<? extends HasArea> box1, Box<? extends HasArea> box2) {
        return Math.abs(box1.getArea() - box2.getArea()) < 1E-6;
    }

}
