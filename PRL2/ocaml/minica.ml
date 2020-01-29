type ide = string;;
type exp =
  Eint of int
| Ebool of bool
| Den of ide
| Prod of exp * exp
| Sum of exp * exp
| Diff of exp * exp
|	Eq of exp * exp
| Minus of exp
| IsZero of exp
| Or of exp * exp
| And of exp * exp
| Not of exp
|	Ifthenelse of exp * exp * exp
| Let of ide * exp * exp
| Fun of ide * exp
| FunCall of exp * exp
|	Letrec of ide * exp * exp
(*MOD*)
| Edict of dict
| Insert of ide * exp * exp
| Delete of ide * exp
| Has_key of ide * exp
| Iterate of exp * exp
| Fold of exp * exp
| Filter of (ide list) * exp
and dict =
	Empty
| Val of ide * exp * dict
;;

(*ambiente polimorfo*)
type 't env = ide -> 't;;
let emptyenv (v : 't) = function x -> v;;
let applyenv (r : 't env) (i : ide) = r i;;
let bind (r : 't env) (i : ide) (v : 't) = function x -> if x = i then v else applyenv r x;;

(*tipi esprimibili*)
type evT =
  Int of int
| Bool of bool
| Unbound
| FunVal of evFun
| RecFunVal of ide * evFun
| Dict of (ide * evT) list
and evFun = ide * exp * evT env

(*rts*)
(*type checking*)
let typecheck (s : string) (v : evT) : bool = match s with
	"int" -> (match v with
		Int(_) -> true |
		_ -> false) |
	"bool" -> (match v with
		Bool(_) -> true |
		_ -> false) |
	"dict" -> (match v with
		Dict(_) -> true |
		_ -> false) |
	_ -> failwith("not a valid type");;

(*funzioni primitive*)
let prod x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n*u))
	else failwith("Type error");;

let sum x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n+u))
	else failwith("Type error");;

let diff x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n-u))
	else failwith("Type error");;

let eq x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Bool(n=u))
	else failwith("Type error");;

let minus x = if (typecheck "int" x)
	then (match x with
	   	Int(n) -> Int(-n))
	else failwith("Type error");;

let iszero x = if (typecheck "int" x)
	then (match x with
		Int(n) -> Bool(n=0))
	else failwith("Type error");;

let vel x y = if (typecheck "bool" x) && (typecheck "bool" y)
	then (match (x,y) with
		(Bool(b),Bool(e)) -> (Bool(b||e)))
	else failwith("Type error");;

let et x y = if (typecheck "bool" x) && (typecheck "bool" y)
	then (match (x,y) with
		(Bool(b),Bool(e)) -> Bool(b&&e))
	else failwith("Type error");;

let non x = if (typecheck "bool" x)
	then (match x with
		Bool(true) -> Bool(false) |
		Bool(false) -> Bool(true))
	else failwith("Type error");;

(*interprete*)
let rec eval (e : exp) (r : evT env) : evT = match e with
	Eint n -> Int n |
	Ebool b -> Bool b |
	IsZero a -> iszero (eval a r) |
	Den i -> applyenv r i |
	Eq(a, b) -> eq (eval a r) (eval b r) |
	Prod(a, b) -> prod (eval a r) (eval b r) |
	Sum(a, b) -> sum (eval a r) (eval b r) |
	Diff(a, b) -> diff (eval a r) (eval b r) |
	Minus a -> minus (eval a r) |
	And(a, b) -> et (eval a r) (eval b r) |
	Or(a, b) -> vel (eval a r) (eval b r) |
	Not a -> non (eval a r) |
	Ifthenelse(a, b, c) ->
		let g = (eval a r) in
			if (typecheck "bool" g)
				then (if g = Bool(true) then (eval b r) else (eval c r))
				else failwith ("nonboolean guard") |
	Let(i, e1, e2) -> eval e2 (bind r i (eval e1 r)) |
	Fun(i, a) -> FunVal(i, a, r) |
	FunCall(f, eArg) ->
		let fClosure = (eval f r) in
			(match fClosure with
				FunVal(arg, fBody, fDecEnv) ->
					eval fBody (bind fDecEnv arg (eval eArg r)) |
				RecFunVal(g, (arg, fBody, fDecEnv)) ->
					let aVal = (eval eArg r) in
						let rEnv = (bind fDecEnv g fClosure) in
							let aEnv = (bind rEnv arg aVal) in
								eval fBody aEnv |
				_ -> failwith("non functional value")) |
        Letrec(f, funDef, letBody) ->
        		(match funDef with
            		Fun(i, fBody) -> let r1 = (bind r f (RecFunVal(f, (i, fBody, r)))) in
                         			                eval letBody r1 |
            		_ -> failwith("non functional def")) |
	Edict(d) -> Dict(evalDict d r "undefined" [])

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

  and has_key (i : ide) (lst : (ide * evT) list) : evT =
    match lst with
        [] -> Bool false
      | (x,v)::xs ->
        if x = i
          then Bool true
          else has_key i xs
      | _ -> failwith("Impossible pattern")
;;

(*#use "minica.ml";;*)

(* =============================  TESTS  ================= *)

(* basico: no let *)
let env0 = emptyenv Unbound;;

let e1 = FunCall(Fun("y", Sum(Den "y", Eint 1)), Eint 3);;

eval e1 env0;;

let e2 = FunCall(Let("x", Eint 2, Fun("y", Sum(Den "y", Den "x"))), Eint 3);;

eval e2 env0;;

let x = Eint 2;;

eval x env0;;

let y = Edict(Val("ba", Eint(10), Val("a", Eint(3), Empty)));;

eval y env0;;
