# Class `map`

## Variables 
None

## Functions
- [put(any key, any value): map](https://github.com/KayJamLang/core/blob/main/docs/en/classes/map.md#putany-key-any-value-map)
- [get(any key): any](https://github.com/KayJamLang/core/blob/main/docs/en/classes/map.md#getany-key-any)
- [get(any key, any default): any](https://github.com/KayJamLang/core/blob/main/docs/en/classes/map.md#getany-key-any-default-any)

### put(any key, any value): map
Inserts a value into a map with a key

#### Parameters
key - key
value - value

#### Example
```
var m = map()
          .put("key", 123);
println(m["key"]);
```
Output:
```
123
```

### get(any key): any
Returns a value by key (Can work with the get construct)

#### Parameters
key - key

#### Example
```
var m = map()
          .put("hello", "Hello");
print(m.get("hello")+" ");
println(m.get("world"));
```
Output:
```
Hello false
```

### get(any key, any default): any
Returns a value by key (Can work with the get construct)
If there is no element with a key, then it will return the default value.

#### Parameters
key - key
default - default value

#### Пример
```
var m = map()
          .put("hello", "Hello");
print(m.get("hello")+" ");
println(m.get("world", "world!"));
```
Output:
```
Hello world!
```
