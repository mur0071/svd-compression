import java.util.List;

/**
 * Created by jonathankeys on 3/31/17.
 *
 */
public class SVD {
    private double[][] S;
    private double[][] U;
    private double[][] V;

    public SVD () {

    }

    public Matrix compose() {
            Matrix matrixS = new Matrix(S);
            Matrix matrixU = new Matrix(U);
            Matrix matrixV = new Matrix(V);

            Matrix US = matrixU.multiplyMatrix(matrixS);
            Matrix composed = US.multiplyMatrix(matrixV);
            composed.convertToInt();

            return composed;
    }

    public void createS (double[] eigenValues, int rows, int columns) {
            for (int i = 0, eigenLength = eigenValues.length; i < eigenLength; i++) {
                    eigenValues[i] = Math.sqrt(eigenValues[i]);
            }

            Matrix matrixS = new Matrix();
            matrixS.createZeroIdentityFromVector(eigenValues, rows, columns);
            setS(matrixS.getMatrix());
    }

    public void createV (List<Matrix> eigenMatrices, int columns) {

            new Jama.EigenvalueDecomposition(new Jama.Matrix(eigenMatrices.get(0).getMatrix())).getRealEigenvalues();
            Matrix V = new Matrix(new Jama.Matrix(eigenMatrices.get(0).getMatrix()).eig().getV().getArray());
            double[][] e = V.getMatrix();
            int size = e.length;
            for (int i = 0; i < e.length; i++) {
                    double[] temp = e[i];
                    e[i] = e[size - 1];
                    e[size - 1] = temp;
            }



            V = new Matrix(e);
            V = V.transposeMatrix();
            setV(V.getMatrix());
    }

    public void  createU (Matrix matrix) {
            Matrix US = new Matrix(matrix.multiplyMatrix(new Matrix(getV())));

            Matrix U = new Matrix();
            U.createUnitMatrix(US);

            setU(U.getMatrix());
    }

    public double[][] getS () {
            return this.S;
    }

    public double[][] getU () {
            return this.U;
    }

    public double[][] getV () {
            return this.V;
    }

    private void setS(double[][] S) {
            this.S = S;
    }

    private void setU(double[][] U) {
            this.U = U;
    }

    private void setV(double[][] V) {
            this.V = V;
    }
}