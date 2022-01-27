package model;

import com.sun.source.tree.Tree;

import java.util.List;
import java.util.TreeSet;
class Elevator {
    public static final int WAIT_TIME=10;
    final int id, lowestFloor, highestFloor;
    TreeSet<Integer> destinations = new TreeSet<>();
    TreeSet<Integer> destinationsUp = new TreeSet<>();
    TreeSet<Integer> destinationsDown = new TreeSet<>();

    TreeSet<Integer> globalDestinationsUp, globalDestinationsDown;
    boolean isOpened = false;
    int currentDirection=0, currentPosition=0;
    int waitingTime;

    public Elevator(int id, int lowestFloor, int highestFloor, TreeSet<Integer> globalDestinationsUp, TreeSet<Integer> globalDestinationsDown) {
        this.id=id;
        this.lowestFloor = lowestFloor;
        this.highestFloor = highestFloor;
        this.globalDestinationsUp = globalDestinationsUp;
        this.globalDestinationsDown = globalDestinationsDown;
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
        destinationsUp.retainAll(globalDestinationsUp);
        destinationsDown.retainAll(globalDestinationsDown);

        if(globalDestinationsUp.contains(currentPosition)) destinationsUp.add(currentPosition);
        if(globalDestinationsDown.contains(currentPosition)) destinationsDown.add(currentPosition);
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
            if(destinations.contains(currentPosition) || destinationsUp.contains(currentPosition)) return false;
            else {
                currentPosition++;
                return true;
            }
        }
        else if (currentDirection == -1) {
            if (destinations.contains(currentPosition) || destinationsDown.contains(currentPosition)) return false;
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
        if((currentDirection==1 && destinationsUp.contains(currentPosition))
                || (currentDirection == -1 && destinationsDown.contains(currentPosition))){
            return true;
        }
        return false;
    }
    void removeLocalTasks() {
        destinations.remove(currentPosition);
        globalDestinationsUp.remove(currentPosition);
        globalDestinationsDown.remove(currentPosition);
    }
    boolean hasNothingToDo() {
        return destinations.isEmpty() && destinationsUp.isEmpty() && destinationsDown.isEmpty();
    }
    void makeStepToGroundFloor() {
        if(currentPosition<0) currentPosition++;
        else if(currentPosition > 0) currentPosition--;
    }
    boolean hasTaskAbove() {
        return destinations.ceiling(currentPosition+1)!=null
                || destinationsDown.ceiling(currentPosition+1)!=null
                || destinationsUp.ceiling(currentPosition)!=null;
    }
    boolean hasTaskBelow() {
        return destinations.floor(currentPosition-1)!=null
                || destinationsDown.floor(currentPosition)!=null
                || destinationsUp.floor(currentPosition-1)!=null;
    }
    void restartClock() {
        waitingTime=WAIT_TIME;
    }


    int calculateDistanceTo(int floor, int direction) {
        updateKnowledge();
        updateDirection();
        if(currentDirection == 0) return Math.abs(currentPosition-floor);

        int min=currentPosition, max=currentPosition;
        if(!destinations.isEmpty()) {
            min = Math.min(min, destinations.first());
            max = Math.max(max, destinations.last());
        }
        if(!destinationsUp.isEmpty()) {
            min = Math.min(min, destinationsUp.first());
            max = Math.max(max, destinationsUp.last());
        }
        if(!destinationsDown.isEmpty()) {
            min = Math.min(min, destinationsDown.first());
            max = Math.max(max, destinationsDown.last());
        }

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
        return new ElevatorSystem.ElevatorInfo(id, currentPosition, destinations.stream().toList(), isOpened);
    }

    public void scheduleFloorTask(int floor, int direction) {
        if (direction>0) destinationsUp.add(floor);
        else destinationsDown.add(floor);
    }
    public void scheduleDestination(int floor) {
        destinations.add(floor);
    }
    public void update(int newPosition, List<Integer> destinations) {
        currentPosition=newPosition;
        this.destinations = new TreeSet<>(destinations);
    }
}
