let rec mapList f l =
  match l with
    | [] ->
      []
    | x :: xs ->
      (f x) :: mapList f xs;;

(*
mapList : ('a -> 'b) -> 'a list -> 'b list = <fun>
*)
