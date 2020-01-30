#use "minica.ml";;

(if (typecheck "int" value) then "int" else "bool")

and iterate (f : exp) (d : dict) (r : evT env) : (ide * evT) list =
  match d with
    | Empty -> []
    | Val(i, e, xs) -> let x = FunCall(f, e) in
      (i, (eval x r))::(iterate f xs r)
    | _ -> failwith("Impossible pattern")
