package view;

import javax.swing.*;
import java.awt.*;

class FloorButtons extends JPanel {
    JButton up = new JButton("UP"), down = new JButton("DOWN");
    private JLabel floor = new JLabel();
    public FloorButtons(int floorNR) {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 0, 3, 0);
        gbc.gridx = 0;

        gbc.gridy = 0;
        add(up, gbc);
        gbc.gridy = 1;
        add(down, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 30, 0, 0);
        floor.setText(Integer.toString(floorNR));
        add(floor, gbc);
    }
    public void setButtonColor(int dest, Color color) {
        if(dest>0) {
            up.setBackground(color);
        }
        else{
            down.setBackground(color);
        }
    }
}
