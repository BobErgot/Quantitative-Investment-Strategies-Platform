package gui;

import java.awt.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gui.utility.ViewDocumentListener;
import gui_controller.Features;
import gui_controller.PortfolioType;

import static gui.ViewValidator.checkValidDate;
import static gui.ViewValidator.checkValidFile;
import static gui.ViewValidator.checkValidStocks;
import static gui.utility.ViewConstants.BOUGHT_FOR;
import static gui.utility.ViewConstants.FILE_UPLOAD_ANOTHER;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_EXTENSION;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_FORMAT;
import static gui.utility.ViewConstants.FILE_UPLOAD_FAIL_NOT_FOUND;
import static gui.utility.ViewConstants.FILE_UPLOAD_SUCCESS;
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
  private JTextField showValuationDatePickerTextField;
  private JComboBox<String> showValuationPortfolioListComboBox;
  private JButton showValuationJButton;
  private JTextPane showCostBasisTextPane;
  private JComboBox<String> showCostBasisPortfolioListComboBox;
  private JTextField showCostBasisDatePickerTextField;
  private JButton showCostBasisJButton;
  private JComboBox<String> purchaseSharePortfolioListComboBox;
  private JTextField purchaseShareCompanyTickerJTextField;
  private JTextField purchaseShareNumberSharesJTextField;
  private JButton purchaseShareJButton;
  private JTextField purchaseShareDatePickerTextField;
  private JComboBox<String> sellSharePortfolioListComboBox;
  private JTextField sellShareCompanyTickerJTextField;
  private JTextField sellShareNumberSharesJTextField;
  private JTextField sellShareDatePickerTextField;
  private JButton sellShareJButton;
  private JLabel portfolioMessageLabel;
  private JLabel companyTickerMessageJLabel;
  private JLabel portfolioJLabel;
  private JLabel numberOfStocksMessageJLabel;
  // Upload
  private Path filePath;

  @Override
  public void addFeatures(Features features) {

    addShareJButton.addActionListener(event -> addShare(features, LocalDate.now()));

    createFixedPortfolioJButton.addActionListener(event -> addPortfolio(features,
            PortfolioType.Fixed));

    createFlexiblePortfolioButton.addActionListener(event -> addPortfolio(features,
            PortfolioType.Flexible));

    uploadButton.addActionListener(event -> uploadPortfolio(features));

    browseFileJButton.addActionListener(event -> browseFiles());

    showCompositionJButton.addActionListener(event -> {
      String composition =
              features.generateComposition((String) portfolioListComboBox.getSelectedItem());
      compositionJTextArea.setText(composition);
    });

    showValuationJButton.addActionListener(event -> {
      String portfolioName = (String) showValuationPortfolioListComboBox.getSelectedItem();
      String date = showValuationDatePickerTextField.getText();
      if (checkValidDate(date)) {
        double valuation = features.getValuation(portfolioName, LocalDate.parse(date));
        valuationTextPane.setText("Valuation: $ " + valuation);
      } else {
        showErrorMessage(INVALID_DATE);
      }
    });

    showCostBasisJButton.addActionListener(event -> {
      String portfolioName = (String) showCostBasisPortfolioListComboBox.getSelectedItem();
      String date = showCostBasisDatePickerTextField.getText();
      if (checkValidDate(date)) {
        double valuation = features.generateCostBasis(portfolioName, LocalDate.parse(date));
        showCostBasisTextPane.setText("Cost Basis: $ " + valuation);
      } else {
        showErrorMessage(INVALID_DATE);
      }
    });

    portfolioNameJTextField.getDocument().addDocumentListener((ViewDocumentListener) e
            -> validateCreatePortfolioField(features));

    numberSharesJTextField.getDocument().addDocumentListener((ViewDocumentListener) e
            -> validateNumberShareField());

    companyTickerJTextField.getDocument().addDocumentListener((ViewDocumentListener) e
            -> validateTickerField(features));

    purchaseShareJButton.addActionListener(event -> purchaseShareOnMutablePortfolio(features));

    sellShareJButton.addActionListener(event -> sellShareOnMutablePortfolio(features));
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

  private void sellShareOnMutablePortfolio(Features features) {
    String portfolioName = (String) sellSharePortfolioListComboBox.getSelectedItem();
    String shareName = sellShareCompanyTickerJTextField.getText();
    String numStocks = sellShareNumberSharesJTextField.getText();
    String date = sellShareDatePickerTextField.getText();
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
            sellShareDatePickerTextField.setText("");
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
    String date = purchaseShareDatePickerTextField.getText();
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
          purchaseShareDatePickerTextField.setText("");
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
    ComboBoxModel<String> portfolioComboBox =
            new DefaultComboBoxModel<>(portfolios.toArray(new String[0]));
    portfolioListComboBox.setModel(portfolioComboBox);
    showValuationPortfolioListComboBox.setModel(portfolioComboBox);
    showCostBasisPortfolioListComboBox.setModel(portfolioComboBox);
  }

  @Override
  public void listAllMutablePortfolios(List<String> portfolios) {
    ComboBoxModel<String> mutablePortfolioComboBox =
            new DefaultComboBoxModel<>(portfolios.toArray(new String[0]));
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
      errorPathSelectedLabel(FILE_UPLOAD_FAIL_EXTENSION + SUPPORTED_FILES
              + SUPPORTED_FILE_EXTENSION);
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
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  private void showInformationMessage(String info) {
    JOptionPane.showMessageDialog(this, info, "Info",
            JOptionPane.INFORMATION_MESSAGE);
  }
}