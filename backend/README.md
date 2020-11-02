# DOCUMENTATION API
### Оглавление
<a href="#пользователи-apiuser">Пользователи</a> | <a href="#анкеты-apiform">Анкеты</a> | <a href="#лайки-apiuserlike">Лайки</a> | <a href="#дизлайки-apiuserdislike">Дизлайки</a>
--- | --- | --- | ---
<a href="#создание-юзера">Создание юзера</a> | <a href="#добавление-анкеты">Добавление анкеты</a> | <a href="#получение-всех-лайков-юзера-с-идентификатором-id">Получение всех лайков юзера с идентификатором</a> | <a href="#получение-всех-дизлайков-юзера-с-идентификатором-id">Получение всех дизлайков юзера с идентификатором `{id}`</a>
<a href="#создание-пачки-юзеров">Создание пачки юзеров</a> | <a href="#получение-всех-анкет">Получение всех анкет</a> | <a href="#получение-всех-лайков-и-дизлайков-юзера-с-идентификатором-id">Получение всех лайков и дизлайков юзера с идентификатором `{id}`</a> | <a href="#добавление-дизлайка-от-from-к-to">Добавление дизлайка от `{from}` к `{to}`</a>
<a href="#получение-всех-юзеров">Получение всех юзеров</a> | <a href="#получение-анкеты-по-идентификатору">Получение анкеты по идентификатору</a> | <a href="#добавление-лайка-от-from-к-to">Добавление лайка от `{from}` к `{to}`</a> | <a href="#удаление-дизлайка-от-from-к-to">Удаление дизлайка от `{from}` к `{to}`</a>
<a href="#получение-юзера-по-идентификатору">Получение юзера по идентификатору</a> | <a href="#получение-анкеты-по-идентификатору-юзера">Получение анкеты по идентификатору юзера</a> | <a href="#удаление-лайка-от-from-к-to">Удаление лайка от `{from}` к `{to}`</a> |
<a href="#получение-юзера-по-юзернейму">Получение юзера по юзернейму</a> | <a href="#обновление-анкеты">Обновление анкеты</a> | |
<a href="#обновление-юзера">Обновление юзера</a> | <a href="#удаление-анкеты-по-идентификатору">Удаление анкеты по идентификатору</a> | |
<a href="#удаление-юзера-по-идентификатору">Удаление юзера по идентификатору</a> | <a href="#удаление-анкеты-по-идентификатору-пользователя">Удаление анкеты по идентификатору пользователя</a> | |
<a href="#удаление-юзера-по-юзернейму">Удаление юзера по юзернейму</a> | | |

<a href="#optional">Optional</a> | <a href="#аутентификация-apiauth">Аутентификация</a> 
--- | ---
<a href="#рукопожатие-с-сервером-и-определение-разрешенных-адресов-методов-и-заголовков">Рукопожатие</a>  | <a href="#аутентификация-по-логину-и-паролю">Аутентификация</a>

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
    "password": "1234",
    "firstName": "test",
    "lastName": "test"
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
    GET address:8080/api/user/{id}/likes (optional param = ?outgoing=true/false) (private)
example response:

<a href="#documentation-api" title="Список разделов">Назад</a>
#### Получение всех лайков и дизлайков юзера с идентификатором `{id}`
    GET address:8080/api/user/{id}/likesDislikes (optional param = ?outgoing=true/false) (private)
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
    GET address:8080/api/user/{id}/dislikes (optional param = ?outgoing=true/false) (private)
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
## Optional
#### Рукопожатие с сервером и определение разрешенных адресов, методов и заголовков
    OPTIONS address:8080 (public)

<a href="#documentation-api" title="Список разделов">Назад</a>