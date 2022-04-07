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
                        int id = playerMove(btn.getID());
                        cpuCheck(id);
                        cells[id].setEnabled(false);

                        // System.out.println(btn.getID());
                        System.out.println(btn.getcolumnID());
                        System.out.println(btn.getRowID());
                        System.out.println(btn.getPlayer());
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

    public int playerMove(int id) {
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
        } else {
            if (!availableGrids.contains(id)) {
                while (!availableGrids.contains(id)) {
                    id += 9;
                    System.out.println(id);
                }
                cells[id].setBackground(Color.red);
                availableGrids.remove(availableGrids.indexOf(id));
                availableGrids.add(id - 9);
                System.out.println(availableGrids + " sada");

            }
        }
        cells[id].setPlayer();
        return id;

    }

    public void cpuCheck(int id) {
        if (columnCheck(id) == true) {
            // Block Player.
            cells[id - 9].setBackground(Color.blue);
            cells[id - 9].setEnabled(false);
            cells[id - 9].setCpu();
            availableGrids.remove(availableGrids.indexOf(id - 9));
            availableGrids.add(id - 18);
        }
        if (rowCheck(id) == true) {
            rowCheck(id);
        }

    }

    public boolean columnCheck(int id) {
        int connects = 0;
        boolean status = false;
        // Checking vertically win status.
        while (true) {
            try {
                if (cells[id].getPlayer() == true) {
                    System.out.println(" Checking " + id);
                    connects++;
                    System.out.println(connects + " in a row");
                    id += 9;
                    if (connects == 5) {
                        status = true;
                        break;
                    }
                    continue;
                } else {
                    System.out.println("Nothing to check");
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
        return status;
    }

    public boolean rowCheck(int id) {
        final int initial = id;
        int row = cells[id].getRowID();
        int connects = 0;
        boolean status = false;
        while (true) {
            if ((cells[id].getPlayer() == true) & (cells[id].getRowID() == row)) {
                connects++;
                id--;
                if (connects == 4) {
                    cells[initial + 1].setCpu();
                    System.out.println(cells[initial + 1].getCpu() + " CPU STATUS");
                    cells[initial + 1].setBackground(Color.green);
                    cells[initial + 1].setEnabled(false);
                    break;
                }

            }
            if (cells[id].getPlayer() == false) {
                break;
            } else {
                continue;
            }

        }
        System.out.println(" Row Check Connects " + connects);
        return status;
    }

}
