-- Piatti(IdPi, Nome, Prezzo, Peso, Tipo, Calorie)
-- PiattiIngredienti(IdPi*, IdI*)
-- Ingredienti(IdI, Nome, Gruppo, Costo)
-- Intolleranze(IdI*, IdPe*)
-- Persone(IdPe, Nome, Professione, Età)

-- L’IdPi e il Nome di tutti i piatti che contengono almeno un ingrediente il cui costo supera 100

SELECT DISTINCT p.IdPi, p.Nome
FROM Piatti p
WHERE EXISTS (
  SELECT *
  FROM PiattiIngredienti pin
    NATURAL JOIN Ingredienti i
  WHERE p.IdPi = pin.IdPi AND i.Costo > 100
)

-- L’IdPi e il Nome di tutti i piatti che contengono solo ingredienti il cui costo unitario supera 100

SELECT p.IdPi, p.Nome
FROM Piatti p
WHERE NOT EXISTS (
  SELECT *
  FROM PiattiIngredienti pin
    NATURAL JOIN Ingredienti i
  WHERE p.IdPi = pin.IdPi AND i.costo <= 100
)

-- Per ogni Tipo di piatto, il Tipo, il costo totale degli ingredienti

SELECT p.Tipo, SUM(i.Costo)
FROM Piatti p
  NATURAL JOIN PiattiIngredienti pin
  NATURAL JOIN Ingredienti i
GROUP BY p.Tipo

-- Per ogni persona che è intollerante a ingredienti di almeno due gruppi diversi, IdPe e Nome della persona, e i diversi gruppi per i quali presenta un’intolleranza

SELECT DISTINCT pe.IdPe, pe.Nome, i.Gruppo
FROM Persone pe
  NATURAL JOIN Intolleranze it
  NATURAL JOIN Ingredienti i
WHERE EXISTS (
  SELECT *
  FROM Intolleranze it2
    NATURAL JOIN Ingredienti i2
  WHERE pe.IdPe = it2.IdPe AND i.IdI != i2.IdI
)

-- Per ogni piatto per cui per ogni ingrediente esiste almeno una persona intollerante, restituire il Nome e il Prezzo

SELECT p.Nome, p.Prezzo
FROM Piatti p
WHERE NOT EXISTS (
  SELECT *
  FROM Ingredienti i
    NATURAL JOIN 
)
