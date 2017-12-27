package tr.edu.hacettepe.util;

import com.google.common.primitives.Ints;
import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashSet;

/**
 * @author Memn
 * @date 27.12.2017
 */

public class MMRTest {
    @Test
    public void marginalOrder() throws Exception {
        double[] r = {0.91, 0.90, 0.50, 0.06, 0.63};
        Vector ranks = new DenseVector(r);
        double[][] s = {
                {1.0, 0.11, 0.23, 0.76, 0.25},
                {0.11, 1.0, 0.29, 0.57, 0.51},
                {0.23, 0.29, 1.0, 0.02, 0.20},
                {0.76, 0.57, 0.02, 1.0, 0.33},
                {0.25, 0.51, 0.20, 0.33, 1.0}
        };
        Matrix similarities = new DenseMatrix(s);
        LinkedHashSet<Integer> i = new MMR(ranks, similarities).marginalOrder();
        int[] expected = {0, 1, 2, 4, 3};
        Assert.assertArrayEquals(expected, Ints.toArray(i));


    }

}