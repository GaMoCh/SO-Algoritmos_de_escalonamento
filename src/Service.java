import java.lang.reflect.Array;

public class Service implements Comparable<Service> {
  private static int idCounter = 0;

  private String id;
  private String clientId;
  private boolean priority;
  private int expectedTime;
  private String arrivalTime;

  public Service(String id, String clientId, boolean priority, int expectedTime, String arrivalTime) {
    setId(id);
    setClientId(clientId);
    setPriority(priority);
    setExpectedTime(expectedTime);
    setArrivalTime(arrivalTime);
  }

  public Service(String clientId, boolean priority, int expectedTime, String arrivalTime) {
    setId(generateId());
    setClientId(clientId);
    setPriority(priority);
    setExpectedTime(expectedTime);
    setArrivalTime(arrivalTime);
  }

  private static String generateId() {
    return String.format("%4s", Integer.toString(++idCounter, 36)).replace(' ', '0').toUpperCase();
  }

  private static boolean idValueIsValid(String id) {
    return id.matches("^[A-Z0-9]{4}$");
  }

  private static boolean cpfFormatIsValid(String cpf) {
    return cpf.matches("^(?=\\d{11})(\\d)(?![$1]{10}).{10}$");
  }

//  private static boolean cpfDigitsAreValid(String cpf) {
//    String[] n = new String[]{cpf.substring(0, 10), cpf.substring(10)};
//    while (!n[1].isEmpty()) {
//      int k = 0;
//      for (int i = 0, j = n[0].length() + 1; i < n[0].length(); ++i, --j)
//        k += (n[0].charAt(i) - '0') * j;
//
//      if (((k %= 11) < 2 ? 0 : 11 - k) != n[1].charAt(0) - '0')
//        return false;
//
//      n[0] += n[1].charAt(0);
//      n[1] = n[1].substring(1);
//    }
//    return true;
//  }

  private static boolean arrivalTimeValueIsValid(String arrivalTime) {
    return Integer.parseInt(arrivalTime) >= 0
      && Integer.parseInt(arrivalTime) < 540;
  }

  public String getId() {
    return id;
  }

  private void setId(String id) {
    if (!idValueIsValid(id))
      throw new IllegalArgumentException("Service id value is invalid.");
    this.id = id;
  }

  public String getClientId() {
    return clientId;
  }

  private void setClientId(String clientId) {
    if (!cpfFormatIsValid(clientId))
      throw new IllegalArgumentException("Client id format is invalid.");
//    if (!cpfDigitsAreValid(clientId))
//      throw new IllegalArgumentException("Client id digits are invalid.");
    this.clientId = clientId;
  }

  public boolean isPriority() {
    return priority;
  }

  private void setPriority(boolean priority) {
    this.priority = priority;
  }

  public int getExpectedTime() {
    return expectedTime;
  }

  private void setExpectedTime(int expectedTime) {
    this.expectedTime = expectedTime;
  }

  public String getArrivalTime() {
    return arrivalTime;
  }

  private void setArrivalTime(String arrivalTime) {
    if (!arrivalTimeValueIsValid(arrivalTime))
      throw new IllegalArgumentException("Arrival time value is invalid.");
    this.arrivalTime = arrivalTime;
  }

  @Override
  public int compareTo(Service service) {
    return id.compareTo(service.id);
  }
}
