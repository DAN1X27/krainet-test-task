# Krainet Тестовое Java Чебан Даниил

## Запуск

Для запуска системы необходимо создать сеть в Docker с помощью команды -
```docker network create krainet-test-task-net```,
после чего запустить 2 сервиса: auth-service и notifications-service, используя команду -
```docker-compose up --build```
в каждом сервисе.
Перед запуском notifications-service в application.yml файле укажите данные для отправки сообщений на почту:

```yaml
  mail:
    host: smtp.gmail.com
    port: 587
    username: ваш email
    password: ваш пароль
```
Для тестирования отправки уведомлений поменяйте email у пользователей с ролью ADMIN в базе данных auth-service.
### Пользователи
* email user1@gmail.com пароль password роль USER
* email user2@gmail.com пароль password роль USER
* email user3@gmail.com пароль password роль USER
* email user4@gmail.com пароль password роль USER
* email user5@gmail.com пароль password роль USER
* email admin1@gmail.com пароль password роль ADMIN
* email admin2@gmail.com пароль password роль ADMIN
* email admin3@gmail.com пароль password роль ADMIN
* email admin4@gmail.com пароль password роль ADMIN
* email admin5@gmail.com пароль password роль ADMIN