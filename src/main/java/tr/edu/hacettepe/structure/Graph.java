package tr.edu.hacettepe.structure;

import org.apache.log4j.Logger;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.SparseRowMatrix;

import java.util.List;

/**
 * @author Memn
 * @date 14.12.2017
 */

public class Graph {

    private final static Logger LOGGER = Logger.getLogger(Graph.class);

    // int values are their id
    private final String[] nodes;
    private Matrix adjacency;

    Graph(List<String> sentences) {

        adjacency = new SparseRowMatrix(sentences.size(), sentences.size());
        nodes = new String[sentences.size()];

        int id = 0;
        for (String t : sentences) {
            addNode(t, id++);
        }

    }

    private void addNode(String node, int id) {
        LOGGER.debug(String.format("Adding node with id: %d", id));
        nodes[id] = node;
    }

    public void addEdge(int source, int target, double weight) {
        LOGGER.debug(String.format("Adding edge between %d - %d with weight %.3f", source, target, weight));
        adjacency.set(source, target, weight);
    }

    public Matrix getAdjacency() {
        return adjacency;
    }

}
