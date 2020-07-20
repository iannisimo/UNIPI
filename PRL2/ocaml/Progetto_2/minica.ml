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
and static =
    Sunbound
  | Sint
  | Sbool
  | Sdict
  | SdictInt
  | SdictBool
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
let typecheck (s : static) (v : evT) : bool = match s with
	| Sint -> (match v with
		| Int(_) -> true
		| _ -> false)
	| Sbool -> (match v with
		| Bool(_) -> true
		| _ -> false)
	| Sdict -> (match v with
		| Dict(_) -> true
		| _ -> false)
	| SdictInt -> (match v with
		| Dict(_) -> true
		| _ -> false)
	| SdictBool -> (match v with
		| Dict(_) -> true
		| _ -> false)
	| _ -> failwith("not a valid type");;

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

let rec staticCheck (e : exp) (r : senv) : static = match e with
  | Eint n -> Sint
  | Ebool b -> Sbool
  | IsZero a ->
    if sameType (staticCheck a r) Sint
      then Sbool
      else failwith "static type error"
  | Den i -> r i
  | Eq(a, b) ->
    if sameType (staticCheck a r) (staticCheck b r)
      then Sbool
      else failwith "static type error"
  | Prod(a, b) ->
    if sameType (staticCheck a r) Sint && sameType (staticCheck b r) Sint
      then Sint
      else failwith "static type error"
  | Sum(a, b) ->
    if sameType (staticCheck a r) Sint && sameType (staticCheck b r) Sint
      then Sint
      else failwith "static type error"
  | Diff(a, b) ->
    if sameType (staticCheck a r) Sint && sameType (staticCheck b r) Sint
      then Sint
      else failwith "static type error"
  | Minus a ->
    if sameType (staticCheck a r) Sint
      then Sint
      else failwith "static type error"
  | And(a, b) ->
    if sameType (staticCheck a r) Sbool && sameType (staticCheck b r) Sbool
      then Sbool
      else failwith "static type error"
  | Or(a, b) ->
    if sameType (staticCheck a r) Sbool && sameType (staticCheck b r) Sbool
      then Sbool
      else failwith "static type error"
  | Not a ->
    if sameType (staticCheck a r) Sbool
      then Sbool
      else failwith "static type error"
  | Ifthenelse(a, b, c) ->
    if sameType (staticCheck a r) Sbool
      then if sameType (staticCheck b r) (staticCheck c r)
        then staticCheck b r
        else failwith "static type error"
      else failwith "static type error"
  | Let(i, e1, e2) -> staticCheck e2 (sbind r i (staticCheck e1 r))
  | Fun(i, a) -> staticCheck a r
  | FunCall(f, eArg) -> (
    match f with
      | Fun(i, a) -> staticCheck f (sbind r i (staticCheck eArg r))
      | _ -> failwith "static type error"
    )
  | FFun(i1, i2, a) -> staticCheck a r
  | Edict d -> staticDict d Sunbound r
  | Insert(i, e, d) -> let dType = (staticCheck d r) in
    if sameType (dictToType dType) (staticCheck e r)
      then dType
      else failwith "static type error"
  | Delete(i, d) -> let dType = (staticCheck d r) in
    if isDict dType
      then dType
      else failwith "static type error"
  | Has_key(i, d) -> let dType = (staticCheck d r) in
    if isDict dType
      then Sbool
      else failwith "static type error"
  (* Cannot check if <f> accepts the values in <d> *)
  | Iterate(f, d) ->
    if isDict (staticCheck d r)
      then (typeToDict (staticCheck f r))
      else failwith "static type error"
  | Fold(f, d, a) -> let aType = (staticCheck a r) in
    if sameType (staticCheck f r) aType && isDict (staticCheck d r)
      then aType
      else failwith "static type error"
  | Filter(is, d) -> let dType = (staticCheck d r) in
    if isDict dType
      then dType
      else failwith "static type error"

and sameType (a : static) (b : static) : bool =
  ((a = Sunbound) || (a = b) || (b = Sunbound))
and typeToDict (a : static) : static = match a with
  | Sunbound -> Sdict
  | Sint -> SdictInt
  | Sbool -> SdictBool
  | _ -> failwith "static type error"
and dictToType (a : static) : static = match a with
  | Sdict -> Sunbound
  | SdictInt -> Sint
  | SdictBool -> Sbool
  | _ -> failwith "static type error"
and isDict (d : static) : bool =
  (d = Sdict || d = SdictInt || d = SdictBool)
and staticDict (d : dict) (t : static) (r : senv) : static =
  match d with
    | Val(i, e, ls) -> let eType = (staticCheck e r) in
        if sameType eType t
          then (staticDict ls eType r)
          else failwith "static type error"
    | Empty -> (typeToDict t)
    | _ -> failwith "static type error"


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
			if (typecheck Sbool g)
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

	Edict(d) -> Dict(evalDict d r Sdict []) |
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

	and evalDict (d : dict) (r : evT env) (tpe : static) (acc : (ide * evT) list) : (ide * evT) list =
		match d with
				Empty -> acc
			| Val(i, e, ls) -> match (has_key i acc) with
          Bool false -> let value = (eval e r) in (
            match tpe with
			       		Sdict -> (evalDict ls r (if (typecheck Sint value) then SdictInt else SdictBool) ((i, value)::acc))
			       	| SdictInt -> if(typecheck Sint value)
			       		then (evalDict ls r SdictInt ((i, value)::acc))
			       		else failwith("Type error")
			       	| SdictBool -> if(typecheck Sbool value)
			       		then (evalDict ls r SdictBool ((i, value)::acc))
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

  and dict_type (d : (ide * evT) list) : static =
    match d with
      | [] -> Sunbound
      | (i,e)::xs -> (
        if typecheck Sint e
          then Sint
          else Sbool
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
