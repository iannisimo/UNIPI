let rec forall p l =
  match l with
    | [] ->
      true
    | x :: xs when p x ->
      forall p xs
    | x :: xs when not (p x) ->
      false
    | _ ->
      false;;

(*
forall: ('a -> bool) -> 'a list -> bool = <fun>
*)
