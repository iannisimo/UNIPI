let foldr f x y = list_it f y x;;

type 'a btree = Void | Node of 'a * 'a btree * 'a btree;;

(*
definire una funzione
  leafsdepth: ’a btree -> int -> int
in modo che (leafsdepth bt n) restituisca il numero di foglie dell’albero che occorrono a profondit`a n
*)

let leafsdepth tree n =
  let rec leafsdepth_aux tree lvl =
    match tree with
      | Void ->
        0
      | Node (x, Void, Void) ->
        if lvl = n then
          1
        else
          0
      | Node (_, lbt, rbt) when (lbt <> Void || rbt <> Void) ->
        if lvl < n then
          leafsdepth_aux lbt (lvl + 1) + leafsdepth_aux rbt (lvl + 1)
        else
          0
  in
  leafsdepth_aux tree 1
;;
