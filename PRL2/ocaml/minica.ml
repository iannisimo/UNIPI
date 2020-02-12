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
| Empty
| Node of exp * int * exp
| Filter of exp * (int -> int) * int * int
;;

(*ambiente polimorfo*)
type 't env = ide -> 't;;
let emptyenv (v : 't) = function x -> v;;
let applyenv (r : 't env) (i : ide) = r i;;
let bind (r : 't env) (i : ide) (v : 't) = function x -> if x = i then v else applyenv r x;;
(*ambiente typi*)
type envt = ide -> string;;
let emptyenvtype (v : string) = function x -> v;;
let applyenvtype (r : envt) (i : ide) = r i;;
let bindtype (r : envt) (i : ide) (v : string) = function x -> if x = i then v else applyenvtype r x;;

(*tipi esprimibili*)
type evT =
  Int of int
| Bool of bool
| Unbound
| FunVal of evFun
| RecFunVal of ide * evFun
| Dict of (ide * evT) list
| NodeVal of evT * int * evT
| EmptyNode
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


let rec check (e : exp) (r : envt) : string = match e with
  | Eint n -> "int"
  | Ebool b -> "bool"
  | IsZero a ->
    if stCheck (check a r) "int"
      then "bool"
      else failwith "static type error"
  | Den i ->  r i
  | Eq(a, b) ->
      if stCheck (check a r) (check b r)
      then "bool"
        else failwith "static type error"
  | Prod(a, b) ->
    if stCheck (check a r) "int" && stCheck (check b r) "int"
      then "int"
      else failwith "static type error"
  | Sum(a, b) ->
    if stCheck (check a r) "int" && stCheck (check b r) "int"
      then "int"
      else failwith "static type error"
  | Diff(a, b) ->
    if stCheck (check a r) "int" && stCheck (check b r) "int"
      then "int"
      else failwith "static type error"
  | Minus a ->
    if stCheck (check a r) "int"
      then "int"
      else failwith "static type error"
  | And(a, b) ->
    if stCheck (check a r) "bool" && stCheck (check b r) "bool"
      then "bool"
      else failwith "static type error"
  | Or(a, b) ->
    if stCheck (check a r) "bool" && stCheck (check b r) "bool"
      then "bool"
      else failwith "static type error"
  | Not a ->
    if stCheck (check a r) "bool"
      then "bool"
      else failwith "static type error"
  | Ifthenelse(a, b, c) ->
    if stCheck (check a r) "bool"
      then if stCheck (check b r) (check c r)
        then check b r
        else failwith "static type error"
      else failwith "static type error"
  | Let(i, e1, e2) -> check e2 (bindtype r i (check e1 r))
  | Fun(i, a) -> check a r
  | FunCall(f, eArg) -> (
    match f with
      | Fun(i, a) -> check f (bindtype r i (check eArg r))
      | _ -> failwith "static type error"
    )
  and stCheck (a : string) (b : string) : bool =
    (a = "unbound" || a = b || b = "unbound")

(*interprete*)
  and eval (e : exp) (r : evT env) : evT = match e with
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
  (* MOD *)
  Node(ln, v, rn) -> NodeVal((eval ln r), v, (eval rn r)) |
  Empty -> EmptyNode |
  Filter(t, f, x, y) ->
    match t with
      | Node(ln, v, rn) -> NodeVal(
          eval (Filter(ln, f, x, y)) r, (
            if (v >= x) && (v <= y)
              then f v
              else v
          ), eval (Filter(rn, f, x, y)) r)
      | Empty -> EmptyNode
      | _ -> failwith("Filter not applied to a tree")






  and fun_call (fClosure : evT) (v : evT) (r : evT env) : evT =
    match fClosure with
      | FunVal(arg, fBody, fDecEnv) ->
        eval fBody (bind fDecEnv arg v)
      | RecFunVal(g, (arg, fBody, fDecEnv)) ->
        let aVal = v in
          let rEnv = (bind fDecEnv g fClosure) in
            let aEnv = (bind rEnv arg aVal) in
              eval fBody aEnv
      | _ -> failwith("Non functional value")



(* =============================  TESTS  ================= *)

let env0 = emptyenv Unbound;;
let t1 = Node(Node(Empty, 1, Empty), 10, Empty);;
eval t1 env0;;
let func = fun x -> x + 1;;
let filtered = Filter(t1, func, 0, 5);;
let dio = 3;;
if (dio >= 0) && (dio <= 5)
  then func dio
  else dio;;
eval filtered env0;;
