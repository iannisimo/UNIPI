let rec filterList p l =
  match l with
    | [] ->
      []
    | x :: xs ->
      if p x then
        x :: filterList p xs
      else
        filterList p xs;;
