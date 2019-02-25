let foldr f x y = list_it f y x;;

type 'a btree = Void | Node of 'a * 'a btree * 'a btree;;

let testBT = Node (5, Node (3, Void, Void), Node (8, Void, Void));;

let rec member el bt =
  match bt with
    | Void ->
      false
    | Node (x, _, _) when x = el ->
      true
    | Node (x, lbt, rbt) when x <> el ->
      if x > el then
        member el lbt
      else
        member el rbt
;;

let rec insBT el bt =
  match bt with
    | Void ->
      Node (el, Void, Void)
    | Node (x, lbt, rbt) ->
      if el <= x then
        Node (x, insBT el lbt, rbt)
      else
        Node (x, lbt, insBT el rbt)
;;

(*
Data una lista generare l'albero di ricerca corrispondente
*)

let rec lisToBT lis =
  match lis with
    | [] ->
      Void
    | x :: xs ->
      insBT x (lisToBT xs)
;;

qwertyuiopasdfghjkl;'\][zxcvbbnmm,,./<>A  1234567890-=`<`]'
