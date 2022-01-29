package view;

import presenter.BuildingPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Building extends JPanel {
    int numOfFloors, numOfElevators;
    FloorButtons[] floorButtons;
    Door[][] doors;
    JButton[] clearButtons;

    void initialize(BuildingPresenter presenter, int numOfFloors, int numOfElevators, int lowestFloor) {
        this.numOfFloors = numOfFloors;
        this.numOfElevators = numOfElevators;

        floorButtons = new FloorButtons[numOfFloors];
        for (int i = 0; i < numOfFloors; i++) {
            floorButtons[i] = new FloorButtons(i + lowestFloor);
            int finalI = i;
            floorButtons[i].up.addActionListener(e-> presenter.clickFloorButton(finalI, 1));
            floorButtons[i].down.addActionListener(e-> presenter.clickFloorButton(finalI, -1));
        }
        doors = new Door[numOfFloors][numOfElevators];
        for (int i = 0; i < numOfFloors; i++) {
            for (int j = 0; j < numOfElevators; j++) {
                doors[i][j] = new Door();
                int finalJ = j;
                int finalI = i;
                doors[i][j].door.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (SwingUtilities.isLeftMouseButton(e))
                            presenter.clickDoor(finalJ, finalI);
                        if(SwingUtilities.isRightMouseButton(e))
                            presenter.forcePosition(finalJ, finalI);
                    }
                });
            }
        }

        clearButtons = new JButton[numOfElevators];
        for (int i=0;i<numOfElevators;i++) {
            clearButtons[i] = new JButton("Clear");
            int finalI = i;
            clearButtons[i].addActionListener(e->presenter.clearDestinations(finalI));
        }

        setLayout(new GridBagLayout());

        // add doors and floor buttons
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < numOfFloors; i++) {
            gbc.gridy = 2 * i;
            gbc.insets = new Insets(20, 0, 0, 20);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            add(floorButtons[numOfFloors - 1 - i], gbc);

            gbc.anchor = GridBagConstraints.LAST_LINE_END;
            for (int j = 0; j < numOfElevators; j++) {
                gbc.gridx = j + 1;
                add(doors[numOfFloors - 1 - i][j], gbc);
            }
        }

        //place clear buttons
        gbc = new GridBagConstraints();
        gbc.gridy=2*numOfFloors;
        gbc.insets = new Insets(10,10,10,10);
        for(int i=0;i<numOfElevators;i++) {
            gbc.gridx=i+1;
            add(clearButtons[i], gbc);
        }

        // add floor separators
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = numOfElevators + 1;
        for (int i = 0; i < numOfFloors; i++) {
            gbc.gridy = 2 * i + 1;
            add(new JSeparator(JSeparator.HORIZONTAL), gbc);
        }
    }
}
