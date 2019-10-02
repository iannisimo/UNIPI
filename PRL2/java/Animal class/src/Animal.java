public class Animal {
    private boolean alive;
    private int strength;

    public Animal(int strength) {
        this.alive = true;
        this.strength = strength;
    }

    public int kill(int force) {
        if(force > strength) {
            this.alive = false;
        }
        return this.alive ? strength : 0;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public int getStrength() {
        return this.strength;
    }

    public String toString() {
        if(alive) {
            return "Alive, Strength: " + this.strength;
        }
        return "Dead";
    }
}
