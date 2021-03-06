/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timboudreau.scamper.chat.swing.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Tim Boudreau
 */
public class NewRoomPanel extends javax.swing.JPanel implements DocumentListener {

    private final UIModels mdls;

    /**
     * Creates new form NewRoomPanel
     */
    public NewRoomPanel(UIModels mdls) {
        initComponents();
        this.mdls = mdls;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        roomField.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roomLabel = new javax.swing.JLabel();
        roomField = new javax.swing.JTextField();
        passwordEnabledCheckbox = new javax.swing.JCheckBox();
        passwordField = new javax.swing.JTextField();

        roomLabel.setText("Room");

        passwordEnabledCheckbox.setText("Password");
        passwordEnabledCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordEnabledCheckboxActionPerformed(evt);
            }
        });

        passwordField.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordEnabledCheckbox)
                    .addComponent(roomLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roomField, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                    .addComponent(passwordField))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomLabel)
                    .addComponent(roomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordEnabledCheckbox)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void passwordEnabledCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordEnabledCheckboxActionPerformed
        passwordField.setEnabled(this.passwordEnabledCheckbox.isSelected());
    }//GEN-LAST:event_passwordEnabledCheckboxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox passwordEnabledCheckbox;
    private javax.swing.JTextField passwordField;
    private javax.swing.JTextField roomField;
    private javax.swing.JLabel roomLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void insertUpdate(DocumentEvent de) {
        changedUpdate(de);
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        changedUpdate(de);
    }

    private boolean ok;

    public boolean isOk() {
        return ok;
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        boolean ok = !mdls.isKnownRoom(roomField.getText());
        if (ok != this.ok) {
            this.ok = ok;
            firePropertyChange("ok", !ok, ok);
        }
    }

    public String[] showDialog() {
        final JOptionPane jp = new JOptionPane(this, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dlg = jp.createDialog(null, "New Room");
        jp.setInitialValue(roomField);

        PropertyChangeListener pcl = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                if (isOk()) {
                    jp.setOptions(new Object[]{JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION});
                } else {
                    jp.setOptions(new Object[]{JOptionPane.CANCEL_OPTION});
                }
            }
        };
        addPropertyChangeListener("ok", pcl);
        try {
            dlg.setVisible(true);
            Object sel = jp.getValue();
            if (sel != null && sel == (Integer) 0) {
                if (passwordEnabledCheckbox.isSelected()) {
                    return new String[]{roomField.getText().trim(), passwordField.getText().trim()};
                } else {
                    return new String[]{roomField.getText().trim()};
                }
            }
        } finally {
            removePropertyChangeListener(pcl);
        }
        return null;
    }
}
