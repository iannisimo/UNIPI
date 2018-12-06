(*List.fold_left f a [b1; ...; bn] is f (... (f (f a b1) b2) ...) bn*)
(*List.fold_right f [a1; ...; an] b is f a1 (f a2 (... (f an b) ...))*)

let sort lis =
  let ins el lis =
    let rec insa el l1 l2 =
      match l1 with
          [] -> l2@[el]
        | x::xs when x>=el -> l2@[el]@l1
        | x::xs when x<el  -> insa el xs (l2@[x])
    in
    insa el lis []
  in
  list_it ins lis [];;
