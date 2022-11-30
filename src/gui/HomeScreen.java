package gui;

import static utility.ViewConstants.BOUGHT_FOR;
import static utility.ViewConstants.INVALID_DATE;
import static utility.ViewConstants.INVALID_STOCKS;
import static utility.ViewConstants.INVALID_TICKER;
import static utility.ViewConstants.PORTFOLIO_CREATED;
import static utility.ViewConstants.PORTFOLIO_EXISTS;
import static utility.ViewConstants.PORTFOLIO_INVALID;
import static utility.ViewConstants.SHARE_NUMBER_EXCEEDS;
import static utility.ViewConstants.SOLD_FOR;
import static utility.ViewConstants.STOCK_INVALID;

import gui_controller.Features;
import gui_controller.PortfolioType;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
  private JComboBox portfolioListComboBox;
  private JTextArea compositionJTextArea;
  private JButton showCompositionJButton;
  private JTextPane valuationTextPane;
  private JTextField showValuationDatePickerTextField;
  private JComboBox showValuationPortfolioListComboBox;
  private JButton showValuationJButton;
  private JTextPane showCostBasisTextPane;
  private JComboBox showCostBasisPortfolioListComboBox;
  private JTextField showCostBasisDatePickerTextField;
  private JButton showCostBasisJButton;
  private JComboBox purchaseSharePortfolioListComboBox;
  private JTextField purchaseShareCompanyTickerJTextField;
  private JTextField purchaseShareNumberSharesJTextField;
  private JButton purchaseShareJButton;
  private JTextField purchaseShareDatePickerTextField;
  private JComboBox sellSharePortfolioListComboBox;
  private JTextField sellShareCompanyTickerJTextField;
  private JTextField sellShareNumberSharesJTextField;
  private JTextField sellShareDatePickerTextField;
  private JButton sellShareJButton;
  // Upload
  private Path filePath;

  public HomeScreen() {
    browseFileJButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        int selectedFile = jFileChooser.showOpenDialog(null);
        if (selectedFile != JFileChooser.APPROVE_OPTION) {
          return;
        }
        filePath = jFileChooser.getSelectedFile().toPath();
        if (filePath != null && !filePath.getFileName().toString().isEmpty()) {
          pathSelectedJLabel.setText(filePath.getFileName().toString());
          pathSelectedJLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
          uploadButton.setEnabled(true);
          browseFileJButton.setText("Browse another File");
        }
      }
    });
  }

  @Override
  public void addFeatures(Features features) {

    addShareJButton.addActionListener(evt -> addShare(features, LocalDate.now()));

    createFixedPortfolioJButton.addActionListener(
        evt -> addPortfolio(features, PortfolioType.Fixed));

    createFlexiblePortfolioButton.addActionListener(
        evt -> addPortfolio(features, PortfolioType.Flexible));

    uploadButton.addActionListener(evt -> uploadPortfolio(features));

    showCompositionJButton.addActionListener(evt -> {
      String composition = features.generateComposition(
          (String) portfolioListComboBox.getSelectedItem());

      compositionJTextArea.setText(composition);
    });

    showValuationJButton.addActionListener(evt -> {
      String portfolioName = (String) showValuationPortfolioListComboBox.getSelectedItem();
      String date = showValuationDatePickerTextField.getText();
      if (checkValidDate(date)) {
        double valuation = features.getValuation(portfolioName, LocalDate.parse(date));
        valuationTextPane.setText("Valuation: $ " + valuation);
      } else {
        JOptionPane.showMessageDialog(new JFrame(), INVALID_DATE, "Dialog",
            JOptionPane.ERROR_MESSAGE);
      }
    });

    showCostBasisJButton.addActionListener(evt -> {
      String portfolioName = (String) showCostBasisPortfolioListComboBox.getSelectedItem();
      String date = showCostBasisDatePickerTextField.getText();
      if (checkValidDate(date)) {
        double valuation = features.generateCostBasis(portfolioName, LocalDate.parse(date));
        showCostBasisTextPane.setText("Cost Basis: $ " + valuation);
      } else {
        JOptionPane.showMessageDialog(new JFrame(), INVALID_DATE, "Dialog",
            JOptionPane.ERROR_MESSAGE);
      }
    });

    purchaseShareJButton.addActionListener(evt -> purchaseShareOnMutablePortfolio(features));

    sellShareJButton.addActionListener(evt -> sellShareOnMutablePortfolio(features));
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
            JOptionPane.showMessageDialog(new JFrame(), INVALID_TICKER, "Dialog",
                JOptionPane.ERROR_MESSAGE);
          } else {
            // Stocks added successfully
            sellShareCompanyTickerJTextField.setText("");
            sellShareNumberSharesJTextField.setText("");
            sellShareDatePickerTextField.setText("");
            JOptionPane.showMessageDialog(new JFrame(), SOLD_FOR + sellingPrice, "Dialog",
                JOptionPane.OK_OPTION);
          }
        } catch (NoSuchElementException noSuchElementException) {
          JOptionPane.showMessageDialog(new JFrame(), STOCK_INVALID, "Dialog",
              JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException illegalArgumentException) {
          JOptionPane.showMessageDialog(new JFrame(), SHARE_NUMBER_EXCEEDS, "Dialog",
              JOptionPane.ERROR_MESSAGE);
        }

      } else {
        JOptionPane.showMessageDialog(new JFrame(), INVALID_DATE, "Dialog",
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(new JFrame(), INVALID_STOCKS, "Dialog",
          JOptionPane.ERROR_MESSAGE);
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
          JOptionPane.showMessageDialog(new JFrame(), INVALID_TICKER, "Dialog",
              JOptionPane.ERROR_MESSAGE);
        } else {
          // Stocks added successfully
          purchaseShareCompanyTickerJTextField.setText("");
          purchaseShareNumberSharesJTextField.setText("");
          purchaseShareDatePickerTextField.setText("");
          JOptionPane.showMessageDialog(new JFrame(), BOUGHT_FOR + buyingPrice, "Dialog",
              JOptionPane.OK_OPTION);
        }
      } else {
        JOptionPane.showMessageDialog(new JFrame(), INVALID_DATE, "Dialog",
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(new JFrame(), INVALID_STOCKS, "Dialog",
          JOptionPane.ERROR_MESSAGE);
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
    String companyStocks = companyTickerJTextField.getText().trim();
    if (checkValidStocks(numStocks)) {
      boolean companyAdded = features.purchaseShare(companyStocks, Integer.parseInt(numStocks),
          date);
      if (!companyAdded) {
        // Give invalid ticker symbol error.
        JOptionPane.showMessageDialog(new JFrame(), INVALID_TICKER, "Dialog",
            JOptionPane.ERROR_MESSAGE);
      } else {
        // Stocks added successfully
        companyTickerJTextField.setText("");
        numberSharesJTextField.setText("");
        createFlexiblePortfolioButton.setEnabled(true);
        createFixedPortfolioJButton.setEnabled(true);

      }
    } else {
      // give invalid stocks exception to user.
      JOptionPane.showMessageDialog(new JFrame(), INVALID_STOCKS, "Dialog",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private boolean checkValidStocks(String numStocks) {
    try {
      int test = Integer.parseInt(numStocks);
      return test > 0;
    } catch (NumberFormatException invalidStock) {
      return false;
    }
  }

  private void addPortfolio(Features features, PortfolioType pType) {
    String portfolioName = portfolioNameJTextField.getText();
    boolean portfolioSaved = true;
    if (portfolioName.length() > 0) {
      portfolioSaved = features.createPortfolio(portfolioName, pType);
    } else {
      JOptionPane.showMessageDialog(new JFrame(), PORTFOLIO_INVALID, "Dialog",
          JOptionPane.ERROR_MESSAGE);
    }
    if (!portfolioSaved) {
      JOptionPane.showMessageDialog(new JFrame(), PORTFOLIO_EXISTS, "Dialog",
          JOptionPane.ERROR_MESSAGE);
    } else {
      // Portfolio created successfully
      portfolioNameJTextField.setText("");
      JOptionPane.showMessageDialog(new JFrame(), PORTFOLIO_CREATED, "Dialog",
          JOptionPane.OK_OPTION);
      createFlexiblePortfolioButton.setEnabled(false);
      createFixedPortfolioJButton.setEnabled(false);
    }
  }

  private void uploadPortfolio(Features features) {
    Path filePath = this.filePath;
    String absoluteFilePath = null;
    if (filePath != null && !filePath.getFileName().toString().isEmpty()) {
      absoluteFilePath = filePath.toAbsolutePath().toString();
    }
    boolean status = features.uploadPortfolio(absoluteFilePath);
    if (status) {
      clearPathSelectedLabel();
    } else {
      errorPathSelectedLabel();
    }
  }

  public void clearPathSelectedLabel() {
    pathSelectedJLabel.setText("No Files Selected");
    notificationJLabel.setText("File uploaded successfully!");
    notificationJLabel.setForeground(Color.GREEN);
    uploadButton.setEnabled(false);
  }


  private boolean checkValidDate(String date) {
    try {
      LocalDate test = LocalDate.parse(date);
      return test.isAfter(LocalDate.of(1949, 12, 31));
    } catch (DateTimeParseException dateError) {
      return false;
    }
  }

  public void errorPathSelectedLabel() {
    pathSelectedJLabel.setText("Try to upload another file");
    notificationJLabel.setText(
        "File upload failed as either the file did not exist or format is " + "wrong!");
    notificationJLabel.setForeground(Color.RED);
    uploadButton.setEnabled(false);
  }

  public void refresh() {
    this.repaint();
  }

  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }
}