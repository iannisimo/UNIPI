let rec insert l x y =
  let rec member n l =
    match l with
        [] ->
          false
      | x::xs ->
          if x = n then
            true
          else
            member n xs
  in
  match l with
      [] ->
        []
    | z :: zs ->
        if z = y && not member y zs then
          z :: x :: zs
        else
          z :: (insert zs x y);;
