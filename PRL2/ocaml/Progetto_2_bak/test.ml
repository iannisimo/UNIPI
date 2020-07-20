let notify_user f =
  try f()
  with e ->
    let msg = Printexc.to_string e
    and stack = Printexc.get_backtrace () in
    Printf.eprintf "there was an error: %s%s\n" msg stack;
    raise e

(* Static typechecking env *)
let envS = emptyenvtype "unbound";;
(* Dynamic env *)
let envD = emptyenv Unbound;;

(* Base operations *)

let cInt = Eint 10;;
let cBool = Ebool false;;
let sum = Sum(cInt, Eint 10);;
let prod = Prod(cInt, Eint 2);;
let eq = Eq(prod, sum);;
let lAnd = And(cBool, Ebool true);;
let ifttt = Ifthenelse(Not(lAnd), Eint 1, Eint 0);;
let myfun = Fun("a", Let("b", Eint 10, Diff(Minus(Den("a")), Den("b"))));;
let call = FunCall(myfun, Eint(-30));;

(* Erroneous base operations *)

let eSum = Sum(Eint 10, Ebool false);;
let eEq = Eq(cBool, cInt);;
let eIfttt = And(Ebool false, Ifthenelse(Not(cBool), Eint 10, Ebool false));;
let eIftttt = Ifthenelse(Eint 10, Ebool false, Ebool false);;
let eFuncall = FunCall(Fun("b", And(Den("b"), Ebool true)), Eint 10);;

(* Dict operations *)

let dctInt = Edict(Val("i1", Eint 1, Val("i2", Eint 2, Val("i3", Eint 3, Empty))));;
let dctBool = Edict(Val("b1", Ebool false, Val("b2", Ebool true, Val("b3", Ebool true, Empty))));;
let intInInt = Insert("i4", Eint 4, dctInt);;
let delBool = Delete("b3", dctBool);;
let hasInt = Has_key("i1", dctInt);;
let hasBool = Has_key("b3", delBool);;
let itFun = Fun("x", Not(Den("x")));;
let iter = Iterate(itFun, dctBool);;
let foldFun = FFun("a", "b", Sum(Den "a", Den "b"));;
let fol = Fold(foldFun, dctInt, Eint 0);;
let fil = Filter(["i1";"i2";"x"], dctInt);;
let fil2 = Filter(["i1";"i2";"x"], dctBool);;

(* Dict Errors *)

let dctErr = Edict(Val("a", Eint 10, Val("b", Ebool false, Empty)));;
let boolInInt = Insert("i4", Ebool false, dctInt);;
let reuseKey = Insert("i1", Eint 0, dctInt);;
let notDict = Delete("x", Sum(Eint 10, Eint 20));;
let iterErr = Iterate(itFun, dctInt);;


"Static typechecking";;
check cInt envS;;
check cBool envS;;
check sum envS;;
check prod envS;;
check eq envS;;
check lAnd envS;;
check ifttt envS;;
check myfun envS;;
check call envS;;

try (check eSum envS) with e -> Printexc.to_string e;;
try (check eEq envS) with e -> Printexc.to_string e;;
try (check eIfttt envS) with e -> Printexc.to_string e;;
try (check eIftttt envS) with e -> Printexc.to_string e;;
try (check eFuncall envS) with e -> Printexc.to_string e;;

check dctInt envS;;
check dctBool envS;;
check intInInt envS;;
check delBool envS;;
check hasInt envS;;
check hasBool envS;;
check itFun envS;;
check iter envS;;
check foldFun envS;;
check fol envS;;
check fil envS;;
check fil2 envS;;

try (check dctErr envS) with e -> Printexc.to_string e;;
try (check boolInInt envS) with e -> Printexc.to_string e;;
try (check reuseKey envS) with e -> Printexc.to_string e;;
try (check notDict envS) with e -> Printexc.to_string e;;
try (check iterErr envS) with e -> Printexc.to_string e;;

"Evaluation";;
"Base operations";;

eval cInt envD;;
eval cBool envD;;
eval sum envD;;
eval prod envD;;
eval eq envD;;
eval lAnd envD;;
eval ifttt envD;;
eval myfun envD;;
eval call envD;;

"Base errors";;

try (eval eSum envD) with e -> Unbound;;
try (eval eEq envD) with e -> Unbound;;
try (eval eIfttt envD) with e -> Unbound;;
try (eval eIftttt envD) with e -> Unbound;;
try (eval eFuncall envD) with e -> Unbound;;

"Dict operations";;

eval dctInt envD;;
eval dctBool envD;;
eval intInInt envD;;
eval delBool envD;;
eval hasInt envD;;
eval hasBool envD;;
eval itFun envD;;
eval iter envD;;
eval foldFun envD;;
eval fol envD;;
eval fil envD;;
eval fil2 envD;;

"Dict errs";;

try (eval dctErr envD) with e -> Unbound;;
try (eval boolInInt envD) with e -> Unbound;;
try (eval reuseKey envD) with e -> Unbound;;
try (eval notDict envD) with e -> Unbound;;
try (eval iterErr envD) with e -> Unbound;;
