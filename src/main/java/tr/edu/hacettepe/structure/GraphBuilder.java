package tr.edu.hacettepe.structure;

import org.apache.log4j.Logger;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.MatrixSlice;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Memn
 * @date 14.12.2017
 */

public class GraphBuilder {

    private final static Logger LOGGER = Logger.getLogger(GraphBuilder.class);

    private static final int DEFAULT_NUM_NEIGHBORS = 20;
    private int numNeighbors = -1;

    public Graph build(List<String> sentences, Matrix weights) {
        Graph graph = new Graph(sentences);
        LOGGER.debug("Graph is created, adding edges....");

        for (MatrixSlice row : weights) {
            nearest(row).forEach((i, w) -> graph.addEdge(row.index(), i, w));
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

    public GraphBuilder numNeighbors(int numNeighbors) {
        if (numNeighbors > 0) {
            this.numNeighbors = numNeighbors;
        }
        return this;
    }
}
