let foldr f x y = list_it f y x;;

type 'a btree = Void | Node of 'a * 'a btree * 'a btree;;

(*
si scriva in CAML una funzione
  leaves : ’a btree -> ’a*int list
che, dato un albero binario, restituisce una lista di coppie in cui ciascuna coppia contiene il valore di una
foglia ed il suo livello. La lista deve contenere una coppia per ciascuna foglia presente nell’albero. L’ordine
degli elementi nella lista risultato `e irrilevante.
*)

let leaves tree =
  let rec leaves_aux tree lvl =
    match tree with
      | Void ->
        []
      | Node (x, Void, Void) ->
        [(x, lvl)]
      | Node (_, lbt, rbt) when lbt <> Void || rbt <> Void ->
        leaves_aux lbt (lvl + 1) @ leaves_aux rbt (lvl + 1)
  in
  leaves_aux tree 1
;;

let rec split lst =
  match lst with
    | [] ->
      ([], [])
    | [x] ->
      ([x], [])
    | x :: y :: ys ->
      let (l1, l2) = split ys in
      (x :: l1, y :: l2)
;;

let rec bldTree lst =
  match lst with
    | [] ->
      Void
    | x :: xs ->
      let (l1, l2) = split xs in
        Node (x, bldTree l1, bldTree l2)
;;
