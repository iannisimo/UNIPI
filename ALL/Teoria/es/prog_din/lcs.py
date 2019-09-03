
# def gen(A, b, n):
#     if b == n-1:
#         print A
#     else:
#         A[b+1] = 0
#         gen(A, b+1, n)
#         A[b+1] = 1
#         gen(A, b+1, n)

import numpy

def lcs_couter(A, B):
    M = [[0 for _ in range(len(B)+1)] for _ in range(len(A)+1)]
    for i in range(1, len(A)+1):
        for j in range(1, len(B)+1):
            if(A[i-1] == B[j-1]):
                M[i][j] = M[i-1][j-1] + 1
            else:
                M[i][j] = max(M[i][j-1], M[i-1][j])
    print M

if __name__ == '__main__':
    A = raw_input('A: ')
    B = raw_input('B: ')
    lcs_couter(A, B)


746257918
yiz573
