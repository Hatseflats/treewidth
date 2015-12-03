from graph_tool.all import *

from collections import deque
from itertools import combinations
import operator
import line_profiler
import numpy as np

def local_search(g):
	tabu_list = deque()

	ordering = initial_solution(g)
	h, successors = triangulate(g, ordering)
	s = score(h, successors)

	i = 0
	a = 1

	while i < a:
		tabu_list.append(ordering)
		if(len(tabu_list) > 7):
			tabu_list.popleft()

		orderings = [o for o in neighborhood(ordering, successors) if o not in tabu_list]
		neighbors = [triangulate(g, o) for o in orderings]
		scores = [score(*neighbor) for neighbor in neighbors]

		max_index, max_value = max(enumerate(scores), key=operator.itemgetter(1))

		ordering = orderings[max_index]
		h, successors = neighbors[max_index]

		print(ordering, max_value)

		i += 1
		if i == a:
			print(max(map(len,successors.values())))


def neighborhood(ordering, successors):
	neighbors = []

	for v in ordering:
		idx = ordering.index(v)

		max_pred = max_predecessor(ordering, v, successors)
		min_succ = min_successor(ordering, v, successors)
		
		if max_pred is not None:
			neighbor1 = list(ordering)
			neighbor1[max_pred], neighbor1[idx] = neighbor1[idx], neighbor1[max_pred]
			if neighbor1 not in neighbors:
				neighbors.append(neighbor1)

		if min_succ is not None:
			neighbor2 = list(ordering)
			neighbor2[min_succ], neighbor2[idx] = neighbor2[idx], neighbor2[min_succ]
			if neighbor2 not in neighbors:
				neighbors.append(neighbor2)

	return neighbors

def min_successor(ordering, v, succ):
	index = ordering.index(v)
	candidates = ordering[v:]
	candidates = [w for w in candidates if w in succ]	
	try:
		return (ordering.index(candidates[0]))
	except IndexError:
		return None

def max_predecessor(ordering, v, successors):
	predecessors = [w for (w,succ) in successors.items() if v in succ]
	positions = map(lambda s: ordering.index(s), predecessors)
	try:
		return max(positions)
	except ValueError:
		return None

def score(H, successors):
	n = len(list(H.vertices()))
	clique_size = max(map(len,successors.values()))
	succ = sum(map(lambda x: len(x)^2, successors.values()))

	return (n^2)*(clique_size^2) + succ

def initial_solution(g):
	return [int(v) for v in g.vertices()]

# @profile
# def make_clique(g,n):
# 	new_edges = []
# 	for i,w in enumerate(n):
# 		neighbours = set(w.all_neighbours())
# 		new_edges.extend([(u,w) for u in n[i+1:] if not (u in neighbours)])
# 	g.add_edge_list(new_edges)

# @profile
# def make_clique(g,n):
# 	new_edges = []
# 	for i,w in enumerate(n):
# 		new_edges.extend([(u,w) for u in n[i+1:]])
# 	g.add_edge_list(new_edges)
# 	# remove_parallel_edges(g)
# 	label_parallel_edges(g, eprop=g.ep.deleted)


# @profile 
# def triangulate(g, ordering):
# 	h = g.copy()
# 	successors = {}

# 	visited = h.new_vertex_property("bool") 
# 	h.vp.visited = visited
# 	h.set_vertex_filter(visited, True)

# 	deleted = h.new_edge_property("bool")
# 	h.ep.deleted = deleted
# 	h.set_edge_filter(deleted, True)

# 	vertices = [h.vertex(i) for i in ordering]

# 	for v in vertices:
# 		n = list(v.all_neighbours())
# 		successors[int(v)] = [int(w) for w in n]
# 		make_clique(h,n)
# 		h.vp.visited[v] = True	

# 	return h, successors

# def read(path):
# 	with open(path, "r") as f:
# 		lines = [line.rstrip('\n') for line in f.readlines() if line[0] == 'e']
# 		edges = [(int(edge[1]),int(edge[2])) for edge in [line.split() for line in lines]]
	
# 	g = Graph(directed=False)
# 	g.add_edge_list(edges)
# 	g.remove_vertex(0)

# 	return g

def read(path):
	with open(path, "r") as f:
		lines = [line.rstrip('\n') for line in f.readlines() if line[0] == 'e']
		edges = [(int(edge[1]),int(edge[2])) for edge in [line.split() for line in lines]]
	
	g = Graph(directed=False)
	g.add_edge_list(edges)
	g.remove_vertex(0)

	n = len(list(g.vertices()))
	m = [[0 for _ in range(n)] for _ in range(n)]
	a = np.matrix(m)

	for v in g.vertices():
		for e in v.all_edges():
			s = int(e.source())
			t = int(e.target())
			a[s,t] = 1

	return a

def symmetrize(a):
    return a + a.T - np.diag(a.diagonal())

# @profile
def make_clique(g,n):
	for i,w in enumerate(n):
		for u in n[i+1:]:
			g[u,w] = 1
			g[w,u] = 1

# @profile
def triangulate(g, ordering):
	successors = {}
	deleted = set()

	for v in ordering:
		n = np.nonzero(g[v,:])[1]
		n = [w for w in n.tolist()[0] if w not in deleted]
		successors[v] = n

		make_clique(g,n)
		deleted.add(v)

	return successors

if __name__ == '__main__':
	g = read("graphs/myciel7.col")
	ordering = range(g.shape[0])
	from time import time
	start = time()
	succ = triangulate(g, ordering)
	print(time() - start)

	print(max(map(len,succ.values())))



