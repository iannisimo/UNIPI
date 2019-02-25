def ins_sort (arr, dim):
    for i in [0, dim]:
        found = False
        j = i
        while not found and j > 0:
            if(arr[j] < arr[j - 1]):
                #swap(arr, j)
                found = False
            else:
                found = True
            j -= 1

if __name__ == '__main__':
    ins_sort ({4,2,1,3,5,6}, 6)
