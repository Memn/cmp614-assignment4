package tr.edu.hacettepe.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.SparseRowMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * @author Memn
 * @date 13.12.2017
 */

public class File2MatrixBuilder {

    private static final char SEPARATOR_CHAR = ',';

    /**
     * Assumes file is a matrix file. If can't read file throws {@link IOException}.
     * <p>
     * Asserts it is a square matrix with given size. else throws {@link AssertionError}.
     * <p>
     * Reads the matrix file line by line, splits with ',' character. Builds matrix with the respected value.
     *
     * @param sims path to similarities matrix.
     * @param size the size of matrix
     * @return the matrix.
     */
    public static Matrix build(Path sims, int size) throws IOException, AssertionError {
        SparseRowMatrix matrix = new SparseRowMatrix(size, size, false);

        List<String> rows = Files.readAllLines(sims);

        assert rows.size() != size :
                System.err.printf("Size not matching: expected: %d actual: %d \n", size, rows.size());


        int i = 0;
        for (String row : rows) {
            double[] data = row2Data(row);

            assert data.length == size :
                    System.err.printf("Size not matching on row %d: expected: %d actual: %d \n", i, size, data.length);

            matrix.set(i++, data);
        }


        return matrix;
    }

    private static double[] row2Data(String row) {
        return Arrays.stream(StringUtils.split(row, SEPARATOR_CHAR)).mapToDouble(Double::parseDouble).toArray();
    }
}
