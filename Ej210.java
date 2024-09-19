import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ej210 {
    private final int N1;  // Número total de copias del recurso r1
    private final int N2;  // Número total de copias del recurso r2

    private int disponibles_r1;  // Recursos r1 disponibles
    private int disponibles_r2;  // Recursos r2 disponibles

    private final Lock lock = new ReentrantLock();
    private final Condition espera_r1 = lock.newCondition();
    private final Condition espera_r2 = lock.newCondition();
    private final Condition espera_r1_y_r2 = lock.newCondition();

    private boolean ambosRecursosOcupados = false; // Para procesos que esperan ambos recursos

    public Ej210(int N1, int N2) {
        this.N1 = N1;
        this.N2 = N2;
        this.disponibles_r1 = N1;
        this.disponibles_r2 = N2;
    }

    public void solicitarRecursos(boolean pedir_r1, boolean pedir_r2) throws InterruptedException {
        lock.lock();
        try {
            if (pedir_r1) {
                while (disponibles_r1 == 0) {
                    espera_r1.await();
                }
                disponibles_r1--;
                if (ambosRecursosOcupados) {
                    espera_r1_y_r2.signal(); // Prioridad para procesos que necesitan ambos recursos
                }
            }
            if (pedir_r2) {
                while (disponibles_r2 == 0) {
                    espera_r2.await();
                }
                disponibles_r2--;
                if (ambosRecursosOcupados) {
                    espera_r1_y_r2.signal(); // Prioridad para procesos que necesitan ambos recursos
                }
            }
            if (pedir_r1 && pedir_r2) {
                ambosRecursosOcupados = true;
            }
        } finally {
            lock.unlock();
        }
    }

    public void liberarRecursos(boolean liberar_r1, boolean liberar_r2) {
        lock.lock();
        try {
            if (liberar_r1) {
                disponibles_r1++;
                espera_r1.signal(); // Despertar procesos esperando r1
            }
            if (liberar_r2) {
                disponibles_r2++;
                espera_r2.signal(); // Despertar procesos esperando r2
            }
            if (disponibles_r1 > 0 && disponibles_r2 > 0 && ambosRecursosOcupados) {
                espera_r1_y_r2.signal(); // Despertar procesos esperando ambos recursos
                ambosRecursosOcupados = false;
            }
        } finally {
            lock.unlock();
        }
    }
}
