package gui;

import static gui.ViewValidator.showErrorMessage;
import static gui.ViewValidator.validateCreatePortfolioField;
import static gui.ViewValidator.validateNumberField;
import static gui.ViewValidator.validateTimelineField;

import gui.utility.ViewDocumentListener;
import generalcontroller.Features;
import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 * GUI Swing to support Creation of Strategies.
 */
public class CreateStrategyPortfolio extends JPanel {

  private JPanel applicationJPanel;
  private JLabel portfolioJLabel;
  private JButton addStrategyJButton;
  private JLabel fixedAmountJLabel;
  private JTextField fixedAmountJTextField;
  private JLabel dateOfInvestmentJLabel;
  private JTextField dateOfInvestmentJTextField;
  private JTable stockJTable;
  private JLabel fixedAmountMessageJLabel;
  private JLabel dateInvestmentMessageJLabel;
  private JScrollPane stockTableJScrollPane;
  private JTextField portfolioNameJTextField;
  private JLabel portfolioNameMessageJLabel;
  private JTextField endDateOfInvestmentJTextField;
  private JLabel endDateInvestmentMessageJLabel;
  private JLabel endDateOfInvestmentJLabel;
  private JLabel frequencyJLabel;
  private JTextField frequencyJTextField;
  private JLabel frequencyMessageJLabel;
  private JTextField countLabelJTextField;
  private JLabel countJLabel;
  private JLabel countMessageJLabel;

  /**
   * Constructor to support create strategy class.
   *
   * @param features Features accessible to action listeners.
   */
  public CreateStrategyPortfolio(Features features) {
    this.add(applicationJPanel);
    this.enableButtonEvents(features);
    this.enableValidations(features);
  }

  private void enableValidations(Features features) {
    portfolioNameJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateCreatePortfolioField(features, portfolioNameJTextField,
            portfolioNameMessageJLabel));

    fixedAmountJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateNumberField(fixedAmountJTextField,
            fixedAmountMessageJLabel, "amount"));

    dateOfInvestmentJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateTimelineField(dateOfInvestmentJTextField,
            dateInvestmentMessageJLabel));

    endDateOfInvestmentJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateTimelineField(endDateOfInvestmentJTextField,
            endDateInvestmentMessageJLabel));

    frequencyJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateNumberField(frequencyJTextField, frequencyMessageJLabel,
            "days"));

    countLabelJTextField.getDocument().addDocumentListener((ViewDocumentListener) e -> {
      boolean flag = validateNumberField(countLabelJTextField, countMessageJLabel,
          "count of companies");
      if (flag) {
        int count = 0;
        try {
          count = Integer.parseInt(countLabelJTextField.getText());
        } catch (NumberFormatException numberFormatException) {
          count = 0;
        }
        countLabelJTextField.setEnabled(false);
        this.generateStockTable(count, features);
      }
    });
  }

  private void enableButtonEvents(Features features) {
    addStrategyJButton.addActionListener(event -> addPortfolioStrategy(features));
  }

  private void generateStockTable(int count, Features features) {
    stockJTable.setShowGrid(true);
    DefaultTableModel model = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return true;
      }
    };
    stockJTable.setModel(model);
    model.addColumn("Stocks");
    model.addColumn("Weightage %");
    for (int i = 0; i < count; i++) {
      model.addRow(new Object[]{"", "0",});
    }
    countLabelJTextField.setEnabled(true);
  }

  private void addPortfolioStrategy(Features features) {
    Set<String> sharesSet = new HashSet<>();
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
      String companyTickerData = (String) stockJTable.getModel().getValueAt(i, 0);
      sharesSet.add(companyTickerData);
    }
    if (sharesSet.size() != stockJTable.getModel().getRowCount()) {
      showErrorMessage(this, "Remove duplicate values from stock column.");
      return;
    }
    for (int i = 0; i < stockJTable.getModel().getRowCount(); i++) {
      String companyTickerData = (String) stockJTable.getModel().getValueAt(i, 0);
      if (!features.checkTickerExists(companyTickerData.toUpperCase())) {
        showErrorMessage(this, "Not a valid ticker at line " + (i + 1) + " under stocks column.");
        return;
      }
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
      String selectedPortfolioName = portfolioNameJTextField.getText();
      if (validateCreatePortfolioField(features, portfolioNameJTextField,
          portfolioNameMessageJLabel) && validateNumberField(fixedAmountJTextField,
          fixedAmountMessageJLabel, "amount") && validateTimelineField(dateOfInvestmentJTextField,
          dateInvestmentMessageJLabel) && validateTimelineField(endDateOfInvestmentJTextField,
          endDateInvestmentMessageJLabel) && validateNumberField(frequencyJTextField,
          frequencyMessageJLabel, "days")) {
        LocalDate startDate = LocalDate.parse(dateOfInvestmentJTextField.getText());
        LocalDate endDate = LocalDate.parse(endDateOfInvestmentJTextField.getText());
        if (endDate.isBefore(startDate)) {
          showErrorMessage(this, "End date cannot be before start date.");
          endDateInvestmentMessageJLabel.setText("Invalid date. Cannot be before start date.");
          endDateInvestmentMessageJLabel.setForeground(Color.RED);
          return;
        }
        String investmentAmount = fixedAmountJTextField.getText();
        LocalDate date = LocalDate.parse(dateOfInvestmentJTextField.getText());
        LocalDate enDate = LocalDate.parse(endDateOfInvestmentJTextField.getText());
        String frequency = frequencyJTextField.getText();
        boolean status = features.createPortfolioStrategy(selectedPortfolioName, investmentAmount,
            date, enDate, companyTickerList, weightageList, frequency);
        if (!status) {
          showErrorMessage(this, "Something went wrong. Please try again!");
        } else {
          stockJTable.setModel(new DefaultTableModel());
          portfolioNameJTextField.setText("");
          fixedAmountJTextField.setText("");
          dateOfInvestmentJTextField.setText("");
          endDateOfInvestmentJTextField.setText("");
          frequencyJTextField.setText("");
          countLabelJTextField.setText("");
        }
      }
    }
  }
}
