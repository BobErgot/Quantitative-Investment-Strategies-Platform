package gui;

import java.awt.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.*;

import gui.utility.BarChart;
import gui.utility.ViewDocumentListener;
import gui_controller.Features;
import gui_controller.PortfolioType;
import model.Periodicity;

import static gui.ViewValidator.checkValidDate;
import static gui.ViewValidator.checkValidStocks;
import static gui.utility.ViewConstants.BOUGHT_FOR;
import static gui.utility.ViewConstants.INPUT_FIELD_EMPTY;
import static gui.utility.ViewConstants.INVALID_DATE;
import static gui.utility.ViewConstants.INVALID_STOCKS;
import static gui.utility.ViewConstants.INVALID_TICKER;
import static gui.utility.ViewConstants.PORTFOLIO_EXISTS;
import static gui.utility.ViewConstants.SHARE_NUMBER_EXCEEDS;
import static gui.utility.ViewConstants.SOLD_FOR;
import static gui.utility.ViewConstants.STOCK_INVALID;

/**
 * Java Swing implementation of GUIView, implements the GUI & all its features for the Stock
 * Application.
 */
public class HomeScreen extends JFrame implements GUIView {

  private JPanel applicationJPanel;
  private JTabbedPane homeScreenTabbedPane;
  private JLabel notificationJLabel;
  private JTabbedPane showCostBasisPane;
  private JComboBox<String> portfolioListComboBox;
  private JTextArea compositionJTextArea;
  private JButton showCompositionJButton;
  private JTextPane valuationTextPane;
  private JTextField showValuationDatePickerJTextField;
  private JComboBox<String> showValuationPortfolioListComboBox;
  private JButton showValuationJButton;
  private JTextPane showCostBasisTextPane;
  private JComboBox<String> showCostBasisPortfolioListComboBox;
  private JTextField showCostBasisDatePickerJTextField;
  private JButton showCostBasisJButton;
  private JComboBox<String> purchaseSharePortfolioListComboBox;
  private JTextField purchaseShareCompanyTickerJTextField;
  private JTextField purchaseShareNumberSharesJTextField;
  private JButton purchaseShareJButton;
  private JTextField purchaseShareDatePickerJTextField;
  private JComboBox<String> sellSharePortfolioListComboBox;
  private JTextField sellShareCompanyTickerJTextField;
  private JTextField sellShareNumberSharesJTextField;
  private JTextField sellShareDatePickerJTextField;
  private JButton sellShareJButton;
  private JComboBox showGraphPerformancePortfolioListComboBox;
  private JComboBox periodicityListComboBox;
  private JTextField performanceFromDatePickerJTextField;
  private JTextField performanceToDatePickerJTextField;
  private JButton performanceGraphJButton;
  private JLabel purchaseShareCompanyTickerJLabel;
  private JLabel purchaseShareNumberSharesJLabel;
  private JLabel sellShareCompanyTickerJLabel;
  private JLabel sellShareNumberSharesJLabel;
  private JLabel sellShareDatePickerJLabel;
  private JLabel purchaseShareDatePickerJLabel;
  private JLabel showCostBasisDatePickerJLabel;
  private JLabel performanceToDatePickerJLabel;
  private JLabel performanceFromDatePickerJLabel;
  private JLabel showValuationDatePickerJLabel;

  @Override
  public void addFeatures(Features features) {
    this.enableButtonEvents(features);
    this.enableValidations(features);

    CreatePortfolioPanel createPortfolioPanel = new CreatePortfolioPanel(features);
    homeScreenTabbedPane.addTab("Create Portfolio", createPortfolioPanel);

    UploadPortfolioPanel uploadPortfolioPanel = new UploadPortfolioPanel(features);
    homeScreenTabbedPane.addTab("Upload Portfolio", uploadPortfolioPanel);
  }

  private void enableValidations(Features features) {
    purchaseShareNumberSharesJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateNumberShareField(purchaseShareNumberSharesJTextField,
                    purchaseShareNumberSharesJLabel));

    sellShareNumberSharesJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateNumberShareField(sellShareNumberSharesJTextField,
                    sellShareNumberSharesJLabel));

    purchaseShareCompanyTickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateTickerField(features,
                    purchaseShareCompanyTickerJTextField, purchaseShareCompanyTickerJLabel));

    sellShareCompanyTickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateTickerField(features, sellShareCompanyTickerJTextField,
                    sellShareCompanyTickerJLabel));

    // Validate Date Field
    sellShareDatePickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateDateField(sellShareDatePickerJTextField,
                    sellShareDatePickerJLabel));

    purchaseShareDatePickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateDateField(purchaseShareDatePickerJTextField,
                    purchaseShareDatePickerJLabel));

    showCostBasisDatePickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateDateField(showCostBasisDatePickerJTextField,
                    showCostBasisDatePickerJLabel));

    performanceFromDatePickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateDateField(performanceFromDatePickerJTextField,
                    performanceFromDatePickerJLabel));

    performanceToDatePickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateDateField(performanceToDatePickerJTextField,
                    performanceToDatePickerJLabel));

    showValuationDatePickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateDateField(showValuationDatePickerJTextField,
                    showValuationDatePickerJLabel));
  }

  private void enableButtonEvents(Features features) {
    showCompositionJButton.addActionListener(event -> {
      String composition = features.generateComposition(
              (String) portfolioListComboBox.getSelectedItem());
      compositionJTextArea.setText(composition);
    });

    showValuationJButton.addActionListener(event -> {
      String portfolioName = (String) showValuationPortfolioListComboBox.getSelectedItem();
      String date = showValuationDatePickerJTextField.getText();
      if (checkValidDate(date)) {
        double valuation = features.getValuation(portfolioName, LocalDate.parse(date));
        valuationTextPane.setText("Valuation: $ " + valuation);
      } else {
        showErrorMessage(INVALID_DATE);
      }
    });

    showCostBasisJButton.addActionListener(event -> {
      String portfolioName = (String) showCostBasisPortfolioListComboBox.getSelectedItem();
      String date = showCostBasisDatePickerJTextField.getText();
      if (checkValidDate(date)) {
        double valuation = features.generateCostBasis(portfolioName, LocalDate.parse(date));
        showCostBasisTextPane.setText("Cost Basis: $ " + valuation);
      } else {
        showErrorMessage(INVALID_DATE);
      }
    });

    purchaseShareJButton.addActionListener(event -> purchaseShareOnMutablePortfolio(features));

    sellShareJButton.addActionListener(event -> sellShareOnMutablePortfolio(features));

    performanceGraphJButton.addActionListener(event -> {
      List<Double> graph = features.generatePerformanceGraph("portland",
              LocalDate.parse("2020-11-26"), LocalDate.parse("2022-11-26"), Periodicity.YEAR);
      String[] test = {"2020", "2021", "2022"};
      JFrame frame = new JFrame();
      frame.setSize(350, 300);
      frame.getContentPane().add(
              new BarChart(graph.stream().mapToDouble(Double::doubleValue).toArray(), test,
                      "Performance graph"));
      frame.setVisible(true);
    });
  }

  private void sellShareOnMutablePortfolio(Features features) {
    String portfolioName = (String) sellSharePortfolioListComboBox.getSelectedItem();
    String shareName = sellShareCompanyTickerJTextField.getText();
    String numStocks = sellShareNumberSharesJTextField.getText();
    String date = sellShareDatePickerJTextField.getText();
    if (checkValidStocks(numStocks)) {
      if (checkValidDate(date)) {
        double sellingPrice = -1.0;
        try {
          sellingPrice = features.sellShare(portfolioName, shareName, Integer.parseInt(numStocks),
                  LocalDate.parse(date));
          if (sellingPrice < 0.0) {
            // Give invalid ticker symbol error.
            showErrorMessage(INVALID_TICKER);
          } else {
            // Stocks added successfully
            sellShareCompanyTickerJTextField.setText("");
            sellShareNumberSharesJTextField.setText("");
            sellShareDatePickerJTextField.setText("");
            showInformationMessage(SOLD_FOR + sellingPrice);
          }
        } catch (NoSuchElementException noSuchElementException) {
          showErrorMessage(STOCK_INVALID);
        } catch (IllegalArgumentException illegalArgumentException) {
          showErrorMessage(SHARE_NUMBER_EXCEEDS);
        }
      } else {
        showErrorMessage(INVALID_DATE);
      }
    } else {
      showErrorMessage(INVALID_STOCKS);
    }
  }

  private void purchaseShareOnMutablePortfolio(Features features) {
    String portfolioName = (String) purchaseSharePortfolioListComboBox.getSelectedItem();
    String shareName = purchaseShareCompanyTickerJTextField.getText();
    String numStocks = purchaseShareNumberSharesJTextField.getText();
    String date = purchaseShareDatePickerJTextField.getText();
    if (checkValidStocks(numStocks)) {
      if (checkValidDate(date)) {
        double buyingPrice = features.purchaseShare(portfolioName, shareName,
                Integer.parseInt(numStocks), LocalDate.parse(date));
        if (buyingPrice < 0.0) {
          // Give invalid ticker symbol error.
          showErrorMessage(INVALID_TICKER);
        } else {
          // Stocks added successfully
          purchaseShareCompanyTickerJTextField.setText("");
          purchaseShareNumberSharesJTextField.setText("");
          purchaseShareDatePickerJTextField.setText("");
          showInformationMessage(BOUGHT_FOR + buyingPrice);
        }
      } else {
        showErrorMessage(INVALID_DATE);
      }
    } else {
      showErrorMessage(INVALID_STOCKS);
    }
  }

  @Override
  public void listAllPortfolios(List<String> portfolios) {
    ComboBoxModel<String> portfolioComboBox = new DefaultComboBoxModel<>(
            portfolios.toArray(new String[0]));
    portfolioListComboBox.setModel(portfolioComboBox);
    showValuationPortfolioListComboBox.setModel(portfolioComboBox);
    showCostBasisPortfolioListComboBox.setModel(portfolioComboBox);
  }

  @Override
  public void listAllMutablePortfolios(List<String> portfolios) {
    ComboBoxModel<String> mutablePortfolioComboBox = new DefaultComboBoxModel<>(
            portfolios.toArray(new String[0]));
    purchaseSharePortfolioListComboBox.setModel(mutablePortfolioComboBox);
    sellSharePortfolioListComboBox.setModel(mutablePortfolioComboBox);

  }

  @Override
  public void showView() {
    makeVisible();
    this.setTitle("Stocker");
    this.setContentPane(applicationJPanel);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
  }

  public void makeVisible() {
    this.setVisible(true);
  }

  private void refresh() {
    this.repaint();
  }

  private void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  private void showInformationMessage(String info) {
    JOptionPane.showMessageDialog(this, info, "Info",
            JOptionPane.INFORMATION_MESSAGE);
  }

  private boolean validateCreatePortfolioField(Features features, JTextField portfolioJTextField,
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

  private boolean validateTickerField(Features features, JTextField tickerJTextField,
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

  private boolean validateNumberShareField(JTextField numSharesJTextField,
                                           JLabel numStocksMessageJLabel) {
    String numberShareEntered = numSharesJTextField.getText().trim();
    if (numberShareEntered.isEmpty()) {
      numStocksMessageJLabel.setText(INPUT_FIELD_EMPTY);
      numStocksMessageJLabel.setForeground(Color.BLUE);
      return false;
    }
    if (!checkValidStocks(numberShareEntered)) {
      numStocksMessageJLabel.setText("Invalid number of shares!");
      numStocksMessageJLabel.setForeground(Color.RED);
      return false;
    } else {
      numStocksMessageJLabel.setText("Valid shares!");
      numStocksMessageJLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  private boolean validateDateField(JTextField dateJTextField, JLabel dateMessageJLabel) {
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
}