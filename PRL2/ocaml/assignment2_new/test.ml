let envD = emptyenv Unbound;;

let d1 = Edict(["i", Eint(1); "l", Eint(2)]);;
eval d1 envD;;
let d2 = Edict(["i", Eint(1); "i", Eint(2)]);;
try eval d2 envD with e -> Unbound;;
let d2 = Edict(["i", Eint(1); "l", Ebool(false)]);;
(* eval d2 envD;; *)
let i = Insert("n", Eint(2), d1);;
eval i envD;;
let ei = Insert("i", Eint(2), d1);;
(* eval ei envD;; *)
let d = Delete("i", Edict(["n", Eint(3); "i", Eint(1); "l", Eint(2)]));;
eval d envD;;
let h = Has_key("r", i);;
eval h envD;;
