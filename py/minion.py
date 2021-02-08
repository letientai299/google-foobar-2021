from collections import defaultdict

def solution(data, n):
    freq = defaultdict(int)
    for v in data:
        freq[v] += 1

    print(freq)
    return [v for v in data if freq[v] <= n]

res = solution([1, 2, 2, 3, 3, 3, 4, 5, 5], 1)
print(res)
