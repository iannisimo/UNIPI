(*
Si definisca in CAML, senza usare la ricorsione esplicita, una funzione
primidiliste : ’a list list -> ’a list
che, data una lista di liste lis, restituisce la lista contenente il primo elemento di ogni lista
NON VUOTA contenuta in lis.
*)

let rec foldr f a l =
match l with
  | [] ->
    a
  | x :: xs ->
    f x (foldr f a xs);;

let pds lst =
  let f l acc =
    match l with
      | [] ->
        acc
      | x :: xs ->
        x :: acc
  in
  foldr f [] lst;;
