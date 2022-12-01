package gui;

import java.time.LocalDate;
import java.util.List;

import javax.swing.*;

import gui.utility.ViewDocumentListener;
import gui_controller.Features;

import static gui.ViewValidator.createJComboBox;
import static gui.ViewValidator.validateDateField;
import static gui.ViewValidator.validateNumberField;

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
  }

  private void enableValidations(Features features) {
    fixedAmountJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateNumberField(fixedAmountJTextField,
                    fixedAmountMessageJLabel, "amount"));

    dateOfInvestmentJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateDateField(dateOfInvestmentJTextField,
                    dateInvestmentJLabel));
  }

  private void enableButtonEvents(Features features) {
    addStrategyJButton.addActionListener(event -> addStrategy(features, LocalDate.now()));
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

  private void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  private void showInformationMessage(String info) {
    JOptionPane.showMessageDialog(this, info, "Info",
            JOptionPane.INFORMATION_MESSAGE);
  }
}
