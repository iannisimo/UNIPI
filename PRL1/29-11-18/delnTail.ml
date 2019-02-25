
let rec canc n l =
  let rec len l =
    match l with
        [] ->
          0
      | x :: xs ->
          1 + (len xs)
  in
  if len l <= n then
    []
  else
    match l with
        x :: xs ->
          x :: (canc n xs);;
