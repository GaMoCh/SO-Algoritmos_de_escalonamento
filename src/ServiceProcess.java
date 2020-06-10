public class ServiceProcess {
  private Service service;
  private Integer beginTime;
  private Integer endTime;
  private int remainingTime;
  private int arrivalTimestamp;

  public ServiceProcess(Service service) {
    setService(service);
    setBeginTime(null);
    setEndTime(null);
    setRemainingTime(service.getExpectedTime());
    setArrivalTimestamp(toTimestamp(service.getArrivalTime()));
  }

  public Service getService() {
    return service;
  }

  private void setService(Service service) {
    this.service = service;
  }

  public Integer getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Integer beginTime) {
    this.beginTime = beginTime;
  }

  public Integer getEndTime() {
    return endTime;
  }

  public void setEndTime(Integer endTime) {
    this.endTime = endTime;
  }

  public int getRemainingTime() {
    return remainingTime;
  }

  public void setRemainingTime(int remainingTime) {
    this.remainingTime = remainingTime;
  }

  public int getArrivalTimestamp() {
    return arrivalTimestamp;
  }

  private void setArrivalTimestamp(int arrivalTimestamp) {
    this.arrivalTimestamp = arrivalTimestamp;
  }

  private static int toTimestamp(String time) {
    return Integer.parseInt(time);
  }

  @Override
  public String toString() {
    return this.getService().getId();
  }
}
