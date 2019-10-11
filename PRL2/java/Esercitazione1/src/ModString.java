public class ModString implements ModString_iface {

    private int dim;
    private final Character[] charEl;

    public ModString(String string) {
        dim = string.length();
        charEl = new Character[dim];
        for (int i = 0; i < dim; i++) {
            charEl[i] = string.charAt(i);
        }
    }

    @Override
    public int size() {
        return dim;
    }

    @Override
    public void update(char c, int num) throws IllegalArgumentException {
        if(num < 0 || num >= dim) throw new IllegalArgumentException("<num> out of bounds");
        charEl[num] = c;
    }

    @Override
    public void remove(int num) throws IllegalArgumentException {
        if(num < 0 || num >= dim) throw new IllegalArgumentException("<num> out of bounds");
        dim -= 1;
        for(int i = num; i < dim; i++) {
            charEl[i] = charEl[i+1];
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            s.append(charEl[i]);
        }
        return s.toString();
    }
}
