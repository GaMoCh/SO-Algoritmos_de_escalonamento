import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
  public static Set<Service> getServicesFromFile(File file) {
    Set<Service> services = new TreeSet<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      for (String line; (line = br.readLine()) != null; ) {
        if (!line.isEmpty()) {
          String[] d = line.split(";");
          services.add(new Service(d[0], d[1], d[2].equals("1"), Integer.parseInt(d[3]), d[4]));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return services;
  }

  public static void main(String[] args) {
    final List<Algorithm> selectedAlgorithms = new ArrayList<>();
    final List<Image> iconImages = new ArrayList<>(Arrays.asList(Objects.requireNonNull(
      new File("src/assets/images/icons").listFiles())))
      .stream().map(file -> Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath()))
      .collect(Collectors.toList());

    JFrame frame = new JFrame("Scheduler Algorithms");
    GridBagConstraints gbc = new GridBagConstraints();

    frame.setLayout(new GridBagLayout());
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;

    JButton runButton = new JButton("Run");
    runButton.setEnabled(false);

    DefaultTableModel resultTableModel = new DefaultTableModel();
    JTable resultTable = new JTable(resultTableModel);

    List<String> columnNames = new ArrayList<>(Arrays.asList("Algorithm", "Clients", "Return T.", "Response T."));
    columnNames.forEach(resultTableModel::addColumn);

    JCheckBox isMultiThreadCheckbox = new JCheckBox("With Multi Thread");

    JButton inputFileButton = new JButton("Select file");
    inputFileButton.addActionListener(inputFileButtonEvent -> {
      File workingDirectory = new File(System.getProperty("user.dir"));
      JFileChooser fileChooser = new JFileChooser(workingDirectory);
      gbc.insets = new Insets(10, 0, 0, 0);

      if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        File inputFile = fileChooser.getSelectedFile();
        runButton.setEnabled(true);

        Set<Service> services = getServicesFromFile(inputFile);
        runButton.addActionListener(runButtonEvent -> {
          while (resultTableModel.getRowCount() > 0)
            resultTableModel.removeRow(0);

          resultTableModel.addRow(columnNames.toArray());
          for (Algorithm algorithm : selectedAlgorithms) {
            AlgorithmResult res = Scheduler.getAlgorithmResult(new ArrayList<>(services), algorithm, isMultiThreadCheckbox.isSelected());
            resultTableModel.addRow(new Object[]{algorithm.name(), res.getClientsAmount(), res.getReturnTime(), res.getResponseTime()});
          }

          frame.add(resultTable, gbc);
          frame.pack();
        });
      }
    });

    frame.add(inputFileButton, gbc);
    frame.add(isMultiThreadCheckbox, gbc);
    frame.add(new JSeparator(), gbc);

    for (Algorithm algorithm : Algorithm.values()) {
      JCheckBox algorithmCheckBox = new JCheckBox(algorithm.getValue());
      frame.add(algorithmCheckBox, gbc);
      algorithmCheckBox.addActionListener(e -> {
        if (algorithmCheckBox.isSelected())
          selectedAlgorithms.add(algorithm);
        else
          selectedAlgorithms.remove(algorithm);
      });
    }
    frame.add(runButton, gbc);

    frame.pack();
    frame.setIconImages(iconImages);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
