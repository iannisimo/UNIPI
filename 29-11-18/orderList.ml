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

let sort l =
  let rec sortA l a =
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
          a
      | x :: xs ->
          sortA xs (pushO x a)
  in
  sortA l [];;
