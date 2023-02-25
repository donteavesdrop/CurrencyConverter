package ru.zakharova.http;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.zakharova.data_transfer.ValCurs;

public interface ExchangeService {
    @GET("/scripts/XML_daily.asp")
    ValCurs GetExchange(@Query("date_req") String date);
}
