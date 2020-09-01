type ide = string;;
type exp =
| Eint of int
| Ebool of bool
| Den of ide
| Prod of exp * exp
| Sum of exp * exp
| Diff of exp * exp
| Eq of exp * exp
| Minus of exp
| IsZero of exp
| Or of exp * exp
| And of exp * exp
| Not of exp
| IfThenElse of exp * exp * exp
| Let of ide * exp * exp
| Fun of ide * exp
| Apply of exp * exp
(* Dict *)
| Edict of (ide * exp) list
| Insert of ide * exp * exp
| Delete of ide * exp
| Has_key of ide * exp
;;
type static =
| Sunbound
| Sint
| Sbool
| Sdict of static
;;

(*ambiente polimorfo*)
type 't env = ide -> 't;;
let emptyenv (v : 't) = function x -> v;;
let applyenv (r : 't env) (i : ide) = r i;;
let bind (r : 't env) (i : ide) (v : 't) = function x -> if x = i then v else applyenv r x;;
(*ambiente typi*)
type senv = ide -> static;;
let emptysenv (v : static) = function x -> v;;
let applysenv (r : senv) (i : ide) = r i;;
let sbind (r : senv) (i : ide) (v : static) = function x -> if x = i then v else applysenv r x;;

type evT =
| Unbound
| Int of int
| Bool of bool
| FunVal of evFun
| RecFunVal of ide * evFun
| Dict of (ide * evT) list
and evFun = ide * exp * evT env



let sameType (a : static) (b : static) : bool =
  ((a = Sunbound) || (a = b) || (b = Sunbound))
and typeofexp (e : exp) : static =
  match e with
  | Eint(_) -> Sint
  | Ebool(_) -> Sbool
  | _ -> failwith("Cannot find type without an enviroment")
and typeofevT (v : evT) : static =
  match v with
  | Int(_) -> Sint
  | Bool(_) -> Sbool
  | _ -> failwith("Cannot find type without an enviroment")
;;

let typecheck (s : static) (v : evT) : bool =
  match s with
	| Sint -> (
    match v with
		| Int(_) -> true
		| _ -> false
    )
	| Sbool -> (
    match v with
		| Bool(_) -> true
		| _ -> false
    )
  | Sdict(t) ->
    if sameType t (typeofevT v)
      then true
      else false
	| _ -> failwith("not a valid type")
;;

(*funzioni primitive*)
let prod x y = if (typecheck Sint x) && (typecheck Sint y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n*u))
	else failwith("Type error");;

let sum x y = if (typecheck Sint x) && (typecheck Sint y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n+u))
	else failwith("Type error");;

let diff x y = if (typecheck Sint x) && (typecheck Sint y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n-u))
	else failwith("Type error");;

let eq x y = if (typecheck Sint x) && (typecheck Sint y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Bool(n=u))
	else failwith("Type error");;

let minus x = if (typecheck Sint x)
	then (match x with
	   	Int(n) -> Int(-n))
	else failwith("Type error");;

let iszero x = if (typecheck Sint x)
	then (match x with
		Int(n) -> Bool(n=0))
	else failwith("Type error");;

let vel x y = if (typecheck Sbool x) && (typecheck Sbool y)
	then (match (x,y) with
		(Bool(b),Bool(e)) -> (Bool(b||e)))
	else failwith("Type error");;

let et x y = if (typecheck Sbool x) && (typecheck Sbool y)
	then (match (x,y) with
		(Bool(b),Bool(e)) -> Bool(b&&e))
	else failwith("Type error");;

let non x = if (typecheck Sbool x)
	then (match x with
		Bool(true) -> Bool(false) |
		Bool(false) -> Bool(true))
	else failwith("Type error");;

let rec has_key (i : ide) (l : (ide * evT) list) : evT =
  match l with
  | [] -> Bool false
  | (id, _) :: ls ->
    if id = i
      then Bool true
      else has_key i ls
  | _ -> failwith("Impossible pattern");;

let rec staticCheck (e : exp) (r : senv) : static =
  match e with
  | Eint n -> Sint
  | Ebool b -> Sbool
  | IsZero a ->
    if sameType (staticCheck a r) Sint
      then Sbool
      else failwith "static type error"
  | Den i -> r i
  | Eq(a, b) ->
    if isInt a r && isInt b r
      then Sbool
      else failwith "static type error"
  | Prod(a, b) ->
    if isInt a r && isInt b r
      then Sint
      else failwith "static type error"
  | Sum(a, b) ->
    if isInt a r && isInt b r
      then Sint
      else failwith "static type error"
  | Diff(a, b) ->
    if isInt a r && isInt b r
      then Sint
      else failwith "static type error"
  | Minus a ->
    if isInt a r
      then Sint
      else failwith "static type error"
  | And(a, b) ->
    if isBool a r && isBool b r
      then Sbool
      else failwith "static type error"
  | Or(a, b) ->
    if isBool a r && isBool b r
      then Sbool
      else failwith "static type error"
  | Not a ->
    if isBool a r
      then Sbool
      else failwith "static type error"
  | IfThenElse(a, b, c) ->
    if isBool a r
      then if sameType (staticCheck b r) (staticCheck c r)
        then staticCheck b r
        else failwith "static type error"
      else failwith "static type error"
  | Let(i, e1, e2) -> staticCheck e2 (sbind r i (staticCheck e1 r))
  | Fun(i, a) -> staticCheck a r
  | Apply(f, eArg) -> (
    match f with
      | Fun(i, a) -> staticCheck f (sbind r i (staticCheck eArg r))
      | _ -> failwith "static type error"
    )
and isInt (e : exp) (r : senv) =
  let t = staticCheck e r in
  t = Sunbound || t = Sint
and isBool (e : exp) (r : senv) =
  let t = staticCheck e r in
  t = Sunbound || t = Sbool
;;

(*interprete*)
let rec eval (e : exp) (r : evT env) : evT =
  match e with
	| Eint n -> Int n
	| Ebool b -> Bool b
	| IsZero a -> iszero (eval a r)
	| Den i -> applyenv r i
	| Eq(a, b) -> eq (eval a r) (eval b r)
	| Prod(a, b) -> prod (eval a r) (eval b r)
	| Sum(a, b) -> sum (eval a r) (eval b r)
	| Diff(a, b) -> diff (eval a r) (eval b r)
	| Minus a -> minus (eval a r)
	| And(a, b) -> et (eval a r) (eval b r)
	| Or(a, b) -> vel (eval a r) (eval b r)
	| Not a -> non (eval a r)
	| IfThenElse(a, b, c) ->
		let g = (eval a r) in
			if (typecheck Sbool g)
				then (if g = Bool(true) then (eval b r) else (eval c r))
				else failwith ("nonboolean guard")
	| Let(i, e1, e2) -> eval e2 (bind r i (eval e1 r))
	| Fun(i, a) -> FunVal(i, a, r)
	| Apply(f, eArg) ->
		let fClosure = (eval f r) in (
      match fClosure with
			| FunVal(arg, fBody, fDecEnv) ->
					eval fBody (bind fDecEnv arg (eval eArg r))
			| RecFunVal(g, (arg, fBody, fDecEnv)) ->
					let aVal = (eval eArg r) in
						let rEnv = (bind fDecEnv g fClosure) in
							let aEnv = (bind rEnv arg aVal) in
								eval fBody aEnv
			|	_ -> failwith("non functional value")
      )
  | Edict(d) ->
    Dict(evalDict d r (Sdict(Sunbound)) [])
  | Insert(i, e, d) -> (
    match d with
    | Edict(dt) ->
      Dict(evalDict dt r (Sdict(typeofexp e)) [(i, eval e r)])
    | _ -> failwith("Not a dict in Insert")
    )
  | Delete(i, d) -> (
    match d with
    | Edict(dt) ->
      Dict(deleteFromDict i dt r (Sdict(Sunbound)) [])
    | _ -> failwith("Not a dict in Insert")
    )
  | Has_key(i, d) -> (
    match d with
    | Edict(dt) ->
      has_key i (evalDict dt r (Sdict(Sunbound)) [])
    | _ -> failwith("Wrong type in Has_key")
    )
and evalDict (d : (ide * exp) list) (r : evT env) (t : static) (acc : (ide * evT) list) : ((ide * evT) list) =
  match d with
  | (i, e) :: xs -> (
    match has_key i acc with
    | Bool false ->
      let te = typeofexp e in (
      match t with
      | Sdict(td) ->
        if td = Sunbound || td = te
          then evalDict xs r (Sdict(te)) ((i, (eval e r)) :: acc)
          else failwith("Non-homogeneous type in dict")
      | _ -> failwith("Something went very very... very wrong here")
      )
    | _ -> failwith("Key already present")
    )
  | [] ->
    acc
and deleteFromDict (id : ide) (d : (ide * exp) list) (r : evT env) (t : static) (acc : (ide * evT) list) : ((ide * evT) list) =
  match d with
  | (i, e) :: xs ->
    let () = Printf.printf "tipi i, id: %s, %s\n" i id in
    if i = id
      then deleteFromDict id xs r t acc
      else (
      match has_key i acc with
      | Bool false ->
        let te = typeofexp e in (
        match t with
        | Sdict(td) ->
          if td = Sunbound || td = te
            then deleteFromDict id xs r (Sdict(te)) ((i, (eval e r)) :: acc)
            else failwith("Non-homogeneous type in dict")
        | _ -> failwith("Something went very very... very wrong here")
        )
      | _ -> failwith("Key already present")
      )
  | [] ->
    acc
;;
