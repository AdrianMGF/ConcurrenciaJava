//Adrián María Gordillo Fernández
//45381691T



/**
 * La clase {@code PetersonN} implementa el algoritmo de Peterson para la sincronización
 * entre múltiples procesos (o hilos). Esta implementación es una extensión del algoritmo de
 * Peterson para manejar más de dos procesos, proporcionando exclusión mutua en una sección crítica.
 */
public class PetersonN implements Runnable {
    private static final int NUM_PROCESSES = 5;
    
    private static volatile boolean[] flag = new boolean[NUM_PROCESSES];
    
    private static volatile int[] turn = new int[NUM_PROCESSES];
    
    private final int id;

    /**
     * Constructor de la clase {@code PetersonN}.
     *
     * @param id El identificador del proceso (o hilo) que debe ser único entre 0 y {@code NUM_PROCESSES - 1}.
     */
    public PetersonN(int id) {
        this.id = id;
    }

    /**
     * El método que se ejecuta en el hilo. Cada proceso intenta acceder a la sección crítica
     * utilizando el algoritmo de Peterson para asegurar la exclusión mutua. El proceso realiza
     * varias iteraciones, intentando acceder a la sección crítica en cada una.
     */
    @Override
    public void run() {
        for (int j = 0; j < 10; j++) { 
            for (int i = 0; i < NUM_PROCESSES; i++) {
                if (i != id) {
                    flag[id] = true; 
                    turn[id] = i;    
                    while (flag[i] && turn[i] == id) {
                       
                    }
                }
            }

           
            System.out.println("Proceso " + id + " en la sección crítica.");

            
            flag[id] = false;
        }
    }
}
