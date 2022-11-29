package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.*;

public class HomeScreen {
  private JPanel applicationJPanel;
  private JTabbedPane homeScreenTabbedPane;
  private JButton createFixedPortfolioJButton;
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

  public HomeScreen() {
    browseFileJButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        int selectedFile = jFileChooser.showOpenDialog(null);
        if (selectedFile != JFileChooser.APPROVE_OPTION) return;
        Path chosenFilePath = jFileChooser.getSelectedFile().toPath();
      }
    });
  }
}
