import java.util.List;
import java.util.concurrent.Semaphore;

public class Consumer extends Thread {
  private List<ServiceProcess> serviceProcesses;
  private Semaphore lock;
  private Semaphore full;
  private String name;
  private int servicesAmount;

  public Consumer(List<ServiceProcess> serviceProcesses, Semaphore lock, Semaphore full, String name, int servicesAmount) {
    this.serviceProcesses = serviceProcesses;
    this.lock = lock;
    this.full = full;
    this.name = name;
    this.servicesAmount = servicesAmount;
  }

  @Override
  public void run() {
    int time = 0;

    while (servicesAmount > 0) {
      try {
        full.acquire();
        sleep(25);
        lock.acquire();
        sleep(25);

        ServiceProcess serviceProcess = serviceProcesses.remove(0);

        if (time + serviceProcess.getService().getExpectedTime() < 540) {
          time = Math.max(time, serviceProcess.getArrivalTimestamp());
          serviceProcess.setBeginTime(time);

          time += serviceProcess.getService().getExpectedTime();
          serviceProcess.setEndTime(time);

          StringBuilder message = new StringBuilder();

          message.append("\t" + name)
            .append(" atendeu o cliente com a senha ")
            .append(serviceProcess.getService().getId())
            .append(" | Ele chegou em ")
            .append(timestampToString(serviceProcess.getArrivalTimestamp(), 480))
            .append(", seu atendimentento foi iniciado em ")
            .append(timestampToString(serviceProcess.getBeginTime(), 480))
            .append(" e terminado em ")
            .append(timestampToString(serviceProcess.getEndTime(), 480));

          System.out.println(message);

          serviceProcesses.add(serviceProcess);
        }
        --servicesAmount;

        lock.release();
      } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
      }
    }
  }

  private String timestampToString(int timestamp, int timeOffset) {
    final int hours = (timestamp + timeOffset) / 60;
    final int minutes = (timestamp + timeOffset) % 60;
    return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
  }
}
