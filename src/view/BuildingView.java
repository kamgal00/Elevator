package view;

import presenter.BuildingPresenter;
import presenter.BuildingViewInt;

import javax.swing.*;
import java.awt.*;

public class BuildingView implements BuildingViewInt {

    private static class BottomPanel extends JPanel{
        JButton back = new JButton("Back"), force = new JButton("Force behavior"), step = new JButton("Step");
        public BottomPanel() {
            super();
            add(back);
            add(force);
            add(step);
        }
    }

    JFrame mainFrame;
    private BottomPanel bottomPanel = new BottomPanel();
    private Building building = new Building();

    public void initialize(BuildingPresenter presenter, int floors, int elevators, int lowest) {
        mainFrame = new JFrame();
        building.initialize(floors,elevators,lowest);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(bottomPanel, BorderLayout.PAGE_END);

        mainFrame.add(new JScrollPane(building), BorderLayout.CENTER);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setSize(800,600);
    }

    @Override
    public void setFloorButtonColor(int floor, int dir, Color color) {
        building.floorButtons[floor].setButtonColor(dir, color);
    }

    @Override
    public void setDoorColor(int floor, int elevator, Color color) {
        building.doors[floor][elevator].setDoorColor(color);
    }

    @Override
    public void setDoorFrameColor(int floor, int elevator, Color color) {
        building.doors[floor][elevator].setFrameColor(color);
    }

    @Override
    public void setDoorMarkText(int floor, int elevator, String text) {
        building.doors[floor][elevator].setMarkText(text);
    }
}
