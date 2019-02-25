(*
Cancellare, mediante foldr, gli elementi che seguono l'ultima occorrenza del valore 0
Se non ci sono elementi = 0 la lista va cancellata completamente
*)

let foldr f x y = list_it f y x;;

let delafterlastzero lst =
  let f val acc =
    if acc = [] then
      if val = 0 then
        0 :: []
      else
        []
    else
      val :: acc
  in
  foldr f [] lst
;;

let dalz x = delafterlastzero x;;

(*
Cancellare, mediante foldr, gli elementi che seguono l'ultima occorrenza del valore 0
Se non ci sono elementi = 0 la lista non viene modificata
*)

let dalz1 lst =
  let f val (l, b) =
    if b then
      (val :: l, b)
    else
      if val <> 0 then
        (val :: l, b)
      else
        (val :: [], true)
  in
  let (l, b) = foldr f ([], false) lst in l
;;

(*
Si definisca una funzione inizio che, data una lista di interi >= 0 ed un intero n restituisca il massimo prefisso
in cui la somma degli elementi e' strettamente minore di n
*)

let inizio lst n =
  let rec inizio_aux l n acc =
    match l with
      | [] ->
        []
      | x :: xs ->
        if acc + x >= n then
          []
        else
          x :: inizio_aux xs n (acc + x)
  in
  inizio_aux lst n 0
;;

let rec inizio1 lst n =
  match lst with
    | [] ->
      []
    | x :: xs ->
      if n - x >= 0 then
        x :: inizio1 xs (n - x)
      else
        []
;;

(*
Si definisca tramite foldr una funzione che data una lista di interi >= 0 e un intero n, calcola la lunghezza del massimo suffisso in cui la somma degli elementi e' strettamente minore di n
*)

let num lst n =
  let f val (sum, lenght) =
    if sum + val < n then
      (sum + val, 1 + lenght)
    else
      (sum + val, lenght)
  in
  let (sum, lenght) = foldr f (0, 0) lst in lenght
;;

(*
Si definisca una funzione ricorsiva split: int list -> int list * int list che, data una lista lst di interi restituisce
la coppia (l1, l2) t.c.
  l1 @ l2 = lst
  l1 e l2 possono essere vuote
  l1 e' la sottolista iniziale piu' lunga possibile i cui elementi sono in ordine strettamente crescente
*)

(*REDO*)

let split lst =
  let split_aux (l1, l2) =
    match l2 with
      | [] ->
        ([], [])
      | x :: [] ->
        ([x], [])
      | x :: y :: ys ->
        if x < y then
          (x :: l1, l2)
        else
          (x :: l1, y :: ys)
  in
  split_aux ([], lst);;

(*REDO*)
