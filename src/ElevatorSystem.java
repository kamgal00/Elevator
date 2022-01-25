import java.util.List;

public interface ElevatorSystem {
    class ElevatorInfo {
        public int id, currentFloor, destination;
    }
    void pickup(int floor, int direction);
    void update(int id, int currentFloor, int destination);
    void step();
    List<ElevatorInfo> status();
    void addDestination(int id, int destination);
}
