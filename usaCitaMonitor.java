//Adrián María Gordillo Fernández
//45381691T


/**
 * Clase principal que ejecuta la simulación de sincronización de procesos.
 * Crea una instancia de CitaMonitor y dos procesos que interactúan con él.
 */
public class usaCitaMonitor {
    public static void main(String[] args) {
        // Crear una instancia del monitor de cita
        CitaMonitor citaMonitor = new CitaMonitor();

        // Crear y lanzar dos procesos
        Thread p1 = new Thread(() -> {
            try {
                citaMonitor.prepararProceso1();
                citaMonitor.esperarCita();
                System.out.println("Proceso 1 ha sido sincronizado.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread p2 = new Thread(() -> {
            try {
                citaMonitor.prepararProceso2();
                citaMonitor.esperarCita();
                System.out.println("Proceso 2 ha sido sincronizado.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Iniciar los hilos
        p1.start();
        p2.start();
    }
}

