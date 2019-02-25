let rec foldr f a l =
  match l with
    | [] ->
      a
    | x :: xs ->
      f x (foldr f a xs);;
