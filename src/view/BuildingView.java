package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BuildingView {
    private static class BottomPanel extends JPanel{
        JButton back = new JButton("Back"), force = new JButton("Force behavior"), step = new JButton("Step");
        public BottomPanel() {
            super();
            add(back);
            add(force);
            add(step);
        }
    }
    private static class Building extends JPanel {
        static class FloorButtons extends JPanel {
            JButton up = new JButton("UP"), down = new JButton("DOWN");
            JLabel floor = new JLabel();
            public FloorButtons() {
                super();
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill=GridBagConstraints.HORIZONTAL;
                gbc.insets= new Insets(3,0,3,0);
                gbc.gridx=0;

                gbc.gridy=0;
                add(up, gbc);
                gbc.gridy=1;
                add(down, gbc);

                gbc = new GridBagConstraints();
                gbc.gridx=1;
                gbc.gridy=0;
                gbc.gridheight=2;
                gbc.anchor=GridBagConstraints.CENTER;
                gbc.insets= new Insets(0,30,0,0);
                add(floor, gbc);
            }
        }
        int numOfFloors, numOfElevators;
        FloorButtons[] floorButtons;
        JLabel[][] doors;
        JButton[] insideButtons;

        void initialize(int numOfFloors, int numOfElevators, int lowestFloor){
            this.numOfFloors = numOfFloors;
            this.numOfElevators = numOfElevators;

            floorButtons = new FloorButtons[numOfFloors];
            for(int i=0;i<numOfFloors;i++) {
                floorButtons[i] = new FloorButtons();
                floorButtons[i].floor.setText(Integer.toString(i+lowestFloor));
            }
            doors = new JLabel[numOfFloors][numOfElevators];
            for(int i=0;i<numOfFloors;i++) {
                for (int j=0;j<numOfElevators;j++) {
                    doors[i][j] = new JLabel();
                    doors[i][j].setOpaque(true);
                    doors[i][j].setPreferredSize(new Dimension(42,70));
                }
            }
            insideButtons = new JButton[numOfElevators];
            for (int i=0;i<numOfElevators;i++) {
                insideButtons[i]= new JButton("Choose floor");
            }

            setLayout(new GridBagLayout());

            // add doors and floor buttons
            GridBagConstraints gbc = new GridBagConstraints();
            for(int i=0;i<numOfFloors;i++) {
                gbc.gridy = 2*i;
                gbc.insets=new Insets(20,0,0,0);

                gbc.anchor=GridBagConstraints.CENTER;
                gbc.gridx=0;
                add(floorButtons[numOfFloors-1-i], gbc);

                gbc.anchor=GridBagConstraints.PAGE_END;
                for(int j=0;j<numOfElevators;j++){
                    gbc.gridx=j+1;
                    add(doors[numOfFloors-1-i][j], gbc);
                }
            }

            // add inside buttons
            gbc = new GridBagConstraints();
            gbc.insets = new Insets(10,10,0,10);
            gbc.anchor=GridBagConstraints.CENTER;
            gbc.gridy=2*numOfFloors;
            for(int i=0;i<numOfElevators;i++) {
                gbc.gridx = i+1;
                add(insideButtons[i], gbc);
            }

            // add floor separators
            gbc = new GridBagConstraints();
            gbc.gridx=0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = numOfElevators+1;
            for(int i=0;i<numOfFloors;i++) {
                gbc.gridy = 2 * i + 1;
                add(new JSeparator(JSeparator.HORIZONTAL), gbc);
            }
            resetColors();
        }
        void resetColors() {
            for(int i=0;i<numOfFloors;i++) {
                floorButtons[i].up.setBackground(Color.ORANGE);
                floorButtons[i].down.setBackground(Color.ORANGE);
            }
            for(int i=0;i<numOfFloors;i++) {
                for (int j=0;j<numOfElevators;j++) {
                    doors[i][j].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    JFrame mainFrame;
    private BottomPanel bottomPanel = new BottomPanel();
    private Building building = new Building();

    public void initialize() {
        mainFrame = new JFrame();
        building.initialize(5,8,-1);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(bottomPanel, BorderLayout.PAGE_END);

        mainFrame.add(new JScrollPane(building), BorderLayout.CENTER);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setSize(800,600);
    }

}
