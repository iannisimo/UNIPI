import java.util.Vector;

public class ModStringCollection implements ModStringCollection_iface{
    private int dim;
    private final Vector<ModString_iface> strs;
    private final Vector<Integer> occs;

    public ModStringCollection() {
        dim = 0;
        strs = new Vector<ModString_iface>();
        occs = new Vector<Integer>();
    }


    @Override
    public int occurrences(ModString_iface str) throws NullPointerException {
        if(str == null) throw new NullPointerException();
        if(strs.contains(str)) {
            return occs.get(strs.indexOf(str));
        }
        return 0;
    }

    @Override
    public int size() {
        return dim;
    }

    @Override
    public void insert(ModString_iface str) throws NullPointerException{
        if(str == null) throw new NullPointerException();
        if(strs.contains(str)) {
            int index = strs.indexOf(str);
            occs.set(index, occs.get(index) + 1);
        } else {
            strs.add(str);
            occs.add(1);
        }
        dim++;
    }

    @Override
    public int remove(ModString_iface str, int num) throws NullPointerException, IllegalArgumentException {
        if(str == null) throw new NullPointerException();
        if(num < 0) throw new IllegalArgumentException();
        if(strs.contains(str)) {
            int index = strs.indexOf(str);
            int occurrences = occs.get(index);

            // FIXME: This shit is garbage
            if(num == 0) {
                strs.removeElementAt(index);
                occs.removeElementAt(index);
                return occurrences;
            } else {
                occs.setElementAt(occurrences - num, index);
                return
            }
            dim -= occurrences - num;
            return occurrences - num;
            // UPTO: Here
        }
        return 0;
    }

    @Override
    public String[] getUnique() {
        String[] tmp = new String[strs.size()];
        for (int i = 0; i < strs.size(); i++) {
            tmp[i] = strs.get(i).toString();
        }
        return tmp;
    }
}
