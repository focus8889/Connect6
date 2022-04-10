import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class LogicProcessing {
    int id = 0;
    ArrayList<Integer> availableGrids = new ArrayList<Integer>();
    ArrayList<Integer> cpuDiscs = new ArrayList<Integer>();
    CustomButton[] cells = new CustomButton[73];
    int cpuLastCell;
    boolean cpuFirst = true;

    public LogicProcessing(Grid grid) throws FileNotFoundException {
        if (grid.saveLoad == false) {
            generateBoard(grid);
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
                        try {
                            int id = playerMove(btn.getID());
                            cpuCheck(id);
                            cpuWinDetect(grid);
                            winDetect(id, grid);
                            System.out.println(availableGrids);
                        } catch (Exception d) {
                        }

                    }
                });

            }
        }
        // Adding initially available coordinates.
        for (int i = 64; i < 73; i++) {
            availableGrids.add(i);
        }
    }

    public int playerMove(int id) {
        if (availableGrids.contains(id)) {
            this.cells[id].setPlayer();
            this.cells[id].setBackground(Color.red);
            if (id < 10) {
                availableGrids.remove(availableGrids.indexOf(id));
            }
            if (id >= 10) {
                availableGrids.remove(availableGrids.indexOf(id));
                availableGrids.add(id - 9);
            } else {

            }
        }
        if (!availableGrids.contains(id)) {
            while (!availableGrids.contains(id)) {
                id += 9;
                System.out.println(availableGrids);
            }
            cells[id].setBackground(Color.red);
            availableGrids.remove(availableGrids.indexOf(id));
            availableGrids.add(id - 9);
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
            if (id - 18 > 1) {
                availableGrids.add(id - 18);
            }
            cpuDiscs.add(id - 9);
        }
        if ((rowCheck(id) == false) & (columnCheck(id) == false)) { // Cpu makes move.
            cpuMove(cpuFirst);

        }
        if (rowCheck(id) == true) { // Checking if player has chance to win horizontally.
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
                    if ((connects == 5) & (id >= 10)) {
                        status = true;
                        break;
                    }
                    continue;
                } else {
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
        int allow = 0;
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
                    allow = 1;
                    cells[id].setCpu();
                    cells[id].setEnabled(false);
                    cells[id].setBackground(Color.blue);
                    if (availableGrids.contains(id)) {
                        availableGrids.remove(availableGrids.indexOf(id));
                        availableGrids.add(id - 9);
                        cpuDiscs.add(id);
                        status = true;
                    }

                }

            }
        } catch (Exception e) {

        }
        if (allow == 1) {
            status = true;
        }
        return status;

    }

    public boolean noWinner() {
        boolean status = false;

        if (availableGrids.size() == 0) {
            status = true;
        }
        System.out.println(status);
        return status;
    }

    public void winDetect(int clicked, Grid grid) {
        if (noWinner() == true) {
            System.out.println("Drow");
            JOptionPane.showMessageDialog(grid.frame, "Drow");
            System.exit(0);
        }
        if (checkVertical(clicked) == true) {
            JOptionPane.showMessageDialog(grid.frame, "YOU WON vertical");
            System.exit(0);
        }
        if (horizonatlCheck(clicked) == true) {
            JOptionPane.showMessageDialog(grid.frame, "YOU WON horizontal");
            System.exit(0);
        }
        if (checkDiagonalRight(clicked) == true) {
            JOptionPane.showMessageDialog(grid.frame, "YOU WONDiagonal");
            System.exit(0);
        }
        if (checkDiagonalLeft(clicked) == true) {
            JOptionPane.showMessageDialog(grid.frame, "YOU WON Diagonal");
            System.exit(0);
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
                    status = true;
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
                    if (connects == 6) {
                        status = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return status;
    }

    public boolean checkDiagonalRightCPu(int id) {
        boolean status = false;
        int connects = 0;
        try {
            while (!cells[id].getCpu() == false) {
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
                while (!cells[id].getCpu() == false) {
                    connects++;
                    id += 8;
                    if (connects == 6) {
                        status = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return status;
    }

    // Cpu Diagonal check.
    public boolean checkDiagonalLeftCpu(int id) {
        boolean status = false;
        int connects = 0;
        try {
            while (!cells[id].getCpu() == false) {
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
                while (!cells[id].getCpu() == false) {
                    connects++;
                    id -= 10;
                    if (connects == 6) {
                        status = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return status;
    }

    // Detecting CPU horizontal discs if 6 then cpu is winner.
    public boolean horizonatlCheckCPU(int id) {
        boolean status = false;
        int row = cells[id].getRowID();
        int connects = 0;
        try {
            while (cells[id].getCpu() == true) {
                if (cells[id].getRowID() == row) {
                    id--;
                } else {
                    break;
                }
            }
            id++;
            while (cells[id].getCpu() == true) {
                connects++;
                id++;
                if (connects == 6) {
                    status = true;
                    break;
                } else {
                    break;
                }
            }
        } catch (Exception e) {

        }
        return status;
    }

    public boolean checkVerticalCpu(int clicked) {
        boolean win = false;
        int discs = 0;
        try {
            while (true) {
                if ((cells[clicked].getCpu() == true) & (clicked <= 63)) {
                    discs++;
                    clicked += 9;

                } else {
                    break;
                }
                if ((discs == 5) & (cells[clicked].getCpu() == true)) { // Checks last iteration (clicked).
                    discs = 6;
                    win = true;
                    break;
                }
            }
        } catch (Exception e) {

        }
        return win;
    }

    public void saveGame() throws IOException {
        // Loading Files.
        File player = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\player.txt");
        File cpu = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\cpu.txt");
        File ava = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\available.txt");
        // Writing to the files.
        BufferedWriter write = new BufferedWriter(new FileWriter(player, false));
        BufferedWriter writeEn = new BufferedWriter(new FileWriter(cpu, false));
        BufferedWriter av = new BufferedWriter(new FileWriter(ava, false));
        String str = new String();
        for (int i = 1; i < this.cells.length; i++) {
            if (this.cells[i].getPlayer() == true) {
                str = String.valueOf(this.cells[i].getID());
                write.write(str);
                write.write(" ");
            }
            if (this.cells[i].getCpu() == true) {
                str = String.valueOf(this.cells[i].getID());
                writeEn.write(str);
                writeEn.write(" ");
            }
        }
        for (int i = 0; i < availableGrids.size(); i++) {
            str = Integer.toString(availableGrids.get(i));
            av.write(str);
            av.write(" ");
        }
        write.close();
        writeEn.close();
        av.close();
    }

    public void loadGame() throws FileNotFoundException {
        // Loading Files.
        File p = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\player.txt");
        File cp = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\cpu.txt");
        File ava = new File("C:\\Users\\Амир\\Documents\\GitHub\\Connect6\\Connect\\savedGame\\available.txt");
        // Reading Files.
        Scanner player = new Scanner(p);
        Scanner cpu = new Scanner(cp);
        Scanner av = new Scanner(ava);
        String[] playerAr;
        String[] cpuAr;
        String[] AvailableAr;
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
        cpuAr = raw_data.split(" ");
        raw_data = "";
        while (av.hasNextLine()) {
            raw_data += av.nextLine();
        }
        av.close();
        AvailableAr = raw_data.split(" ");
        for (int i = 0; i < cpuAr.length; i++) {
            int index = Integer.parseInt(cpuAr[i]);
            this.cells[index].setCpu();
            this.cells[index].setBackground(Color.blue);
            this.cells[index].setEnabled(false);
        }
        for (int i = 0; i < playerAr.length; i++) {
            availableGrids.add(Integer.parseInt(playerAr[i]) - 9);
            this.cells[Integer.parseInt(playerAr[i])].setEnabled(false);
            this.cells[Integer.parseInt(playerAr[i])].setPlayer();
            this.cells[Integer.parseInt(playerAr[i])].setBackground(Color.red);

        }
        availableGrids.clear();
        for (int i = 0; i < AvailableAr.length; i++) {
            availableGrids.add(Integer.parseInt(AvailableAr[i]));
        }
        cpuLastCell = Integer.parseInt(AvailableAr[AvailableAr.length - 1]);
    }

    public void cpuMove(boolean first) {
        if (first == true) {
            Random random = new Random();
            try {
                while (true) {
                    int index = random.nextInt(availableGrids.size());
                    cpuLastCell = availableGrids.get(index);
                    if (index >= 0) {
                        break;
                    } else {
                        break;
                    }

                }
                cells[cpuLastCell].setCpu();
                cells[cpuLastCell].setEnabled(false);
                cells[cpuLastCell].setBackground(Color.blue);
                availableGrids.remove(availableGrids.indexOf(cpuLastCell));
                if (cpuLastCell - 9 >= 10) {
                    availableGrids.add(cpuLastCell - 9);
                }
                cpuDiscs.add(cpuLastCell);
                cpuFirst = false;

            } catch (Exception e) {

            }
        } else {
            if ((availableGrids.contains(cpuLastCell - 9))) { // Checking if there available
                                                              // // cell to move.
                cpuLastCell -= 9;
                System.out.println(cpuLastCell);
                cpuDiscs.add(cpuLastCell);
                cells[cpuLastCell].setCpu();
                cells[cpuLastCell].setBackground(Color.blue);
                cells[cpuLastCell].setEnabled(false);
                availableGrids.remove(availableGrids.indexOf(cpuLastCell));
                if (cpuLastCell >= 10) {
                    availableGrids.add(cpuLastCell - 9);
                }
                cpuFirst = false;
            } else { // If the coordinate is not available we are going to new one.
                cpuMove(true);
            }
        }
    }

    public void cpuWinDetect(Grid grid) {
        for (int i = 0; i < cpuDiscs.size(); i++) {
            if (horizonatlCheckCPU(cpuDiscs.get(i)) == true) {
                JOptionPane.showMessageDialog(grid.frame, "CPU WON horizontal");
                System.exit(0);
            }
            if (checkVerticalCpu(cpuDiscs.get(i)) == true) {
                JOptionPane.showMessageDialog(grid.frame, "CPU WON vertical");
                System.exit(0);
            }
            if (checkDiagonalRightCPu(cpuDiscs.get(i)) == true) {
                JOptionPane.showMessageDialog(grid.frame, "CPU WON diagonal");
                System.exit(0);
            }
            if (checkDiagonalLeftCpu(cpuDiscs.get(i)) == true) {
                JOptionPane.showMessageDialog(grid.frame, "CPU WON diagonal");
                System.exit(0);
            }
        }
    }

    public void resetGrid() {
        for (int i = 1; i < cells.length; i++) {
            if (cells[i].getPlayer() == true) {
                cells[i].setPlayerFalse();
                cells[i].setEnabled(true);
            }
            if (cells[i].getCpu() == true) {
                cells[i].setCpuFalse();
                cells[i].setEnabled(true);
            }
            cells[i].setBackground(Color.WHITE);
        }
        availableGrids.clear();
        for (int i = 64; i < 73; i++) {
            availableGrids.add(i);
        }
    }
}
