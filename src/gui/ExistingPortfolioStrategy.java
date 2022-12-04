package gui;

import static gui.ViewValidator.createJComboBox;
import static gui.ViewValidator.showErrorMessage;
import static gui.ViewValidator.validateNumberField;
import static gui.ViewValidator.validateTimelineField;

import gui.utility.ViewDocumentListener;
import generalcontroller.Features;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 * GUI Swing to support Creation of  Existing Portfolio strategies.
 */
public class ExistingPortfolioStrategy extends JPanel {

  private JPanel applicationJPanel;
  private JLabel portfolioJLabel;
  private JButton addStrategyJButton;
  private JComboBox<String> portfolioNameJComboBox;
  private JLabel fixedAmountJLabel;
  private JTextField fixedAmountJTextField;
  private JLabel dateOfInvestmentJLabel;
  private JTextField dateOfInvestmentJTextField;
  private JTable stockJTable;
  private JLabel fixedAmountMessageJLabel;
  private JLabel dateInvestmentJLabel;
  private JScrollPane stockTableJScrollPane;

  /**
   * Constructor to support update existing portfolio strategies class.
   *
   * @param features Features accessible to action listeners.
   */
  public ExistingPortfolioStrategy(Features features) {
    this.add(applicationJPanel);
    this.enableButtonEvents(features);
    this.enableValidations(features);
    this.checkTableCondition(features);
  }

  private void enableValidations(Features features) {
    fixedAmountJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateNumberField(fixedAmountJTextField,
            fixedAmountMessageJLabel, "amount"));

    dateOfInvestmentJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateTimelineField(dateOfInvestmentJTextField,
            dateInvestmentJLabel));
  }

  private void enableButtonEvents(Features features) {
    addStrategyJButton.addActionListener(event -> addStrategy(features));
    portfolioNameJComboBox.addActionListener(event -> checkTableCondition(features));
  }

  /**
   * Checks if the table is present or not, if no then it is generated.
   *
   * @param features Features accessible to action listeners.
   */
  public void checkTableCondition(Features features) {
    String selectedPortfolioName = (String) portfolioNameJComboBox.getSelectedItem();
    if (null != selectedPortfolioName && !selectedPortfolioName.isEmpty()) {
      generateStockTable(selectedPortfolioName, features);
    }
  }

  private void generateStockTable(String portfolioName, Features features) {
    stockJTable.setShowGrid(true);
    DefaultTableModel model = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 1;
      }
    };
    stockJTable.setModel(model);
    model.addColumn("Stocks");
    model.addColumn("Weightage %");

    List<String> sharesList = new ArrayList<>();
    sharesList.addAll(features.getShareTickerInPortfolio(portfolioName));
    for (String share : sharesList) {
      model.addRow(new Object[]{share, "0",});
    }
  }

  /**
   * List mutable portfolios into combo box.
   *
   * @param portfolios List of portfolios.
   */
  public void listAllMutablePortfolios(List<String> portfolios) {
    createJComboBox(portfolios, portfolioNameJComboBox);
  }

  private void addStrategy(Features features) {
    if (stockJTable.isEditing()) {
      TableCellEditor editor = stockJTable.getCellEditor();
      if (editor != null) {
        if (!editor.stopCellEditing()) {
          editor.cancelCellEditing();
        }
      }
    }
    ArrayList<Integer> weightageList = new ArrayList<>();
    ArrayList<String> companyTickerList = new ArrayList<>();
    for (int i = 0; i < stockJTable.getModel().getRowCount(); i++) {
      int numberOfStocks = -1;
      try {
        numberOfStocks = Integer.parseInt((String) stockJTable.getModel().getValueAt(i, 1));
        companyTickerList.add((String) stockJTable.getModel().getValueAt(i, 0));
      } catch (NumberFormatException invalidStock) {
        showErrorMessage(this, "Not a valid number at line " + (i + 1)
            + " under stock weightage percentage. Input only positive numeric values from "
            + "0-100.");
        return;
      }
      if (numberOfStocks < 0 || numberOfStocks > 100) {
        showErrorMessage(this, "Enter whole numbers from 0-100 at line " + (i + 1)
            + " under stock weightage percentage. ");
        return;
      } else {
        weightageList.add(numberOfStocks);
      }
    }
    int weightageSum = 0;
    for (int weightage : weightageList) {
      weightageSum += weightage;
    }
    if (weightageSum != 100) {
      showErrorMessage(this,
          "Sum of all weightage percentage should be 100. " + "Please reassign the weightage.");
    } else {
      String selectedPortfolioName = (String) portfolioNameJComboBox.getSelectedItem();
      if (null != selectedPortfolioName && !selectedPortfolioName.isEmpty() && validateNumberField(
          fixedAmountJTextField, fixedAmountMessageJLabel, "amount") && validateTimelineField(
          dateOfInvestmentJTextField, dateInvestmentJLabel)) {
        String investmentAmount = fixedAmountJTextField.getText();
        LocalDate date = LocalDate.parse(dateOfInvestmentJTextField.getText());
        boolean status = features.createStrategy(selectedPortfolioName, investmentAmount, date,
            date, companyTickerList, weightageList);
        if (!status) {
          showErrorMessage(this, "Something went wrong. Please try again!");
        } else {
          stockJTable.setModel(new DefaultTableModel());
          fixedAmountJTextField.setText("");
          dateOfInvestmentJTextField.setText("");
        }
      }
    }
  }
}
