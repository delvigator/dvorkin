package net.thumbtack.school.boxes.v3;

import net.thumbtack.school.iface.v3.HasArea;

public class ArrayBox<Box> {
    private Box[] content;

    public ArrayBox(Box[] array) {
        this.content = array;
    }

    public Box[] getContent() {
        return content;
    }

    public void setContent(Box[] array) {
        this.content = array;
    }

    public Box getElement(int i) {
        return content[i];
    }

    public void setElement(int i, Box box) {
        content[i] = box;
    }

    // REVU поточнее ArrayBox<? extends что-то>
    public boolean isSameSize(ArrayBox<?> arrayBox) {
        return content.length == arrayBox.content.length;
    }
}
