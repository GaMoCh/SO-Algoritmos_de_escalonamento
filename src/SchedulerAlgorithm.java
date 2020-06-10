import java.util.Comparator;

public class SchedulerAlgorithm {
  private SchedulerAlgorithm() {
  }

  public static Comparator<ServiceProcess> fcfs = Comparator.comparingInt(ServiceProcess::getArrivalTimestamp);

  public static Comparator<ServiceProcess> sjf = Comparator.comparingInt(ServiceProcess::getArrivalTimestamp)
    .thenComparingInt((s -> s.getService().getExpectedTime()));

  public static Comparator<ServiceProcess> fcfs_priority = Comparator.comparingInt(ServiceProcess::getArrivalTimestamp)
    .thenComparingInt(s1 -> reversedBoolToInt(s1.getService().isPriority()));

  public static Comparator<ServiceProcess> sjf_priority = Comparator.comparingInt(ServiceProcess::getArrivalTimestamp)
    .thenComparingInt((s -> s.getService().getExpectedTime()))
    .thenComparingInt(s1 -> reversedBoolToInt(s1.getService().isPriority()));

  private static int reversedBoolToInt(boolean bool) {
    return bool ? 0 : 1;
  }
}
