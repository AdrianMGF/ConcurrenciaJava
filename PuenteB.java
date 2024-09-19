
//Adrián María Gordillo Fernández
//45381691T


/**
 * La clase <code>PuenteB</code> implementa un monitor para coordinar el acceso de coches a un puente.
 * Mejora <code>PuenteA</code> al cambiar la dirección del tráfico cada vez que se han cruzado 10 coches 
 * en una dirección y hay coches esperando en la dirección opuesta.
 */
public class PuenteB {
    private boolean puenteOcupado = false;
    private boolean direccionActual; // true para norte, false para sur
    private int cochesEnDirecNorte = 0;
    private int cochesEnDirecSur = 0;
    private int cochesCruzadosEnDirecActual = 0;
    private final int LIMITE_COHES = 10;

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
        cochesCruzadosEnDirecActual++;
        
        if (cochesCruzadosEnDirecActual >= LIMITE_COHES && (cochesEnDirecNorte > 0 || cochesEnDirecSur > 0)) {
            cambiarDireccion();
        }
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

    /**
     * Cambia la dirección del tráfico en el puente y reinicia el contador de coches cruzados.
     */
    private void cambiarDireccion() {
        direccionActual = !direccionActual;
        cochesCruzadosEnDirecActual = 0;
        notifyAll();
    }
}
