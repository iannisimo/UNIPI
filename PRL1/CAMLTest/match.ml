let rec delVal list val =
  match list with
      [] -> []
    | x::xs ->
      if x = val then
        delVal xs val
      else
        x::delVal xs val;;

let rec len l =
  match l with
      [] -> 0
    | x::xs -> 1 + len xs;;

let rec getLast (list, n) =
  match list with
      ([], n) -> []
    | (x::xs, 0) -> []
    | (x::xs, (len list)-n) when ((len list) - n) > 0 -> x::getLast(list, n+1);;
