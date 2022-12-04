package gui;

import static gui.ViewValidator.showErrorMessage;
import static gui.ViewValidator.showInformationMessage;
import static gui.ViewValidator.validateCreatePortfolioField;
import static gui.ViewValidator.validateNumberField;
import static gui.ViewValidator.validateTickerField;
import static gui.utility.ViewConstants.INVALID_STOCKS;
import static gui.utility.ViewConstants.INVALID_TICKER;
import static gui.utility.ViewConstants.PORTFOLIO_CREATED;
import static gui.utility.ViewConstants.PORTFOLIO_EXISTS;
import static gui.utility.ViewConstants.PORTFOLIO_INVALID;

import gui.utility.ViewDocumentListener;
import generalcontroller.Features;
import generalcontroller.PortfolioType;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI Swing to support Creation of Fixed and Flexible Portfolios.
 */
public class CreatePortfolioPanel extends JPanel {

  private JPanel applicationJPanel;
  private JLabel portfolioJLabel;
  private JLabel companyTickerJLabel;
  private JLabel numberSharesJLabel;
  private JTextField portfolioNameJTextField;
  private JTextField companyTickerJTextField;
  private JTextField numberSharesJTextField;
  private JLabel portfolioMessageLabel;
  private JLabel companyTickerMessageJLabel;
  private JLabel numberOfStocksMessageJLabel;
  private JButton addShareJButton;
  private JButton createFlexiblePortfolioButton;
  private JButton createFixedPortfolioJButton;

  /**
   * Constructor to support create portfolio class.
   *
   * @param features Features accessible to action listeners.
   */
  public CreatePortfolioPanel(Features features) {
    this.add(applicationJPanel);
    this.enableButtonEvents(features);
    this.enableValidations(features);
  }

  private void enableValidations(Features features) {
    portfolioNameJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateCreatePortfolioField(features, portfolioNameJTextField,
            portfolioMessageLabel));

    numberSharesJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateNumberField(numberSharesJTextField,
            numberOfStocksMessageJLabel, "shares"));

    companyTickerJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateTickerField(features, companyTickerJTextField,
            companyTickerMessageJLabel));
  }

  private void enableButtonEvents(Features features) {
    createFlexiblePortfolioButton.addActionListener(
        event -> addPortfolio(features, PortfolioType.FLEXIBLE));

    createFixedPortfolioJButton.addActionListener(
        event -> addPortfolio(features, PortfolioType.FIXED));

    addShareJButton.addActionListener(event -> addShare(features, LocalDate.now()));
  }

  private void addShare(Features features, LocalDate date) {
    String numStocks = numberSharesJTextField.getText().trim();
    String companyStocks = companyTickerJTextField.getText().trim().toUpperCase();
    if (validateCreatePortfolioField(features, portfolioNameJTextField, portfolioMessageLabel)
        && validateTickerField(features, companyTickerJTextField, companyTickerMessageJLabel)
        && validateNumberField(numberSharesJTextField, numberOfStocksMessageJLabel, "shares")) {
      boolean companyAdded = features.purchaseShare(companyStocks, Integer.parseInt(numStocks),
          date);
      if (!companyAdded) {
        // Give invalid ticker symbol error.
        showErrorMessage(this, INVALID_TICKER);
      } else {
        // Stocks added successfully
        companyTickerJTextField.setText("");
        numberSharesJTextField.setText("");
        portfolioNameJTextField.setEnabled(false);
        createFlexiblePortfolioButton.setEnabled(true);
        createFixedPortfolioJButton.setEnabled(true);
      }
    } else {
      // give invalid stocks exception to user.
      showErrorMessage(this, INVALID_STOCKS);
    }
  }

  private void addPortfolio(Features features, PortfolioType pType) {
    String portfolioName = portfolioNameJTextField.getText();
    boolean portfolioSaved = true;
    if (portfolioName.length() > 0) {
      portfolioSaved = features.createPortfolio(portfolioName, pType);
      portfolioNameJTextField.setEnabled(true);
    } else {
      showErrorMessage(this, PORTFOLIO_INVALID);
      portfolioNameJTextField.setText("");
      portfolioNameJTextField.setEnabled(true);
      createFlexiblePortfolioButton.setEnabled(false);
      createFixedPortfolioJButton.setEnabled(false);
    }
    if (!portfolioSaved) {
      showErrorMessage(this, PORTFOLIO_EXISTS);
      portfolioNameJTextField.setText("");
      portfolioNameJTextField.setEnabled(true);
    } else {
      // Portfolio created successfully
      portfolioNameJTextField.setText("");
      showInformationMessage(this, PORTFOLIO_CREATED);
      createFlexiblePortfolioButton.setEnabled(false);
      createFixedPortfolioJButton.setEnabled(false);
      portfolioNameJTextField.setEnabled(true);
    }
  }
}
