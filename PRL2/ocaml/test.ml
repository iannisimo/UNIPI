let envS = emptyenvtype "unbound";;
let envD = emptyenv Unbound;;

let testF a : string = try a with _ -> "task failed successfully";;

(* Dict declaration *)

let dctInt = Edict(Val("i1", Eint 1, Val("i2", Eint 2, Val("i3", Eint 3, Empty))));;
let dctBool = Edict(Val("b1", Ebool false, Val("b2", Ebool true, Val("b3", Ebool true, Empty))));;
let dctErr = Edict(Val("a", Eint 10, Val("b", Ebool false, Empty)));;

check dctInt envS;;
check dctBool envS;;
try (check dctErr envS) with _ -> "Task failed successfully";;
eval dctInt envD;;
eval dctBool envD;;
try (eval dctErr envD) with _ -> Unbound;;

(* Dict manipulation *)

let intInInt = Insert("i4", Eint 4, dctInt);;
let boolInInt = Insert("i4", Ebool false, dctInt);;
let reuseKey = Insert("i1", Eint 0, dctInt);;
let delBool = Delete("b3", dctBool);;
let notDict = Delete("x", Sum(Eint 10, Eint 20));;

eval notDict envD;;
check notDict envS;;

check reuseKey envS;;
eval delBool envD;;
