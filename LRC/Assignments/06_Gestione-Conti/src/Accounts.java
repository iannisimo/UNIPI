import java.util.ArrayList;

public class Accounts {
    private final ArrayList<Account> accounts = new ArrayList<Account>();
    
    public ArrayList<Account> getAccounts() {
        return new ArrayList<Account>(this.accounts);
    }

    public void addAccount(Account a) {
        accounts.add(a);
    }
}
