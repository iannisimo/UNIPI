let rec pushO el l =
  match l with
      [] ->
        el :: []
    | x :: xs ->
        if el > x then
          x :: pushO el xs
        else
          el :: l;;

(*
RIVELAZIONE!!
  Non tutti gli operatori si possono usare nei pattern
  Ex:
    match n with
      x + 1 -> ...      !!!
    match l with
      l1 @ l2 -> ..     !!!
  Operatori accettabili (costruttori di valori, operatori di base):
    Servono per costruire valori di un tipo
    Liste:
      []
      ::
*)
