package ru.zakharova.view;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;
import ru.zakharova.data_transfer.ValCurs;
import ru.zakharova.data_transfer.Valute;
import ru.zakharova.http.ExchangeService;
import ru.zakharova.model.CurrencyExchange;
import ru.zakharova.repository.CurrencyExchangeRepository;
import ru.zakharova.repository.CurrencyExchangeRepositoryImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class Interface {
    private static final CurrencyExchangeRepository repository = new CurrencyExchangeRepositoryImpl();
    public Interface() {
        super();
        createGUI();
    }
    public static void createGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Перевести из рублей в: ");

        panel.add(label);

        String items;
        JComboBox comboBox = new JComboBox();
        for (var item : UpdateValutes(31,12,2022)){
            items = (item.getName());
            comboBox.addItem(items);
        }

        panel.add(comboBox);
        var updateButton = new JButton("Convert");
        updateButton.setBounds(600, 600, 100, 25);
        updateButton.setBackground(Color.getHSBColor(0.58f, 0.08f, 0.95f));
        panel.add(updateButton);
        final int[] select = new int[1];
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                select[0] = comboBox.getSelectedIndex();
            }
        };
        JTextField smallField = new JTextField(15);
        smallField.setToolTipText("Короткое поле");
        // Слушатель окончания ввода
        final double[] amount = new double[1];
        smallField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amount[0] = Double.parseDouble((smallField.getText()));
            }
        });
        panel.add(smallField);
        comboBox.addActionListener(actionListener);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int day = LocalDate.now().getDayOfYear();
                int month = LocalDate.now().getMonthValue();
                int year = LocalDate.now().getYear();
                double value = (double) UpdateValutes(day,month,year).get(select[0]).getValue();
                int nominal = (int) UpdateValutes(day,month,year).get(select[0]).getNominal();
                double result = (amount[0] *value/nominal);
                JLabel res = new JLabel("Результат перевода: "+ String.valueOf(amount[0]) +" рублей = " + String.valueOf(result)+" "+ UpdateValutes(day,month,year).get(select[0]).getName());
                panel.add(res).setBounds(100,100,700,300);

            }
        });

        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(700, 300));

        frame.pack();
        frame.setVisible(true);
    }
    private static List<Valute> CreateNewRequest(String date) {
        try {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("https://www.cbr.ru/")
                    .setConverter(new JacksonConverter(new XmlMapper()))
                    .build();

            ExchangeService service = adapter.create(ExchangeService.class);

            ValCurs curs = service.GetExchange(date);
            return curs.getValuteList();
        } catch (Exception e) {
            System.out.println("Problems with request -- Exception message: " + e.getMessage());
            return new ArrayList<Valute>();
        }
    }
    private static List<Valute> ConvertFromCurrencyExchangeList(List<CurrencyExchange> currencies) {
        List<Valute> valutes = new ArrayList<>();

        for (int i = 0; i < currencies.size(); i++) {
            CurrencyExchange exchange = currencies.get(i);
            Valute tmp = new Valute(
                    String.valueOf(exchange.getId()),
                    i + 1,
                    exchange.getCurrencyCode(),
                    exchange.getNominal(),
                    exchange.getCurrencyName(),
                    exchange.getValue()
            );
            valutes.add(tmp);
        }
        return valutes;
    }
    private static String ConvertDate(int day, int month, int year) {
        String dayStr = String.valueOf(day);
        String monthStr = String.valueOf(month);

        if (dayStr.length() == 1) {
            dayStr = "0" + dayStr;
        }
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        try {
            LocalDate date = LocalDate.of(year, month, day);
            if (date.isAfter(LocalDate.now())) {
                final LocalDate nowDate = LocalDate.now();
                return nowDate.getDayOfMonth() + "/" + nowDate.getMonthValue() + "/" + nowDate.getYear();
            } else {
                return dayStr + "/" + monthStr + "/" + date.getYear();
            }
        } catch (Exception exc) {
            System.out.println("Exception in date converting - Default date set (LocalDate.now()) - Exception message:  " + exc.getMessage());
            final LocalDate nowDate = LocalDate.now();
            return nowDate.getDayOfMonth() + "/" + nowDate.getMonthValue() + "/" + nowDate.getYear();
        }
    }
    private static List<Valute> UpdateValutes(int day, int month, int year) {

        final List<CurrencyExchange> dbContent = repository.findAll();

        if (!dbContent.isEmpty()) {
            boolean isAnotherDate = true;
            LocalDate date = LocalDate.of(year, month, day);

            for (CurrencyExchange dbItem : dbContent) {
                if (date.equals(dbItem.getDate())) {
                    isAnotherDate = false;
                    break;
                }
            }

            if (!isAnotherDate) {
                return ConvertFromCurrencyExchangeList(dbContent);
            } else {
                repository.deleteAll();

                List<Valute> newData = CreateNewRequest(ConvertDate(day, month, year));


                for (var item : ConvertFromValuteList(newData, LocalDate.of(year, month, day))) {
                    repository.insert(item);
                }
                return newData;
            }
        } else {
            List<Valute> newData = CreateNewRequest(ConvertDate(day, month, year));

            for (var item : ConvertFromValuteList(newData, LocalDate.of(year, month, day))) {
                repository.insert(item);
            }
            return newData;
        }
    }

    private static List<CurrencyExchange> ConvertFromValuteList(List<Valute> valutes, LocalDate date) {
        List<CurrencyExchange> currencies = new ArrayList<>();

        for (int i = 0; i < valutes.size(); i++) {
            var valute = valutes.get(i);
            CurrencyExchange exchange = new CurrencyExchange(
                    i + 1,
                    valute.getValue(),
                    valute.getNominal(),
                    valute.getName(),
                    valute.getCharCode(),
                    date
            );
            currencies.add(exchange);
        }
        return currencies;
    }
    public static void main(String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Interface frame = new Interface();

    }
}
