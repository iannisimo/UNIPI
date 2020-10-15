package src;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import src.Utils.UserType;

public class Tutor {

    private int numPC;
    // private Boolean[] pcs;
    private ReentrantLock tutorLock;
    private ReentrantLock[] pcLocks;
    private Condition pcFreed;
    private int freePCs;
    private int[] gradWaiting;
    private int profWaiting;
    

    public Tutor() {
        numPC = Const.NUM_PC;
        freePCs = numPC;
        // pcs = new Boolean[numPC];
        gradWaiting = new int[numPC];
        tutorLock = new ReentrantLock();
        pcFreed = tutorLock.newCondition();
        pcLocks = new ReentrantLock[numPC];
        for (int i = 0; i < numPC; i++) {
            // pcs[i] = false;
            pcLocks[i] = new ReentrantLock();
            gradWaiting[i] = 0;
        }
        profWaiting = 0;
    }

    public int requestPCID() {
        return Utils.random(1, numPC);
    }

    public int requestPC(int userID, UserType userType, int pc) {
        tutorLock.lock();
        switch (userType) {
            case Undergrad:
                System.out.printf("User %d (U) is waiting a pc\n", userID);
                while(freePCs <= 0 || profWaiting > 0) {
                    try { pcFreed.await(); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                pc = findFreePC();
                System.out.printf("User %d (U) got pc n#%d\n", userID, pc);
                // pcs[pc] = true;
                pcLocks[pc].lock();
                --freePCs;
                break;
            case Grad:
                System.out.printf("User %d (G) is waiting for pc n#%d\n", userID, pc);
                ++gradWaiting[pc];
                while(pcLocks[pc].isLocked() || profWaiting > 0) {
                    try { pcFreed.await(); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                --gradWaiting[pc];
                System.out.printf("User %d (G) got pc n#%d\n", userID, pc);
                // pcs[pc] = true;
                pcLocks[pc].lock();
                --freePCs;
                break;
            case Professor:
                System.out.printf("User %d (P) is waiting the lab\n", userID);
                ++profWaiting;
                while(freePCs < numPC) {
                    try { pcFreed.await(); } 
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                --profWaiting;
                System.out.printf("User %d (P) got the lab\n", userID);
                pc = -1;
                freePCs = 0;
                for(int i = 0; i < numPC; i++) {
                    pcLocks[i].lock();
                    // pcs[i] = true;
                }
                break;
        }
        tutorLock.unlock();
        return pc;
    }

    public void releasePC(int userID, UserType userType, int pc) {
        tutorLock.lock();
        if(userType.equals(UserType.Professor)) {
            System.out.printf("User %d (P) released the lab\n", userID);
            for(int i = 0; i < numPC; i++) {
                pcLocks[i].unlock();
                // pcs[i] = false;
            }
            freePCs = numPC;
        } else {
            System.out.printf("User %d (%s) released pc n#%d\n", userID, userType == UserType.Undergrad ? "U" : "G", pc);
            pcLocks[pc].unlock();
            // pcs[pc] = false;
            ++freePCs;
        }
        pcFreed.signalAll();
        tutorLock.unlock();
    }

    // returns the fist available pc, -1 if none is available
    private int findFreePC() {
        for(int i = 0; i < numPC; i++) {
            if(!pcLocks[i].isLocked() && gradWaiting[i] <= 0) return i;
        }
        return -1;
    }
}

