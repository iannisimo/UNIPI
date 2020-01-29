import java.util.List;

public interface Game<V> {
    /**
     * Overview:   Collection of results of a sport
     * Typical EL: {n, res_0 ... res_dim}
     */

    /**
     * Overview: Sets the result res of player num
     *
     */
    // assegna al concorrente num il risultato res
    public void setResult(int num, V res);
    // restituisce il miglior risultato fra quelli conseguiti fino a quel momento
    public int first();
    // restituisce la lista dei risultati conseguiti fino a quel momento
    public List<V> results();
}