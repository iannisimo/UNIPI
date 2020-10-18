public class User implements Runnable {
    
    private Utils.UserType userType;
    private int userID;
    private Tutor tutor;
    private int pc;
    private int accesses;
    
    public User(int userID, Utils.UserType userType, Tutor tutor) {
        this.userID = userID;
        this.userType = userType;
        this.tutor = tutor;
        this.accesses = Utils.random(Const.MIN_ACCESSES, Const.MAX_ACCESSES);
        if(this.userType == Utils.UserType.Grad) this.pc = this.tutor.requestPCID();
    }

    public void run() {
        for(int i = 0; i < accesses; i++) {
            pc = tutor.requestPC(userID, userType, pc);
            try { Thread.sleep(Utils.random(0, Const.MAX_USAGE_TIME)); }
            catch (InterruptedException e) { e.printStackTrace(); }
            tutor.releasePC(userID, userType, pc);
            if(i <= accesses-1) {
                try { Thread.sleep(Utils.random(0, Const.MAX_PAUSE_TIME)); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

}
