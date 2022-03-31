import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;

public class Grid {
    // Main frame.
    private JFrame frame = new JFrame();

    // Panels.
    public JPanel grid = new JPanel();
    private JPanel menu = new JPanel();
    // Buttons.
    private JButton b_save = new JButton("Save");
    private JButton b_exit = new JButton("Exit");

    public Grid() {
        LogicProcessing logicProcessing = new LogicProcessing(this);
        frame.setSize(1000, 800);
        frame.add(grid);
        frame.add(menu);

        // Positions and Views.
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        grid.setLayout(new GridLayout(8, 9));
        grid.setBounds(50, 50, 700, 650);
        menu.setBounds(750, 50, 200, 400);
        menu.setBackground(Color.red);
    }

}
