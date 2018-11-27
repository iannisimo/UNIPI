let inizio list n =
  let rec check list n sum =
    match list with
        [] ->
          []
      | x::xs ->
          if (sum + x) < n then
            x :: (check xs n (sum + x))
          else
            x::[]
  in
  check list n 0;;
