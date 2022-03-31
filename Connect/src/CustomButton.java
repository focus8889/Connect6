import javax.swing.JButton;

public class CustomButton extends JButton {
    private boolean player;
    private boolean cpu;
    private boolean first;
    private boolean last;
    private int id;

    // Setters.

    public void setPlayer() {
        this.player = true;
    }

    public void setCpu() {
        this.cpu = true;
    }

    public void setFirst() {
        this.first = true;
    }

    public void setLast() {
        this.last = true;
    }

    public void setID(int id) {
        this.id = id;
    }

    // Getters.

    public boolean getFirst() {
        return this.first;
    }

    public boolean getLast() {
        return this.last;
    }

    public boolean getPlayer() {
        return this.player;
    }

    public boolean getCpu() {
        return this.cpu;
    }

    public int getID() {
        return this.id;
    }
}