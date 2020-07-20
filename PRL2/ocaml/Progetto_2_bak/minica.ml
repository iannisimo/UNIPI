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
| FFun of ide * ide * exp
| Edict of dict
| Insert of ide * exp * exp
| Delete of ide * exp
| Has_key of ide * exp
| Iterate of exp * exp
| Fold of exp * exp * exp
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
  | FFun(i1, i2, a) -> check a r
  | Edict d -> dictCheck d "unbound" r
  | Insert(i, e, d) -> let dType = (check d r) in (
    match (String.split_on_char '_' dType) with
      | x::y::ys ->
        if stCheck x (check e r)
          then dType
          else failwith "static type error"
      | _ -> failwith "static type error"
    )
  | Delete(i, d) -> let dType = (check d r) in (
    match (String.split_on_char '_' dType) with
      | x::y::ys ->
        if y = "dict"
          then dType
          else failwith "static type error"
      | _ -> failwith "static type error"
    )
  | Has_key(i, d) -> let dType = (check d r) in (
    match (String.split_on_char '_' dType) with
      | x::y::ys ->
        if y = "dict"
          then dType
          else failwith "static type error"
      | _ -> failwith "static type error"
    )
  | Iterate(f, d) -> let dType = (check d r) in (
    match (String.split_on_char '_' dType) with
      | x::y::ys ->
        if stCheck (check f r) x
          then dType
          else  failwith "static type error"
      | _ -> failwith "static type error"
    )
    | Fold(f, d, a) -> let aType = check a r in
      if stCheck (check f r) aType
        then aType
        else failwith "static type error"
    | Filter(is, d) -> let dType = (check d r) in (
      match (String.split_on_char '_' dType) with
        | x::y::ys ->
          if y = "dict"
            then dType
            else failwith "static type error"
        | _ -> failwith "static type error"
      )


  and dictCheck (d : dict) (t : string) (r : envt): string =
    match d with
      | Val(i, e, ls) -> let eType = (check e r) in
        if stCheck t eType
          then (dictCheck ls eType r)
          else failwith "static type error"
      | Empty -> t ^ "_dict"
      | _ -> failwith "static type error"
  and stCheck (a : string) (b : string) : bool =
    (a = "unbound" || a = b || b = "unbound")
  (*
  | Edict d -> match d with
    | Empty -> "unbound-dict"
    | Val(i, e, ls) -> check e r ^ "-dict"
    | _ -> "static type error"
  | Insert(i, e, d) -> match check d r with
    | x ^ "-dict" -> x
    | _ -> "static type error"
  *)
  (* and bindtype (r : 't env) (i : ide) (v : string) = function x ->
    if i = x
      then v
      else applyenvtype r x *)


 (*  eval e2 (bind r i (eval e1 r)) | *)

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
  FFun(i1, i2, a) -> FunVal(i2, Fun(i1, a), r) |

	Edict(d) -> Dict(evalDict d r "undefined" []) |
  Insert(i, e, d) -> let value = eval e r in (
    match eval d r with
      | Dict(dt) -> Dict(insert i e dt r)
      | _ -> failwith("Not a dict in Insert")
    ) |
  Delete(i, d) -> (
    match eval d r with
      | Dict(dt) -> Dict(delete i dt)
      | _ -> failwith("Not a dict in Delete")
    ) |
  Has_key(i, d) -> (
    match eval d r with
      | Dict(dt) -> (has_key i dt)
      | _ -> failwith("Wrong type in Has_key")
    ) |
  Iterate(f, d) -> (
    match eval d r with
      | Dict(dt) -> Dict(iterate (eval f r) dt r)
      | _ -> failwith("Not a dict in Iterate")
    ) |
  Fold(f, d, a) -> (
    match eval d r with
      | Dict(dt) -> (fold (eval f r) dt (eval a r) r)
      | _ -> failwith("Not a dict in Fold")
    ) |
  Filter(is, d) -> (
    match eval d r with
      | Dict(dt) -> Dict(filter is dt)
      | _ -> failwith("Not a dict in Filter")
    )

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

  and dict_type (d : (ide * evT) list) : string =
    match d with
      | [] -> "undefined"
      | (i,e)::xs -> (
        if typecheck "int" e
          then "int"
          else "bool"
      )
      | _ -> failwith("Impossible pattern")


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

  and insert (i : ide) (e : exp) (d : (ide * evT) list) (r : evT env) : (ide * evT) list =
    match (has_key i d) with
      | Bool false -> let value = eval e r in (
        if typecheck (dict_type d) value
          then (i,value)::d
          else failwith("Incompatible types for insert")
      )
      | _ -> failwith("Key already present")

  and delete (i : ide) (d : (ide * evT) list) : (ide * evT) list =
    match d with
      | [] -> []
      | (x, v)::ls ->
        if i = x
          then ls
          else (x, v) :: (delete i ls)

  and iterate (f : evT) (d : (ide * evT) list) (r : evT env) : (ide * evT) list =
    match d with
      | [] -> []
      | (i, v)::xs ->
        (i, (fun_call f v r))::(iterate f xs r)
      | _ -> failwith("Impossible pattern")
  and fold (f : evT) (d : (ide * evT) list) (acc : evT) (r : evT env) : evT =
    match d with
      | [] -> acc
      | (i, v)::xs ->
        fold f xs (fun_call (fun_call f acc r) v r) r
  and filter (is : ide list) (d : (ide * evT) list) : (ide * evT) list =
    match d with
      | [] -> []
      | (i, v)::xs ->
        if (contains i is)
          then (i, v)::(filter is xs)
          else filter is xs


  and contains (i : ide) (is : ide list) : bool =
    match is with
      | [] -> false
      | x::xs ->
        if x = i
          then true
          else (contains i xs)
      | _ -> failwith("Impossible pattern")
;;

(* =============================  TESTS  ================= *)

let typeEnv = emptyenvtype "unbound";;
let a = Den("x");;
check a typeEnv;;
let f = Fun("y", Den "y");;
check f typeEnv;;
let ff = FFun("x", "y", Sum(Den "y", Den "x"));;
check ff typeEnv;;
let e1 = FunCall(Fun("y", Den "y"), Eint 3);;
check e1 typeEnv;;
let dctInt = Edict(Val("i1", Eint 1, Val("i2", Eint 2, Val("i3", Eint 3, Empty))));;
check dctInt typeEnv;;
let dctBool = Edict(Val("b1", Ebool false, Val("b2", Ebool true, Val("b3", Ebool true, Empty))));;
check dctBool typeEnv;;
let dctInsert = Insert("i4", Eint 4, dctInt);;
check dctInsert typeEnv;;
let it = Iterate(f, dctInt);;
check it typeEnv;;
let bf = FFun("a", "b", Ifthenelse(Den "a", Sum(Den "b", Eint 1), Den "b"));;
check bf typeEnv;;
let fol = Fold(bf, dctBool, Eint 0);;
check fol typeEnv;;
let fil = Filter(["i1"; "i3"], dctInsert);;
check fil typeEnv;;


let envD = emptyenv Unbound;;

let sep = "---";;
eval fol envD;;
