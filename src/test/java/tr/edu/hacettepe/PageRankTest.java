package tr.edu.hacettepe;

import org.apache.log4j.Logger;
import org.apache.mahout.math.SparseRowMatrix;
import org.apache.mahout.math.Vector;
import org.junit.Test;
import tr.edu.hacettepe.util.VectorPrintUtil;

/**
 * @author Memn
 * @date 15.12.2017
 */

public class PageRankTest {
    private final static Logger LOGGER = Logger.getLogger(PageRankTest.class);

    @Test
    public void testCalculateRanks() throws Exception {
        SparseRowMatrix matrix = new SparseRowMatrix(11, 11);
        matrix.set(1, 2, 1d);
        matrix.set(2, 1, 1d);

        matrix.set(3, 0, 1d);
        matrix.set(3, 1, 1d);

        matrix.set(4, 1, 1d);
        matrix.set(4, 3, 1d);
        matrix.set(4, 5, 1d);

        matrix.set(5, 1, 1d);
        matrix.set(5, 4, 1d);

        matrix.set(6, 4, 1d);

        matrix.set(7, 4, 1d);

        matrix.set(8, 1, 1d);
        matrix.set(8, 4, 1d);

        matrix.set(9, 1, 1d);
        matrix.set(9, 4, 1d);

        matrix.set(10, 1, 1d);
        matrix.set(10, 4, 1d);

        Vector v = new PageRank(matrix).dumpingFactor(0.85).calculateRanks();
        LOGGER.info("Final:" + VectorPrintUtil.toString(v));

    }

}