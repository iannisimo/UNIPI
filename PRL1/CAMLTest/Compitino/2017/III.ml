(*
Si definisca in CAML, senza usare la ricorsione esplicita, una funzione
prec : int list -> int -> int -> bool
che, data una lista di interi e due interi n ed m, restituisce true se nella lista tutte le occorrenze
di n precedono tutte le occorrenze di m. La funzione restituisce false altriment
*)

let rec foldr f a l =
match l with
  | [] ->
    a
  | x :: xs ->
    f x (foldr f a xs);;

let prec lst n m =
  let f val status =
    if val = m then
      if status = 0 || status = 1 then
        1
      else
        3
    else if val = n then
      if status = 0 || status = 1 || status = 2 then
        2
      else
        3
    else
      status
  in
  foldr f 0 lst <> 3;;
