let rec split_inizio list =
  let rec split (list1, list2) =
    match list2 with
      | [x;y] ->
          if x < y then
            (list1 @ [x;y], [])
          else
            (list1 @ [x], [y])
      | x::y::ys when ys != [] ->
          if x < y then
            split (list1 @ [x], y::ys)
            else
              if list1 != [] then
                (list1 @ [x], y::ys)
              else
                (list1, list2)
      | _ -> (list1, list2)
  in
  split ([], list);;

let splitBarb lis =
  let rec split_accum lis1 lis2 =
    match lis2 with
      [] -> (lis1,[])
    |  x::[] -> (lis1@[x],[])
    |  x::y::xs ->
      if x < y then
        split_accum (lis1@[x]) (y::xs)
      else
        (lis1@[x],y::xs)
  in
    split_accum [] lis;;
