
def gen(A, b, n):
    if b == n-1:
        print A
    else:
        A[b+1] = 0
        gen(A, b+1, n)
        A[b+1] = 1
        gen(A, b+1, n)


if __name__ == '__main__':
    n = 15
    Arr = []
    for i in range(n):
        Arr.append(0)
    gen(Arr, 0, n)
