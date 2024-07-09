package com.formenshop.MailLibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailAPI {

    // Variables
    private Context mContext;
    private Session mSession;
    private String mEmail;
    private String mSubject;
    private String mMessage;
    private ProgressDialog mProgressDialog;

    // Constructor
    public JavaMailAPI(Context mContext, String mEmail, String mSubject, String mMessage) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
    }

    public void execute() {
        onPreExecute();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            doInBackground();
            new Handler(Looper.getMainLooper()).post(this::onPostExecute);
        });
    }

    protected void onPreExecute() {
        // Show progress dialog while sending email
        mProgressDialog = ProgressDialog.show(mContext, "Sending message", "Please wait...", false, false);
    }
    protected void onPostExecute() {
        // Dismiss progress dialog when message is successfully sent
        mProgressDialog.dismiss();

        // Show success toast
        Toast.makeText(mContext, "Message Sent", Toast.LENGTH_SHORT).show();
    }
    protected void doInBackground() {
        // Creating properties
        Properties props = new Properties();

        // Configuring properties for Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        mSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            // Authenticating the password
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailInfo.EMAIL, MailInfo.PASSWORD);
            }
        });

        try {
            // Creating MimeMessage object
            MimeMessage mm = new MimeMessage(mSession);

            // Setting sender address
            mm.setFrom(new InternetAddress(MailInfo.EMAIL));
            // Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            // Adding subject
            mm.setSubject(mSubject);
            // Adding message
            mm.setText(mMessage);
            // Sending email
            Transport.send(mm);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}