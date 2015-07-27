package tiq;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class DijkstraAlgorithmTest extends DijkstraAlgorithm {

	@Test
	public void testFindShortestPath() {
		/*
		 * Test case
		 *   Edges:
		 *     6,5,9
		 *     6,3,2
		 *     6,1,14
		 *     5,4,6
		 *     3,4,11
		 *     3,2,10
		 *     3,1,9
		 *     4,2,15
		 *     1,2,7
		 * 
		 *   Travel:
		 *     1 -> 5
		 *   
		 *   Output:
		 *     Path: 1,3,6,5
		 *     Cost: 20
		 */
		EdgeSet edges = new EdgeSet(
				ImmutableList.of(
						new Edge(6,5,9),
						new Edge(5,6,9),
						new Edge(6,3,2),
						new Edge(3,6,2),
						new Edge(6,1,14),
						new Edge(1,6,14),
						new Edge(5,4,6),
						new Edge(4,5,6),
						new Edge(3,4,11),
						new Edge(4,3,11),
						new Edge(3,2,10),
						new Edge(2,3,10),
						new Edge(3,1,9),
						new Edge(1,3,9),
						new Edge(4,2,15),
						new Edge(2,4,15),
						new Edge(1,2,7),
						new Edge(2,1,7)
						));
		
		Answer answer = findShortestPath(1, 5, edges);
		
		Answer expected = new Answer(
				ImmutableList.of(5,6,3,1),
				20);
		
		assertEquals(expected, answer);
	}

}
