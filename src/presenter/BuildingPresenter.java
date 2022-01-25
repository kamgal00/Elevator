package presenter;

import model.ElevatorSystem;

import java.awt.*;

public class BuildingPresenter {
    BuildingViewInt view;
    ElevatorSystem elevatorSystem;
    private final int floors, elevators, lowest;
    public BuildingPresenter(BuildingViewInt view, ElevatorSystem elevatorSystem) {
        this.view = view;
        this.elevatorSystem = elevatorSystem;

        floors=elevatorSystem.getNumOfFloors();
        elevators=elevatorSystem.getNumOfElevators();
        lowest = elevatorSystem.getLowestFloor();

        view.initialize(this, floors, elevators, lowest);
        redraw();
    }

    private void clearView() {
        for (int i=0;i<floors;i++) {
            view.setFloorButtonColor(i,1, Color.ORANGE);
            view.setFloorButtonColor(i,-1, Color.ORANGE);
        }
        for(int i=0;i<floors;i++) {
            for (int j=0;j<elevators;j++) {
                view.setDoorColor(i,j,Color.LIGHT_GRAY);
                view.setDoorMarkText(i, j, "");
                view.setDoorFrameColor(i,j,new Color(34, 46, 142));
            }
        }
    }
    private void redraw() {
        clearView();
        for (ElevatorSystem.FloorInfo info : elevatorSystem.floorButtonsStatus()) {
            if(info.up) view.setFloorButtonColor(info.level,1, Color.RED);
            if(info.down) view.setFloorButtonColor(info.level,-1, Color.RED);
        }

        for(ElevatorSystem.ElevatorInfo info : elevatorSystem.elevatorsStatus()){
            for(int dest:info.destinations) {
                view.setDoorFrameColor(dest, info.id, Color.RED);
            }
            view.setDoorMarkText(info.currentFloor, info.id, "-->");
            if(info.isOpened) {
                view.setDoorColor(info.currentFloor, info.id, Color.WHITE);
            }
        }
    }
}
