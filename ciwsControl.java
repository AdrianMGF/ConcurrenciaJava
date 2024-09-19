
//Adrián María Gordillo Fernández
//45381691T



/**
 * La clase ciwsControl gestiona el acceso a dos sistemas de defensa disponibles.
 * Utiliza métodos synchronized para coordinar el acceso concurrente a los sistemas de defensa,
 * actuando como un monitor.
 */
public class ciwsControl {
    private final boolean[] sistemasDisponibles = {true, true};  

    /**
     * Método sincronizado para que una estación adquiera un sistema de defensa.
     * Si ambos sistemas de defensa están ocupados, el hilo llamante esperará hasta que uno se libere.
     *
     * @return El índice del sistema de defensa adquirido (0 o 1).
     * @throws InterruptedException Si el hilo es interrumpido mientras espera.
     */
    public synchronized int adquirirSistema() throws InterruptedException {
        while (!sistemasDisponibles[0] && !sistemasDisponibles[1]) {
            wait();
        }

        int sistemaAsignado = -1;
        for (int i = 0; i < sistemasDisponibles.length; i++) {
            if (sistemasDisponibles[i]) {
                sistemasDisponibles[i] = false;
                sistemaAsignado = i;
                System.out.println("Estación " + Thread.currentThread().getName() + " ha adquirido el sistema de defensa " + (i + 1));
                break;
            }
        }
        return sistemaAsignado;
    }

    /**
     * Método sincronizado para liberar un sistema de defensa.
     * Esto marca el sistema como disponible y notifica a cualquier hilo que esté esperando.
     *
     * @param n El índice del sistema de defensa a liberar (0 o 1).
     */
    public synchronized void liberarSistema(int n) {
        sistemasDisponibles[n] = true;
        System.out.println("Estación " + Thread.currentThread().getName() + " ha liberado el sistema de defensa " + (n + 1));
        notifyAll();  
    }
}
