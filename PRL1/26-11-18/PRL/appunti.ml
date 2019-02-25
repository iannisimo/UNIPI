
(*ES*)

match [3;4] with
    [] -> 0
  | x::xs -> x;;

(*Lunghezza di una lista*)

(*
let rec len l =
  if l = [] then
    0
  else
    1 + len(tl l);;
*)

let rec lenP l =
  match l with
      [] -> 0
    | x::xs -> 1 + lenP xs;;

(*Ultimo elemento di una lista*)

let rec last l =
  match l with
      [x] -> x
    | x::xs -> last xs
    | _ -> hd l;;

    (*Per chiarezza vogliamo avere i pattern MUTUAMENTE ESCLUSIVI*)
      (*Se posso uguagliare un valore ad un patterns non voglio che possa essere uguagliato ad altri*)
        (*xs puo' essere la lista vuota*)

let rec lastBetter l =
  match l with
      [x] -> x
    | x::y::ys -> lastBetter(y::ys)
    | _ -> hd l;;

(*In CAML e' predefinito l'operatore di concatenazione tra liste*)

let concat x y = x @ y;;

(*prefix: fa diventare un operatore infisso un operatore prefisso*)

let concatPrefix x y = prefix @ (x y);;

(*@ prop*)
(*
[] @ l = l @ [] = l
x::(l1@l2) = (x::l1)@l2
*)

(*Definiamo '@' ricorsivamente*)

(*CURRYed*)
let rec append l1 l2 =
  match l1 with
      [] -> l2
    | x::xs -> x :: append xs l2;;
 (*unCURRYed*)
(*let rec append (l1, l2) =*)

(*
generalizzazione di hd
  restituisce n elementi di l o quanti possibile
*)

let rec take n l =
  match l with
      [] -> []
    | x::xs ->
      if n = 0 then
        []
      else
        x::take (n-1) xs;;

let rec take2 n l =
  match (l, n) with
      ([], n) -> []
    | (x::xs, 0) -> []
    | (x::xs, n) -> x::take2 (n-1) xs;;

(*Clausola when*)

let rec take3 n l =
match (l, n) with
([], n) -> []
| (x::xs, 0) -> []
| (x::xs, n) when n > 0 -> x::take3 (n-1) xs
| _ -> take3 n l;;

(*Generalizzazione tail*)

let rec drop n l =
  match (l, n) with
      ([], n) -> []
    | (x::xs, 0) -> x::xs
    | (x::xs, n) when n > 0 -> drop (n-1) xs
    | _ -> drop n l;;

(*get n'th element of l*)

let rec get n l =
  match (n, l) with
      (*(0, l) -> dioCaneStronzoBastardoInfame*)
      (1, x::xs) -> x
    | (n, x::xs) when n > 1 -> get (n-1) xs
    | _ -> 0;;
