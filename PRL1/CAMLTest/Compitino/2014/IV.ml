let foldr f x y = list_it f y x;;

type 'a btree = Void | Node of 'a * 'a btree * 'a btree;;

(*
Si definisca in CAML, senza utilizzare ricorsione esplicita, una funzione
  split : ’a list -> ’a list list
in modo che (split xs) restituisca la lista delle pi`u lunghe sottoliste non decrescenti in xs.
Ad esempio
  split [1;2;2;1;5;4;6;3;2;3] = [[1;2;2]; [1;5]; [4;6]; [3]; [2;3]]
*)

let testList = [1;2;2;1;5;4;6;3;2;3];;

let split lst =
  let f val acc =
    match acc with
      | [] ->
        [[val]]
      | x :: xs ->
        match x with
          y :: ys ->
            if val <= y then
              (val :: x) :: xs
            else
              [val] :: acc
  in
  foldr f [] lst;;

(*
Si scriva in CAML una funzione
  even : ’a btree -> ’a list
che, dato un albero binario, restituisce la lista con i valori di tutti i nodi che occorrono a livelli pari nell’albero.
*)

let even btO =
  let rec even_aux bt lvl =
    match bt with
      | Void ->
        []
      | Node (x, lbt, rbt) ->
        if lvl mod 2 = 0 then
          (even_aux lbt (lvl+1))@[x]@(even_aux rbt (lvl+1))
        else
          (even_aux lbt (lvl+1))@(even_aux rbt (lvl+1))
  in
  even_aux btO 0
;;
