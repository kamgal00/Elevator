package model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElevatorSystemImpl implements ElevatorSystem {

    public ElevatorSystemImpl(int numOfElevators, int numOfFloors, int lowestFloor) {
        this.numOfElevators = numOfElevators;
        this.numOfFloors = numOfFloors;
        this.lowestFloor = lowestFloor;

        for (int i=0;i<numOfElevators;i++) {
            elevators.add(new Elevator(i, lowestFloor, lowestFloor+numOfFloors, destinationsUp, destinationsDown, this));
        }
    }

    private final int numOfElevators, numOfFloors, lowestFloor;
    private List<Elevator> elevators = new ArrayList<>();
    private TreeSet<Integer> destinationsUp=new TreeSet<>(), destinationsDown = new TreeSet<>();

    @Override
    public void pickup(int floor, int direction) {
        if (direction>0) destinationsUp.add(floor);
        else destinationsDown.add(floor);

        elevators.stream()
                .min(Comparator.comparingInt(a -> 2*a.calculateDistanceTo(floor, direction)-Math.abs(a.currentDirection)))
                .get()
                .scheduleFloorTask(floor, direction);
    }

    public void rescheduleIfCloserThan(int floor, int direction, int distance) {
        System.out.println(floor+" "+direction+" "+distance);
        Elevator min = elevators.stream()
                .min(Comparator.comparingInt(a -> 2*a.calculateDistanceTo(floor, direction)-Math.abs(a.currentDirection)))
                .get();
        System.out.println(min.id+" "+min.calculateDistanceTo(floor, direction));
        if (min.calculateDistanceTo(floor, direction) < distance) min.scheduleFloorTask(floor, direction);

    }

    @Override
    public void update(int id, int currentFloor, List<Integer> destinations) {
        elevators.get(id).update(currentFloor, destinations);
    }

    @Override
    public void step() {
        for (Elevator e: elevators) e.step();
    }

    @Override
    public List<ElevatorInfo> elevatorsStatus() {
        return elevators.stream()
                .map(Elevator::getInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<FloorInfo> floorButtonsStatus() {
        return IntStream.range(0, numOfFloors)
                .mapToObj(
                        i->new FloorInfo(
                                i,
                                destinationsUp.contains(i),
                                destinationsDown.contains(i)
                        ))
                .collect(Collectors.toList());
    }

    @Override
    public void addDestination(int id, int destination) {
        elevators.get(id).scheduleDestination(destination);
    }

    @Override
    public int getNumOfFloors() {
        return numOfFloors;
    }

    @Override
    public int getNumOfElevators() {
        return numOfElevators;
    }

    @Override
    public int getLowestFloor() {
        return lowestFloor;
    }
}
