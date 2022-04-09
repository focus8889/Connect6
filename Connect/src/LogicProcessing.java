import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LogicProcessing {
    int id = 0;
    ArrayList<Integer> availableGrids = new ArrayList<Integer>();
    ArrayList<Integer> playerDiscs = new ArrayList<Integer>();
    ArrayList<Integer> cpuDiscs = new ArrayList<Integer>();
    CustomButton[] cells = new CustomButton[73];
    boolean gameOver = false;

    public LogicProcessing(Grid grid) throws FileNotFoundException {
        if (grid.saveLoad == false) {
            // generateBoard(grid);
        } else {
            generateBoard(grid);
            loadGame();
        }

    }

    public void generateBoard(Grid grid) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                CustomButton btn = new CustomButton();
                if (j == 0) {
                    btn.setFirst(); // First cell in a row.
                }
                if (j == 8) {
                    btn.setLast(); // Last cell in a row.
                }
                id++;
                this.cells[id] = btn; // Adding btn to the array.
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
                        winDetect(id);
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
                availableGrids.add(id - 9);
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
            }
        }
        cells[id].setPlayer();
        cells[id].setEnabled(false);
        return id;

    }

    public void cpuCheck(int id) {

        if (columnCheck(id) == true) { // Checking if player has chance to win vertically.

            // Block Player.
            cells[id - 9].setBackground(Color.blue);
            cells[id - 9].setEnabled(false);
            cells[id - 9].setCpu();
            availableGrids.remove(availableGrids.indexOf(id - 9));
            availableGrids.add(id - 18);
        }
        if (rowCheck(id) == true) { // Checking if player has chance to win horizontally.

        } else { // Cpu makes move.

        }

    }

    public boolean columnCheck(int id) {
        int connects = 0;
        boolean status = false;
        // Checking vertically win status.
        while (true) {
            try {
                if (cells[id].getPlayer() == true) {
                    connects++;
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
        int row = cells[id].getRowID();
        int connects = 0;
        boolean status = false;
        try {
            while (!cells[id].getPlayer() == false) {
                if (cells[id].getRowID() == row) {
                    id--;
                }
            }
            id++;
            while (!cells[id].getPlayer() == false) {
                connects++;
                id++;
                if ((connects == 4) & (cells[id].getPlayer() == false) & availableGrids.contains(id)
                        & (cells[id].getRowID() == row)) {
                    cells[id].setCpu();
                    cells[id].setEnabled(false);
                    cells[id].setBackground(Color.blue);
                    if (availableGrids.contains(id)) {
                        availableGrids.remove(availableGrids.indexOf(id));
                        availableGrids.add(id - 9);
                    }
                }
            }
        } catch (Exception e) {

        }

        return status;

    }

    public void winDetect(int clicked) {
        if (checkVertical(clicked) == true) {
            System.out.println("Win man");
        }
        if (horizonatlCheck(clicked) == true) {
        }
        if (checkDiagonalRight(clicked) == true) {
        }
        if (checkDiagonalLeft(clicked) == true) {

        }
    }

    public boolean checkVertical(int clicked) {
        boolean win = false;
        int discs = 0;
        try {
            while (true) {
                if ((cells[clicked].getPlayer() == true) & (clicked <= 63)) {
                    discs++;
                    clicked += 9;

                } else {
                    break;
                }
                if ((discs == 5) & (cells[clicked].getPlayer() == true)) { // Checks last iteration (clicked).
                    discs = 6;
                    win = true;
                    break;
                }
            }
        } catch (Exception e) {

        }

        return win;
    }

    public boolean horizonatlCheck(int id) {
        boolean status = false;
        int row = cells[id].getRowID();
        int connects = 0;
        try {
            while (!cells[id].getPlayer() == false) {
                if (cells[id].getRowID() == row) {
                    id--;
                }
            }
            id++;
            while (!cells[id].getPlayer() == false) {
                connects++;
                id++;
                if (connects == 6) {
                    System.out.println("Win");
                    break;
                }
            }
        } catch (Exception e) {

        }

        return status;
    }

    public boolean checkDiagonalRight(int id) {
        boolean status = false;
        int connects = 0;
        try {
            while (!cells[id].getPlayer() == false) {
                connects++;
                id -= 8;
            }
        } catch (Exception e) {
            // Catch Error.
        }
        try {
            if (status == false) {
                connects = 0;
                id += 8;
                while (!cells[id].getPlayer() == false) {
                    connects++;
                    id += 8;
                    if (connects == 6) {
                        status = true;
                        System.out.println("Win Diagonal man" + id);
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return status;
    }

    public boolean checkDiagonalLeft(int id) {
        boolean status = false;
        int connects = 0;
        try {
            while (!cells[id].getPlayer() == false) {
                connects++;
                id += 10;
                status = false;

            }

        } catch (Exception e) {

        }
        try {
            if (status == false) {
                connects = 0;
                id -= 10;
                while (!cells[id].getPlayer() == false) {
                    connects++;
                    id -= 10;
                    System.out.println("These are ur " + connects);
                    if (connects == 6) {
                        status = true;
                        System.out.println("Win Diagonal man" + id);
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return status;
    }

    public void saveGame() throws IOException {
        File player = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\player.txt");
        File cpu = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\cpu.txt");
        BufferedWriter write = new BufferedWriter(new FileWriter(player, true));
        BufferedWriter writeEn = new BufferedWriter(new FileWriter(cpu, true));
        String str = new String();
        for (int i = 1; i < this.cells.length; i++) {
            if (this.cells[i].getPlayer() == true) {
                write.append(' ');
                str = String.valueOf(this.cells[i].getID());
                write.append(str);
            }
            if (this.cells[i].getCpu() == true) {
                writeEn.append(' ');
                str = String.valueOf(this.cells[i].getID());
                writeEn.append(str);
            }
        }
        write.close();
        writeEn.close();
    }

    public void loadGame() throws FileNotFoundException {
        File p = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\player.txt");
        File cp = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\cpu.txt");
        Scanner player = new Scanner(p);
        Scanner cpu = new Scanner(cp);
        String[] playerAr;
        String[] cpuAr;
        String raw_data = "";
        while (player.hasNextLine()) {
            raw_data += player.nextLine();
        }
        player.close();
        playerAr = raw_data.split(" ");
        raw_data = "";

        while (cpu.hasNextLine()) {
            raw_data += cpu.nextLine();
        }
        cpu.close();
        System.out.println(raw_data);
        cpuAr = raw_data.split(" ");
        for (int i = 0; i < cpuAr.length; i++) {
            int index = Integer.parseInt(cpuAr[i]);
            this.cells[index].setCpu();
            this.cells[index].setBackground(Color.blue);

        }
        for (int i = 0; i < playerAr.length; i++) {
            
            this.cells[Integer.parseInt(playerAr[i])].setPlayer();
            this.cells[Integer.parseInt(playerAr[i])].setBackground(Color.red);

        }
    }
}
