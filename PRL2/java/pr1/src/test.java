import BoardPKG.Data;
import BoardPKG.DataBoard;
import BoardPKG.*;

import java.util.Iterator;
import java.util.List;

public class test {

    private static final String USER_A = "a";
    private static final String USER_B = "b";
    private static final String USER_C = "c";
    private static final String PW_A = "pwd_a";
    private static final String PW_B = "pwd_b";
    private static final String PW_C = "pwd_c";

    public static void main(String[] args) {
        System.out.println("\u001B[1mTesting DataBoard.Board\u001B[0m\n");
        DataBoard<Data> a = new Board<>(USER_A, PW_A);
        DataBoard<Data> b = new Board<>(USER_B, PW_B);
        DataBoard<Data> c = new Board<>(USER_C, PW_C);
        try {
            if(!batch(a, b, c)) throw new Exception();
        } catch (Exception e) {
            System.out.print("\n\u001B[1m\u001B[31mTHIS SHOULD NOT HAVE HAPPENED\u001B[0m\n\n");
            e.printStackTrace();
        }
        System.out.println("\n\u001B[1mTesting DataBoard.Board2\u001B[0m\n");
        a = new Board2<>(USER_A, PW_A);
        b = new Board2<>(USER_B, PW_B);
        c = new Board2<>(USER_C, PW_C);
        try {
            if(!batch(a, b, c)) throw new Exception();
        } catch (Exception e) {
            System.out.print("\n\u001B[1m\u001B[31mTHIS SHOULD NOT HAVE HAPPENED\u001B[0m\n\n");
            e.printStackTrace();
        }
    }

    public static Boolean batch(DataBoard<Data> a, DataBoard<Data> b, DataBoard<Data> c) throws NullPointerException, InvalidActionException {
        a.createCategory("memes", PW_A);
        a.createCategory("serious", PW_A);
        b.createCategory("memes", PW_B);
        b.createCategory("serious", PW_B);
        c.createCategory("serious", PW_C);
        a.put(PW_A, new Data("Cute doggo doing doggo things"), "memes");
        a.put(PW_A, new Data("BOTTOM TEXT"), "memes");
        a.put(PW_A, new Data("Generic political opinion"), "serious");
        b.put(PW_B, new Data("Watch this catto, he is a piece of s***"), "memes");
        b.put(PW_B, new Data("Other political opinion"), "serious");
        c.put(PW_C, new Data("Same political opinion as a"), "serious");
        a.addFriend("memes", PW_A, USER_B);
        a.addFriend("serious", PW_A, USER_C);
        b.addFriend("memes", PW_B, USER_A);
        c.addFriend("serious", PW_C, USER_A);
        a.addFriend("serious", PW_A, USER_B);
        c.addFriend("serious", PW_C, USER_A);
        testPrint("Wrong password");
        try {
            a.createCategory("", "");
            return false;
        } catch (Exception e) {
            passPrint(e);
        }
        testPrint("Non-existent category");
        try {
            a.put(PW_A, new Data(""), "invalid");
            return false;
        } catch (Exception e) {
            passPrint(e);
        }
        testPrint("Null data");
        try {
            a.put(PW_A, null, "memes");
            return false;
        } catch (Exception e) {
            passPrint(e);
        }

        Iterator<Data> b_a = a.getFriendIterator(USER_B);
        Data serious = null;
        Data meme = null;
        while (b_a.hasNext()) {
            Data data = b_a.next();
            if(data.getCategory() == "memes") {
                meme = data;
                a.insertLike(USER_B, data);
            }
            else
                serious = data;
        }
        // B finds out A has a different political opinion
        a.removeFriend("serious", PW_A, USER_B);
        b.removeFriend("serious", PW_B, USER_A);
        
        testPrint("Like from a non-friend");
        try {
            a.insertLike(USER_B, serious);
            return false;
        } catch (Exception e) {
            passPrint(e);
        }
        testPrint("Re-posting");
        try {
            b.put(PW_B, meme, "memes");
            return false;
        } catch (Exception e) {
            passPrint(e);
        }
        testPrint("Post in the wrong category");
        try {
            c.put(PW_C, new Data(USER_C, "", ""), "serious");
            return false;
        }  catch (Exception e) {
            passPrint(e);
        }
//         Clearing c board
        Iterator<Data> c_ = c.getIterator(PW_C);
        while (c_.hasNext()) {
            serious = c.remove(PW_C, c_.next());
        }
        testPrint("Remove a post that doesn't exist");
        try {
            c.remove(PW_C, new Data(USER_C, "", "serious"));
            return false;
        } catch (Exception e) {
            passPrint(e);
        }

        //TODO
        Iterator<Data> a_ = a.getIterator(PW_A);
        Data d = a_.next();
        a.insertLike(USER_C, d);
        Data copy = a.get(PW_A, d);
        //TODO

        List<Data> a_list = a.getDataCategory(PW_A, "memes");
        return true;
    }

    private static void testPrint(String str) {
        System.out.print("\u001B[34mTesting: " + str + "\u001B[0m\n");
    }
    private static void passPrint(Exception e) {
        System.out.print("\u001B[32m\tPASS: " + e + "\u001B[0m\n");
    }
}
