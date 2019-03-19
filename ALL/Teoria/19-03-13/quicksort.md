## Quicksort
---
**Divide:**
Scegli un pivot e dividi l'insieme in elementi > e < di esso

**Impera:**
Ordina ricorsivamente le due parti

**Combina:**
***NO***

---
    Quicksort(A, p, r):
        if p < r:
            q = Partition(A, p, r)
            Quicksort(A, p, q - 1)
            Quicksort(A, q + 1, r)


    Partiton(A, p, r):
        i = p - 1
        for j = p to r - 1:
            if A[j] <= x:
                i++
                Swap(A[i]), A[j]
        Swap(A[i++], A[r])
    return i

    Swap(a, b):
        Do some swap stuff
---
**Caso migliore**
*(gli elementi venono divisi sempre in 2 parti quasi equivalenti)***:**

$\Theta(n\cdot\log{n})$ 

**Caso peggiore**
*(gli elementi sono gia' ordinati)***:**

$\Theta(n^n)$