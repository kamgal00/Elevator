package view;

import presenter.BuildingPresenter;
import presenter.BuildingViewInt;

import javax.swing.*;
import java.awt.*;

public class BuildingView extends JPanel implements BuildingViewInt {

    private static class BottomPanel extends JPanel{
        JButton step = new JButton("Step");
        public BottomPanel() {
            super();
            add(step);
        }
    }

    private final BottomPanel bottomPanel = new BottomPanel();
    private final Building building = new Building();

    public void initialize(BuildingPresenter presenter, int floors, int elevators, int lowest) {
        building.initialize(presenter, floors,elevators,lowest);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0; gbc.gridy=1;
        add(bottomPanel, gbc);

        gbc.gridy=0; gbc.weightx = 1; gbc.weighty=1; gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(building), gbc);

        bottomPanel.step.addActionListener(e->presenter.step());
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
