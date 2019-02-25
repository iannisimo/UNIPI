let inizio list n =
  let rec check list n sum =
    match list with
        [] ->
          []
      | x::xs ->
          if (sum + x) < n then
            x :: (check xs n (sum + x))
          else
            x::[]
  in
  check list n 0;;

let rec foldr f a l =
  match l with
    | [] ->
      a
    | x :: xs ->
      f x (foldr f a xs);;

let listasomme lis =
  let f val acc =
    let (l, sum) = acc in
    if val <> 0 then
      (l, sum + val)
    else
      if l = [0] then
        ([], 0)
      else
        (sum :: l, 0)
  in
  let (l, sum) = foldr f ([0], 0) lis in l;;

let lstsum lst =
  let f val out =
    match out with
      | [] ->
        if val = 0 then
          [val]
        else
          []
      | x :: xs ->
        if val <> 0 then
          (x + val) :: xs
        else
          val :: out
  in
  match (foldr f [] lst) with
    | [] ->
      []
    | x :: xs ->
      xs
;;

let newls lst =
  let f val acc =
    let (l, sum) = acc in
    if l = [] then
      if val <> 0 then
        ([], 0)
      else
        ([0], 0)
    else
      if val <> 0 then
        (l, (sum + val))
      else
        (sum :: l, 0)
  in
  let (out, sum) = foldr f ([], 0) lst in out;;          
