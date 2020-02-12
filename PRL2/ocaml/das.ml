type exp =
  Eint of int
| Fun of ide * exp
| FunCall of exp * exp
| Edict of dict
and dict = Empty | Val of ide * exp * dict;;
type 't env = ide -> 't;;
let emptyenv (v : 't) = function x -> v;;
let applyenv (r : 't env) (i : ide) = r i;;
let bind (r : 't env) (i : ide) (v : 't) = function x -> if x = i then v else applyenv r x;;
type evT =
  Int of int
| FunVal of evFun
| Dict of (ide * evT) list
and evFun = ide * exp * evT env
and eval (e : exp) (r : evT env) : evT = match e with
  | Eint n -> Int n
  | Fun(i, a) -> FunVal(i, a, r)
  | FunCall(f, eArg) ->
    let fClosure = (eval f r) in
    (
    match fClosure with
      | FunVal(arg, fBody, fDecEnv) ->
        eval fBody (bind fDecEnv arg (eval eArg r))
    )
  | Edict(d) -> Dict(evalDict d r "undefined" [])
  | Insert(i, e, d) -> let value = eval e r in (
    match eval d r with
      | Dict(dt) -> Dict(insert i e dt r)
      | _ -> failwith("Not a dict in Insert")

and evalDict (d : dict) (r : evT env) (tpe : string) (acc : (ide * evT) list) : (ide * evT) list =
  match d with
      Empty -> acc
    | Val(i, e, ls) -> match (has_key i acc) with
        Bool false -> let value = (eval e r) in (
          match tpe with
              "undefined" -> (evalDict ls r (if (typecheck "int" value) then "int" else "bool") ((i, value)::acc))
            | "int" -> if(typecheck "int" value)
              then (evalDict ls r "int" ((i, value)::acc))
              else failwith("Type error")
            | "bool" -> if(typecheck "bool" value)
              then (evalDict ls r "bool" ((i, value)::acc))
              else failwith("Type error")
        )
      | _ ->
          failwith("Key already present")




echo "#use \"test.ml\";;" | ocaml -init minica.ml -w -a



#use "minica.ml";;

(if (typecheck "int" value) then "int" else "bool")

and iterate (f : exp) (d : dict) (r : evT env) : (ide * evT) list =
  match d with
    | Empty -> []
    | Val(i, e, xs) -> let x = FunCall(f, e) in
      (i, (eval x r))::(iterate f xs r)
    | _ -> failwith("Impossible pattern")




    (* basico: no let *)
    let env0 = emptyenv Unbound;;

    let e1 = FunCall(Fun("y", Sum(Den "y", Eint 1)), Eint 3);;

    (* eval e1 env0;; *)

    let e2 = FunCall(Let("x", Eint 9, Fun("y", Sum(Den "y", Den "x"))), Eint 3);;

    eval e2 env0;;

    let x = Ifthenelse(Ebool true, Sum(Eint 2, Eint 1), Prod(Eint 1, Eint 2));;
    eval x env0;;

    let e3 = Fun("y", Fun("x", Sum(Den "y", Den "x")));;
    eval e3 env0;;
    let e3_bis = FunCall(FunCall(e3, Eint 4), Eint 3);;
    eval e3_bis env0;;

    let x = Eint 2;;

    (* eval x env0;; *)

    let y = Edict(Val("a", Eint(10), Val("b", Eint(3), Empty)));;

    (* eval y env0;; *)

    let yy = Insert("c", Eint(3), y);;

    eval yy env0;;

    let zz = Delete("c", yy);;

    let myf = Fun("x", Sum(Den("x"), Eint(10)));;

    let it = Iterate(myf, yy);;
    eval it env0;;

    let fil = Filter(["a";"c"], yy);;

    eval fil env0;;

    let ff = FFun("x", "y", Sum(Den "y", Den "x"))
    let fol = Fold(ff, yy);;
    eval fol env0;;

    let bf = FFun("a", "b", Ifthenelse(Den "a", Sum(Den "b", Eint 1), Den "b"));;
    let dick = Edict(Val("a", Ebool true, Val("b", Ebool false, Val("c", Ebool true, Empty))));;
    let dickFold = Fold(bf, dick);;
    eval dickFold env0;;

    let zzz = Let("x", Eint 9, Den("x"));;
    let ndas = Let("y", Ebool true, Ifthenelse(Den "y", Eint 1, Eint 2));;
    eval zzz env0;;
    eval ndas env0;;

    let nnn = Let("x", Eint 1, Sum(Eint 9, Den "x"));;

    (* let bt = (bindtype (emptyenvtype "unbound") "x" ("int"));;

    bt "x";;

    check (Den "x") bt;; *)

    check nnn (emptyenvtype "unbound");;

    (* | Let(i, e1, e2) -> check e2 (bindtype r i (check e1 r)) *)

    (* check (Den("x")) (bind (emptyenv Unbound) "x" (Int 9));;
    check (Den("x")) (bindtype (emptyenv Unbound) "x" ("int"));; *)

    and fold (f : evT) (d : (ide * evT) list) (acc : evT) (r : evT env) : evT =
      match d with
        | [] -> acc
        | (i, v)::xs ->
          let temp = (fold f xs (fun_call (fun_call f acc r) v r) r) in
          if typecheck "int" temp
            then temp
            else failwith("Non-int Fold partial result")
