from graph_tool.all import *

from collections import deque
import operator

# def local_search(G):
# 	tabu_list = deque()

# 	ordering = initial_solution(G)
# 	H, successors = triangulate(G, ordering)
# 	s = score(H, successors)

# 	i = 0
# 	a = 2

# 	while i < a:
# 		tabu_list.append(ordering)
# 		if(len(tabu_list) > 7):
# 			tabu_list.popleft()

# 		orderings = [o for o in neighborhood(ordering, successors) if o not in tabu_list]
# 		neighbors = [triangulate(G, o) for o in orderings]
# 		scores = [score(*neighbor) for neighbor in neighbors]

# 		max_index, max_value = max(enumerate(scores), key=operator.itemgetter(1))

# 		ordering = orderings[max_index]
# 		H, successors = neighbors[max_index]

# 		print(ordering, max_value)

# 		i += 1
# 		if i == a:
# 			print(chordal_graph_treewidth(H))




# def neighborhood(ordering, successors):
# 	neighbors = []

# 	for v in ordering:
# 		idx = ordering.index(v)

# 		max_pred = max_predecessor(ordering, v, successors)
# 		min_succ = min_successor(ordering, v, successors)
		
# 		if max_pred is not None:
# 			neighbor1 = list(ordering)
# 			neighbor1[max_pred], neighbor1[idx] = neighbor1[idx], neighbor1[max_pred]
# 			if neighbor1 not in neighbors:
# 				neighbors.append(neighbor1)

# 		if min_succ is not None:
# 			neighbor2 = list(ordering)
# 			neighbor2[min_succ], neighbor2[idx] = neighbor2[idx], neighbor2[min_succ]
# 			if neighbor2 not in neighbors:
# 				neighbors.append(neighbor2)

# 	return neighbors

# def min_successor(ordering, v, succ):
# 	index = ordering.index(v)
# 	candidates = ordering[v:]
# 	candidates = [w for w in candidates if w in succ]	
# 	try:
# 		return (ordering.index(candidates[0]))
# 	except IndexError:
# 		return None

# def max_predecessor(ordering, v, successors):
# 	predecessors = [w for (w,succ) in successors.items() if v in succ]
# 	positions = map(lambda s: ordering.index(s), predecessors)
# 	try:
# 		return max(positions)
# 	except ValueError:
# 		return None

# def score(H, successors):
# 	n = len(H.nodes())
# 	clique_size = chordal_graph_treewidth(H)
# 	succ = sum(map(lambda x: len(x)^2, successors.values()))

# 	return (n^2)*(clique_size^2) + succ

def initial_solution(G):
	return G.nodes()

def triangulate(G, ordering):
	triangulated_edges = set(G.edges())
	successors = {}

	G = G.copy()

	for node in ordering:
		neighbors = list(all_neighbors(G, node))
		successors[node] = neighbors

		G = make_clique(G, neighbors)
		triangulated_edges = triangulated_edges | set(G.edges())
		G.remove_node(node)

	H = Graph()
	H.add_edges_from((triangulated_edges))

	return (H, successors)

# def make_clique(G, subgraph):
# 	for i, v in enumerate(subgraph):
# 		for w in subgraph[i+1:]:
# 			G.add_edge(v,w)

# 	return G

def read(path):
	with open(path, "r") as f:
		lines = [line.rstrip('\n') for line in f.readlines() if line[0] == 'e']
		edges = [(int(edge[1]),int(edge[2])) for edge in [line.split() for line in lines]]
	
	g = Graph(directed=False)
	for (v,w) in edges:
		g.add_edge(v,w)
	# Graphs have 1 default vertex
	g.remove_vertex(0)

	return g

if __name__ == '__main__':
	g = read("graphs/myciel2.col")




