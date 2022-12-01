package gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import gui.utility.ViewDocumentListener;
import gui_controller.Features;

import static gui.ViewValidator.createJComboBox;
import static gui.ViewValidator.validateDateField;
import static gui.ViewValidator.validateNumberField;
import static gui.ViewValidator.validateTimelineField;

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
    addStrategyJButton.addActionListener(event -> addStrategy(features, LocalDate.now()));
    portfolioNameJComboBox.addActionListener(event -> checkTableCondition(features));
  }

  public void checkTableCondition (Features features){
    String selectedPortfolioName = (String) portfolioNameJComboBox.getSelectedItem();
    if(null != selectedPortfolioName && !selectedPortfolioName.isEmpty()){
      generateStockTable(selectedPortfolioName, features);
    }
  }

  private void generateStockTable (String portfolioName, Features features) {
    stockJTable.setShowGrid(true);
    DefaultTableModel model = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int column) {return column == 1;}
    };
    stockJTable.setModel(model);
    model.addColumn("Stocks");
    model.addColumn("Weightage %");

    List<String> sharesList = new ArrayList<>();
    sharesList.addAll(features.getShareTickerInPortfolio(portfolioName));
    for (String share: sharesList){
      model.addRow(new Object[]{share, "0",});
    }
  }

  public void listAllMutablePortfolios(List<String> portfolios) {
    createJComboBox(portfolios, portfolioNameJComboBox);
  }

  private void addStrategy(Features features, LocalDate date) {
//    String numStocks = numberSharesJTextField.getText().trim();
//    String companyStocks = companyTickerJTextField.getText().trim().toUpperCase();
//    if (validateCreatePortfolioField(features, portfolioNameJTextField, portfolioMessageLabel)
//            && validateTickerField(features, companyTickerJTextField, companyTickerMessageJLabel)
//            && validateNumberShareField(numberSharesJTextField, numberOfStocksMessageJLabel)) {
//      boolean companyAdded = features.purchaseShare(companyStocks, Integer.parseInt(numStocks),
//              date);
//      if (!companyAdded) {
//        // Give invalid ticker symbol error.
//        showErrorMessage(INVALID_TICKER);
//      } else {
//        // Stocks added successfully
//        companyTickerJTextField.setText("");
//        numberSharesJTextField.setText("");
//        portfolioNameJTextField.setEnabled(false);
//        createFlexiblePortfolioButton.setEnabled(true);
//        createFixedPortfolioJButton.setEnabled(true);
//      }
//    } else {
//      // give invalid stocks exception to user.
//      showErrorMessage(INVALID_STOCKS);
//    }
  }
}
