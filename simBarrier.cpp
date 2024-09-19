//Adrián María Gordillo Fernández
//45381691T
#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>

/**
 * @class simBarrier
 * @brief Simula una barrera para sincronización de hebras.
 * 
 * Esta clase implementa una barrera que permite sincronizar un número específico de hebras.
 * Las hebras deben esperar en la barrera hasta que todas hayan llegado, momento en el cual
 * pueden continuar su ejecución. La barrera puede ser reiniciada para su reutilización.
 */
class simBarrier {
private:
    int total_threads;          
    int waiting_threads;        
    std::mutex mtx;             
    std::condition_variable cv;

public:
    /**
     * @brief Constructor que inicializa la barrera con el número de hebras.
     * @param n Número de hebras que deben llegar a la barrera.
     */
    simBarrier(int n) : total_threads(n), waiting_threads(0) {}

    /**
     * @brief Método para que las hebras esperen en la barrera.
     * 
     * Este método bloquea a una hebra hasta que todas las hebras necesarias
     * hayan llegado a la barrera. Cuando todas las hebras han llegado, todas
     * pueden continuar su ejecución.
     */
    void toWaitOnBarrier() {
        std::unique_lock<std::mutex> lock(mtx);
        waiting_threads++;
        
        if (waiting_threads == total_threads) {
            cv.notify_all();
        } else {
            cv.wait(lock, [this]() { return waiting_threads == total_threads; });
        }
    }

    /**
     * @brief Método para reiniciar la barrera.
     * 
     * Este método permite que la barrera sea reutilizada reseteando el número
     * de hebras que están esperando a cero.
     */
    void resetBarrier() {
        std::unique_lock<std::mutex> lock(mtx);
        waiting_threads = 0;
    }
};

std::mutex print_mtx;

/**
 * @brief Función que ejecutarán las hebras.
 * 
 * Esta función simula el comportamiento de una hebra que llega a una barrera,
 * espera a que otras hebras lleguen, y luego continúa su ejecución.
 * 
 * @param bar Referencia al objeto simBarrier utilizado para la sincronización.
 */
void threadFunction(simBarrier &bar) {
    {
        std::lock_guard<std::mutex> lock(print_mtx);
        std::cout << std::this_thread::get_id() << " llegando a barrera...\n";
    }

    bar.toWaitOnBarrier();

    {
        std::lock_guard<std::mutex> lock(print_mtx);
        std::cout << std::this_thread::get_id() << " saliendo de barrera...\n";
    }
}

/**
 * @brief Función principal del programa.
 * 
 * Esta función crea un objeto simBarrier, lanza dos grupos de tres hebras
 * cada uno, y las sincroniza utilizando la barrera. Después de que el primer
 * grupo de hebras pase la barrera, la barrera es reiniciada y se lanza el
 * segundo grupo de hebras.
 * 
 * @return int Código de salida del programa.
 */
int main() {
    std::cout << "main creando barrera para tres hebras...\n";
    simBarrier bar(3); 

    std::thread t1(threadFunction, std::ref(bar));
    std::thread t2(threadFunction, std::ref(bar));
    std::thread t3(threadFunction, std::ref(bar));

    t1.join();
    t2.join();
    t3.join();

    std::cout << "main reseteando barrera para tres nuevas hebras...\n";
    bar.resetBarrier(); 

    std::thread t4(threadFunction, std::ref(bar));
    std::thread t5(threadFunction, std::ref(bar));
    std::thread t6(threadFunction, std::ref(bar));

    t4.join();
    t5.join();
    t6.join();

    return 0;
}

