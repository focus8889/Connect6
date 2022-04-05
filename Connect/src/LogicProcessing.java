import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LogicProcessing {
    int id = 0;
    ArrayList<Integer> availableGrids = new ArrayList<Integer>();
    ArrayList<Integer> playerDiscs = new ArrayList<Integer>();
    ArrayList<Integer> cpuDiscs = new ArrayList<Integer>();
    CustomButton[] cells = new CustomButton[72];
    boolean gameOver = false;

    public LogicProcessing(Grid grid) {
        generateBoard(grid);
        // while (gameOver != true) {

        // }
    }

    public void generateBoard(Grid grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                CustomButton btn = new CustomButton();
                btn.setFocusPainted(false);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("HEllo " + btn.getID());
                        System.out.println(btn.getColumnId());
                    }
                });
                if (j == 0) {
                    btn.setFirst();
                }
                if (j == 7) {
                    btn.setLast();
                }
                cells[id] = btn;
                id++;
                grid.grid.add(btn);
                btn.setID(id);
                btn.setText(String.valueOf(id));
                btn.setColumn(j);

            }
        }
        // Adding initially available coordinates.
        for (int i = 64; i < 73; i++) {
            availableGrids.add(i);
        }
        System.out.println(availableGrids);
    }

    public void buttonController(ActionEvent e) {
        System.out.println(e.getSource());
    }

}
