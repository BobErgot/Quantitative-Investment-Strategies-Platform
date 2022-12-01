package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import gui_controller.Features;

import static gui.utility.ViewConstants.INPUT_FIELD_EMPTY;
import static gui.utility.ViewConstants.INVALID_DATE;
import static gui.utility.ViewConstants.INVALID_TICKER;
import static gui.utility.ViewConstants.PORTFOLIO_EXISTS;

public class ViewValidator {
  static boolean checkValidFile(String fileName, Set<String> supportedFileExtension){
    if(null == fileName || fileName.isEmpty()){
      return false;
    }
    String [] fileComponents = fileName.split("\\.");
    return fileComponents.length == 2 &&
            supportedFileExtension.contains(fileComponents[1].trim().toLowerCase());
  }

  static boolean checkValidDate(String date) {
    try {
      LocalDate localDate = LocalDate.parse(date);
      return localDate.isAfter(LocalDate.of(1949, 12, 31));
    } catch (DateTimeParseException dateError) {
      return false;
    }
  }

  static boolean checkValidStocks(String numStocks) {
    try {
      int numberOfStocks = Integer.parseInt(numStocks);
      return numberOfStocks > 0;
    } catch (NumberFormatException invalidStock) {
      return false;
    }
  }

   static boolean validateCreatePortfolioField(Features features, JTextField portfolioJTextField,
                                               JLabel portfolioMsgLabel) {
    String portfolioTextEntered = portfolioJTextField.getText().trim();
    if (portfolioTextEntered.isEmpty()) {
      portfolioMsgLabel.setText(INPUT_FIELD_EMPTY);
      portfolioMsgLabel.setForeground(Color.BLUE);
      return false;
    }
    boolean exists = features.checkPortfolioNameExists(portfolioTextEntered);
    if (exists) {
      portfolioMsgLabel.setText(PORTFOLIO_EXISTS);
      portfolioMsgLabel.setForeground(Color.RED);
      return false;
    } else {
      portfolioMsgLabel.setText("Valid portfolio name!");
      portfolioMsgLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  static boolean validateTickerField(Features features, JTextField tickerJTextField,
                                     JLabel tickerMessageJLabel) {
    String tickerTextEntered = tickerJTextField.getText().trim().toUpperCase();
    if (tickerTextEntered.isEmpty()) {
      tickerMessageJLabel.setText(INPUT_FIELD_EMPTY);
      tickerMessageJLabel.setForeground(Color.BLUE);
      return false;
    }
    boolean exists = features.checkTickerExists(tickerTextEntered);
    if (!exists) {
      tickerMessageJLabel.setText(INVALID_TICKER);
      tickerMessageJLabel.setForeground(Color.RED);
      return false;
    } else {
      tickerMessageJLabel.setText("Valid company ticker!");
      tickerMessageJLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  static boolean validateNumberField(JTextField numSharesJTextField,
                                          JLabel numStocksMessageJLabel, String object) {
    String numberShareEntered = numSharesJTextField.getText().trim();
    if (numberShareEntered.isEmpty()) {
      numStocksMessageJLabel.setText(INPUT_FIELD_EMPTY);
      numStocksMessageJLabel.setForeground(Color.BLUE);
      return false;
    }
    if (!checkValidStocks(numberShareEntered)) {
      numStocksMessageJLabel.setText("Invalid number for " + object + "!");
      numStocksMessageJLabel.setForeground(Color.RED);
      return false;
    } else {
      numStocksMessageJLabel.setText("Valid " + object + "!");
      numStocksMessageJLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  static boolean validateDateField(JTextField dateJTextField, JLabel dateMessageJLabel) {
    String dateEntered = dateJTextField.getText().trim();
    if (dateEntered.isEmpty()) {
      dateMessageJLabel.setText(INPUT_FIELD_EMPTY);
      dateMessageJLabel.setForeground(Color.BLUE);
      return false;
    }
    if (!checkValidDate(dateEntered)) {
      dateMessageJLabel.setText(INVALID_DATE);
      dateMessageJLabel.setForeground(Color.RED);
      return false;
    } else {
      dateMessageJLabel.setText("Valid date!");
      dateMessageJLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  static void createJComboBox(List<String> portfolios, JComboBox<String> jComboBox) {
    ComboBoxModel<String> mutablePortfolioComboBox = new DefaultComboBoxModel<>(
            portfolios.toArray(new String[0]));
    jComboBox.setModel(mutablePortfolioComboBox);
  }
}
