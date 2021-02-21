# Встроенные функции KayJam
> Примичание

Перечисленные здесь функции могут быть не везде реализованы,
так как для каждого интерпретатора/исполнителя свой набор функций.

Смотрите реализованные функции у используемого интерпретатора.

- [Функции для работы с консолью](https://github.com/KayJamLang/core/blob/main/docs/ru/functions.md#%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8-%D0%B4%D0%BB%D1%8F-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B-%D1%81-%D0%BA%D0%BE%D0%BD%D1%81%D0%BE%D0%BB%D1%8C%D1%8E)
- [Функции для работы с типом string](https://github.com/KayJamLang/core/blob/main/docs/ru/functions.md#%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8-%D0%B4%D0%BB%D1%8F-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B-%D1%81-%D1%82%D0%B8%D0%BF%D0%BE%D0%BC-string)

## Функции для работы с консолью

- [print](https://github.com/KayJamLang/core/blob/main/docs/ru/functions.md#print)
- [println](https://github.com/KayJamLang/core/blob/main/docs/ru/functions.md#println)

### print
> ``print(any value): string``

#### Описание
Выводит любое значение в консоль без переноса на следующую строку

#### Параметры
`value` - строка

#### Пример использования
```
print("Hello, ");
print("World!");
```

Выведет:
```
Hello, World!
```

### println
> ``println(any value): string``

#### Описание
Выводит любое значение в консоль с переносом на следующую строку

#### Параметры
`value` - строка

#### Пример использования
```
println("Hello, World!");
print("Can you use KayJam?");
```

Выведет:
```
Hello, World!
Can you use KayJam?
```

## Функции для работы с типом string

- [String::charAt](https://github.com/KayJamLang/core/blob/main/docs/ru/functions.md#stringcharat)
- [String::replace](https://github.com/KayJamLang/core/blob/main/docs/ru/functions.md#stringreplace)
- [String::join](https://github.com/KayJamLang/core/blob/main/docs/ru/functions.md#stringjoin)

### String::charAt
> ``String::charAt(string source, int position): string``

#### Описание
Функция возвращает символ, находящийся на указанной позиции.

#### Параметры
`source` - строка
`position` - позиция символа в строке

#### Пример использования
```
var str = "KayJam is cool!";
println(String::charAt(str, 3));
```

Выведет:
```
J
```

### String::replace
> ``String::replace(string source, any search, any replaceValue): string``

#### Описание
Функция возвращает строку с заменённым значением.

#### Параметры
`source` - строка
`search` - значение, которое требуется заменить
`replaceValue` - значение замены

#### Пример использования
```
var str = "KayJam is bad!";
println(String::replace(str, "bad", "cool"));
```

Выведет:
```
KayJam is cool!
```

### String::join
> ``String::join(array strings, any delimiter): string``

#### Описание
Объединяет список в строку.

#### Параметры
`strings` - список со значениями, которые нужно объединить
`delimiter` - разделитель

#### Пример использования
```
var array = ["Can","you","buy","?"];
println(String::join(array, " "));
```

Выведет:
```
Can you buy?
```

