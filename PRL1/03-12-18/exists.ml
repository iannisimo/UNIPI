let rec existList p l =
  match l with
    | [] ->
      false
    | x :: xs ->
      if p x then
        true
      else
        existList p xs;;
