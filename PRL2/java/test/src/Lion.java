public class Lion implements Animal {

    private boolean alive;
    private int strength;

    public Lion(boolean alive, int strength) {
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

    public String toString() {
        return "Alive: " + this.alive + ", strength: " + this.strength;
    }
}
