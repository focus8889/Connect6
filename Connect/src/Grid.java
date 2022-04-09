import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Grid {
    // Main frame.
    private JFrame frame = new JFrame();

    // Panels.
    public JPanel grid = new JPanel();
    public JPanel game = new JPanel();
    private JPanel menu = new JPanel();
    private JPanel start = new JPanel();
    // Buttons.
    private JButton b_save = new JButton("Save");
    private JButton b_exit = new JButton("Exit");
    private JButton b_play = new JButton("Play");
    private JButton b_cont = new JButton("Continue");
    private Icon icon = new ImageIcon("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\img\\bg.jpg");
    private JLabel background = new JLabel(icon);
    public boolean saveLoad = false;

    // Label.
    private JLabel title = new JLabel("Connect 6");

    public Grid() {
        // Adding elements.
        frame.add(start);

        start.add(b_cont);
        start.add(b_play);
        start.add(title);
        start.add(background);

        start.setLayout(null);

        // Setting size.
        frame.setSize(1000, 700);
        start.setBounds(0, 0, 1000, 1000);
        title.setBounds(400, 150, 200, 100);
        b_play.setBounds(400, 250, 200, 100);
        b_cont.setBounds(400, 360, 200, 100);
        background.setBounds(0, 0, 1000, 700);

        // Additional configuration.
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        b_cont.setFocusPainted(false);
        b_play.setFocusPainted(false);
        frame.setTitle("Connect 6");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        b_cont.setFont(new Font("Arial", Font.BOLD, 20));
        b_play.setFont(new Font("Arial", Font.BOLD, 20));

        // Adding ActionListeners.
        b_play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    gamePanel();
                } catch (FileNotFoundException e1) {

                }
            }

        });
        b_cont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveLoad = true;
                try {
                    gamePanel();
                } catch (FileNotFoundException e1) {
                    
                }

            }

        });

    }

    public void gamePanel() throws FileNotFoundException {

        frame.remove(start);
        start.setVisible(false);
        frame.add(game);
        game.setVisible(true);
        game.add(grid);
        game.add(menu);
        menu.add(b_exit);
        menu.add(b_save);
        LogicProcessing logicProcessing = new LogicProcessing(this);

        game.setLayout(null);
        menu.setLayout(null);
        grid.setLayout(new GridLayout(8, 9));
        grid.setBounds(50, 50, 700, 600);
        game.setBounds(0, 0, 1000, 700);
        menu.setBounds(750, 50, 250, 500);
        b_exit.setBounds(50, 50, 100, 50);
        b_save.setBounds(50, 110, 100, 50);
        game.setBackground(new Color(187, 239, 242));
        menu.setBackground(new Color(187, 239, 242));

        b_save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    logicProcessing.saveGame();
                } catch (IOException e1) {
                }
            }

        });
    }

}
