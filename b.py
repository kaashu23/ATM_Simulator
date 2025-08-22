import networkx as nx
import matplotlib.pyplot as plt

def Cost(H, condition, weight = 1):
    cost = {}
    if 'AND' in condition:
        AND_nodes = condition['AND']
        Path_A = ' AND '.join(AND_nodes)
        PathA = sum(H[node]+weight for node in AND_nodes)
        cost[Path_A] = PathA

    if 'OR' in condition:
        OR_nodes = condition['OR']
        Path_B = ' OR '.join(OR_nodes)
        PathB = min(H[node]+weight for node in OR_nodes)
        cost[Path_B] = PathB
    return cost

#Update the cost
def update_cost(H, Conditions, weight=1):
    Main_nodes = list(Conditions.keys())
    Main_nodes.reverse()
    least_cost = {}
    for key in Main_nodes:
        condition = Conditions[key]
        print(key, ':', Conditions[key], '>>>', Cost(H, condition, weight))
        c = Cost(H, condition, weight) 
        H[key] = min(c.values())
        least_cost[key] = c
    return least_cost

def shortest_path(Start, Updated_cost, H):
    Path = Start
    if Start in Updated_cost.keys():
        Min_cost = min(Updated_cost[Start].values())
        key = list(Updated_cost[Start].keys())
        values = list(Updated_cost[Start].values())
        Index = values.index(Min_cost)
        
        Next = key[Index].split()
        if len(Next) == 1:
            Start = Next[0]
            Path += ' = ' + shortest_path(Start, Updated_cost, H)
        else:
            Path += '=('+key[Index]+') '
            Start = Next[0]
            Path += '[' + shortest_path(Start, Updated_cost, H) +' + '
            Start = Next[-1]
            Path += shortest_path(Start, Updated_cost, H) + ']'

    return Path

def visualize_graph(conditions, updated_cost, H):
    G = nx.DiGraph()

    for node, condition in conditions.items():
        if 'AND' in condition:
            for n in condition['AND']:
                G.add_edge(node, n, label='AND', color='green')
        if 'OR' in condition:
            for n in condition['OR']:
                G.add_edge(node, n, label='OR', color='blue')

    pos = nx.spring_layout(G)
    labels = nx.get_edge_attributes(G, 'label')
    colors = [G[u][v]['color'] for u, v in G.edges]

    plt.figure(figsize=(8, 6))
    nx.draw(G, pos, with_labels=True, node_color='lightblue', node_size=2000,
            font_size=10, font_weight='bold', edge_color=colors, arrows=True)
    nx.draw_networkx_edge_labels(G, pos, edge_labels=labels, font_color='red')
    plt.title('AND-OR Path Graph')
    plt.show()

# Heuristic values of Nodes
H1 = {'A': 1, 'B': 6, 'C': 2, 'D': 12, 'E': 2, 'F': 1, 'G': 5, 'H': 7, 'I': 7, 'J': 1, 'T':3}

Conditions = {
    'A': {'OR': ['D'], 'AND': ['B', 'C']},
    'B': {'OR': ['G', 'H']},
    'C': {'OR': ['J']},
    'D': {'AND': ['E', 'F']},
    'G': {'OR': ['I']}
}

weight = 1

print('Updated Cost:')
Updated_cost = update_cost(H1, Conditions, weight=1)
print('*' * 75)

print('Shortest Path:\n', shortest_path('A', Updated_cost, H1))

visualize_graph(Conditions, Updated_cost, H1)
