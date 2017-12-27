package tr.edu.hacettepe.structure;

import org.apache.log4j.Logger;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.MatrixSlice;
import org.apache.mahout.math.SparseRowMatrix;
import tr.edu.hacettepe.util.File2MatrixBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Memn
 * @date 14.12.2017
 */

public class Graph {

    private final static Logger LOGGER = Logger.getLogger(Graph.class);

    private Matrix adjacency;
    private final String docset;

    private Graph(int size, String docset) {

        adjacency = new SparseRowMatrix(size, size);

        this.docset = docset;
    }

    void addEdge(int source, int target, double weight) {
        LOGGER.debug(String.format("Adding edge between %d - %d with weight %.3f", source, target, weight));
        adjacency.set(source, target, weight);
    }

    public Matrix getAdjacency() {
        return adjacency;
    }

    public String getDocset() {
        return docset;
    }

    public static class Builder {

        private final static Logger LOGGER = Logger.getLogger(Builder.class);

        private static final int DEFAULT_NUM_NEIGHBORS = 20;
        private int numNeighbors = -1;

        public Graph build(Path sims, int size) throws IOException {

            Matrix weights = File2MatrixBuilder.build(sims, size);
            Graph graph = new Graph(size, sims.getParent().toFile().getName());
            LOGGER.debug("Graph is created, adding edges....");

            for (MatrixSlice row : weights) {
                Map<Integer, Double> nearest = nearest(row);
                nearest.forEach((i, w) -> graph.addEdge(row.index(), i, w));
            }

            return graph;
        }

        private Map<Integer, Double> nearest(MatrixSlice row) {
            Map<Integer, Double> map = new HashMap<>(row.size());
            row.all().forEach(element -> map.put(element.index(), element.get()));
            return map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(getNumNeighbors())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        }

        private int getNumNeighbors() {
            return numNeighbors == -1 ? DEFAULT_NUM_NEIGHBORS : numNeighbors;
        }

        public Builder numNeighbors(int numNeighbors) {
            if (numNeighbors > 0) {
                this.numNeighbors = numNeighbors;
            }
            return this;
        }
    }
}
