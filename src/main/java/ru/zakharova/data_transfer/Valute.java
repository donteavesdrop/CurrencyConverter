package ru.zakharova.data_transfer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import ru.zakharova.model.DoubleDeserializer;

public class Valute {
    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    private String id;
    @JacksonXmlProperty(localName = "NumCode")
    private int numCode;
    @JacksonXmlProperty(localName = "CharCode")
    private String charCode;
    @JacksonXmlProperty(localName = "Nominal")
    private int nominal;
    @JacksonXmlProperty(localName = "Name")
    private String name;
    @JacksonXmlProperty(localName = "Value")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private double value;

    public Valute() {  }
    public Valute(String id, int numCode, String charCode, int nominal, String name, double value) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getId() { return id; }
    public int getNumCode() { return numCode; }
    public String getCharCode() { return charCode; }
    public int getNominal() { return nominal; }
    public String getName() { return name; }
    public double getValue() { return value; }

    public void setId(String id) { this.id = id; }
    public void setNumCode(int numCode) { this.numCode = numCode; }
    public void setCharCode(String charCode) { this.charCode = charCode; }
    public void setNominal(int nominal) { this.nominal = nominal; }
    public void setName(String name) { this.name = name; }
    public void setValue(double value) { this.value = value; }

    @Override
    public String toString() {
        /*

         */
        return "Valute{" +
                "id='" + id + '\'' +
                ", numCode=" + numCode +
                ", charCode='" + charCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}