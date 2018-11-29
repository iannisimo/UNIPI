let rec order l =
  let rec pushO el l =
    match l with
        [] ->
          el :: []
      | x :: xs ->
          if el > x then
            x :: pushO el xs
          else
            el :: l
  in
  match l with
      [] ->
        []
    | x :: xs ->
        pushO x (order xs);;
