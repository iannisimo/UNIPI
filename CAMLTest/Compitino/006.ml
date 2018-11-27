let inizio list n =
  let rec member l =
    match l with
        [] ->
          false
      | x::xs ->
        if x = n then
          true
        else
          member xs
  in
  let rec inizio_aux l =
    match l with
        [] ->
          []
      | x::xs ->
        if x = n then
          [x]
        else
          x::(inizio_aux xs)
  in
  if (member list) then
    inizio_aux list
  else
    [];;
