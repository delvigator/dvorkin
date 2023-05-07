package net.thumbtack.school.boxes.v3;

import net.thumbtack.school.figures.v3.Figure;
import net.thumbtack.school.iface.v3.HasArea;

public class PairBox<T extends HasArea, V extends HasArea> implements HasArea {
    private T contentFirst;
    private V contentSecond;

    public PairBox(T contentFirst, V contentSecond) {
        this.contentFirst = contentFirst;
        this.contentSecond = contentSecond;

    }

    @Override
    public double getArea() {
        return ((HasArea) contentFirst).getArea() + ((HasArea) contentSecond).getArea();
    }

    public T getContentFirst() {
        return contentFirst;
    }

    public void setContentFirst(T contentFirst) {
        this.contentFirst = contentFirst;
    }

    public V getContentSecond() {
        return contentSecond;
    }

    public void setContentSecond(V contentSecond) {
        this.contentSecond = contentSecond;
    }

    public boolean isAreaEqual(PairBox<? extends HasArea, ? extends HasArea> box) {
        return Math.abs(this.getArea() - box.getArea()) < 1E-6;
    }

    public static boolean isAreaEqual(PairBox<? extends HasArea, ? extends HasArea> box1,
                                      PairBox<? extends HasArea, ? extends HasArea> box2) {
        return Math.abs(box1.getArea() - box2.getArea()) < 1E-6;
    }

}