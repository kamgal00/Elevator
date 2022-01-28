import model.ElevatorSystemImpl;
import presenter.BuildingPresenter;
import view.BuildingView;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JButton generate=new JButton("Generate");
    JTextField elevators=new JTextField("3", 10),
            floors=new JTextField("6",10),
            lowestFloor=new JTextField("-1",10);
    JLabel elevatorLabel = new JLabel("Number of elevators:"),
            floorLabel=new JLabel("Number of floors:"),
            lowestFloorLabel = new JLabel("Lowest floor:");
    JPanel leftPanel = new JPanel(), centerPanel = new JPanel();
    public MainFrame() throws HeadlessException {
        super();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0; gbc.gridy=0; gbc.insets = new Insets(0,20,0,0);
        add(leftPanel, gbc);
        gbc.gridx=1; gbc.weightx = 1; gbc.weighty=1; gbc.fill = GridBagConstraints.BOTH;
        add(centerPanel, gbc);
        centerPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        leftPanel.setLayout(new GridBagLayout());
        gbc.gridx=0; gbc.gridy=0;
        leftPanel.add(elevatorLabel,gbc);
        gbc.gridy=1;
        leftPanel.add(elevators,gbc);
        gbc.gridy=2;
        leftPanel.add(floorLabel,gbc);
        gbc.gridy=3;
        leftPanel.add(floors,gbc);
        gbc.gridy=4;
        leftPanel.add(lowestFloorLabel,gbc);
        gbc.gridy=5;
        leftPanel.add(lowestFloor,gbc);
        gbc.gridy=6;
        leftPanel.add(generate,gbc);
        generate.addActionListener(e->{
            centerPanel.removeAll();
            int elev = Integer.parseInt(elevators.getText()),
                    fl = Integer.parseInt(floors.getText()),
                    low = Integer.parseInt(lowestFloor.getText());
            BuildingView bv = new BuildingView();
            new BuildingPresenter(bv, new ElevatorSystemImpl(elev, fl, low));
            GridBagConstraints gbcc = new GridBagConstraints();
            gbcc.weightx = 1; gbcc.weighty=1; gbcc.fill = GridBagConstraints.BOTH;
            centerPanel.add(bv, gbcc);
            revalidate();
            repaint();
        });
        setVisible(true);
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
