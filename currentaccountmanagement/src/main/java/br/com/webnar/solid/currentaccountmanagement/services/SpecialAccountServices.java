package br.com.webnar.solid.currentaccountmanagement.services;

public interface SpecialAccountServices {

    boolean billPayment(String barCode);

    String smsMessage(long accountNumber);
}
