public class Tutor {

    private int numPC;
    private int freePCs;
    private boolean[] pcInUse;
    private int[] gradWaiting;
    private int profWaiting;
    

    public Tutor() {
        numPC = Const.NUM_PC;
        freePCs = numPC;
        gradWaiting = new int[numPC];
        pcInUse = new boolean[numPC];
        for (int i = 0; i < numPC; i++) {
            pcInUse[i] = false;
            gradWaiting[i] = 0;
        }
        profWaiting = 0;
    }

    public int requestPCID() {
        return Utils.random(1, numPC);
    }

    public synchronized int requestPC(int userID, Utils.UserType userType, int pc) {
        switch (userType) {
            case Undergrad:
                System.out.printf("User %d (U) is waiting a pc\n", userID);
                while(findFreePC() == -1 || freePCs <= 0 || profWaiting > 0) {
                    try { wait(); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                pc = findFreePC();
                System.out.printf("User %d (U) got pc n#%d\n", userID, pc);
                lockPC(pc);
                --freePCs;
                break;
            case Grad:
                System.out.printf("User %d (G) is waiting for pc n#%d\n", userID, pc);
                ++gradWaiting[pc];
                while(pcInUse[pc] || profWaiting > 0) {
                    try { wait(); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                --gradWaiting[pc];
                System.out.printf("User %d (G) got pc n#%d\n", userID, pc);
                lockPC(pc);
                --freePCs;
                break;
            case Professor:
                System.out.printf("User %d (P) is waiting the lab\n", userID);
                ++profWaiting;
                while(freePCs < numPC) {
                    try { wait(); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                --profWaiting;
                System.out.printf("User %d (P) got the lab\n", userID);
                pc = -1;
                freePCs = 0;
                lockPC(pc);
                break;
        }
        return pc;
    }

    public synchronized void releasePC(int userID, Utils.UserType userType, int pc) {
        if(userType.equals(Utils.UserType.Professor)) {
            System.out.printf("User %d (P) released the lab\n", userID);
            unlockPC(pc);
            freePCs = numPC;
        } else {
            System.out.printf("User %d (%s) released pc n#%d\n", userID, userType == Utils.UserType.Undergrad ? "U" : "G", pc);
            unlockPC(pc);
            ++freePCs;
        }
        notifyAll();
    }

    // returns the fist available pc, -1 if none is available
    private int findFreePC() {
        for(int i = 0; i < numPC; i++) {
            if(!pcInUse[i] && gradWaiting[i] <= 0) return i;
        }
        return -1;
    }

    /**
     * Lock a pc or the entire lab
     * 
     * @param pcId 0 -> numPC: lock the requested pc; -1: lock the entire lab
     * @throws IndexOutOfBoundsException
     */
    private synchronized void lockPC(int pcId) throws IndexOutOfBoundsException {
        if(pcId == -1)          for (int i = 0; i < numPC; i++) pcInUse[i] = true;
        else if(pcId > numPC)   throw new IndexOutOfBoundsException();
        else                    pcInUse[pcId] = true;
    }

    /**
     * Unlock a pc or the entire lab
     * 
     * @param pcId 0 -> numPC: unlock the requested pc; -1: unlock the entire lab
     * @throws IndexOutOfBoundsException
     */
    private synchronized void unlockPC(int pcId) throws IndexOutOfBoundsException {
        if(pcId == -1)          for (int i = 0; i < numPC; i++) pcInUse[i] = false;
        else if(pcId > numPC)   throw new IndexOutOfBoundsException();
        else                    pcInUse[pcId] = false;
    }
}

