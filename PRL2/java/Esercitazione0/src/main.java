import java.util.Scanner;

import static java.lang.System.exit;

public class main {

    public static void main(String[] args) {
        StringTable st = new StringTable();
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("Key: ");
            String key = input.next();
            System.out.print("Value: ");
            int value = input.nextInt();
            try {
                st.addName(key, value);
            } catch (DuplicateEntryException | EmptyStringException e) {
                e.printStackTrace();
                exit(1);
            }
        }
    }
}
