# Класс `map`

## Переменные 
Отсутствуют

## Функции
- [put(any key, any value): map](https://github.com/KayJamLang/core/blob/main/docs/ru/classes/map.md#putany-key-any-value-map)
- [get(any key): any](https://github.com/KayJamLang/core/blob/main/docs/ru/classes/map.md#getany-key-any)
- [get(any key, any default): any](https://github.com/KayJamLang/core/blob/main/docs/ru/classes/map.md#getany-key-any-default-any)

### put(any key, any value): map
Вставляет значение в map с ключём

#### Параметры
key - ключ
value - значение

#### Пример
```
var m = map()
          .put("key", 123);
println(m["key"]);
```
Выведет:
```
123
```

### get(any key): any
Выдаёт значение по ключу (Может работать с конструкцией get)

#### Параметры
key - ключ

#### Пример
```
var m = map()
          .put("hello", "Hello");
print(m.get("hello")+" ");
println(m.get("world"));
```
Выведет:
```
Hello false
```

### get(any key, any default): any
Выдаёт значение по ключу (Может работать с конструкцией get)
Если элемента с ключём нет, то вернёт значение по умолчанию

#### Параметры
key - ключ
defalut - значение по умолчанию

#### Пример
```
var m = map()
          .put("hello", "Hello");
print(m.get("hello")+" ");
println(m.get("world", "world!"));
```
Выведет:
```
Hello world!
```
