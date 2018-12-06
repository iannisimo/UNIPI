(*
Si definisca in CAML, senza usare la ricorsione esplicita, una funzione
num : int list -> int -> int
che, data una lista di interi e un intero n, calcola la lunghezza del massimo suffisso (la pi`u
lunga porzione finale della lista) in cui la somma degli elementi `e strettamente minore di n.
*)

let rec foldr f a l =
match l with
  | [] ->
    a
  | x :: xs ->
    f x (foldr f a xs);;

let num lst n =
  let f val acc =
    let (out, sum) = acc in
    if (sum + val) < n then
      ((out + 1), (sum + val))
    else
      acc
  in
  let (out, sum) = (foldr f (0, 0) lst) in out
;;
