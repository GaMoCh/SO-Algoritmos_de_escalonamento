import java.util.Comparator;

public enum Algorithm {
  FCFS(SchedulerAlgorithm.fcfs, "First Come First Serve"),
  SJF(SchedulerAlgorithm.sjf, "Shortest Job First"),
  FCFS_PRIORITY(SchedulerAlgorithm.fcfs_priority, "Priority + First Come First Serve"),
  SJF_PRIORITY(SchedulerAlgorithm.sjf_priority, "Priority + Shortest Job First");

  private Comparator<ServiceProcess> comparator;
  private String value;

  Algorithm(Comparator<ServiceProcess> comparator, String value) {
    setComparator(comparator);
    setValue(value);
  }

  public Comparator<ServiceProcess> getComparator() {
    return comparator;
  }

  public String getValue() {
    return value;
  }

  private void setComparator(Comparator<ServiceProcess> comparator) {
    this.comparator = comparator;
  }

  private void setValue(String value) {
    this.value = value;
  }
}
