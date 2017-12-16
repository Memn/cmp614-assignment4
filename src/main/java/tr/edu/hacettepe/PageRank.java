package tr.edu.hacettepe;

import org.apache.log4j.Logger;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.Vector;
import tr.edu.hacettepe.structure.Graph;
import tr.edu.hacettepe.util.VectorPrintUtil;

import java.util.Arrays;

/**
 * @author Memn
 * @date 13.12.2017
 */

public class PageRank {
    private final static Logger LOGGER = Logger.getLogger(PageRank.class);


    private static final double LP_SPACE = 1.0;

    private final int cardinality;    // number of nodes
    private Matrix similarities;

    // optionals
    private int maxIterations = 100;
    private double EPSILON = 0.001;
    private double dumpingFactor = 0.85d;


    public PageRank(Graph graph) {
        this(graph.getAdjacency());
    }

    public PageRank(Matrix similarities) {
        this.similarities = similarities;
        this.cardinality = similarities.columnSize();
    }


    public Vector calculateRanks() {


        double[] r = new double[cardinality];
        Arrays.fill(r, 1.0 / cardinality);

        Vector ranks = new DenseVector(r);
        Vector newRanks;

        LOGGER.debug(String.format("P%d: %s", 0, VectorPrintUtil.toString(ranks)));

        int k = 1;
        while (true) {
            // next = (1-d)*e + d*A^T*first
            newRanks = iteration(ranks, dumpingFactor);
            LOGGER.debug(String.format("P%d: %s", k, VectorPrintUtil.toString(newRanks)));

            //test for convergence
            if (Math.abs(newRanks.minus(ranks).norm(LP_SPACE)) < EPSILON || ++k > maxIterations) {
                break;
            }

            ranks = new DenseVector(newRanks);
        }

        return newRanks;
    }

    private Vector iteration(Vector ranks, double d) {

        double dTerm = (1 - d) / cardinality;

        Vector newRanks = new DenseVector(cardinality);
        for (int i = 0; i < cardinality; i++) {

            double newRank = dTerm + (d * sum(ranks, i));
            newRanks.set(i, newRank);
        }

        return newRanks;
    }

    private double sum(Vector ranks, int i) {
        double sum = 0.0;

        for (Vector.Element in : similarities.viewColumn(i).nonZeroes()) {
            double outLinksFrom = similarities.viewRow(in.index()).zSum();
            if (outLinksFrom > 0.0) {
                sum += ranks.get(in.index()) / outLinksFrom;
            }
        }
        return sum;
    }

    public PageRank dumpingFactor(double d) {
        if (d > 0) {
            this.dumpingFactor = d;
        }
        return this;
    }

    public PageRank epsilon(double e) {
        if (e > 0) {
            this.EPSILON = e;
        }
        return this;
    }

    public PageRank maxIterations(int i) {
        if (i > 0) {
            this.maxIterations = i;
        }
        return this;
    }
}
