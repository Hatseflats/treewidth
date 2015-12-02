from graphimporter import GraphImporter
from localsearch import triangulate

if __name__ == '__main__':
	importer = GraphImporter()
	graph = importer.read("graphs/myciel3.col")
	ordering = [1,2,3,4,5,6,7,8,9,10,11]
	a = triangulate(graph,ordering)
