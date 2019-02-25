let rec take n l =
  match (n, l) with
      (0, l) ->
        []
    | (n, []) when n > 0 ->
        []
    | (n, x::xs) when n > 0 ->
        x::take (n-1) xs;;

let takeAcc n l =
  let rec takeA n l a =
    match (n, l) with
        (0, l) ->
          a
      | (n, []) when n > 0 ->
          a
      | (n, x::xs) when n > 0 ->
          takeA (n-1) xs (a @ [x])
  in
  takeA n l [];;

(*Non conviene usare l'accumulatore per la take perche' ha bisogno dell'append*)
