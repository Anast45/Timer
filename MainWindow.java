/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerupdated;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener {

    private JComboBox<Integer> timerH;
    private JComboBox<Integer> timerM;
    private JButton confirm;
    private JButton cb;
    private JButton exit;
    private JPanel obj;
    private FlowLayout flow;
    private boolean timerActive;
    private Timer countdownTimer;
    private JLabel countdownLabel;
    private int remainingTime;
    private boolean isGreek;
    private JComboBox<String> languageBox;

    public MainWindow() {
        timerActive = false;
        setTitle("Timer-Shutdown");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);
        initComponents();
        setContentPane(obj);
        countdownTimer = new Timer(1000, this);
    }

    private void initComponents() {
        // Labels
        JLabel timeL = new JLabel("Enter the time at which you want to shut down your computer");
        JLabel timeHL = new JLabel("Hours");
        JLabel timeML = new JLabel("Minutes");

        // ComboBoxes for hours and minutes
        timerH = new JComboBox<>();
        timerM = new JComboBox<>();

        for (int i = 0; i <= 10; i++) {
            timerH.addItem(i);
        }

        for (int i = 0; i <= 60; i++) {
            timerM.addItem(i);
        }

        // Buttons
        confirm = new JButton("Confirm");
        cb = new JButton("Cancel");
        exit = new JButton("Exit");

        // Language selection box
        languageBox = new JComboBox<>();
        languageBox.addItem("English");
        languageBox.addItem("Greek");
        languageBox.addActionListener(this);

        // Panel
        obj = new JPanel();
        flow = new FlowLayout(FlowLayout.LEFT);
        obj.setLayout(flow);
        obj.setBackground(Color.LIGHT_GRAY);

        // Add components to the panel
        obj.add(languageBox);
        obj.add(Box.createRigidArea(new Dimension(20, 0)));
        obj.add(timeL);
        obj.add(timeHL);
        obj.add(timerH);
        obj.add(timeML);
        obj.add(timerM);
        obj.add(confirm);
        obj.add(cb);
        obj.add(exit);

        // Register action listeners
        confirm.addActionListener(this);
        cb.addActionListener(this);
        exit.addActionListener(this);

        // Countdown label
        countdownLabel = new JLabel();
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 24));
    }

    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        if (source == confirm) {
            if (!timerActive) {
                int hours = (int) timerH.getSelectedItem();
                int minutes = (int) timerM.getSelectedItem();
                remainingTime = hours * 3600 + minutes * 60;
                if (remainingTime > 0) {
                    showShutdownTime(hours, minutes);
                }
                startCountdown();
                showTimerActivatedPopup();
            }
        } else if (source == cb) {
            if (timerActive) {
                stopCountdown();
                showTimerDeactivatedPopup();
            }
        } else if (source == countdownTimer) {
            remainingTime--;
            if (remainingTime <= 0) {
                stopCountdown();
                showShutdownMessage();
            } else {
                countdownLabel.setText(formatTime(remainingTime));
            }
        } else if (source == exit) {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit the program?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (source == languageBox) {
            String selectedLanguage = (String) languageBox.getSelectedItem();
            if (selectedLanguage.equals("Greek")) {
                setLanguage("Greek");
            } else {
                setLanguage("English");
            }
        }
    }

    private void showShutdownTime(int hours, int minutes) {
        String shutdownTime = formatTime(hours) + ":" + formatTime(minutes);
        JOptionPane.showMessageDialog(this, "The computer will shut down at " + shutdownTime,
                "Timer Activated", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showTimerActivatedPopup() {
        JOptionPane.showMessageDialog(this, "Timer activated. Computer will shut down in " +
                formatTime(remainingTime), "Timer Activated", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showTimerDeactivatedPopup() {
        JOptionPane.showMessageDialog(this, "Timer deactivated.", "Timer Deactivated",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showShutdownMessage() {
        JOptionPane.showMessageDialog(this, "Shutdown time reached. The computer will now shut down.",
                "Shutdown", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startCountdown() {
        countdownTimer.start();
        timerActive = true;
        confirm.setEnabled(false);
        timerH.setEnabled(false);
        timerM.setEnabled(false);
        obj.add(countdownLabel);
        obj.revalidate();
        obj.repaint();
    }

    private void stopCountdown() {
        countdownTimer.stop();
        timerActive = false;
        confirm.setEnabled(true);
        timerH.setEnabled(true);
        timerM.setEnabled(true);
        obj.remove(countdownLabel);
        obj.revalidate();
        obj.repaint();
    }

    private String formatTime(int time) {
        return String.format("%02d", time);
    }

    private void setLanguage(String language) {
        if (language.equals("Greek")) {
            setTitle("Χρονομετρητής-Απενεργοποίηση");
            JLabel timeL = new JLabel("Εισάγετε την ώρα κλεισίματος του υπολογιστή σας");
            JLabel timeHL = new JLabel("Ώρες");
            JLabel timeML = new JLabel("Λεπτά");
            confirm.setText("Επιβεβαίωση");
            cb.setText("Ακύρωση");
            exit.setText("Έξοδος");
            languageBox.setSelectedItem("Ελληνικά");
        } else {
            setTitle("Timer-Shutdown");
            JLabel timeL = new JLabel("Enter the time at which you want to shut down your computer");
            JLabel timeHL = new JLabel("Hours");
            JLabel timeML = new JLabel("Minutes");
            confirm.setText("Confirm");
            cb.setText("Cancel");
            exit.setText("Exit");
            languageBox.setSelectedItem("English");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow form = new MainWindow();
            form.setVisible(true);
        });
    }
}
