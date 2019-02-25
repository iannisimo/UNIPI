(*
si definisca in CAML una funzione subst con tipo
  subst : int btree -> int btree -> int btree
in modo che (subst bt tr) restituisca l’albero ottenuto da bt rimpiazzando con tr tutte le foglie con valore
positivo
*)

let rec subst bt tr =
  match bt with
    | Void ->
      Void
    | Node (x, Void, Void) ->
      if x > 0 then
        tr
      else
        Node (x, Void, Void)
    | Node (x, lbt, rbt) when lbt <> Void || rbt <> Void ->
      Node (x, subst lbt tr, subst rbt tr)
;;

subst (Node (5, (Node (-1, Void, Void)), (Node (4, Void, Void)))) (Node (10, Void, Void));;
