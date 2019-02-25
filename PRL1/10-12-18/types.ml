type day = mon | tue | wed | thu | fri | sat | sun;;
 (* mon < tue < ... < sun*)

(*
Definire un tipo che raccolga valori bool e int
*)

(*
Eh, volevi!
*)

type union = B of bool | N of int;;

(*
Alberi binari
  - Un albero vuoto e' un albero binario
  - Un valore della radice piu' un sottoalbero binario sinistro piu' un sottoalbero binario destro e' un albero binario
*)

type 'a bTree = Void | Node of 'a * 'a bTree * 'a bTree;;

let rec split lst =
  match lst with
    | [] ->
      ([], [])
    | [x] ->
      ([x], [])
    | x :: y :: ys ->
      let (l1, l2) = split ys in
      (x :: l1, y :: l2)
;;

let rec bldTree lst =
  match lst with
    | [] ->
      Void
    | x :: xs ->
      let (l1, l2) = split xs in
        Node (x, bldTree l1, bldTree l2)
;;

(*
Linearizzazione anticipata
  Radice; Linearizzazione anticipata sott. sx; Linearizzazione anticipata sott. dx
*)


(*
Linearizzazione differita
  Linearizzazione differita sott. sx; Linearizzazione differita sott. dx; Radice
*)


(*
Linearizzazione simmetrica
  Linearizzazione simmetrica sott. sx; Radice; Linearizzazione simmetrica sott. dx
*)

let rec linS bt =
  match bt with
    | Void ->
      []
    | Node (x, lbt, rbt) ->
      linS lbt @ [x] @ linS rbt
;;
