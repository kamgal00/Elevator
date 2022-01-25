package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockElevatorSystem implements ElevatorSystem{
    int floors, elevators, lowest;

    public MockElevatorSystem(int floors, int elevators, int lowest) {
        this.floors = floors;
        this.elevators = elevators;
        this.lowest = lowest;
    }

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
        ArrayList<ElevatorInfo> infos = new ArrayList<>();
        for (int i=0;i<elevators;i++) infos.add(new ElevatorInfo(i, i%floors, new ArrayList<Integer>(List.of(new Integer[]{floors - 1 - (i % floors)})), i%6==0));
        return infos;
    }

    @Override
    public List<FloorInfo> floorButtonsStatus() {
        ArrayList<FloorInfo> infos = new ArrayList<>();

        for (int i=0;i<floors;i++) infos.add(new FloorInfo(i, i%2==1, i%3==1));
        return infos;
    }

    @Override
    public void addDestination(int id, int destination) {

    }

    @Override
    public int getNumOfFloors() {
        return floors;
    }

    @Override
    public int getNumOfElevators() {
        return elevators;
    }

    @Override
    public int getLowestFloor() {
        return lowest;
    }
}
