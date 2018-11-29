let rec member n l =
  match l with
      [] ->
        false
    | x::xs ->
        if x = n then
          true
        else
          member n xs;;
