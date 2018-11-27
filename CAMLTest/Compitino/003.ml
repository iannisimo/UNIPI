let rec cancella n list =
  let rec delLast list =
    match list with
        x::[] ->
          []
      | x::xs when xs != [] ->
          x::(delLast xs)
  in
  match (n, list) with
      (0, list) ->
        list
    | (n, []) ->
        []
    | (n, l) when list != [] ->
        cancella (n - 1) (delLast l);;

let rec inserisci list x y =
  let stepAndAdd list val =
    match list with
        x::xs ->
          x::val::xs
  in
  match list with
      [] ->
        []
    | z::zs ->
        if z = y then
          stepAndAdd list x
        else
          z::(inserisci zs x y);;
