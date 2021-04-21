# Built-in named functions
> Note

The classes listed here may not be implemented everywhere,
since each interpreter/executor/compiler has its own set of components and libraries.

See the implemented functions for the interpreter used.

## Functions for working with threads
> P.S. May be absent in some places where there is no possibility of working with streams

- [thread](https://github.com/KayJamLang/core/blob/main/docs/en/namedFunctions.md#thread)
- [async](https://github.com/KayJamLang/core/blob/main/docs/en/namedFunctions.md#async)

### thread
> ``named fun thread``

#### Description
Creates and starts a new thread.

#### Example
```
var str = "test";
thread {
    str = "123";
}

thread -> println(str);
```

Output:
```
test
```
else
```
123
```
Depends on the speed of execution

### async
> ``named fun async``

#### Description
Executes code asynchronously

#### Example
```
async {
    Thread::Sleep(1000);
    println("Dot №1");
}

println("Dot №2");
Thread::sleep(2000);
println("Dot №3");
```

Output:
```
Dot №2
Dot №1
Dot №3
```
