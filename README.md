# CurrencyConverter
Конвертер валют

При запуске приложения, получаем список актуальных 
валют, их курсов и номиналов с сайта ЦБРФ и записываем их в базу данных 
(индентификаторы, коды, названия), а так же курсы (привязанные к валюте) 
на дату запроса. 
Пользователь может выбрать из какой валюты и в какую будет 
конвертация, указывает количество переводимых средств и нажимает кнопку 
"Convert". После чего происходит запрос в БД на получение актуального курса 
на текущую дату, если данные на текущую дату отсутствуют, необходимо, 
произвести обновление базы данных.

![image](https://user-images.githubusercontent.com/125668287/221357632-a7a5f452-565f-41cf-8751-cb1d11e6340a.png)

выбор конечной валюты

![image](https://user-images.githubusercontent.com/125668287/221357735-0670c640-5ea7-4dcd-8eda-4afa854d3d4e.png)

пример перевода в Доллар США


