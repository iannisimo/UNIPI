public class Bunny implements Animal {

    private boolean alive;
    private int strength;

    public Bunny(boolean alive, int strength) {
        this.alive = alive;
        this.strength = strength;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public boolean kill(int force) {
        if(force > this.strength) {
            this.alive = false;
        }
        return this.alive;
    }

    public void setAlive(int strength) {
        this.alive = true;
        this.strength = strength;
    }

    public String toString() {
        return "Alive: " + this.alive + ", strength: " + this.strength;
    }
}
