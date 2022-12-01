package gui;

import static gui.ViewValidator.checkValidDate;
import static gui.ViewValidator.checkValidFile;
import static gui.ViewValidator.checkValidStocks;
import static gui.utility.ViewConstants.BOUGHT_FOR;
import static gui.utility.ViewConstants.FILE_UPLOAD_ANOTHER;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_EXTENSION;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_FORMAT;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_NOT_FOUND;
import static gui.utility.ViewConstants.FILE_UPLOAD_SUCCESS;
import static gui.utility.ViewConstants.INPUT_FIELD_EMPTY;
import static gui.utility.ViewConstants.INVALID_DATE;
import static gui.utility.ViewConstants.INVALID_STOCKS;
import static gui.utility.ViewConstants.INVALID_TICKER;
import static gui.utility.ViewConstants.NO_FILES_SELECTED;
import static gui.utility.ViewConstants.PORTFOLIO_CREATED;
import static gui.utility.ViewConstants.PORTFOLIO_EXISTS;
import static gui.utility.ViewConstants.PORTFOLIO_INVALID;
import static gui.utility.ViewConstants.SHARE_NUMBER_EXCEEDS;
import static gui.utility.ViewConstants.SOLD_FOR;
import static gui.utility.ViewConstants.STOCK_INVALID;
import static gui.utility.ViewConstants.SUPPORTED_FILES;
import static gui.utility.ViewConstants.SUPPORTED_FILE_EXTENSION;
import static gui.utility.ViewConstants.UPLOAD_ANOTHER_FILE;

import gui.utility.BarChart;
import gui.utility.ViewDocumentListener;
import gui_controller.Features;
import gui_controller.PortfolioType;
import java.awt.Color;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import model.Periodicity;

/**
 * Java Swing implementation of GUIView, implements the GUI & all its features for the Stock
 * Application.
 */
public class HomeScreen extends JFrame implements GUIView {

  private JPanel applicationJPanel;
  private JTabbedPane homeScreenTabbedPane;
  private JButton createFixedPortfolioJButton;
  private JTextField companyTickerJTextField;
  private JTextField numberSharesJTextField;
  private JButton addShareJButton;
  private JButton browseFileJButton;
  private JLabel notificationJLabel;
  private JPanel createPortfolioJPanel;
  private JPanel uploadPortfolioJPanel;
  private JLabel companyTickerJLabel;
  private JLabel numberSharesJLabel;
  private JLabel pathSelectedJLabel;
  // Create Portfolio
  private JButton createFlexiblePortfolioButton;
  private JButton uploadButton;
  private JTextField portfolioNameJTextField;
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
  private JLabel portfolioMessageLabel;
  private JLabel companyTickerMessageJLabel;
  private JLabel portfolioJLabel;
  private JLabel numberOfStocksMessageJLabel;
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
  // Upload
  private Path filePath;

  @Override
  public void addFeatures(Features features) {
    this.enableButtonEvents(features);
    this.enableValidations(features);
  }

  private void enableValidations(Features features) {
    // Validations

    // Validate Portfolio Name
    portfolioNameJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateCreatePortfolioField(features, portfolioNameJTextField,
            portfolioMessageLabel));

    // Validate Number of shares
    numberSharesJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateNumberShareField(numberSharesJTextField,
            numberOfStocksMessageJLabel));

    purchaseShareNumberSharesJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateNumberShareField(purchaseShareNumberSharesJTextField,
            purchaseShareNumberSharesJLabel));

    sellShareNumberSharesJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateNumberShareField(sellShareNumberSharesJTextField,
            sellShareNumberSharesJLabel));

    // Validate Ticker Field
    companyTickerJTextField.getDocument().addDocumentListener(
        (ViewDocumentListener) e -> validateTickerField(features, companyTickerJTextField,
            companyTickerMessageJLabel));

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
    // Buttons
    addShareJButton.addActionListener(event -> addShare(features, LocalDate.now()));

    createFixedPortfolioJButton.addActionListener(
        event -> addPortfolio(features, PortfolioType.Fixed));

    createFlexiblePortfolioButton.addActionListener(
        event -> addPortfolio(features, PortfolioType.Flexible));

    uploadButton.addActionListener(event -> uploadPortfolio(features));

    browseFileJButton.addActionListener(event -> browseFiles());

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
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
  }

  public void makeVisible() {
    this.setVisible(true);
  }

  private void addShare(Features features, LocalDate date) {
    String numStocks = numberSharesJTextField.getText().trim();
    String companyStocks = companyTickerJTextField.getText().trim().toUpperCase();
    if (validateCreatePortfolioField(features, portfolioNameJTextField, portfolioMessageLabel)
        && validateTickerField(features, companyTickerJTextField, companyTickerMessageJLabel)
        && validateNumberShareField(numberSharesJTextField, numberOfStocksMessageJLabel)) {
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

  private void uploadPortfolio(Features features) {
    Path filePath = this.filePath;
    String absoluteFilePath = null;
    if (filePath != null && !filePath.getFileName().toString().isEmpty()) {
      absoluteFilePath = filePath.toAbsolutePath().toString();
    }
    try {
      int status = features.uploadPortfolio(absoluteFilePath);
      if (status == 0) {
        clearPathSelectedLabel();
      } else if (status == 1) {
        errorPathSelectedLabel(FILE_UPLOAD_FAIL_FORMAT);
      } else if (status == 2) {
        errorPathSelectedLabel(FILE_UPLOAD_FAIL_NOT_FOUND);
      }
    } catch (DuplicateFormatFlagsException duplicateFormatFlagsException) {
      errorPathSelectedLabel(duplicateFormatFlagsException.getMessage());
    }
  }

  private void browseFiles() {
    JFileChooser jFileChooser = new JFileChooser();
    int selectedFile = jFileChooser.showOpenDialog(null);
    if (selectedFile != JFileChooser.APPROVE_OPTION) {
      return;
    }
    filePath = jFileChooser.getSelectedFile().toPath();
    String fileName = filePath.getFileName().toString();
    if (checkValidFile(fileName, SUPPORTED_FILE_EXTENSION)) {
      pathSelectedJLabel.setText(filePath.getFileName().toString());
      pathSelectedJLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
      uploadButton.setEnabled(true);
      browseFileJButton.setText(FILE_UPLOAD_ANOTHER);
    } else {
      errorPathSelectedLabel(
          FILE_UPLOAD_FAIL_EXTENSION + SUPPORTED_FILES + SUPPORTED_FILE_EXTENSION);
    }
  }

  private void clearPathSelectedLabel() {
    pathSelectedJLabel.setText(NO_FILES_SELECTED);
    notificationJLabel.setText(FILE_UPLOAD_SUCCESS);
    notificationJLabel.setForeground(Color.GREEN);
    uploadButton.setEnabled(false);
  }

  private void errorPathSelectedLabel(String error) {
    pathSelectedJLabel.setText(UPLOAD_ANOTHER_FILE);
    notificationJLabel.setText(error);
    notificationJLabel.setForeground(Color.RED);
    uploadButton.setEnabled(false);
    showErrorMessage(error);
  }

  private void refresh() {
    this.repaint();
  }

  private void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void showInformationMessage(String info) {
    JOptionPane.showMessageDialog(this, info, "Info", JOptionPane.INFORMATION_MESSAGE);
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