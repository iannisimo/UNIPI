let prec list n m =
  let rec check list n m control =
    match list with
        [] ->
          true
      | x::xs ->
          if x = n then
            if control = false then
              check xs n m false
            else
              false
          else
            check xs n m true
  in
  check list n m false;;
