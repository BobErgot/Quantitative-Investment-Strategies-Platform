package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import gui.utility.ViewDocumentListener;
import gui_controller.Features;

import static gui.ViewValidator.showErrorMessage;
import static gui.ViewValidator.validateCreatePortfolioField;
import static gui.ViewValidator.validateNumberField;
import static gui.ViewValidator.validateTimelineField;

public class CreateStrategyPortfolio extends JPanel{
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

  public CreateStrategyPortfolio(Features features) {
    this.add(applicationJPanel);
    this.enableButtonEvents(features);
    this.enableValidations(features);
  }

  private void enableValidations(Features features) {
    portfolioNameJTextField.getDocument().addDocumentListener((ViewDocumentListener) e
            -> validateCreatePortfolioField(features, portfolioNameJTextField,
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
            (ViewDocumentListener) e -> validateNumberField(frequencyJTextField,
                    frequencyMessageJLabel, "days"));

    countLabelJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> {
              boolean flag = validateNumberField(frequencyJTextField,
                      frequencyMessageJLabel, "count of companies");
              if (flag) {
                int count = Integer.parseInt(countLabelJTextField.getText());
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
    for (int i = 0; i<count;i++) {
      model.addRow(new Object[]{"", "0",});
    }
  }

  private void addPortfolioStrategy(Features features) {
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
                + " under stock weightage percentage. Input only positive numeric values from " +
                "0-100.");
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
      showErrorMessage(this, "Sum of all weightage percentage should be 100. " +
              "Please reassign the weightage.");
      return;
    }
  }
}
