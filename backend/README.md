# DOCUMENTATION API
### Оглавление
<a href="#пользователи-apiuser">Пользователи</a> | <a href="#анкеты-apiform">Анкеты</a> | <a href="#лайки-apiuserlike">Лайки</a> | <a href="#дизлайки-apiuserdislike">Дизлайки</a>
--- | --- | --- | ---
Создание юзера | Добавление анкеты | Получение всех лайков юзера с идентификатором | Получение всех дизлайков юзера с идентификатором `{id}`
Создание пачки юзеров | Получение всех анкет | Получение всех лайков и дизлайков юзера с идентификатором `{id}` | Добавление дизлайка от `{from}` к `{to}`
Получение всех юзеров | Получение анкеты по идентификатору | Добавление лайка от `{from}` к `{to}` | Удаление дизлайка от `{from}` к `{to}`
Получение юзера по идентификатору | Получение анкеты по идентификатору юзера | Удаление лайка от `{from}` к `{to}` |
Получение юзера по юзернейму | Обновление анкеты | |
Обновление юзера | Удаление анкеты по идентификатору | |
Удаление юзера по идентификатору | Удаление анкеты по идентификатору пользователя | |
Удаление юзера по юзернейму | | |
Обновление юзера | | |

<a href="#cors">CORS</a> | <a href="#аутентификация-apiauth">Аутентификация</a> 
--- | ---
Рукопожатие  | Аутентификация 
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

<a href="#documentation-api" title="Список разделов">Назад</a>
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

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Создание пачки юзеров
    POST address:8080/api/user/batch (private)
body:
```
```
example response: `204 ""`

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Получение всех юзеров
    GET address:8080/api/user (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Получение юзера по идентификатору
    GET address:8080/api/user/{id} (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
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

<a href="#documentation-api" title="Список разделов">Назад</a>
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

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Удаление юзера по идентификатору
    DELETE address:8080/api/user/{id} (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Удаление юзера по юзернейму
    DELETE address:8080/api/user/username/{username} (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
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

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Получение всех анкет
    GET address:8080/api/form/all (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Получение анкеты по идентификатору
    GET address:8080/api/form/{id} (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Получение анкеты по идентификатору юзера
    GET address:8080/api/form/user/{id} (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
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

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Удаление анкеты по идентификатору
    DELETE address:8080/api/form/{id} (private)
example response: `204 ""`

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Удаление анкеты по идентификатору пользователя
    DELETE address:8080/api/form/user/{id} (private)
example response: `204 ""`

<a href="#documentation-api" title="Список разделов">Назад</a>
## Лайки (`/api/user/like`)
#### Получение всех лайков юзера с идентификатором `{id}`    
    GET address:8080/api/user/{id}/likes (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Получение всех лайков и дизлайков юзера с идентификатором `{id}`
    GET address:8080/api/user/{id}/likesDislikes (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Добавление лайка от `{from}` к `{to}`
    POST address:8080/api/user/like/from/{from}/to/{to} (private)
example response: `204 ""

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Удаление лайка от `{from}` к `{to}`
    DELETE address:8080/api/user/like/from/{from}/to/{to} (private)
example response: `204 ""`

<a href="#documentation-api" title="Список разделов">Назад</a>
## Дизлайки (`/api/user/dislike`)
#### Получение всех дизлайков юзера с идентификатором `{id}`
    GET address:8080/api/user/{id}/dislikes (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Добавление дизлайка от `{from}` к `{to}`
    POST address:8080/api/user/dislike/from/{from}/to/{to} (private) -
example response: `204 ""`

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Удаление дизлайка от `{from}` к `{to}`
    DELETE address:8080/api/user/dislike/from/{from}/to/{to} (private) -
example response: `204 ""`

<a href="#documentation-api" title="Список разделов">Назад</a>
## CORS
#### Рукопожатие с сервером и определение разрешенных адресов, методов и заголовков
    OPTIONS address:8080 (public)

<a href="#documentation-api" title="Список разделов">Назад</a>