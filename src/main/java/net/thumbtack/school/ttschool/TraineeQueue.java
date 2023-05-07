package net.thumbtack.school.ttschool;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TraineeQueue {
    private Queue<Trainee> queue;

    public TraineeQueue() {
        queue = new LinkedList<>();
    }

    public void addTrainee(Trainee trainee) {
        queue.add(trainee);
    }

    public Trainee removeTrainee() throws TrainingException {
        Trainee res = queue.poll();
        if (res == null) throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        return res;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
