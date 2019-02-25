let rec count n l =
  match l with
      [] ->
        0
    | x :: xs ->
      if x = n then
        1 + (count n xs)
      else
        count n xs;;

let countAcc n l =
  let rec countA n l a =
    match l with
        [] ->
          a
      | x :: xs ->
        if x = n then
          countA n xs (a+1)
        else
          countA n xs a
  in
  countA n l 0;;
