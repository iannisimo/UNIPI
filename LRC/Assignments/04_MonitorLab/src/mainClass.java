public class mainClass {
    
    private static User[] users;
    private static Thread[] threads;
    private static Tutor tutor;

    /*
     * args: {nUndergrad, nGrad, nProf}
    */
    public static void main(String[] args) {
        if(args.length != Const.ARGS) {
            System.out.println("Usage: pName nStud, nTes, nProf");
            System.exit(1);
        }
        final int nUndergrad = Integer.parseInt(args[0]);
        final int nGrad = Integer.parseInt(args[1]);
        final int nProf = Integer.parseInt(args[2]);
        users = new User[nUndergrad + nGrad + nProf];
        threads = new Thread[nUndergrad + nGrad + nProf];
        tutor = new Tutor();
        for(int i = 0; i < nUndergrad; i++) {
            users[i] = new User(i, Utils.UserType.Undergrad, tutor);
            threads[i] = new Thread(users[i]);
            threads[i].start();
        }
        for(int i = 0; i < nGrad; i++) {
            users[nUndergrad + i] = new User(nUndergrad + i, Utils.UserType.Grad, tutor);
            threads[nUndergrad + i] = new Thread(users[nUndergrad + i]);
            threads[nUndergrad + i].start();
        }
        for(int i = 0; i < nProf; i++) {
            users[nUndergrad + nGrad + i] = new User(nUndergrad + i, Utils.UserType.Professor, tutor);
            threads[nUndergrad + nGrad + i] = new Thread(users[nUndergrad + nGrad + i]);
            threads[nUndergrad + nGrad + i].start();
        }
        
    }

}
