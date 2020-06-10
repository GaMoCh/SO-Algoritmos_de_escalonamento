import utils.Calc;

public class AlgorithmResult {
  private int clientsAmount;
  private double returnTime;
  private double responseTime;

  public AlgorithmResult(int clientsAmount, double returnTime, double responseTime) {
    setClientsAmount(clientsAmount);
    setReturnTime(returnTime);
    setResponseTime(responseTime);
  }

  public int getClientsAmount() {
    return clientsAmount;
  }

  public void setClientsAmount(int clientsAmount) {
    this.clientsAmount = clientsAmount;
  }

  public double getReturnTime() {
    return returnTime;
  }

  public double getResponseTime() {
    return responseTime;
  }

  private void setReturnTime(double returnTime) {
    this.returnTime = Calc.round(returnTime, 2);
  }

  private void setResponseTime(double responseTime) {
    this.responseTime = Calc.round(responseTime, 2);
  }
}
