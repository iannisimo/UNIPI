let rec rising l =
  match l with
      [] ->
        true
    | x :: [] ->
        true
    | x :: y :: ys ->
        if x < y then
          rising (y :: ys)
        else
          false;;
