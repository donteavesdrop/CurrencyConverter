package ru.zakharova.repository;

import ru.zakharova.model.CurrencyExchange;

import java.util.List;

public interface CurrencyExchangeRepository {
    public CurrencyExchange findById(int id);
    public List<CurrencyExchange> findAll();
    public List<CurrencyExchange> findAllByCode(String currencyCode);
    public int delete(int id);
    public void deleteAll();
    public int insert(CurrencyExchange currency);
    public int update(CurrencyExchange currency);
}
