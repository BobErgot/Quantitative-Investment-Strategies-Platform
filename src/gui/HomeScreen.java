package gui;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.*;

import gui.utility.BarChart;
import gui.utility.ViewDocumentListener;
import gui_controller.Features;
import model.Periodicity;

import static gui.ViewValidator.checkValidDate;
import static gui.ViewValidator.checkValidStocks;
import static gui.ViewValidator.createJComboBox;
import static gui.ViewValidator.validateDateField;
import static gui.ViewValidator.validateNumberField;
import static gui.ViewValidator.validateTickerField;
import static gui.utility.ViewConstants.BOUGHT_FOR;
import static gui.utility.ViewConstants.INVALID_DATE;
import static gui.utility.ViewConstants.INVALID_STOCKS;
import static gui.utility.ViewConstants.INVALID_TICKER;
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

  private CreatePortfolioPanel createPortfolioPanel;

  private UploadPortfolioPanel uploadPortfolioPanel;

  private ExistingPortfolioStrategy existingPortfolioStrategy;

  @Override
  public void addFeatures(Features features) {
    this.enableButtonEvents(features);
    this.enableValidations(features);

    createPortfolioPanel = new CreatePortfolioPanel(features);
    homeScreenTabbedPane.addTab("Create Portfolio", createPortfolioPanel);

    uploadPortfolioPanel = new UploadPortfolioPanel(features);
    homeScreenTabbedPane.addTab("Upload Portfolio", uploadPortfolioPanel);

    existingPortfolioStrategy = new ExistingPortfolioStrategy(features);
    homeScreenTabbedPane.addTab("Existing Portfolio Strategy", existingPortfolioStrategy);
  }

  private void enableValidations(Features features) {
    purchaseShareNumberSharesJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateNumberField(purchaseShareNumberSharesJTextField,
                    purchaseShareNumberSharesJLabel, "shares"));

    sellShareNumberSharesJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateNumberField(sellShareNumberSharesJTextField,
                    sellShareNumberSharesJLabel, "shares"));

    purchaseShareCompanyTickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateTickerField(features,
                    purchaseShareCompanyTickerJTextField, purchaseShareCompanyTickerJLabel));

    sellShareCompanyTickerJTextField.getDocument().addDocumentListener(
            (ViewDocumentListener) e -> validateTickerField(features, sellShareCompanyTickerJTextField,
                    sellShareCompanyTickerJLabel));

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
    createJComboBox(portfolios, portfolioListComboBox);
    createJComboBox(portfolios, showValuationPortfolioListComboBox);
    createJComboBox(portfolios, showCostBasisPortfolioListComboBox);
  }

  @Override
  public void listAllMutablePortfolios(List<String> portfolios) {
    createJComboBox(portfolios, purchaseSharePortfolioListComboBox);
    createJComboBox(portfolios, sellSharePortfolioListComboBox);
    existingPortfolioStrategy.listAllMutablePortfolios(portfolios);
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
}