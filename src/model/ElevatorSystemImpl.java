package model;

import java.util.List;
import java.util.TreeSet;

public class ElevatorSystemImpl implements ElevatorSystem {

    public ElevatorSystemImpl(int numOfElevators, int numOfFloors, int lowestFloor) {
        this.numOfElevators = numOfElevators;
        this.numOfFloors = numOfFloors;
        this.lowestFloor = lowestFloor;
    }

    private static class Elevator{
        TreeSet<Integer> requestedFloors;
        int currentDirection;
    }

    private final int numOfElevators, numOfFloors, lowestFloor;

    @Override
    public void pickup(int floor, int direction) {

    }

    @Override
    public void update(int id, int currentFloor, List<Integer> destinations) {

    }

    @Override
    public void step() {

    }

    @Override
    public List<ElevatorInfo> elevatorsStatus() {
        return null;
    }

    @Override
    public List<FloorInfo> floorButtonsStatus() {
        return null;
    }

    @Override
    public void addDestination(int id, int destination) {

    }

    @Override
    public int getNumOfFloors() {
        return 0;
    }

    @Override
    public int getNumOfElevators() {
        return 0;
    }

    @Override
    public int getLowestFloor() {
        return 0;
    }
}
