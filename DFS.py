#DFS - Visits as deep as possible before backtracking
graph1 = {'A': set(['B', 'C']),
          'B': set(['A', 'D', 'E']),
          'C': set(['A', 'F']),
          'D': set(['B']),
          'E': set(['B', 'F']),
          'F': set(['C', 'E'])
          }

def dfs(graph, node, visited):
    if node not in visited:
        visited.append(node)
        for n in graph[node]:
            dfs(graph, n, visited)
    return visited
visited = dfs(graph1, 'A', [])
print(visited)

#2
def dfs(graph, node, visited):
    print(node)
    visited.add(node)
    for neighbor in graph[node]:
        dfs(graph, neighbor, visited)
graph = {'A':['B','C'],'B':['D'],'C':[],'D':[]}
dfs(graph, 'A', set())
