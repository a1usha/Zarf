# Zarf programming lanuage

Toy programming language compiled to Java bytecode and JIT-compiled during execution as regular Java programs to improve performance.

## Why zarf?

Just because the word sounds cool.

A zarf (plural: zarfs, zuruuf, zarves) is a holder, usually of ornamental metal, for a coffee cup without a handle. [Wikipedia](https://en.wikipedia.org/wiki/Zarf) 

<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/01464_vintage_Russian_Soviet_silver_cup_tea_holder.jpg/220px-01464_vintage_Russian_Soviet_silver_cup_tea_holder.jpg" />
</p>

Programming is always about writing some kind of wrappers over objects in the real world, so this name suits is more than anything.

## What can Zarf do?

- Work with integers and strings
- Print them to the console
- Concat strings
- Conditional operators if...else...
- Goto
- Arithmetic expressions

Hello world program
```
say "Hello World!"
```

Compute factorial
```
let num = 6
let fact = 1
let i = 1

!loop
fact = fact * i
i = i + 1
if i <= num {
    goto loop
}

say "Factorial of"
say num
say "is"
say fact
```

Check if number is even or odd
```
let num = 4

if (num % 2 == 0) {
    say "Even"
} else {
    say "Odd"
}
```

More examples can be found [here](https://github.com/a1usha/ojp/tree/main/ojp_4/ZarfExamples)

## How to use and compile?
Just run `gradle jar` in cloned repo to create Zarf compiler. After that, pass `.zf` file to `zarf.jar`. It will generate the bytecode and give you a `.class` file that can be run later.

```
> java -jar .\build\libs\zarf.jar .\ZarfExamples\Arithm.zf
> java Arithm
```

## How is it done?
The grammar and creation of an abstract syntax tree is done using [Antlr](https://www.antlr.org/), bytecode generation is done using [ASM](https://asm.ow2.io/) library.