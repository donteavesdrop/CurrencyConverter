package ru.zakharova.model;

import java.time.LocalDate;

public class CurrencyExchange {
    private int id;
    private double value;
    private int nominal;
    private String currencyName;
    private String currencyCode;
    private LocalDate date;

    public CurrencyExchange(int id, double value, int nominal, String currencyName, String currencyCode, LocalDate date) {
        this.id = id;
        this.value = value;
        this.nominal = nominal;
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.date = date;
    }

    public int getId() { return id; }
    public double getValue() { return value; }
    public int getNominal() { return nominal; }
    public String getCurrencyName() { return currencyName; }
    public String getCurrencyCode() { return currencyCode; }
    public LocalDate getDate() { return date; }

    public void setId(int id) { this.id = id; }
    public void setValue(double value) { this.value = value; }
    public void setNominal(int nominal) { this.nominal = nominal; }
    public void setCurrencyName(String currencyName) { this.currencyName = currencyName; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }
    public void setDate(LocalDate date) { this.date = date; }
}
