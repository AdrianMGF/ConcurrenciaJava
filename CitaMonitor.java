//Adrián María Gordillo Fernández
//45381691T



/**
 * Clase que implementa un monitor para la sincronización de dos procesos.
 * Proporciona métodos para preparar los procesos y esperar a que ambos estén listos.
 */
public class CitaMonitor {
    private boolean proceso1Preparado = false;
    private boolean proceso2Preparado = false;

    /**
     * Marca el primer proceso como preparado y notifica a los hilos
     * si el segundo proceso también está preparado.
     * 
     * @throws InterruptedException si el hilo espera es interrumpido.
     */
    public synchronized void prepararProceso1() throws InterruptedException {
        proceso1Preparado = true;
        System.out.println("Proceso 1 está preparado.");
        if (proceso2Preparado) {
            notifyAll();
        }
    }

    /**
     * Marca el segundo proceso como preparado y notifica a los hilos
     * si el primer proceso también está preparado.
     * 
     * @throws InterruptedException si el hilo espera es interrumpido.
     */
    public synchronized void prepararProceso2() throws InterruptedException {
        proceso2Preparado = true;
        System.out.println("Proceso 2 está preparado.");
        if (proceso1Preparado) {
            notifyAll();
        }
    }

    /**
     * Hace que el hilo espere hasta que ambos procesos estén preparados.
     * 
     * @throws InterruptedException si el hilo espera es interrumpido.
     */
    public synchronized void esperarCita() throws InterruptedException {
        while (!(proceso1Preparado && proceso2Preparado)) {
            wait();
        }
        System.out.println("Ambos procesos están sincronizados.");
    }

    /**
     * Reinicia el estado de preparación de los procesos.
     */
    public synchronized void reiniciar() {
        proceso1Preparado = false;
        proceso2Preparado = false;
    }
}

