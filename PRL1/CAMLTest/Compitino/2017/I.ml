(*
Si definisca in CAML, senza usare la ricorsione esplicita, una funzione
split_fine : int list -> int list * int list
che, data una lista lis di interi, restituisce la coppia (lis1,lis2) tale che
• lis1 e lis2 sono due sottoliste (porzioni possibilmente vuote) di lis,
• lis1 concatenato a lis2 corrisponde esattamente all’intera lista lis
• e lis2 `e la sottolista FINALE pi`u lunga possibile i cui elementi sono disposti in ordine
strettamente crescente
*)

let rec foldr f a l =
match l with
  | [] ->
    a
  | x :: xs ->
    f x (foldr f a xs);;

let split_fine lst =
  let f val acc =
    let (lis1, lis2) = acc in
    match lis2 with
      | [] ->
        ([], val :: [])
      | x :: xs ->
        if lis1 = [] then
          if val < x then
            ([], val :: lis2)
          else
            (val :: [], lis2)
        else
          (val :: lis1, lis2)
  in
  foldr f ([], []) lst;;
