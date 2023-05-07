package net.thumbtack.school.ttschool;

import java.util.*;

public class Group {
    private String groupName;
    private String audience;
    private List<Trainee> students;

    public Group(String name, String room) throws TrainingException {
        setName(name);
        setRoom(room);
        this.students = new ArrayList<>();
    }

    public String getName() {
        return groupName;
    }

    public void setName(String name) throws TrainingException {
        if (name == null || name.equals("")) throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
        this.groupName = name;
    }

    public String getRoom() {
        return this.audience;
    }

    public void setRoom(String room) throws TrainingException {
        if (room == null || room.equals("")) throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
        this.audience = room;
    }

    public List<Trainee> getTrainees() {
        return this.students;
    }

    public void addTrainee(Trainee trainee) {
        this.students.add(trainee);
    }

    public void removeTrainee(Trainee trainee) throws TrainingException {
        if (!students.remove(trainee)) throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void removeTrainee(int index) throws TrainingException {
        if (students.size() <= index) throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        students.remove(students.get(index));

    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        for (Trainee counter : students) {
            if (counter.getFirstName().equals(firstName)) {
                return counter;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException {
        for (Trainee counter : students) {
            if (counter.getFullName().equals(fullName)) {
                return counter;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void sortTraineeListByFirstNameAscendant() {
        students.sort(Group.compareByFirstName);
    }

    public void sortTraineeListByRatingDescendant() {
        students.sort(Group.compareByRating.reversed());
    }

    public void reverseTraineeList() {
        Collections.reverse(students);
    }

    public void rotateTraineeList(int positions) {
        Collections.rotate(students, positions);
    }

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
        if (students.isEmpty()) throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        List<Trainee> result = new ArrayList<>();
        List<Trainee> copy = new ArrayList<>(students);
        copy.sort(compareByRating);
        Collections.reverse(copy);
        int num = copy.get(0).getRating();
        for (Trainee counter : students) {
            if (counter.getRating() == num)
                result.add(counter);
        }
        if (result.isEmpty()) throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        return result;
    }

    public boolean hasDuplicates() {
        Set<Trainee> set = new HashSet<>(students);
        return set.size() != students.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupName, group.groupName) && Objects.equals(audience, group.audience) && Objects.equals(students, group.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, audience, students);
    }

    public static final Comparator<Trainee> compareByFirstName = (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName());

    public static final Comparator<Trainee> compareByRating = (o1, o2) -> o1.getRating() - o2.getRating();
}
