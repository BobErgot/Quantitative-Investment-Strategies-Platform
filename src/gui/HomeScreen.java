package gui;

import static utility.ViewConstants.INVALID_STOCKS;
import static utility.ViewConstants.INVALID_TICKER;
import static utility.ViewConstants.PORTFOLIO_CREATED;
import static utility.ViewConstants.PORTFOLIO_EXISTS;
import static utility.ViewConstants.PORTFOLIO_INVALID;

import gui_controller.Features;
import gui_controller.PortfolioType;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
  private JPanel viewPortfolioJPanel;
  private JButton compositionButton;
  private JButton valuationButton;
  private JButton costBasisButton;
  private JButton buySharesButton;
  private JButton sellSharesButton;
  private JButton investmentPerformanceButton;
  private JButton createNewInvestmentStrategyButton;
  // Create Portfolio
  private JButton createFlexiblePortfolioButton;
  private JButton uploadButton;
  private JTextField portfolioNameJTextField;
  // Upload
  private Path filePath;

  private String absoluteFilePath;

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

    uploadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (filePath != null && !filePath.getFileName().toString().isEmpty()) {
          absoluteFilePath = filePath.toAbsolutePath().toString();
        }
      }
    });
  }

  public void showView() {
    makeVisible();
    this.setTitle("Stocker");
    this.setContentPane(applicationJPanel);
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    browseFileJButton.addActionListener(actionListener);
    this.pack();
  }

  public String getFilePath() {
    if (filePath != null && !filePath.getFileName().toString().isEmpty()) {
      absoluteFilePath = filePath.toAbsolutePath().toString();
    }
    return absoluteFilePath;
  }

  public void clearPathSelectedLabel() {
    pathSelectedJLabel.setText("No Files Selected");
    absoluteFilePath = null;
    filePath = null;
    notificationJLabel.setText("File uploaded successfully!");
    notificationJLabel.setForeground(Color.GREEN);
    uploadButton.setEnabled(false);
  }

  public void errorPathSelectedLabel() {
    pathSelectedJLabel.setText("Try to upload another file");
    absoluteFilePath = null;
    filePath = null;
    notificationJLabel.setText("File upload failed as either the file did not exist or format is " +
        "wrong!");
    notificationJLabel.setForeground(Color.RED);
    uploadButton.setEnabled(false);
  }

  public void makeVisible() {
    this.setVisible(true);
  }

  public void refresh() {
    this.repaint();
  }

  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private boolean checkValidStocks(String numStocks) {
    try {
      int test = Integer.parseInt(numStocks);
      return test > 0;
    } catch (NumberFormatException invalidStock) {
      return false;
    }
  }

  private void addShare(Features features) {
    String numStocks = numberSharesJTextField.getText().trim();
    String companyStocks = companyTickerJTextField.getText().trim();
    if (checkValidStocks(numStocks)) {
      boolean companyAdded = features.purchaseShare(companyStocks, Integer.parseInt(numStocks));
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

  @Override
  public void addFeatures(Features features) {

    addShareJButton.addActionListener(evt -> addShare(features));
    createFixedPortfolioJButton.addActionListener(
        evt -> addPortfolio(features, PortfolioType.Fixed));
    createFlexiblePortfolioButton.addActionListener(
        evt -> addPortfolio(features, PortfolioType.Flexible));
  }


  @Override
  public void clearText(String id) {

  }
}