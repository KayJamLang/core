# Class `array`

## Variables 
None

## Functions
- [add(any value): array](https://github.com/KayJamLang/core/blob/main/docs/en/classes/array.md#addany-value-array)
- [get(int key): any](https://github.com/KayJamLang/core/blob/main/docs/en/classes/array.md#getint-key-any)
- [get(int key, any defaultValue): any](https://github.com/KayJamLang/core/blob/main/docs/en/classes/array.md#getint-key-any-defaultvalue-any)

### add(any value): array
Adds the last item to the list

#### Parameters 
value - item

#### Example
```
var array = [];
array.add(1);
println(array[0]);
```

Output:
```
1
```

### get(int key): any
Returns an element at a given position (Can work with the get construct)

#### Parameters 
key - position

#### Example
```
var array = [1,2,3];
println(array.get(2));
println(array.get(3));
println(array[2]==array.get(2));
```

Output:
```
2
false
true
```

### get(int key, any defaultValue): any
Returns an element at a given position

#### Parameters 
key - position
defaultValue - value to return if the item is missing

#### Example
```
var array = [1,2,3];
println(array.get(2, 123));
println(array.get(3, 123));
println(array[2]==array.get(2, 123));
```

Output:
```
2
123
true
```
