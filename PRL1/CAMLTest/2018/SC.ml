let foldr f x y = list_it f y x;;

type 'a btree = Void | Node of 'a * 'a btree * 'a btree;;

let split l x =
  let f val (l1, l2) =
    if l1 = [] then
      match l2 with
          [] ->
            ([], [val])
        | y :: ys ->
            if y = x then
              ([val], l2)
            else
              ([], val :: l2)
    else
      (val :: l1, l2)
  in
  foldr f ([], []) l
;;

let tbt = Node (1, Node (2, Node (3, Void, Void), Node (3, Void, Void)), Node (2, Node (3, Void, Void), Node (3, Void, Void)));;

let rec subst bt x n =
  match bt with
      Void ->
        Void
    | Node (y, lbt, rbt) ->
      if n <> 1 then
        Node (y, (subst lbt x (n-1)), (subst rbt x (n-1)))
      else
        Node (x, lbt, rbt)
;;

let prec l x =
  let rec acc lst (l1, n) f =
    match lst with
        [] ->
          (l1, n)
      | y :: ys ->
          let n1 =
            if y > 0 then
              n + 1
            else
              n
          in
          if f = 1 || y = x then
            (l1, n1)
          else
            acc ys (y :: l1, n1) 0
  in
  acc l ([], 0) 0
;;
