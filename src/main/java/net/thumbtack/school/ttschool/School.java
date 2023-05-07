package net.thumbtack.school.ttschool;

import java.util.*;

public class School {
    private String schoolName;
    private int year;
    private SortedSet<Group> set;

    public School(String name, int year) throws TrainingException {
        setName(name);
        setYear(year);

        set = new TreeSet<>(compareByName);
    }

    public String getName() {
        return schoolName;
    }

    public void setName(String name) throws TrainingException {
        if (name == null || name.equals(""))
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
        this.schoolName = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Group> getGroups() {
        return set;
    }

    public void addGroup(Group group) throws TrainingException {
        if (!set.add(group)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
        }
    }

    public void removeGroup(Group group) throws TrainingException {
        if (!set.remove(group))
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
    }

    public void removeGroup(String name) throws TrainingException {
        for (Group group : set) {
            if (group.getName().equals(name)) {
                set.remove(group);
                return;
            }
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public boolean containsGroup(Group group) {
        return set.contains(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return year == school.year && Objects.equals(schoolName, school.schoolName) && Objects.equals(set, school.set);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolName, year, set);
    }

    public static final Comparator<Group> compareByName = ((o1, o2) -> o1.getName().compareTo(o2.getName()));

}
