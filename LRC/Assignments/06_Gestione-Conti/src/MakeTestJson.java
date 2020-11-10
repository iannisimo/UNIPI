import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MakeTestJson {
    static final int N_ACCOUNTS = 100;
    static final int MIN_TRANS = 5;
    static final int MAX_TRANS = 50;

    private static String getRandCausal() {
        return Const.causals.get(new Random().nextInt(Const.causals.size()));
    }

    private static int random(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("res/bank_records.json");
        Accounts accounts = new Accounts();
        for (int i = 0; i < N_ACCOUNTS; i++) {
            Account account = new Account();
            account.setName(String.valueOf(i));
            for (int j = 0; j < random(MIN_TRANS, MAX_TRANS); j++) {
                Movement movement = new Movement();
                movement.setCausal(getRandCausal());
                movement.setDate(new Date(random(1970, 2020), random(0, 11), random(0, 28)));
                account.addMovement(movement);
            }
            accounts.addAccount(account);
        }
        try {
            objectMapper.writeValue(file, accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
