import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private final ArrayList<Movement> movements = new ArrayList<Movement>();
    
    public void setName(String name) {
        this.name = name;
    }

    public void addMovement(Movement m) {
        movements.add(m);
    }

    public String getName() {
        return this.name;
    }

    public List<Movement> getMovements() {
        return new ArrayList<>(this.movements);
    }
}
