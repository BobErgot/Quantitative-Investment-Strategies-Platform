package gui;

import static utility.ViewConstants.INVALID_STOCKS;
import static utility.ViewConstants.INVALID_TICKER;

import gui_controller.Features;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HomeScreen extends JFrame implements GUIView {
  private JPanel applicationJPanel;
  private JTabbedPane homeScreenTabbedPane;
  private JButton createFlexiblePortfolioJButton;
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
  private JButton createFlexiblePortfolioButton;
  private JButton uploadButton;

  private Path filePath;

  private String absoluteFilePath;

  public HomeScreen() {
    browseFileJButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        int selectedFile = jFileChooser.showOpenDialog(null);
        if (selectedFile != JFileChooser.APPROVE_OPTION) return;
        filePath = jFileChooser.getSelectedFile().toPath();
        if(filePath != null && !filePath.getFileName().toString().isEmpty()){
          pathSelectedJLabel.setText(filePath.getFileName().toString());
          pathSelectedJLabel.setBorder(new EmptyBorder(0,10,0,0));
          uploadButton.setEnabled(true);
          browseFileJButton.setText("Browse another File");
        }
      }
    });

    uploadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(filePath != null && !filePath.getFileName().toString().isEmpty()){
          absoluteFilePath = filePath.toAbsolutePath().toString();
        }
      }
    });
  }

  public void showView () {
    makeVisible();
    this.setTitle("Stocker");
    this.setContentPane(applicationJPanel);
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    browseFileJButton.addActionListener(actionListener);
    this.pack();
  }

  public String getFilePath() {
    if(filePath != null && !filePath.getFileName().toString().isEmpty()){
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
      return test>     0  ;
    } catch (NumberFormatException invalidStock) {
      return false;
    }
  }
  private void addShare(Features features)  {
    String numStocks = numberSharesJTextField.getText().trim();
    String companyStocks = companyTickerJTextField.getText().trim();
    if (checkValidStocks(numStocks)) {
      boolean companyAdded = features.purchaseShare(companyStocks, Integer.parseInt(numStocks));
      if (!companyAdded) {
        // Give invalid ticker symbol error.
        JOptionPane.showMessageDialog(new JFrame(), INVALID_TICKER, "Dialog",
            JOptionPane.ERROR_MESSAGE);
      }
      else{
        // Stocks added successfully
        companyTickerJTextField.setText("");
        numberSharesJTextField.setText("");
      }
    } else {
      // give invalid stocks exception to user.
      JOptionPane.showMessageDialog(new JFrame(), INVALID_STOCKS, "Dialog",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void addFeatures(Features features) {
    addShareJButton.addActionListener(evt -> addShare(features));
    createFixedPortfolioJButton.addActionListener(evt->{
//      String portfolioName =
//      boolean portfolioSaved = features.createPortfolio(portfolioName);
    });
  }



  @Override
  public void clearText(String id) {

  }
}