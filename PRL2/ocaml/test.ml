let env0 = emptyenv Unbound;;

(* Dict declaration *)

let dctInt = Edict(Val("i1", Eint 1, Val("i2", Eint 2, Val("i3", Eint 3, Empty))));;
let dctBool = Edict(Val("b1", Ebool false, Val("b2", Ebool true, Val("b3", Ebool true, Empty))));;
let dctErr = Edict(Val("a", Eint 10, Val("b", Ebool false, Empty)));;
eval dctInt env0;;
eval dctBool env0;;
try (eval dctErr env0) with _ -> Unbound;;

(* Dict manipulation *)
