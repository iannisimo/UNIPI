public class test {
    public static void main(String[] args) {
        ModStringCollection msc = new ModStringCollection();
        msc.insert(new ModString("ciao"));
        msc.insert(new ModString("ciao"));
        msc.insert(new ModString("miao"));
        System.out.println(msc.occurrences(new ModString("ciao")));
        System.out.println(msc.occurrences(new ModString("miao")));
        System.out.println(msc.occurrences(new ModString("test")));
        String[] mscS = msc.getUnique();
        for(String s : mscS) {
            System.out.println(s);
        }
    }
}
