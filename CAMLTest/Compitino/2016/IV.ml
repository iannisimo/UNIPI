let rec foldr f a l =
match l with
  | [] ->
    a
  | x :: xs ->
    f x (foldr f a xs)
;;

let minfirst lst =
  let f val acc =
    match acc with
      | [] ->
        val :: []
      | x :: xs ->
        if val <= x then
          val :: acc
        else
          acc @ [val]
  in
  foldr f [] lst
;;
