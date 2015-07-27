package tiq;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

import com.google.common.base.Predicate;
import com.google.common.primitives.Doubles;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;


public class DijkstraAlgorithm {
    public static class Answer {
        private final Iterable<Object> path;
        private final double cost;
        
        private transient Iterable<Object> pathReversed = null;

        public Answer(Iterable<Object> path, double cost) {
            this.path = Iterables.unmodifiableIterable(path);
            this.cost = cost;
        }

        public Answer move(Edge edge) {
            Iterable<Object> newPath =
                Iterables.concat(ImmutableList.of(edge.getEnd()), this.path);
            return new Answer(newPath, this.cost + edge.getCost());
        }

        public Iterable<Object> getPath() {
        	if (pathReversed == null) {
        		pathReversed = ImmutableList.copyOf(path).reverse(); 
        	}
            return pathReversed;
        }

        public double getCost() {
            return cost;
        }

        public Object getLastNode() {
            return path.iterator().next();
        }
        
        @Override
        public String toString() {
        	return this.getClass().getSimpleName() + "(" + path.toString() + "," + cost + ")";
        }
        
        @Override
        public boolean equals(Object o) {
        	if (o instanceof Answer) {
        		return equals((Answer) o);
        	} else {
        		return false;
        	}
        }
        
        public boolean equals(Answer o) {
        	return Iterables.elementsEqual(this.path, o.path)
        			&& this.getCost() == o.getCost();
        }
    }

    public static class EdgeSet {
        private Iterable<Edge> edges;

        public EdgeSet(Iterable<Edge> edges) {
            this.edges = Iterables.unmodifiableIterable(edges);
        }

        public Iterable<Edge> findByBegin(Object begin) {
            Predicate<Edge> filter = new Predicate<Edge>() {
                    @Override
                    public boolean apply(Edge edge) {
                        return edge.getBegin().equals(begin);
                    }
                };
            return Iterables.filter(edges, filter);
        }
    }

    public static class Edge {
        private Object begin;
        private Object end;
        private double cost;

        public Edge(Object begin, Object end, double cost) {
            this.begin = begin;
            this.end = end;
            this.cost = cost;
        }

        public Object getBegin() {
            return begin;
        }

        public Object getEnd() {
            return end;
        }

        public double getCost() {
            return cost;
        }
    }

    public static Answer findShortestPath(Object start, Object end, EdgeSet edgeSet) {
        PriorityQueue<Answer> candidates =
            new PriorityQueue<>(2,
                                new Comparator<Answer>() {
                                    @Override
                                    public int compare(Answer o1, Answer o2) {
                                        return Doubles.compare(o1.getCost(), o2.getCost());
                                    }
                                });
        Set<Object> visited = new HashSet<>();

        Answer first = new Answer(ImmutableList.of(start), 0);
        candidates.offer(first);

        while (true) {
            Answer c = candidates.poll();
            if (c.getLastNode().equals(end)) {
                return c;
            }
            
            visited.add(c.getLastNode());

            for (Edge edge : edgeSet.findByBegin(c.getLastNode())) {
                if (visited.contains(edge.getEnd())) {
                    continue;
                }

                candidates.offer(c.move(edge));
            }
        }
    }
}
