package presenter;

import java.awt.*;

public interface BuildingViewInt {
    void setFloorButtonColor(int floor, int dir, Color color);
    void setDoorColor(int floor, int elevator, Color color);
    void setDoorFrameColor(int floor, int elevator, Color color);
    void setDoorMarkText(int floor, int elevator, String text);
    void initialize(BuildingPresenter presenter, int floors, int elevators, int lowest);
}
