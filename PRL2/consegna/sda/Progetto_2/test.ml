(* Static typechecking env *)
let envS = emptysenv Sunbound;;
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
"Standard objects";;
staticCheck cInt envS;;
staticCheck cBool envS;;
staticCheck sum envS;;
staticCheck prod envS;;
staticCheck eq envS;;
staticCheck lAnd envS;;
staticCheck ifttt envS;;
staticCheck myfun envS;;
staticCheck call envS;;


"Errors (Should display Sunbound)";;
try (staticCheck eSum envS) with e -> Sunbound;;
try (staticCheck eEq envS) with e -> Sunbound;;
try (staticCheck eIfttt envS) with e -> Sunbound;;
try (staticCheck eIftttt envS) with e -> Sunbound;;
try (staticCheck eFuncall envS) with e -> Sunbound;;

"Dictionary";;
staticCheck dctInt envS;;
staticCheck dctBool envS;;
staticCheck intInInt envS;;
staticCheck delBool envS;;
staticCheck hasInt envS;;
staticCheck hasBool envS;;
staticCheck itFun envS;;
staticCheck iter envS;;
staticCheck foldFun envS;;
staticCheck fol envS;;
staticCheck fil envS;;
staticCheck fil2 envS;;

"Dictionary errors (Should display Sunbound)";;
try (staticCheck dctErr envS) with e -> Sunbound;;
try (staticCheck boolInInt envS) with e -> Sunbound;;
try (staticCheck notDict envS) with e -> Sunbound;;

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
