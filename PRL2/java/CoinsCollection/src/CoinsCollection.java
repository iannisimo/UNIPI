//public interface CoinsCollection {
//    /**
//     * Overview:  Collezione di monete da 50c 1E e 2E
//     * TypicalEl: [v1*50C, v2*1E, v3*2E]
//     */
//
//    /**
//     * Overview: Crea una CoinsCollection vuota
//     * Modifies: this
//     * Effects:  this.v1 = this.v2 = this.v3 = 0
//     */
//    void createCC();
//
//    /**
//     * Overview: Aggiunge una moneta di valore coin (in centesimi) alla collezione
//     * Requires: coin != null, coin appartenente {50, 100, 200}
//     * Throws:   NullPointerException (unchecked, incluso in Java)
//     *           IllegalArgumentException (unchecked, incluso in Java)
//     * Modifies: this
//     * Effects:  switch (coin)
//     *             case  50: v1++
//     *             case 100: v2++
//     *             case 200: v3++
//     */
//    void addCoin(Integer coin);
//
//    /**
//     * Overview: Restituisce il valore totale della collezione (in centesimi)
//     * Requires: -
//     * Modifies: -
//     * Effects:  Ritorna v1*50 + v2*100 * v3*200
//     */
//    Integer balance();
//
//    /**
//     * Overview: 'Vedere testo'
//     * Requires: amount != null, amount >= 0,
//     *           amount = a*50 + b*100 + c*200 con (0 <= a <= v1, 0 <= b <= v2, 0 <= c <= v3)
//     * Throws:   NullPointerException (unchecked, incluso in java)
//     *           IllegalArgumentException (unchecked, incluso in Java)
//     *           NotExchangeableException (checked, non presente in Java)
//     * Modifies: this
//     * Effects:  this_post = this_pre - {a, b. c}
//     *           ritorna una CoinsCollection con v1 = a, v2 = b, v3 = c
//     */
//    CoinsCollection makeChange(Integer amount);
//}
