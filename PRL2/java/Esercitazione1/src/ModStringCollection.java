import java.util.Vector;

public class ModStringCollection implements ModStringCollection_iface{
    private int dim;
    private final Vector<ModString_iface> strs;
    private final Vector<Integer> occs;

    public ModStringCollection() {
        dim = 0;
        strs = new Vector<>();
        occs = new Vector<>();
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
            occs.setElementAt(occs.get(index) + 1, index);
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

            if(0 < num && num < occurrences) {
                occs.setElementAt(occurrences - num, index);
                dim -= occurrences - num;
                return num;
            } else {
                strs.removeElementAt(index);
                occs.removeElementAt(index);
                dim -= occurrences;
                return occurrences;
            }
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

//    private boolean contains(ModString_iface str) {
//        return strs.indexOf(str) >= 0;
//    }
//
//    private int strs.indexOf(ModString_iface str) {
//        for(int i = 0; i < strs.size(); i++) {
//            if(strs.get(i).toString().equals(str.toString())) {
//                return i;
//            }
//        }
//        return -1;
//    }
}
