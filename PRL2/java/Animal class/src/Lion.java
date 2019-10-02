public class Lion extends Animal {

    private static final int minStrength = 100;
    private static final int maxStrength = 1000;

    private int id;

    public Lion(int id) {
        super(utils.randominRange(minStrength, maxStrength));
        this.id = id;
    }

    public String toString() {
        return id + ":\t" + super.toString();
    }
}
