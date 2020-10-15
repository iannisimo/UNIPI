public class CalculatePi {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: CalculatePi <accuracy> <timeout>");
            return;
        }
        Float accuracy = Float.parseFloat(args[0]);
        Integer timeout = Integer.parseInt(args[1]);
        PiCalc pc = new PiCalc(accuracy, timeout);
        pc.start();
        try {
            pc.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(pc.getPi());
    }
}
