let rec foldr f a l =
match l with
  | [] ->
    a
  | x :: xs ->
    f x (foldr f a xs)
;;

let somma lst =
  let f val acc =
    val + acc
  in
  foldr f 0 lst
;;

(*
Definizione della funzione filter p l tramite foldr
*)

let pari val =
  if (val mod 2) = 0 then
    true
  else
    false
;;

let filter p lst =
  let f val acc =
    if p val then
      val :: acc
    else
      acc
  in
  foldr f [] lst
;;

(*
Definizione di map f l tramite foldr
*)

let map f lst =
  let f val acc =
    (f val) :: acc
  in
  foldr f [] lst
;;

(*
Data una lista dividerla in due liste l1 ed l2 t.c. l1 contiene tutti gli elementi di l >= 0 ed l2 contiene tutti quelli < 0
*)

let rec divPos lst =
  match lst with
    | [] ->
      ([], [])
    | x :: xs ->
      let (l1, l2) = divPos xs in
      if x >= 0 then
        (x :: l1, l2)
      else
        (l1, x :: l2)
;;

let split lst =
  let f val (l1, l2) =
    if val >= 0 then
      (val :: l1, l2)
    else
      (l1, val :: l2)
  in
  foldr f ([], []) lst
;;

(*
Definire una funzione minmax lst che ritorna il massimo ed il minimo valore di lst
*)

let minmax lst =
  let f val (min, max) =
    if val < min then
      (val, max)
    else if val > max then
      (min, val)
    else
      (min, max)
  in
  match lst with
    | [] ->
      begin
        print_string "minmax is not defined with []\n";
        (0, 0)
      end
    | x :: xs ->
      foldr f (x, x) xs
;;

(*
Data una lista di coppie di interi ritornare una lista formata dalla somma delle coppie
*)

let sumPairs lst =
  let f val acc =
    let (val1, val2) = val in
    (val1 + val2) :: acc
  in
  foldr f [] lst
;;

(*
Definire una funzione canc n l che cancella gli ultimi n elementi da l
*)

let canc n lst =
  let f val acc =
    let (l, m) = acc in
    if m < n then
      (l, m + 1)
    else
      (val :: l, m)
  in
  let (l, m) = foldr f ([], 0) lst in l
;;
