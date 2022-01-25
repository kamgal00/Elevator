package view;

import javax.swing.*;
import java.awt.*;

class Building extends JPanel {
    int numOfFloors, numOfElevators;
    FloorButtons[] floorButtons;
    Door[][] doors;
    JButton[] insideButtons;

    void initialize(int numOfFloors, int numOfElevators, int lowestFloor) {
        this.numOfFloors = numOfFloors;
        this.numOfElevators = numOfElevators;

        floorButtons = new FloorButtons[numOfFloors];
        for (int i = 0; i < numOfFloors; i++) {
            floorButtons[i] = new FloorButtons(i + lowestFloor);
        }
        doors = new Door[numOfFloors][numOfElevators];
        for (int i = 0; i < numOfFloors; i++) {
            for (int j = 0; j < numOfElevators; j++) {
                doors[i][j] = new Door();
            }
        }
        insideButtons = new JButton[numOfElevators];
        for (int i = 0; i < numOfElevators; i++) {
            insideButtons[i] = new JButton("Choose floor");
        }

        setLayout(new GridBagLayout());

        // add doors and floor buttons
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < numOfFloors; i++) {
            gbc.gridy = 2 * i;
            gbc.insets = new Insets(20, 0, 0, 0);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            add(floorButtons[numOfFloors - 1 - i], gbc);

            gbc.anchor = GridBagConstraints.PAGE_END;
            for (int j = 0; j < numOfElevators; j++) {
                gbc.gridx = j + 1;
                add(doors[numOfFloors - 1 - i][j], gbc);
            }
        }

        // add inside buttons
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 2 * numOfFloors;
        for (int i = 0; i < numOfElevators; i++) {
            gbc.gridx = i + 1;
            add(insideButtons[i], gbc);
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
