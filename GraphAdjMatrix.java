/*
 * @author Rakesh Raju
 * 12/11/17
 * 
 */


import java.util.Stack;

public class GraphAdjMatrix implements Graph {

	public int size;
	public int[][] matrix;

	public GraphAdjMatrix(int size) {
		this.size = size;
		matrix = new int[size][size];
	}

	@Override
	public void addEdge(int v1, int v2) {
		matrix[v1][v2] = 1;

	}

	@Override
	public void topologicalSort() {
		Stack<Integer> s = new Stack<Integer>();
		int[] numbers = new int[size];
		int content = 0;

		for (int i = 0; i < size; i++) {
			numbers[i] = 0;
		}

		for (int i = 0; i < size; i++) {
			int[] neighbors = neighbors(i);
			for (int j = 0; j < neighbors.length; j++) {
				numbers[neighbors[j]]++;
			}
		}

		for (int i = 0; i < size; i++) {
			if (numbers[i] == 0) {
				s.push(i);
			}
		}

		if (s.empty()) {
			int i = 0;
			numbers[i] = 0;
			s.push(i);
		}

		while (!s.empty()) {
			int instance = s.pop();
			System.out.print(instance + " ");
			content++;

			int[] neighbors = neighbors(instance);
			for (int i = 0; i < neighbors.length; i++) {

				int dest = neighbors[i];
				numbers[dest]--;
				if (numbers[dest] == 0) {
					s.push(dest);
				}
			}

		}

		System.out.println("");
	}

	@Override
	public void addEdge(int v1, int v2, int weight) {
		matrix[v1][v2] = weight;
		matrix[v2][v1] = weight;

	}

	@Override
	public int getEdge(int v1, int v2) {
		return matrix[v1][v2];
	}

	@Override
	public int createSpanningTree() {
		int[] costs = prim();
		int total = 0;
		for (int i = 0; i < costs.length; i++) {
			total += costs[i];
		}

		return total;

	}

	public int[] prim() {
		int[] paths = new int[size];
		int[] costs = new int[size];
		boolean[] known = new boolean[size];

		for (int i = 0; i < size; i++) {
			costs[i] = Integer.MAX_VALUE; // in place of infinity
			known[i] = false;
		}

		paths[0] = -1;
		costs[0] = 0;

		for (int i = 0; i < size - 1; i++) {
			int k = minimumVertex(costs, known);
			known[k] = true;

			for (int j = 0; j < size; j++) {
				if (matrix[k][j] != 0 && known[j] == false && matrix[k][j] < costs[j]) {
					paths[j] = k;
					costs[j] = matrix[k][j];
				}
			}
		}
		return costs;
	}

	public int minimumVertex(int[] costs, boolean[] known) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < known.length; i++) {
			if (known[i] == false && costs[i] < min) {
				min = costs[i];
				index = i;
			}
		}
		return index;

	}

	public int[] neighbors(int vertex) {
		int[] neighborArray = new int[size];
		int index = 0;
		for (int i = 0; i < size; i++) {
			int vertex1 = matrix[vertex][i];
			if (vertex1 == 1 && i != vertex) {
				neighborArray[index] = i;
				index++;
			}
		}

		int[] neighbors = new int[index];
		for (int i = 0; i < index; i++) {
			neighbors[i] = neighborArray[i];
		}

		return neighbors;
	}
}
