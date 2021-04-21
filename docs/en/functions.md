# Built-in functions
> Note

The classes listed here may not be implemented everywhere,
since each interpreter/executor/compiler has its own set of components and libraries.

See the implemented functions for the interpreter used.

- [Functions for working with the console](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8-%D0%B4%D0%BB%D1%8F-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B-%D1%81-%D0%BA%D0%BE%D0%BD%D1%81%D0%BE%D0%BB%D1%8C%D1%8E)
- [Functions for working with the string type](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8-%D0%B4%D0%BB%D1%8F-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B-%D1%81-%D1%82%D0%B8%D0%BF%D0%BE%D0%BC-string)
- [Functions for working with threads](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#%D1%84%D1%83%D0%BD%D0%BA%D1%86%D0%B8%D0%B8-%D0%B4%D0%BB%D1%8F-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B-%D1%81-%D0%BF%D0%BE%D1%82%D0%BE%D0%BA%D0%B0%D0%BC%D0%B8)
- [Прочее](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#%D0%9F%D1%80%D0%BE%D1%87%D0%B5%D0%B5)

## Functions for working with the console

- [print](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#print)
- [println](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#println)

### print
> ``print(any value): string``

#### Description
Prints any value to the console without wrapping to the next line

#### Parameters
`value` - any value

#### Example
```
print("Hello, ");
print("World!");
```

Output:
```
Hello, World!
```

### println
> ``println(any value): string``

#### Description
Prints any value to the console with a break to the next line

#### Parameters
`value` - any value

#### Example
```
println("Hello, World!");
print("Can you use KayJam?");
```

Output:
```
Hello, World!
Can you use KayJam?
```

## Functions for working with the string type

- [String::charAt](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#stringcharat)
- [String::replace](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#stringreplace)
- [String::join](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#stringjoin)
- [match](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#match)
- [matchAll](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#matchAll)

### String::charAt
> ``String::charAt(string source, int position): string``

#### Description
The functionContainer returns the character at the specified position.

#### Parameters
`source` - any string
`position` - character position in the string

#### Example
```
var str = "KayJam is cool!";
println(String::charAt(str, 3));
```

Output:
```
J
```

### String::replace
> ``String::replace(string source, any search, any replaceValue): string``

#### Description
The functionContainer returns a string with the replaced value.

#### Parameters
`source` - any string
`search` - value to be replaced
`replaceValue` - replaced value

#### Example
```
var str = "KayJam is bad!";
println(String::replace(str, "bad", "cool"));
```

Output:
```
KayJam is cool!
```

### String::join
> ``String::join(array strings, any delimiter): string``

#### Description
Concatenates the list into a string.

#### Parameters
`strings` - array with values to be concatenated
`delimiter` - delimiter

#### Example
```
var array = ["Can","you","buy","?"];
println(String::join(array, " "));
```

Output:
```
Can you buy?
```

### match
> ``match(string pattern, string string): array|bool``

#### Description
Checks against a regular expression and returns and returns search results

#### Parameters
`pattern` - The desired pattern as a string
`string` - Input string

#### Example
```
if (var result = match("123", "12454353123")) 
    println("Entry found: "+result)
else println("No entry found.");
```

Output:
```
Entry found: [123, 123]
```

### match
> ``match(string pattern, string string): array|bool``

#### Description
Checks against a regular expression and returns all possible search results

#### Parameters
`pattern` - The desired pattern as a string
`string` - Input string

#### Example
```
if (var result = matchAll("123", "12412354353123")) 
    println(result)
else println("No entry found.");
```

Output:
```
[[123, 123], [123, 123]]
```

## Functions for working with threads

- [Threads::sleep](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#threadssleep)

### Threads::sleep
> ``Threads::sleep(int ms): string``

#### Description
Stops the current thread for a few milliseconds

#### Parameters 
`ms` - time in milliseconds to stop the current thread

## Other
- [getKayJamVersion](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md#getKayJamVersion)

### getKayJamVersion
> ``getKayJamVersion(): int``

#### Description
Returns the KayJam version code


