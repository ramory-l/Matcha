# DOCUMENTATION API
* <a href="#aутентификация-apiauth">Аутентификация</a>
* <a href="#пользователи-apiuser">Пользователи</a>
* <a href="#анкеты-apiform">Анкеты</a>
* <a href="#лайки-apiuserlike">Лайки</a>
* <a href="#дизлайки-apiuserdislike">Дизлайки</a>
* <a href="#cors">CORS</a>
## Аутентификация (`/api/auth/`)
#### Аутентификация по логину и паролю
    POST address:8080/api/auth/login (public)
body:
```
{
    "username": "test",
    "password": "test"
}
```
example response: `200 "{jwtToken}"`
## Пользователи (`/api/user/`)
#### Создание юзера
    POST address:8080/api/user (public)
body:
```
{
    "username": "test",
    "email": "test@testov.ru",
    "password": "1234"
}
```
example response: `204 ""`
#### Создание пачки юзеров
    POST address:8080/api/user/batch (private)
body:
```
```
example response: `204 ""`
#### Получение всех юзеров
    GET address:8080/api/user (private)
example response:    
#### Получение юзера по идентификатору
    GET address:8080/api/user/{id} (private)
example response:    
#### Получение юзера по юзернейму
    GET address:8080/api/user/username/{username} (private)
example response:
```
{
    "username": "test",
    "firstName": "",
    "lastName": "",
    "email": "test@testov.ru",
    "gender": null,
    "birthday": null,
    "description": "",
    "form": {
        "man": false,
        "woman": false,
        "friendship": false,
        "love": false,
        "sex": false,
        "flirt": false
    }
}
```
#### Обновление юзера
    PUT address:8080/api/user (private)
body:
```
{
    "username": "test",
    "firstName": "test",
    "lastName": "test",
    "email": "test",
    "gender": "m/w",
    "birthday": date,
    "description": "test"
}
```
example response: `"User with username: test updated"`
#### Удаление юзера по идентификатору
    DELETE address:8080/api/user/{id} (private)
example response:
#### Удаление юзера по юзернейму
    DELETE address:8080/api/user/username/{username} (private)
example response:
## Анкеты (`/api/form`)
#### Добавление анкеты
    POST address:8080/api/form (private)
body:
```
{
    "man": true/false,
    "woman": true/false,
    "friendship": true/false,
    "love": true/false,
    "sex": true/false,
    "flirt": true/false
}
```
example response: `200 "1"`
#### Получение всех анкет
    GET address:8080/api/form/all (private)
example response:
#### Получение анкеты по идентификатору
    GET address:8080/api/form/{id} (private)
example response:
#### Получение анкеты по идентификатору юзера
    GET address:8080/api/form/user/{id} (private)
example response:
#### Обновление анкеты
    PUT address:8080/api/form/{userId} (private)
body:
```
{
    "man": true/false,
    "woman": true/false,
    "friendship": true/false,
    "love": true/false,
    "sex": true/false,
    "flirt": true/false
}
```
example response: `204 ""`
#### Удаление анкеты по идентификатору
    DELETE address:8080/api/form/{id} (private)
example response: `204 ""`
#### Удаление анкеты по идентификатору пользователя
    DELETE address:8080/api/form/user/{id} (private)
example response: `204 ""`
## Лайки (`/api/user/like`)
#### Получение всех лайков юзера с идентификатором `{id}`    
    GET address:8080/api/user/{id}/likes (private)
example response:
#### Получение всех лайков и дизлайков юзера с идентификатором `{id}`
    GET address:8080/api/user/{id}/likesDislikes (private)
example response:
#### Добавление лайка от `{from}` к `{to}`
    POST address:8080/api/user/like/from/{from}/to/{to} (private)
example response: `204 ""
#### Удаление лайка от `{from}` к `{to}`
    DELETE address:8080/api/user/like/from/{from}/to/{to} (private)
example response: `204 ""`
## Дизлайки (`/api/user/dislike`)
#### Получение всех дизлайков юзера с идентификатором `{id}`
    GET address:8080/api/user/{id}/dislikes (private)
example response: 
#### Добавление дизлайка от `{from}` к `{to}`
    POST address:8080/api/user/dislike/from/{from}/to/{to} (private) -
example response: `204 ""`
#### Удаление дизлайка от `{from}` к `{to}`
    DELETE address:8080/api/user/dislike/from/{from}/to/{to} (private) -
example response: `204 ""`
## CORS
#### Рукопожатие с сервером и определение разрешенных адресов, методов и заголовков
    OPTIONS address:8080 (public)