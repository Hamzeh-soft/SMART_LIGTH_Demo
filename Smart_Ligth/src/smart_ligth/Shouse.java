/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart_ligth;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.text.html.CSS;
import static smart_ligth.TimerData.schedulList;

/**
 *
 * @author User
 */
public class Shouse extends javax.swing.JFrame {
//singleton class

    /**
     * Creates new form Shouse
     */
    public static TimerTask schedulTask;
    private static volatile Shouse shouseins;
    ImageIcon ligthonimag = null, ligthoffimage = null;
    ImageIcon switchoffimage = null;
    ImageIcon switchonimage = null;
    ImageIcon exiticon = null;
    TimerData timedata;
    static Timer timer = new Timer();
    ButtonGroup radiogroup = new ButtonGroup();
    static boolean switchstatus = false;
    long countdown;
    //   long countdown=timedata.getScdulabyMiilseconds();
    long numberofDays;
    long numberofHours;
    long numberofMinute;
    Consumer<String> geMood = (t) -> {
        if (t.equals("ON")) {
            turnOnLigth();
            //  onMode();
        } else if (t.endsWith("OFF")) {
            turnOffLigth();
            // ofMode();
        }
        
    };
    
    private Shouse() {
        initComponents();
        schedulList = new ArrayList<>();
        switchoffimage = new ImageIcon("C:\\Users\\User\\Desktop\\Java_Practic\\Smart_Ligth\\src\\smart_ligth\\Pictures\\SwitchOff.png");
        switchonimage = new ImageIcon("C:\\Users\\User\\Desktop\\Java_Practic\\Smart_Ligth\\src\\smart_ligth\\Pictures\\SwitchOn.png");
        SwitchONOFF.setIcon(switchoffimage);
        radiogroup.add(OnRadio);
        radiogroup.add(OFfRadio);
        radiogroup.add(SafeMode);
        OnRadio.setSelected(true);
        jPanel1.setBackground(Color.BLACK);
        ligthoffimage = new ImageIcon("C:\\Users\\User\\Desktop\\Java_Practic\\Smart_Ligth\\src\\smart_ligth\\Pictures\\LigthOf.png");
        ligthonimag = new ImageIcon("C:\\Users\\User\\Desktop\\Java_Practic\\Smart_Ligth\\src\\smart_ligth\\Pictures\\LigthOn.png", "ON switch");
        exiticon = new ImageIcon("C:\\Users\\User\\Desktop\\Java_Practic\\Smart_Ligth\\src\\smart_ligth\\Pictures\\cancel (1)_1.png");
        jLabel5.setIcon(ligthoffimage);
        jLabel6.setIcon(ligthoffimage);
        jLabel7.setIcon(ligthoffimage);
        // exitelable.setSize(exitelable.preferredSize());
        jLabel5.setSize(jLabel5.preferredSize());
        jLabel6.setSize(jLabel6.preferredSize());
        jLabel7.setSize(jLabel7.preferredSize());
        exitelable.setIcon(exiticon);
        DaysText.setText("0");
        MinuteTxt.setText("0");
        HoursText.setText("0");
        
    }
    
    public static Shouse getInstant() {
        if (shouseins == null) {
            synchronized (Shouse.class) {
                if (shouseins == null) {
                    shouseins = new Shouse();
                }
            }
        }
        return shouseins;
    }
    
    public void turnOnLigth() {
        jLabel5.setIcon(ligthonimag);
        jLabel7.setIcon(ligthonimag);
        jLabel6.setIcon(ligthonimag);
    }
    
    public void turnOffLigth() {
        jLabel5.setIcon(ligthoffimage);
        jLabel7.setIcon(ligthoffimage);
        jLabel6.setIcon(ligthoffimage);
    }
    
    public void timerCountDown() {
        Timer timer2 = new Timer();
        countdown = timedata.getScdulabyMiilseconds();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (countdown >= 0) {
                    long s = ((countdown / 1000) % 60);
                    long m = (((countdown / 1000) / 60) % 60);
                    long h = ((((countdown / 1000) / 60) / 60) % 60);
                    timeLabel.setText(h + " :Hours " + m + ":Minutes " + s + ":Seconds");
                    countdown -= 1000;
                } else {
                    timer2.cancel();
                    timer2.purge();
                    
                }
            }
        }, 0, 1000);
        
    }
    
    private void switchonoffimage() {
        if (switchstatus == true) {
            SwitchONOFF.setIcon(switchonimage);
        } else {
            SwitchONOFF.setIcon(switchoffimage);
        }
    }
    
    public String getSelectedButton() {
        for (Enumeration<AbstractButton> buttons = radiogroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return "ON";
    }
    
    public void OnOffMode() {
        timedata = new TimerData(numberofDays, numberofHours, numberofMinute, getSelectedButton());
        timer = new Timer();
        timer.schedule(schedulTask, timedata.getScdulabyMiilseconds());
        
        schedulList.add(timedata);
        
    }
    
    public void safeMode() {

        //write stope method
        TimerTask stopSleepMode = new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                timer.purge();
                
            }
        };
        schedulTask = new TimerTask() {
            @Override
            public void run() {
                
                int r = new Random().nextInt(2);
                switch (r) {
                    case 0:
                        turnOnLigth();
                        break;
                    case 1:
                        turnOffLigth();
                }
                
            }
        };
        
        timedata = new TimerData(numberofDays, numberofHours, numberofMinute, getSelectedButton());
        timer = new Timer();
        timer.scheduleAtFixedRate(schedulTask, 10000, 10000);
        Timer stoptime = new Timer();
        stoptime.schedule(stopSleepMode, timedata.getScdulabyMiilseconds());
        schedulList.add(timedata);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DaysText = new javax.swing.JTextField();
        HoursText = new javax.swing.JTextField();
        MinuteTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        schedule = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        OFfRadio = new javax.swing.JRadioButton();
        OnRadio = new javax.swing.JRadioButton();
        SafeMode = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        SwitchONOFF = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        StopButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        exitelable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(250, 100));
        setUndecorated(true);

        DaysText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        DaysText.setMinimumSize(new java.awt.Dimension(8, 20));
        DaysText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CheckDaysIsValid(evt);
            }
        });

        HoursText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        HoursText.setMinimumSize(new java.awt.Dimension(8, 20));
        HoursText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CheckHoursVaild(evt);
            }
        });

        MinuteTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MinuteTxt.setMinimumSize(new java.awt.Dimension(8, 20));
        MinuteTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinuteTxtActionPerformed(evt);
            }
        });
        MinuteTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CheckMinuteISvaild(evt);
            }
        });

        jLabel1.setText("Days");

        jLabel2.setText("Hours");

        jLabel3.setText("Minute");

        schedule.setText("Schedule");
        schedule.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        schedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheduleActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        OFfRadio.setText("OFF");

        OnRadio.setText("ON");

        SafeMode.setText("Safe");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OnRadio)
                .addGap(18, 18, 18)
                .addComponent(OFfRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SafeMode)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OnRadio)
                    .addComponent(OFfRadio)
                    .addComponent(SafeMode))
                .addGap(41, 41, 41))
        );

        jLabel4.setText("Mode");

        SwitchONOFF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SwitchONOFFMouseClicked(evt);
            }
        });
        SwitchONOFF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SwitchONOFFActionPerformed(evt);
            }
        });

        jLabel8.setText("Room");

        StopButton.setText("Stop");
        StopButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        StopButton.setMaximumSize(new java.awt.Dimension(75, 23));
        StopButton.setMinimumSize(new java.awt.Dimension(75, 23));
        StopButton.setPreferredSize(new java.awt.Dimension(75, 23));
        StopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopButtonActionPerformed(evt);
            }
        });

        jLabel9.setText("Wating Time");

        timeLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        exitelable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exitelableMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exitelable, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(SwitchONOFF, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(DaysText, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(57, 57, 57)
                                            .addComponent(jLabel9))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(41, 41, 41)
                                            .addComponent(schedule, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(StopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(HoursText, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3)
                                        .addGap(31, 31, 31)
                                        .addComponent(MinuteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(189, 189, 189))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(exitelable, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HoursText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(MinuteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DaysText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(SwitchONOFF, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(schedule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(StopButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MinuteTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinuteTxtActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_MinuteTxtActionPerformed

    private void scheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scheduleActionPerformed
        
        try {
            numberofDays = Long.parseLong(DaysText.getText());
            numberofHours = Long.parseLong(HoursText.getText());
            numberofMinute = Long.parseLong(MinuteTxt.getText());
            schedulTask = new TimerTask() {
                @Override
                public void run() {
                    geMood.accept(getSelectedButton());
                }
            };
            if (getSelectedButton().equals("ON") || getSelectedButton().equals("OFF")) {
                OnOffMode();
            } else if (getSelectedButton().equals("Safe")) {
                safeMode();
            }
            timerCountDown();
        } catch (Exception e) {
            out.println(e.getMessage());
        }

    }//GEN-LAST:event_scheduleActionPerformed

    private void SwitchONOFFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SwitchONOFFActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_SwitchONOFFActionPerformed

    private void SwitchONOFFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SwitchONOFFMouseClicked
        // TODO add your handling code here:
        if (switchstatus == true) {
            switchstatus = false;
            switchonoffimage();
            turnOffLigth();
            
        } else {
            switchstatus = true;
            turnOnLigth();
            switchonoffimage();
        }
        

    }//GEN-LAST:event_SwitchONOFFMouseClicked

    private void StopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopButtonActionPerformed
        // TODO add your handling code here:
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        countdown = 0;
        

    }//GEN-LAST:event_StopButtonActionPerformed

    private void CheckHoursVaild(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CheckHoursVaild
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
            evt.consume(); // consume non-numbers
            HoursText.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else if (Character.isDigit(c)) {
            HoursText.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        } else if (HoursText.getText().isEmpty()) {
            HoursText.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        

    }//GEN-LAST:event_CheckHoursVaild

    private void CheckDaysIsValid(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CheckDaysIsValid
        // TODO add your handling code here:
        //VK_BACK_SPACE
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
            evt.consume(); // consume non-numbers
            DaysText.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else if (Character.isDigit(c)) {
            DaysText.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        } else if (DaysText.getText().isEmpty()) {
            DaysText.setBorder(BorderFactory.createLineBorder(Color.RED));
        }

    }//GEN-LAST:event_CheckDaysIsValid

    private void CheckMinuteISvaild(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CheckMinuteISvaild
        // TODO add your handling code here
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
            evt.consume(); // consume non-numbers
            MinuteTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else if (Character.isDigit(c)) {
            MinuteTxt.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        } else if (MinuteTxt.getText().isEmpty()) {
            MinuteTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        

    }//GEN-LAST:event_CheckMinuteISvaild

    private void exitelableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitelableMousePressed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitelableMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Shouse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Shouse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Shouse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Shouse.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Shouse().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DaysText;
    private javax.swing.JTextField HoursText;
    private javax.swing.JTextField MinuteTxt;
    private javax.swing.JRadioButton OFfRadio;
    private javax.swing.JRadioButton OnRadio;
    private javax.swing.JRadioButton SafeMode;
    private javax.swing.JButton StopButton;
    private javax.swing.JButton SwitchONOFF;
    private javax.swing.JLabel exitelable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton schedule;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables

}
