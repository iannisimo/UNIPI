let even val = val mod 2 = 0;;

let rec filterEven lis =
  match lis with
    | [] ->
      []
    | x :: xs ->
      if even x then
        x :: filterEven xs
      else
        filterEven xs
;;
