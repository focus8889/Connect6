import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LogicProcessing {
    int id = 0;
    ArrayList<Integer> availableGrids = new ArrayList<Integer>();
    ArrayList<Integer> playerDiscs = new ArrayList<Integer>();
    ArrayList<Integer> cpuDiscs = new ArrayList<Integer>();
    CustomButton[] cells = new CustomButton[73];
    boolean gameOver = false;

    public LogicProcessing(Grid grid) {
        generateBoard(grid);
        // while (gameOver != true) {

        // }
    }

    public void generateBoard(Grid grid) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                CustomButton btn = new CustomButton();
                if (j == 0) {
                    btn.setFirst(); // First cell in a row.
                }
                if (j == 7) {
                    btn.setLast(); // Last cell in a row.
                }
                id++;
                cells[id] = btn; // Adding btn to the array.
                grid.grid.add(btn); // Adding and Displaying buttons in the grid.
                btn.setID(id); // Setting button id.
                btn.setText(String.valueOf(id));
                btn.setRowID(i); // Setting row id.
                btn.setColumnID(j);
                btn.setFocusPainted(false);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playerMove(btn.getID());
                        // System.out.println(btn.getID());
                        System.out.println(btn.getcolumnID());
                    }
                });

            }
        }
        // Adding initially available coordinates.
        for (int i = 64; i < 73; i++) {
            availableGrids.add(i);
        }
        System.out.println(availableGrids);
    }

    public void playerMove(int id) {
        if (availableGrids.contains(id)) {
            this.cells[id].setPlayer();
            this.cells[id].setBackground(Color.red);
            System.err.println(id);
            if (id >= 10) {
                availableGrids.remove(availableGrids.indexOf(id));
                System.out.println(availableGrids + " Removed " + id);
                availableGrids.add(id - 9);
                System.out.println(availableGrids);
            }
        }
        if (!availableGrids.contains(id)) {
            while (!availableGrids.contains(id)) {
                id += 9;
                System.out.println(id);
            }
            cells[id].setBackground(Color.red);
            availableGrids.remove(availableGrids.indexOf(id));
            availableGrids.add(id - 9);

        }
    }
}
