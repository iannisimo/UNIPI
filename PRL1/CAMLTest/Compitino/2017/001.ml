(*let zerouno list =
  let rec binary list =
    match list with
        [] -> true
      | x::xs ->
        if (x = 0) or (x = 1) then
          binary xs
        else
          false
  in
  let rec count list val =
    match list with
        [] -> 0
      | x::xs ->
        if x = val then
          1 + count xs val
        else
          count xs val
  in
  (count list 0 = count list 1) && binary list;;*)

  (*List.fold_right f [a1; ...; an] b is f a1 (f a2 (... (f an b) ...))*)

let zerouno_ordinati lis =
  let binary el =
    if el = 0 || el = 1 then
      true
    else
      false
  in
  let ordered el status =
    if el = 1 then
      if status = 0 then
        0
      else
        2
    else
      if status = 0 || status = 1 then
        1
      else
        2
  in
  ((List.filter binary lis) = lis) && ((List.fold_right ordered lis 0) = 1);;
