let zerouno list =
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
  (count list 0 = count list 1) && binary list;;
