package ru.zakharova.repository;

import ru.zakharova.model.CurrencyExchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CurrencyExchangeRepositoryImpl implements CurrencyExchangeRepository {

    private static final String TABLE_NAME = "currencies";

    private static final String ID = "id";
    private static final String VALUE = "value";
    private static final String NOMINAL = "nominal";
    private static final String NAME = "currency_name";
    private static final String CODE = "currency_code";
    private static final String DATE = "date";


    @Override
    public CurrencyExchange findById(int id) {
        Connection connection = Database.getInstance().getConnection();

        try (PreparedStatement statement = connection.prepareStatement("select * from " + TABLE_NAME + " where " + ID + "=" + id)) {
            var set = statement.executeQuery();
            while (set.next()) {
                return new CurrencyExchange(
                        Integer.parseInt(set.getString(ID)),
                        Double.parseDouble(set.getString(VALUE)),
                        Integer.parseInt(set.getString(NOMINAL)),
                        set.getString(NAME),
                        set.getString(CODE),
                        LocalDate.parse(set.getString(DATE))
                );
            }
        } catch (SQLException exc) {
            System.out.println("Error in findById() -- Cannot create or execute statement -- Exception message: " + exc.getMessage());
        }
        return null;
    }

    @Override
    public List<CurrencyExchange> findAll() {
        Connection connection = Database.getInstance().getConnection();
        List<CurrencyExchange> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from " + TABLE_NAME)) {
            var set = statement.executeQuery();
            while (set.next()) {
                CurrencyExchange exchange = new CurrencyExchange(
                        Integer.parseInt(set.getString(ID)),
                        Double.parseDouble(set.getString(VALUE)),
                        Integer.parseInt(set.getString(NOMINAL)),
                        set.getString(NAME),
                        set.getString(CODE),
                        LocalDate.parse(set.getString(DATE))
                );
                result.add(exchange);
            }
        } catch (SQLException exc) {
            System.out.println("Error in findAll() -- Cannot create or execute statement -- Exception message: " + exc.getMessage());
        }
        return result;
    }

    @Override
    public List<CurrencyExchange> findAllByCode(String currencyCode) {
        Connection connection = Database.getInstance().getConnection();
        List<CurrencyExchange> result = new ArrayList<>();

        // sql request
        try (PreparedStatement statement = connection.prepareStatement("select * from " + TABLE_NAME + " where " + CODE + "=" + currencyCode)) {
            var set = statement.executeQuery();
            while (set.next()) {
                // create currency exchange object
                CurrencyExchange exchange = new CurrencyExchange(
                        Integer.parseInt(set.getString(ID)),
                        Double.parseDouble(set.getString(VALUE)),
                        Integer.parseInt(set.getString(NOMINAL)),
                        set.getString(NAME),
                        set.getString(CODE),
                        LocalDate.parse(set.getString(DATE))
                );
                result.add(exchange);
            }
        } catch (SQLException exc) {
            System.out.println("Error in findAllByCode() -- Cannot create or execute statement -- Exception message: " + exc.getMessage());
        }
        return result;
    }

    @Override
    public int delete(int id) {
        Connection connection = Database.getInstance().getConnection();
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement("delete from " + TABLE_NAME + " where " + ID + " = " + id)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result++;
            }
        } catch (SQLException exc) {
            System.out.println("Error in delete() -- Cannot create or execute statement -- Exception message: " + exc.getMessage());
        }
        return result;
    }

    @Override
    public void deleteAll() {
        Connection connection = Database.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("delete from " + TABLE_NAME)) {
            statement.execute();
        } catch (SQLException exc) {
            System.out.println("Error in deleteAll() -- Cannot create or execute statement -- Exception message: " + exc.getMessage());
        }
    }

    @Override
    public int insert(CurrencyExchange currency) {
        Connection connection = Database.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                String.format(
                        "insert into %s (%s, %s, %s, '%s', '%s', '%s') values (%d, %s, %d, '%s', '%s', '%s')",
                        TABLE_NAME, ID, VALUE, NOMINAL, NAME, CODE, DATE,
                        currency.getId(), currency.getValue(), currency.getNominal(), currency.getCurrencyName(), currency.getCurrencyCode(), currency.getDate()
                        ))) {
            statement.execute();
        } catch (SQLException exc) {
            System.out.println("Error in insert() -- Cannot create or execute statement -- Exception message: " + exc.getMessage());
            return 0;
        }
        return 1;
    }

    @Override
    public int update(CurrencyExchange currency) {
        Connection connection = Database.getInstance().getConnection();
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(
                String.format(
                        "update %s set %s = %s, %s = %d, %s = %s, %s = %s, %s = %s where %s = %d", TABLE_NAME,
                        VALUE, currency.getValue(),
                        NOMINAL, currency.getNominal(),
                        NAME, currency.getCurrencyName(),
                        CODE, currency.getCurrencyCode(),
                        DATE, currency.getDate(),
                        ID, currency.getId()))) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result++;
            }
        } catch (SQLException exc) {
            System.out.println("Error in update() -- Cannot create or execute statement -- Exception message: " + exc.getMessage());
        }
        return result;
    }



    public CurrencyExchange findById_test(int id) {
        Connection connection = Database.getInstance().getConnection();

        try (PreparedStatement statement = connection.prepareStatement("select * from " + TABLE_NAME + " where " + ID + "=" + id)) {
            var set = statement.executeQuery();
            while (set.next()) {
                return new CurrencyExchange(
                        Integer.parseInt(set.getString(ID)),
                        Double.parseDouble(set.getString(VALUE)),
                        Integer.parseInt(set.getString(NOMINAL)),
                        set.getString(NAME),
                        set.getString(CODE),
                        LocalDate.parse(set.getString(DATE))
                );
            }
        } catch (SQLException exc) {
            System.out.println("Error in findById() -- Cannot create or execute statement -- Exception message: " + exc.getMessage());
        }
        return null;
    }
}
