import java.util.List;
import java.util.concurrent.Semaphore;

public class Producer extends Thread {
  private List<ServiceProcess> serviceProcesses;
  private List<ServiceProcess> availableServiceProcesses;
  private Semaphore lock;
  private Semaphore full;

  public Producer(List<ServiceProcess> serviceProcesses, List<ServiceProcess> availableserviceProcesses,
                  Semaphore lock, Semaphore full) {
    this.serviceProcesses = serviceProcesses;
    this.availableServiceProcesses = availableserviceProcesses;
    this.lock = lock;
    this.full = full;
  }

  @Override
  public void run() {
    final int serviceProcessesAmount = serviceProcesses.size();
    for (int i = 0; i < serviceProcessesAmount; ++i) {
      try {
        lock.acquire();
        availableServiceProcesses.add(serviceProcesses.remove(0));
        full.release();
        lock.release();

      } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
      }
    }
  }
}
