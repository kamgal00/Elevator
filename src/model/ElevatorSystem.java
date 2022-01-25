package model;

import java.util.List;

public interface ElevatorSystem {
    class ElevatorInfo {
        public int id, currentFloor;
        public List<Integer> destinations;
        public boolean isOpened;

        public ElevatorInfo(int id, int currentFloor, List<Integer> destinations, boolean isOpened) {
            this.id = id;
            this.currentFloor = currentFloor;
            this.destinations = destinations;
            this.isOpened = isOpened;
        }
    }
    class FloorInfo {
        public int level;
        public boolean up, down;

        public FloorInfo(int level, boolean up, boolean down) {
            this.level = level;
            this.up = up;
            this.down = down;
        }
    }

    void update(int id, int currentFloor, List<Integer> destinations);
    void step();

    List<ElevatorInfo> elevatorsStatus();
    List<FloorInfo> floorButtonsStatus();

    void addDestination(int id, int destination);
    void pickup(int floor, int direction);

    int getNumOfFloors();
    int getNumOfElevators();
    int getLowestFloor();
}
