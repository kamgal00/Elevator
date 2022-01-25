package view;

import javax.swing.*;
import java.awt.*;

class Door extends JPanel {
    private JLabel arrow = new JLabel();
    private JLabel door = new JLabel();
    private JLabel frame = new JLabel();

    public Door() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 15);
        arrow.setPreferredSize(new Dimension(30, 30));
        add(arrow, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);

        frame.setOpaque(true);
        frame.setPreferredSize(new Dimension(42+4, 70+5));
        add(frame, gbc);

        frame.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.anchor=GridBagConstraints.PAGE_END;
        door.setOpaque(true);
        door.setPreferredSize(new Dimension(42, 70));
        frame.add(door, gbc);
    }
    public void setDoorColor(Color color) {
        door.setBackground(color);
    }
    public void setMarkText(String text) {
        arrow.setText(text);
    }
    public void setFrameColor(Color color) {
        frame.setBackground(color);
    }
}
