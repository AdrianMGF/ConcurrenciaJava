
//Adrián María Gordillo Fernández
//45381691T


/**
 * La clase <code>PuenteA</code> implementa un monitor para coordinar el acceso de coches a un puente.
 * Permite que los coches de una sola dirección crucen el puente a la vez y asegura que no haya coches 
 * de direcciones opuestas en el puente simultáneamente.
 */
public class PuenteA {
    private boolean puenteOcupado = false;
    private boolean direccionActual; // true para norte, false para sur
    private int cochesEnDirecNorte = 0;
    private int cochesEnDirecSur = 0;

    /**
     * Solicita permiso para que un coche cruce el puente.
     *
     * @param direccionNorte <code>true</code> si el coche viene del norte, <code>false</code> si viene del sur.
     * @throws InterruptedException si el hilo es interrumpido mientras está esperando.
     */
    public synchronized void solicitarPaso(boolean direccionNorte) throws InterruptedException {
        while (puenteOcupado && direccionNorte != direccionActual) {
            wait();
        }
        puenteOcupado = true;
        if (direccionNorte) {
            cochesEnDirecNorte++;
        } else {
            cochesEnDirecSur++;
        }
        direccionActual = direccionNorte;
    }

    /**
     * Libera el puente después de que un coche ha cruzado.
     */
    public synchronized void liberarPuente() {
        if (direccionActual) {
            cochesEnDirecNorte--;
            if (cochesEnDirecNorte == 0) {
                puenteOcupado = false;
                notifyAll();
            }
        } else {
            cochesEnDirecSur--;
            if (cochesEnDirecSur == 0) {
                puenteOcupado = false;
                notifyAll();
            }
        }
    }
}
