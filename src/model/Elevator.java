package model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

class Elevator {
    public static final int WAIT_TIME=10;
    final int id, lowestFloor, highestFloor;
    TreeSet<Integer> destinations = new TreeSet<>();
    TreeSet<Integer> scheduledButtonsUp = new TreeSet<>();
    TreeSet<Integer> scheduledButtonsDown = new TreeSet<>();

    TreeSet<Integer> globalButtonsUp, globalButtonsDown;

    ElevatorSystemImpl system;
    boolean isOpened = false;
    int currentDirection=0, currentPosition;
    int waitingTime;

    public Elevator(int id, int lowestFloor, int highestFloor, TreeSet<Integer> globalDestinationsUp, TreeSet<Integer> globalDestinationsDown, ElevatorSystemImpl system) {
        this.id=id;
        this.lowestFloor = lowestFloor;
        this.highestFloor = highestFloor;
        this.globalButtonsUp = globalDestinationsUp;
        this.globalButtonsDown = globalDestinationsDown;
        this.system = system;
        this.currentPosition = -lowestFloor;
    }

    public void step() {
        isOpened = false;
        updateKnowledge();

        if(hasNothingToDo()) waitingTime--;
        else restartClock();

        updateDirection();
        if(move()) {
            updateKnowledge();
            updateDirection();
        }

        isOpened = open();
        if(isOpened) removeLocalTasks();
    }
    void updateKnowledge() {
        scheduledButtonsUp.retainAll(globalButtonsUp);
        scheduledButtonsDown.retainAll(globalButtonsDown);

        if(globalButtonsUp.contains(currentPosition)) scheduledButtonsUp.add(currentPosition);
        if(globalButtonsDown.contains(currentPosition)) scheduledButtonsDown.add(currentPosition);
    }
    void updateDirection() {
        if(hasNothingToDo()) {
            currentDirection = 0;
            return;
        }
        if (currentDirection == 0) {
            if (hasTaskAbove()) {
                currentDirection=1;
            }
            else if (hasTaskBelow()) {
                currentDirection=-1;
            }
        }
        else if (currentDirection == 1) {
            if(!(hasTaskAbove() || destinations.contains(currentPosition))) {
                currentDirection = -1;
            }
        }
        else if (currentDirection == -1) {
            if(!(hasTaskBelow() || destinations.contains(currentPosition))) {
                currentDirection = 1;
            }
        }
    }
    boolean move() {
        if(currentDirection == 0) {
            if(waitingTime < 0) makeStepToGroundFloor();
            return false;
        }
        else if (currentDirection == 1) {
            if(destinations.contains(currentPosition) || scheduledButtonsUp.contains(currentPosition) || currentPosition == highestFloor-lowestFloor-1) return false;
            else {
                currentPosition++;
                return true;
            }
        }
        else if (currentDirection == -1) {
            if (destinations.contains(currentPosition) || scheduledButtonsDown.contains(currentPosition) || currentPosition==0) return false;
            else {
                currentPosition--;
                return true;
            }
        }
        return false;
    }
    boolean open() {
        if (destinations.contains(currentPosition)) {
            return true;
        }
        return (currentDirection == 1 && scheduledButtonsUp.contains(currentPosition))
                || (currentDirection == -1 && scheduledButtonsDown.contains(currentPosition));
    }
    void removeLocalTasks() {
        destinations.remove(currentPosition);
        globalButtonsUp.remove(currentPosition);
        globalButtonsDown.remove(currentPosition);
    }
    boolean hasNothingToDo() {
        return destinations.isEmpty() && scheduledButtonsUp.isEmpty() && scheduledButtonsDown.isEmpty();
    }
    void makeStepToGroundFloor() {
        if(currentPosition<-lowestFloor) currentPosition++;
        else if(currentPosition > -lowestFloor) currentPosition--;
    }
    boolean hasTaskAbove() {
        return destinations.ceiling(currentPosition+1)!=null
                || scheduledButtonsDown.ceiling(currentPosition+1)!=null
                || scheduledButtonsUp.ceiling(currentPosition)!=null;
    }
    boolean hasTaskBelow() {
        return destinations.floor(currentPosition-1)!=null
                || scheduledButtonsDown.floor(currentPosition)!=null
                || scheduledButtonsUp.floor(currentPosition-1)!=null;
    }
    void restartClock() {
        waitingTime=WAIT_TIME;
    }


    int calculateDistanceTo(int floor, int direction) {
        updateKnowledge();
        if(currentDirection == 0) return Math.abs(currentPosition-floor);

        int min=currentPosition, max=currentPosition;
        if(!destinations.isEmpty()) {
            min = Math.min(min, destinations.first());
            max = Math.max(max, destinations.last());
        }
        if(!scheduledButtonsUp.isEmpty()) {
            min = Math.min(min, scheduledButtonsUp.first());
            max = Math.max(max, scheduledButtonsUp.last());
        }
        if(!scheduledButtonsDown.isEmpty()) {
            min = Math.min(min, scheduledButtonsDown.first());
            max = Math.max(max, scheduledButtonsDown.last());
        }
        System.out.println("id: "+id+", min: "+min+", max: "+max);
        if(currentDirection == 1 && direction>0) {
            if(floor >= currentPosition) return floor-currentPosition;
            else if(floor >= min) return max-currentPosition+max-min+floor-min;
            else return max-currentPosition+max-floor;
        }
        else if (currentDirection == 1 && direction < 0) {
            if (floor>=max) return floor-currentPosition;
            else return max-currentPosition + max-floor;
        }
        else if (currentDirection == -1 && direction < 0) {
            if(floor <= currentPosition) return currentPosition-floor;
            else if (floor <= max) return currentPosition-min+max-min+max-floor;
            else return currentPosition-min+floor-min;
        }
        else if (currentDirection == -1 && direction > 0) {
            if (floor<=min) return currentPosition-floor;
            else return currentPosition-min + floor-min;
        }
        return -300;
    }

    ElevatorSystem.ElevatorInfo getInfo() {
        return new ElevatorSystem.ElevatorInfo(id, currentPosition, new ArrayList<>(destinations), isOpened);
    }

    public void scheduleFloorTask(int floor, int direction) {
        if (direction>0) scheduledButtonsUp.add(floor);
        else scheduledButtonsDown.add(floor);

        if(currentDirection == 0) updateDirection();

        for(int dest: scheduledButtonsUp) system.rescheduleIfCloserThan(dest, 1, calculateDistanceTo(dest, 1));
        for(int dest: scheduledButtonsDown) system.rescheduleIfCloserThan(dest, -1, calculateDistanceTo(dest, -1));

    }
    public void scheduleDestination(int floor) {
        destinations.add(floor);

        if(currentDirection == 0) updateDirection();
    }
    public void update(int newPosition, List<Integer> destinations) {
        currentPosition=newPosition;
        this.destinations = new TreeSet<>(destinations);
        this.isOpened = false;
        restartClock();
    }
}
