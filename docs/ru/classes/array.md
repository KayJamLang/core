# Класс `array`

## Переменные 
Отсутствуют

## Функции
- [add(any value): array](https://github.com/KayJamLang/core/blob/main/docs/ru/classes/array.md#addany-value-array)
- [get(int key): any](https://github.com/KayJamLang/core/blob/main/docs/ru/classes/array.md#getint-key-any)
- [get(int key, any defaultValue): any](https://github.com/KayJamLang/core/blob/main/docs/ru/classes/array.md#getint-key-any-defaultvalue-any)

### add(any value): array
Добавляет элемент в список последним

#### Параметры 
value - элемент

#### Пример
```
var array = [];
array.add(1);
println(array[0]);
```

Выведет:
```
1
```

### get(int key): any
Возвращает элемент по заданной позиции

#### Параметры 
key - ключ

#### Пример
```
var array = [1,2,3];
println(array.get(2));
println(array.get(3));
println(array[2]==array.get(2));
```

Выведет:
```
2
false
true
```

### get(int key, any defaultValue): any
Возвращает элемент по заданной позиции

#### Параметры 
key - ключ
defaultValue - значение, которое нужно вернуть, если элемент отсутсвует

#### Пример
```
var array = [1,2,3];
println(array.get(2, 123));
println(array.get(3, 123));
println(array[2]==array.get(2, 123));
```

Выведет:
```
2
123
true
```
