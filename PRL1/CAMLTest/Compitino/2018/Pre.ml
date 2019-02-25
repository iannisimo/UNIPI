let foldr f x y = list_it f y x;;

type 'a btree = Void | Node of 'a * 'a btree * 'a btree;;

let conta lst =
  let f val (l1, n) =
    if val > 0 then
      (val :: l1, n)
    else
      (l1, n + 1)
  in
  foldr f ([], 0)
;;

let prec l v =
  let rec prec_aux lst val (n1, n2) =
    match lst with
      | [] ->
        if n2 = 0 then
          (0, n1)
        else
          (n1, n2)
      | x :: xs ->
        if n2 = 0 then
          if x = val then
            prec_aux xs val (n1, 1)
          else
            prec_aux xs val (n1 + 1, n2)
        else
          prec_aux xs val (n1, n2 + 1)
  in
  prec_aux l v (0, 0)
;;

let rec subst bt x y =
  match bt with
    | Void ->
      Void
    | Node (r, Void, Void) ->
      if r = x then
        Node (y, Void, Void)
      else
        Node (r, Void, Void)
    | Node (r, lbt, rbt) when lbt <> Void || rbt <> Void ->
      Node (r, (subst lbt x y), (subst rbt x y))
;;
