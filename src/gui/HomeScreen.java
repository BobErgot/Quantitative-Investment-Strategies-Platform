package gui;

import static gui.ViewValidator.checkValidDate;
import static gui.ViewValidator.checkValidStocks;
import static gui.ViewValidator.createJComboBox;
import static gui.ViewValidator.showErrorMessage;
import static gui.ViewValidator.showInformationMessage;
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
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

import generalcontroller.Features;
import gui.utility.BarChart;
import gui.utility.ViewDocumentListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import model.Periodicity;

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

  private ExistingPortfolioStrategy existingPortfolioStrategy;

  @Override
  public void addFeatures(Features features) {
    this.enableButtonEvents(features);
    this.enableValidations(features);

    CreatePortfolioPanel createPortfolioPanel = new CreatePortfolioPanel(features);
    homeScreenTabbedPane.addTab("Create Portfolio", createPortfolioPanel);

    UploadPortfolioPanel uploadPortfolioPanel = new UploadPortfolioPanel(features);
    homeScreenTabbedPane.addTab("Upload Portfolio", uploadPortfolioPanel);

    existingPortfolioStrategy = new ExistingPortfolioStrategy(features);
    homeScreenTabbedPane.addTab("Existing Portfolio Strategy", existingPortfolioStrategy);

    CreateStrategyPortfolio createStrategyPortfolio = new CreateStrategyPortfolio(features);
    homeScreenTabbedPane.addTab("Create Strategy Portfolio", createStrategyPortfolio);
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
        showValuationDatePickerJTextField.setText("");
      } else {
        showErrorMessage(this, INVALID_DATE);
      }
    });

    showCostBasisJButton.addActionListener(event -> {
      String portfolioName = (String) showCostBasisPortfolioListComboBox.getSelectedItem();
      String date = showCostBasisDatePickerJTextField.getText();
      if (checkValidDate(date)) {
        double valuation = features.generateCostBasis(portfolioName, LocalDate.parse(date));
        showCostBasisTextPane.setText("Cost Basis: $ " + valuation);
        showCostBasisDatePickerJTextField.setText("");
      } else {
        showErrorMessage(this, INVALID_DATE);
      }
    });

    purchaseShareJButton.addActionListener(event -> purchaseShareOnMutablePortfolio(features));

    sellShareJButton.addActionListener(event -> sellShareOnMutablePortfolio(features));

    performanceGraphJButton.addActionListener(event -> {
      String portfolioName = (String) showGraphPerformancePortfolioListComboBox.getSelectedItem();
      String fromDateString = performanceFromDatePickerJTextField.getText();
      String toDateString = performanceToDatePickerJTextField.getText();
      if (checkValidDate(fromDateString) && checkValidDate(toDateString)) {
        LocalDate fromDate = LocalDate.parse(fromDateString);
        LocalDate toDate = LocalDate.parse(toDateString);
        Periodicity group = getPeriodicity(fromDate, toDate);
        String[] dates = getStringDates(fromDate, toDate, group);
        List<Double> graph = features.generatePerformanceGraph(portfolioName, fromDate, toDate,
            group);
        JFrame frame = new JFrame();
        frame.setSize(350, 300);
        frame.getContentPane().add(
            new BarChart(graph.stream().mapToDouble(Double::doubleValue).toArray(), dates,
                "Performance graph"));
        frame.setVisible(true);
      } else {
        showErrorMessage(this, INVALID_DATE);
      }

    });
  }

  private String[] getStringDates(LocalDate fromDate, LocalDate toDate, Periodicity group) {
    List<String> dates = new ArrayList<>();
    LocalDate date = fromDate;
    Map<Periodicity, Function<LocalDate, LocalDate>> updateFunction = new HashMap<>();
    updateFunction.put(Periodicity.DAY, (currentDate) -> currentDate.plusDays(1));
    updateFunction.put(Periodicity.MONTH, (currentDate) -> currentDate.plusMonths(1));
    updateFunction.put(Periodicity.YEAR, (currentDate) -> currentDate.plusYears(1));
    while (date.isBefore(toDate) || date.equals(toDate)) {
      String dateToAdd;
      if (group == Periodicity.DAY) {
        dateToAdd = date.toString();
      } else if (group == Periodicity.MONTH) {
        dateToAdd = date.getMonth().toString().substring(0, 3) + "/" + date.getYear() % 100;
      } else {
        dateToAdd = Integer.toString(date.getYear());
      }
      dates.add(dateToAdd);
      date = updateFunction.get(group).apply(date);
    }
    return dates.toArray(new String[0]);
  }

  private Periodicity getPeriodicity(LocalDate fromDate, LocalDate toDate) {
    if (DAYS.between(fromDate, toDate) > 30) {
      if (MONTHS.between(fromDate, toDate) > 30) {
        return Periodicity.YEAR;
      } else {
        return Periodicity.MONTH;
      }
    }
    return Periodicity.DAY;
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
            showErrorMessage(this, INVALID_TICKER);
          } else {
            // Stocks added successfully
            sellShareCompanyTickerJTextField.setText("");
            sellShareNumberSharesJTextField.setText("");
            sellShareDatePickerJTextField.setText("");
            showInformationMessage(this, SOLD_FOR + sellingPrice);
          }
        } catch (NoSuchElementException noSuchElementException) {
          showErrorMessage(this, STOCK_INVALID);
        } catch (IllegalArgumentException illegalArgumentException) {
          showErrorMessage(this, SHARE_NUMBER_EXCEEDS);
        }
      } else {
        showErrorMessage(this, INVALID_DATE);
      }
    } else {
      showErrorMessage(this, INVALID_STOCKS);
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
          showErrorMessage(this, INVALID_TICKER);
        } else {
          // Stocks added successfully
          purchaseShareCompanyTickerJTextField.setText("");
          purchaseShareNumberSharesJTextField.setText("");
          purchaseShareDatePickerJTextField.setText("");
          showInformationMessage(this, BOUGHT_FOR + buyingPrice);
        }
      } else {
        showErrorMessage(this, INVALID_DATE);
      }
    } else {
      showErrorMessage(this, INVALID_STOCKS);
    }
  }

  @Override
  public void listAllPortfolios(List<String> portfolios) {
    createJComboBox(portfolios, portfolioListComboBox);
    createJComboBox(portfolios, showValuationPortfolioListComboBox);
    createJComboBox(portfolios, showCostBasisPortfolioListComboBox);
    createJComboBox(portfolios, showGraphPerformancePortfolioListComboBox);

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
}