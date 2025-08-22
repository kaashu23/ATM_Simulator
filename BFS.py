#BFS - Visits nodes level by level using a queue
graph = { 'A': set(['B', 'C']),
          'B': set(['A', 'D', 'E']),
          'C': set(['A', 'F']),
          'D': set(['B']),
          'E': set(['B', 'F']),
          'F': set(['C', 'E'])
          }

# Implement Logic of BFS (to find levels and visited nodes)

def bfs(start):
    queue = [start]
    levels = {}  #This Dict Keeps Track of Levels
    levels[start] = 0 #Depth of Start node is 0
    visited = set([start])
    while queue:
        node = queue.pop(0)
        neighbours = graph[node]
        for neighbor in neighbours:
            if neighbor not in visited:
                queue.append(neighbor)
                visited.add(neighbor)
                levels[neighbor] = levels[node] + 1
    print(levels)  #Print graph level
    return visited
print(str(bfs('A')))  #Print graph node


# For finding Breadth First Search path
def bfs_paths(graph, start, goal):
    queue = [(start, [start])]
    while queue:
        (vertex, path) = queue.pop(0)
        for next in graph[vertex] - set(path):
            if next == goal:
                yield path + [next]
            else:
                queue.append((next, path + [next]))
result = list(bfs_paths(graph, 'A', 'F'))
print(result)  # [['A', 'C', 'F'], ['A', 'B', 'E', 'F']]
# For finding shortest path
def shortest_path(graph, start, goal):
    try:
        return next(bfs_paths(graph, start, goal))
    except StopIteration:
        return None

result1 = shortest_path(graph, 'A', 'F')
print(result1)  # ['A', 'C', 'F']


#2
from collections import deque
def bfs(graph, start):
    visited = set()
    queue = deque([start])
    while queue:
        node = queue.popleft()
        if node not in visited:
            print(node)
            visited.add(node)
            queue.extend(graph[node])
graph = {'A':['B','C'], 'B':['D'],'C':[],'D':[]}
bfs(graph, 'A')
