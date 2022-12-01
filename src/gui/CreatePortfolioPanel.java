package gui;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;

import gui.utility.ViewDocumentListener;
import gui_controller.Features;
import gui_controller.PortfolioType;

import static gui.ViewValidator.checkValidStocks;
import static gui.utility.ViewConstants.INVALID_STOCKS;
import static gui.utility.ViewConstants.INVALID_TICKER;
import static gui.utility.ViewConstants.PORTFOLIO_CREATED;
import static gui.utility.ViewConstants.PORTFOLIO_EXISTS;
import static gui.utility.ViewConstants.PORTFOLIO_INVALID;

public class CreatePortfolioPanel extends JPanel {
  private JPanel applicationJPanel;
  private JPanel createPortfolioJPanel;
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

  public CreatePortfolioPanel(Features features) {
    this.add(applicationJPanel);

    createFlexiblePortfolioButton.addActionListener(event -> addPortfolio(features,
            PortfolioType.Flexible));

    createFixedPortfolioJButton.addActionListener(event -> addPortfolio(features,
            PortfolioType.Fixed));

    portfolioNameJTextField.getDocument().addDocumentListener((ViewDocumentListener) e
            -> validateCreatePortfolioField(features));

    numberSharesJTextField.getDocument().addDocumentListener((ViewDocumentListener) e
            -> validateNumberShareField());

    companyTickerJTextField.getDocument().addDocumentListener((ViewDocumentListener) e
            -> validateTickerField(features));

    addShareJButton.addActionListener(event -> addShare(features, LocalDate.now()));
  }

  private void addShare(Features features, LocalDate date) {
    String numStocks = numberSharesJTextField.getText().trim();
    String companyStocks = companyTickerJTextField.getText().trim().toUpperCase();
    if (validateCreatePortfolioField(features) && validateTickerField(features)
            && validateNumberShareField()) {
      boolean companyAdded = features.purchaseShare(companyStocks, Integer.parseInt(numStocks),
              date);
      if (!companyAdded) {
        // Give invalid ticker symbol error.
        showErrorMessage(INVALID_TICKER);
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
      showErrorMessage(INVALID_STOCKS);
    }
  }

  private boolean validateCreatePortfolioField(Features features) {
    String portfolioTextEntered = portfolioNameJTextField.getText().trim();
    if (portfolioTextEntered.isEmpty()) {
      portfolioMessageLabel.setText("Field cannot be empty!");
      portfolioMessageLabel.setForeground(Color.BLUE);
      return false;
    }
    boolean exists = features.checkPortfolioNameExists(portfolioTextEntered);
    if (exists) {
      portfolioMessageLabel.setText("Portfolio name already exists!");
      portfolioMessageLabel.setForeground(Color.RED);
      return false;
    } else {
      portfolioMessageLabel.setText("Valid portfolio name!");
      portfolioMessageLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  private boolean validateTickerField(Features features) {
    String tickerTextEntered = companyTickerJTextField.getText().trim().toUpperCase();
    if (tickerTextEntered.isEmpty()) {
      companyTickerMessageJLabel.setText("Field cannot be empty!");
      companyTickerMessageJLabel.setForeground(Color.BLUE);
      return false;
    }
    boolean exists = features.checkTickerExists(tickerTextEntered);
    if (!exists) {
      companyTickerMessageJLabel.setText("Invalid company Ticker!");
      companyTickerMessageJLabel.setForeground(Color.RED);
      return false;
    } else {
      companyTickerMessageJLabel.setText("Valid company ticker!");
      companyTickerMessageJLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  private boolean validateNumberShareField() {
    String numberShareEntered = numberSharesJTextField.getText().trim();
    if (numberShareEntered.isEmpty()) {
      numberOfStocksMessageJLabel.setText("Field cannot be empty!");
      numberOfStocksMessageJLabel.setForeground(Color.BLUE);
      return false;
    }
    if (!checkValidStocks(numberShareEntered)) {
      numberOfStocksMessageJLabel.setText("Invalid number of shares!");
      numberOfStocksMessageJLabel.setForeground(Color.RED);
      return false;
    } else {
      numberOfStocksMessageJLabel.setText("Valid shares!");
      numberOfStocksMessageJLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  private void addPortfolio(Features features, PortfolioType pType) {
    String portfolioName = portfolioNameJTextField.getText();
    boolean portfolioSaved = true;
    if (portfolioName.length() > 0) {
      portfolioSaved = features.createPortfolio(portfolioName, pType);
      portfolioNameJTextField.setEnabled(true);
    } else {
      showErrorMessage(PORTFOLIO_INVALID);
      portfolioNameJTextField.setText("");
      portfolioNameJTextField.setEnabled(true);
      createFlexiblePortfolioButton.setEnabled(false);
      createFixedPortfolioJButton.setEnabled(false);
    }
    if (!portfolioSaved) {
      showErrorMessage(PORTFOLIO_EXISTS);
      portfolioNameJTextField.setText("");
      portfolioNameJTextField.setEnabled(true);
    } else {
      // Portfolio created successfully
      portfolioNameJTextField.setText("");
      showInformationMessage(PORTFOLIO_CREATED);
      createFlexiblePortfolioButton.setEnabled(false);
      createFixedPortfolioJButton.setEnabled(false);
      portfolioNameJTextField.setEnabled(true);
    }
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
