# Syntax KayJam

## Initial information
All code in KayJam is separated by ** semicolon ** ``;``,
but in some cases it is not necessary to write it
(for example, before the closing wavy bracket ``}`` or before the ``else`` in an ``if``)

```
var test = 123;
if(test)
    return test //To write ';' before the 'else' is not needed
else return false;
```

## Containers
Containers are constructs that contain some code.
The most basic is ``{}``. In basic constructs, all variables passed
and are saved.

```
var test = true;
{
    var test1 = true;
    test = false;
}

// test will be false and the variable test 1 will be created
```

Some do not. For example, in functions, objects, classes, variables
not shared with other containers. They are scoped.

## Variable creation
To **create** a variable, you need to write ``var`` followed by a name.
You need to assign a value through ``=``.
In one scope, a variable can be created **only once**.

A different construction used to change the variables! (see below)

```
var test = "Hello world!";
var test = 123; // Will throw an error

fun test() {
    var test = 123; // There will be no error, but the value will not change, it will remain in the functionContainer
}
```

## Modifying Variables
To change an already created variable, you need to use the construction,
as in creating a variable, but without the word ``var``

```
var test = "Hello world!";
test = 123; // There will be no mistake

fun test() {
    test = 123; // Will throw an error
}
```

## if construct
The ``if`` construct works like this:
if the condition is not equal to ``false``, then the code after the if executed;
is ``false``, then the code after ``else`` will be executed (if specified).

```
var code = false;
if(code) {
    return 1; // This code will never execute
} else {
    return 2; // And this code will be executed
}
```

Also, this code can check and create a variable if it is not equal to false:
```
if(var code = true) {
    return code; //Return true
}
```

With this construction, you can assign (create) an expression:
```
var boolVar = if(true) 
    return "Hello World"
else return false;
```

## Negation (inversion)
With this construction, you can turn false to true and vice versa.
For example, `!false` will output `true`, and `!true` will output `false`.

## Functions
With functions, you don't have to write the same code multiple times.
You can take out some code and call it when you need it.

To write a functionContainer, write ``fun`` or ``functionContainer`` and the name of the functionContainer.
Next, open the parenthesis, list the arguments (``type name``) and close the parenthesis.
Write, by opening the wavy bracket `{`, the code itself.

```
functionContainer test(string str) {
    println(str);
}

fun test(string str) {
    println(str);
}
```

Также вы можете указать тип возврата функции. Для этого нужно после 
перечисления аргументов поставить двоеточие, и написать тип. 
По умолчанию тип возврата void. То есть функция ничего не возвращает.

```
fun test(string str): any {
    return str;
}

fun test(string str): int {
    return str; //Выведет ошибку
}
```

Чтобы вызвать функцию, вы должны написать имя функции и, открыв скобку,
написать аргументы:

```
var test = test("test");

fun test(string str): any {
    return str;
}
```

### Additional Information
You can read about the built-in standard functions of KayJam on 
[this page](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md)

## Объекты
Objects are containers that contain variables and functions.
They can be carried over to other variables.
You can take variables from them and call functions from them.

To create an object, just write `object` and, opening a wavy bracket,
write down the "insides" of the object

```
var testObj = object {
    var test = 100;

    fun test(int test): int {
        return test;
    }
};

testObj.test(testObj.test);
```

## Loops
To make it easier to work, the `for` and` while` loops invented.

### while
The while loop will execute the code until the condition is negative - `false`.

```
var i = 5;
while(i!=0){
    println(i);
    i = i+5;
}
```

## for
This cycle will run as long as a number is within the specified interval.

```
for(i in 0..10)
    println(i);
```

## Classes
> In-dev

Classes are objects, but unlike them, a class can be written once
and instantiate objects from it.

To create a class, you need to write `class`, then the name of the class and, opening a wavy bracket,
write down the "insides" of the class.

```
class A {
    var testVar = 123;

    fun test(): int {
        return this.testVar;
    }
}
```

You can also inherit functions and variables from another class.
You need to add a colon `:` after the class name and write the name of the class you want to inherit.

```
class A {
    var testVar = 123;

    fun test(): int {
        return this.testVar;
    }
}

class B {
    fun getTest(): any {
        return this.test();
    }
}
``` 

So that when creating an instance of a class, you can pass
or perform some functions,
you need to write a constructor in the class. The constructor written like this:
we write inside the class `constructor` and, opening the parenthesis, write the arguments.
After the arguments written, you need to open the rounded parenthesis
and write down the actions to be taken.

```
class A {
    var testVar = 0;

    constructor(int testVar) {
        this.testVar = testVar;
        //Some actions
    }

    fun test(): int {
        return this.testVar;
    }
}
```

To create an instance of a class, you need to write the name of the class and
arguments to one of its constructors. That is, just like calling a function.

```
class A {
    var testVar = 0;

    constructor(int testVar) {
        this.testVar = testVar;
    }

    fun test(): int {
        return this.testVar;
    }
}

var a = A(123);

return a.test();
```

### Additional Information
You can read about the built-in standard KayJam classes on
[this page](https://github.com/KayJamLang/core/blob/main/docs/en/classes.md)

### Companion-object for class
It is an object in a class that is class independent.
This is a static object with which you can interact anywhere on behalf of this class.

Ecample: 
```
class Test {
    companion object {
        var test = 123;

        fun a(): double {
            return 0.5;
        }
    } 
}
```

You can interact with an object using the following construction: `[class name]::[expression]`
Example for the previous class:
```
println(Test::test); //123
println(Test:a()); //0.5
```

Also, if you want to create a companion object, but you will not create a class,
then you can simplify the construction:
```
object Test {
    var test = 123;

    fun a(): double {
       return 0.5;
    }
} 
```

### Additional Information
You can read about the built-in standard functions of KayJam companion objects on
[this page](https://github.com/KayJamLang/core/blob/main/docs/en/functions.md)


## Lambda functions or anonymous functions
Lambda functions (anonymous functions) are functions that
which can be contained in variables.

To write it, you need to write `->` and, opening a parenthesis,
list the arguments. Then you can write any construction,
or open the container and write the code in it.

```
var lambdaFun = -> (test): any {
    return test;
};

lambdaFun = -> (test): any test; //simplify construction 


return lambdaFun(123); // Returns 123
```

Также есть упрощённая запись лямбды без параметров:

```
var lambdaFun = -> {
    return 123;
};

lambdaFun = -> : int 123; //simplify construction 

return lambdaFun(); // Returns 123
``` 

## Arrays

To create a list, write a square bracket `[`
and list the values in it. When all values listed,
must be closed with a square bracket `]`.

```
var test = [1,2,3,"test"];
```

You can also do this by creating an instance of the `array` class, because
the above construct also returns an instance of the class.

```
var test = array(1,2,3,"test");
```

From the list, you can get the value as follows:
```
var test = [1,2,3,"Hello World!"];
var helloWorld = test[3]; 
```

But, since this is an instance of the class, you can call it all through the `get` method.
The first parameter is position. The second parameter is the value that will be returned if the item is not in the list.
(The second parameter is optional)
```
var test = [1,2,3,"Hello World!"];
test.get(0);
//Also with default value
test.get(4, 123);
```

To add a value to the list, you need to call the `add` method
```
var test = [1,2,3,"Hello World!"];
test.add(123);
```

## Using classes, functions from another file
In order not to store all classes, functions in one file, you can use the construction
`use`. You have to write `use` and after that the path to the file:

```
// func.kj
fun helloWorld() {
    println("Hello world!");
}

// main.kj
use "func.kj";

helloWorld();
```

## named functions and named expressions
named expressions is the kind of expression that calls the named function.
As an argument, it has any other expression.
The named function may or may not call it.

Here's an example of a named expression:
```
loop -> print(1);
//Or
loop -> {
    print(1);
}
```

Here is the function itself: 
```
named fun loop {
    while(true) {
        loop(); //Это и есть то самый параметер
    }
}
```

### Additional Information
You can read about KayJam's built-in standard named functions on
[this page](https://github.com/KayJamLang/core/blob/main/docs/en/namedFunctions.md)
