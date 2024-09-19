//Adrián María Gordillo Fernández
//45381691T


//javac -cp .;"%MPJ_HOME%\lib\mpj.jar" calIntegral.java
//mpjrun.bat -np 5 calIntegral




import mpi.*;

/**
 * Esta clase calcula una integral utilizando el método de la suma de Riemann,
 * distribuyendo el cálculo entre múltiples procesos utilizando MPI.
 */
public class calIntegral {

    /**
     * El método principal inicializa MPI, distribuye las tareas entre 
     * diferentes procesos y realiza la reducción final de los resultados.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     * @throws MPIException si ocurre un error de MPI.
     */
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();

        int size = MPI.COMM_WORLD.Size();

        double precision = 4e6;
        double result = 0.0;

        if (rank == 0) {
            System.out.println("Master difundiendo factor de precisión: " + (int)precision);
        }

        double[] precisionArray = new double[]{precision};
        MPI.COMM_WORLD.Bcast(precisionArray, 0, 1, MPI.DOUBLE, 0);

        precision = precisionArray[0];

        if (rank == 1) {
            double a = 0.0, b = 3.0;
            System.out.println("PRUEBAS SLAVE " + rank + ": precisión [" + a + ", " + b + "]");
            result = riemannSum(a, b, precision, 1);
            System.out.println("Soy el proceso " + rank + " y mi integración es: " + result);
            MPI.COMM_WORLD.Send(new double[]{result}, 0, 1, MPI.DOUBLE, 4, 0);
        }
        else if (rank == 2) {
            double a = 0.0, b = 8.0;
            System.out.println("PRUEBAS SLAVE " + rank + ": precisión [" + a + ", " + b + "]");
            result = riemannSum(a, b, precision, 2);
            System.out.println("Soy el proceso " + rank + " y mi integración es: " + result);
            MPI.COMM_WORLD.Send(new double[]{result}, 0, 1, MPI.DOUBLE, 4, 0);
        }
        else if (rank == 3) {
            double a = 0.0, b = 1.0;
            System.out.println("PRUEBAS SLAVE " + rank + ": precisión [" + a + ", " + b + "]");
            result = riemannSum(a, b, precision, 3);
            System.out.println("Soy el proceso " + rank + " y mi integración es: " + result);
            MPI.COMM_WORLD.Send(new double[]{result}, 0, 1, MPI.DOUBLE, 4, 0);
        }
        else if (rank == 4) {
            double[] partialResult = new double[3];
            for (int i = 1; i <= 3; i++) {
                MPI.COMM_WORLD.Recv(partialResult, i-1, 1, MPI.DOUBLE, i, 0);
            }
            result = partialResult[0] + partialResult[1] + partialResult[2];
            System.out.println("Soy el proceso " + rank + " y la reducción es: " + result);
            MPI.COMM_WORLD.Send(new double[]{result}, 0, 1, MPI.DOUBLE, 0, 0);
        }

        if (rank == 0) {
            double[] reducedResult = new double[1];
            MPI.COMM_WORLD.Recv(reducedResult, 0, 1, MPI.DOUBLE, 4, 0);
            double integral_x = riemannSum(0, 3, precision, 0);
            double finalResult = reducedResult[0] * integral_x;
            System.out.println("Resultado Final (master), I= " + finalResult);
        }

        MPI.Finalize();
    }

    /**
     * Calcula la suma de Riemann para una función específica dentro del intervalo [a, b].
     *
     * @param a El límite inferior del intervalo.
     * @param b El límite superior del intervalo.
     * @param precision El número de subintervalos para la suma de Riemann.
     * @param type El tipo de función a utilizar para la integración.
     *             0: función lineal f(x) = x.
     *             1: función f(x) = 8 / (1 + x^2).
     *             2: función f(x) = cos(x) + 3.
     *             3: función f(x) = exp(x).
     * @return El valor aproximado de la integral utilizando la suma de Riemann.
     */
    public static double riemannSum(double a, double b, double precision, int type) {
        double sum = 0.0;
        double delta = (b - a) / precision;

        for (int i = 0; i < precision; i++) {
            double xk = a + i * delta;
            double xk1 = a + (i + 1) * delta;
            double tk = xk; 
            double ftk = 0.0;

            if (type == 1) {
                ftk = 8 / (1 + tk * tk);         
            } else if (type == 2) {
                ftk = Math.cos(tk) + 3;            
            } else if (type == 3) {
                ftk = Math.exp(tk);                
            } else if (type == 0) {
                ftk = tk;                          
            }

            sum += ftk * (xk1 - xk);               
        }

        return sum;
    }
}
