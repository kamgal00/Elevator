import java.util.List;
import java.util.TreeSet;

public class ElevatorSystemImpl implements ElevatorSystem {

    public ElevatorSystemImpl(int numOfElevators, int numOfFloors, int lowestFloor) {
        this.numOfElevators = numOfElevators;
        this.numOfFloors = numOfFloors;
        this.lowestFloor = lowestFloor;
    }

    private static class Elevator extends ElevatorInfo {
        TreeSet<Integer> requestedFloors;
        int currentDirection;
    }

    private final int numOfElevators, numOfFloors, lowestFloor;

    @Override
    public void pickup(int floor, int direction) {

    }

    @Override
    public void update(int id, int currentFloor, int destination) {

    }

    @Override
    public void step() {

    }

    @Override
    public List<ElevatorInfo> status() {
        return null;
    }

    @Override
    public void addDestination(int id, int destination) {

    }
}
