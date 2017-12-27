package tr.edu.hacettepe;

import org.apache.log4j.Logger;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.MatrixSlice;
import org.apache.mahout.math.Vector;
import tr.edu.hacettepe.structure.Graph;
import tr.edu.hacettepe.util.VectorPrintUtil;

/**
 * @author Memn
 * @date 13.12.2017
 */

public class PageRank {
    private final static Logger LOGGER = Logger.getLogger(PageRank.class);


    private static final double LP_SPACE = 1.0;
    private static final int MAX_ITERATIONS = 1000;
    private static final double EPSILON = 0.0001;

    private final Vector eigen;
    private Vector eTimesOneMinusDumpFactor;
    private Matrix transposedTimesDumpFactor;
    // optionals
    private String docset;

    public PageRank(Graph graph, double dumpingFactor) {
        this(graph.getAdjacency(), dumpingFactor, graph.getDocset());
    }

    PageRank(Matrix similarities, double dumpingFactor, String docset) {
        // make stochastic
        for (MatrixSlice row : similarities) {
            // zero row
            if (!row.nonZeroes().iterator().hasNext()) {
                row.assign(1.0);
            }
            row.assign(row.normalize(1.0));
        }

        eigen = new DenseVector(similarities.columnSize());
        double e = 1.0 / similarities.columnSize();
        eigen.assign(e);
        this.docset = docset;

        this.transposedTimesDumpFactor = similarities.transpose().times(dumpingFactor);
        this.eTimesOneMinusDumpFactor = eigen.times(1.0 - dumpingFactor);
    }

    public Vector calculateRanks() {

        Vector ranks = new DenseVector(eigen);
        Vector newRanks;

        LOGGER.debug(String.format("P%d: %s", 0, VectorPrintUtil.toString(ranks)));
        double prevNorm = Double.MAX_VALUE;
        int k = 1;
        while (true) {
            // next = (1-d)*e + d*A^T*first
            newRanks = iteration2(ranks);
            LOGGER.debug(String.format("P%d: %s", k, VectorPrintUtil.toString(newRanks)));

            double norm = newRanks.minus(ranks).norm(LP_SPACE);

            if (norm > prevNorm) {
                LOGGER.error("NORM is increased...");
            }

            //test for convergence
            if (Math.abs(norm) < EPSILON || ++k > MAX_ITERATIONS) {
                break;
            }
            prevNorm = norm;
            ranks.assign(newRanks);
        }
        LOGGER.info(String.format("Ranks for %s after iteration %d: %s", docset, k, VectorPrintUtil.toString(newRanks, 5)));
        return newRanks;
    }

    private Vector iteration2(Vector ranks) {
        return eTimesOneMinusDumpFactor.plus(transposedTimesDumpFactor.times(ranks));
    }

}
