import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Grid {
    private JFrame frame = new JFrame();
    private JPanel grid = new JPanel();

    private void buttons() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                JButton btn = new JButton();
                grid.add(btn);
            }
        }
    }

    public void start() {
        frame.setVisible(true);
        frame.add(grid);
        grid.setVisible(true);
        frame.setSize(600, 600);
        frame.setLayout(null);
        grid.setBounds(50, 50, 500, 500);
        grid.setLayout(new GridLayout(9, 8));
        System.out.println("DOne");
        buttons();
    }
}
