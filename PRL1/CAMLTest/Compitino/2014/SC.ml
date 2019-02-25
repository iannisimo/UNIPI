let foldr f x y = list_it f y x;;

type 'a btree = Void | Node of 'a * 'a btree * 'a btree;;

(*
Si definisca in CAML una funzione pfoglie con tipo
  pfoglie : (’a -> bool) -> ’a btree -> int
in modo che (pfoglie p bt) restituisca il numero dei nodi foglia di bt la cui etichetta soddisfa p.
*)

let rec pfoglie p bt =
  match bt with
    | Void ->
      0
    | Node (_, lbt, rbt) when lbt <> Void || rbt <> Void ->
      pfoglie p lbt + pfoglie p rbt
    | Node (x, Void, Void) ->
      if p x then
        1
      else
        0
;;
