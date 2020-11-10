import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Const {
    public static final int N_CONSUMERS = 7;
    public static final List<String> causals = Collections.unmodifiableList(
        new ArrayList<String>() {{
            add("Bonifico");
            add("Accredito");
            add("Bollettino");
            add("F24");
            add("PagoBancomat");
        }});
}
