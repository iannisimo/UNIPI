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
