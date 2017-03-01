# Voting Application

Standalone RESTful приложение для голосования. Имеет два представления: Главная страница (по адресу */voting); Страница статистики (по адресу */voting/{votingId}).
- На главной странице отображаются: Список всех зарегистрированных голосований (пагинация реализована через ajax кпокой "Load More", загрузка осуществляется по 10 голосований); Форма для создания новой темы голосования состоит из поля "Заголовка темы" и поля опций (которые разделены переносом строки). После удачной регистрации нового голосования под формой отображается ссылка на страницу статистики данного голосования.
<details> 
  <summary>Example</summary>
  ![alt tag](http://i67.tinypic.com/t8sr2e.png)
</details>
- На странице статистики имеются кнопки для голосования за каждый из вариантов, кнопка закрытия голосования "Close voting", и кнопка возврата на главную страницу.
<details> 
  <summary>Example</summary>
  ![alt tag](http://i63.tinypic.com/33tq8g1.png)
</details>

## Быстрый старт
```sh
$ git clone https://github.com/DenisNaukovich/Voting-application.git
$ cd Voting-application
$ mvn clean package && mvn spring-boot:run
```

## Настройка базы данных
В классе SqlInitialization следует задать Ваши логин и пароль
```java
dataSource.setUsername("your-username");
dataSource.setPassword("your-password");
```

## Тестирование в браузере
http://localhost:8083/

## Стек технологий
Проект использует следующие технологии: <br/>
- **web/REST**: [Spring Boot](https://projects.spring.io/spring-boot/) 1.5.1.RELEASE <br/>
- **marshalling**: [Jackson](https://github.com/FasterXML/jackson-databind) 2.8.6 (for JSON) <br/>
- **persistence**: [Spring Data JPA](http://www.springsource.org/spring-data/jpa) and [Hibernate](http://www.hibernate.org/) 5 <br/>
- **persistence providers**: PostgreSQL <br/>
- **frontend**: jQuery, Bootstrap <br/>
- **build automation**: [Maven](http://maven.apache.org) 3
