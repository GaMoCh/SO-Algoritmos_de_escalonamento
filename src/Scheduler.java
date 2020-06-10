import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Scheduler {
  public static AlgorithmResult getAlgorithmResult(List<Service> services, Algorithm algorithm, boolean isMultiThread) {
    List<ServiceProcess> availableServiceProcesses = new ArrayList<>();
    List<ServiceProcess> serviceProcesses = services.stream()
      .map(ServiceProcess::new)
      .sorted(algorithm.getComparator())
      .collect(Collectors.toList());

    final int size = serviceProcesses.size();
    final int half = size / 2;
    final boolean isEven = size % 2 == 0;

    Semaphore lock = new Semaphore(1);
    Semaphore full = new Semaphore(0);

    Producer producer = new Producer(serviceProcesses, availableServiceProcesses, lock, full);
    Consumer consumerA = new Consumer(availableServiceProcesses, lock, full, "John", !isMultiThread ? size : isEven ? half : half + 1);
    Consumer consumerB = new Consumer(availableServiceProcesses, lock, full, "Jane", half);

    System.out.printf("\n%s\n", algorithm.getValue());

    try {
      producer.start();
      consumerA.start();

      if (isMultiThread) consumerB.start();

      producer.join();
      consumerA.join();

      if (isMultiThread) consumerB.join();
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    }

    double returnTime = availableServiceProcesses.stream()
      .mapToInt(s -> s.getEndTime() - s.getArrivalTimestamp()).average().orElse(0);
    double responseTime = availableServiceProcesses.stream()
      .mapToInt(s -> s.getBeginTime() - s.getArrivalTimestamp()).average().orElse(0);

    return new AlgorithmResult(availableServiceProcesses.size(), returnTime, responseTime);
  }
}
