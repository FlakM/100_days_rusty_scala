# 100_days_rusty_scala

100 little tasks showcasing how to perform some operations in both scala and rust

## Day 1 - extend foreign type

Showcase of how to extend types from foreign library ie to add some
behaviour.

### Scala 3

Since scala 3 there are two language level concepts allowing us to do
just that:

- [extension methods](https://docs.scala-lang.org/scala3/book/ca-extension-methods.html)
- [type classes](https://docs.scala-lang.org/scala3/book/ca-type-classes.html)

Both concepts are very powerful because they allow us to implement
polymorphic extensions. 

Example is present in: [Day1.scala](scala/src/main/scala/Day1.scala)

### Rust

Rust compiler has notion of coherence - it forces only one implementation 
of a trait for particular type.
Without this property we would be able to write two implementations of a
trait and the compiler would
have no knowledge which implementation it should use.
The rule that guards against this is called [orphan
rule](https://github.com/Ixrec/rust-orphan-rules#what-are-the-orphan-rules).


There is nothing stopping us though from implementing
our own trait for foreign type (or the opposite).
The code doing just that is presented in
[day1.rs](./rust/examples/day1.rs)

## Day 2 - analyze byte code

### Scala

You can do it using javap:

```bash
cd scala
sbt compile
cd target/scala-3.1.2/classes
# list all methods for all classes
javap -public *.class  

# show byte code for specific class
❯ javap -c Day1\$package
Compiled from "Day1.scala"
public final class Day1$package {
  public static void day1();
    Code:
       0: getstatic     #16                 // Field Day1$package$.MODULE$:LDay1$package$;
       3: invokevirtual #18                 // Method Day1$package$.day1:()V
       6: return

  public static void hello(Person);
    Code:
       0: getstatic     #16                 // Field Day1$package$.MODULE$:LDay1$package$;
       3: aload_0
       4: invokevirtual #22                 // Method Day1$package$.hello:(LPerson;)V
       7: return

  public static <T> void veryNiceWelcoming(T, Welcoming<T>);
    Code:
       0: getstatic     #16                 // Field Day1$package$.MODULE$:LDay1$package$;
       3: aload_0
       4: aload_1
       5: invokevirtual #27                 // Method Day1$package$.veryNiceWelcoming:(Ljava/lang/Object;LWelcoming;)V
       8: return
}❯ javap -c Day1\$package
Compiled from "Day1.scala"
public final class Day1$package {
  public static void day1();
    Code:
       0: getstatic     #16                 // Field Day1$package$.MODULE$:LDay1$package$;
       3: invokevirtual #18                 // Method Day1$package$.day1:()V
       6: return

  public static void hello(Person);
    Code:
       0: getstatic     #16                 // Field Day1$package$.MODULE$:LDay1$package$;
       3: aload_0
       4: invokevirtual #22                 // Method Day1$package$.hello:(LPerson;)V
       7: return

  public static <T> void veryNiceWelcoming(T, Welcoming<T>);
    Code:
       0: getstatic     #16                 // Field Day1$package$.MODULE$:LDay1$package$;
       3: aload_0
       4: aload_1
       5: invokevirtual #27                 // Method Day1$package$.veryNiceWelcoming:(Ljava/lang/Object;LWelcoming;)V
       8: return
}
```

Understanding java binary representation:
[wiki](https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions)


### Rust 

```bash
cd rust
cargo rustc -- --emit asm
# for my case crate name is rust
ls target/release/deps/<crate_name>-<hash>.s

# or alternative:
cargo install cargo-asm
cargo asm rust::main
rust::main:
 sub     rsp, 56
 lea     rax, [rip, +, .L__unnamed_1]
 mov     qword, ptr, [rsp, +, 8], rax
 mov     qword, ptr, [rsp, +, 16], 1
 mov     qword, ptr, [rsp, +, 24], 0
 lea     rax, [rip, +, .L__unnamed_2]
 mov     qword, ptr, [rsp, +, 40], rax
 mov     qword, ptr, [rsp, +, 48], 0
 lea     rdi, [rsp, +, 8]
 call    qword, ptr, [rip, +, _ZN3std2io5stdio6_print17hefffacdf580ae8b0E@GOTPCREL]
 add     rsp, 56
 ret
```

[ASM cheat sheet](http://cs.brown.edu/courses/cs033/docs/guides/x64_cheatsheet.pdf)


## Day 3 - type conversions

### Scala

Scala doesn't have specific mechanisms for casting types.
The closest thing to this are [implicit conversions](https://docs.scala-lang.org/scala3/reference/contextual/conversions.html)

Example is present in: [Day3.scala](scala/src/main/scala/Day3.scala)


### Rust

Rust has two types of conversions: [coercion](https://web.mit.edu/rust-lang_v1.25/arch/amd64_ubuntu1404/share/doc/rust/html/book/first-edition/casting-between-types.html)
and type level expressed using traits [From](https://doc.rust-lang.org/std/convert/trait.From.html) for converstaions that will always success or 
[TryFrom](https://doc.rust-lang.org/stable/std/convert/trait.TryFrom.html)
Checkout [day3.rs](./rust/examples/day3.rs) for simple examples of usage.

## Day 4 - dependent types

Let's say we want to create an implementation of symmetric algorithm
that abstracts over the key length but still is type safe.

### Scala

We can use the [type dependant methods](https://blog.rockthejvm.com/scala-3-dependent-types/) which are super cool 
but due to `Array[Byte]` type we cannot express the requirements for the
length of the array in type system. It would be easy to perform those
checks in runtime.

### Rust

Rust has a type array `[T, usize]` which is parametrized by `T` so array
elements type and second parameter - length of the array. 

After that we can use [const generics](https://doc.rust-lang.org/reference/items/generics.html#const-generics) to abstract over the length of the array.
Checkout [day4.rs](./rust/examples/day4.rs) for simple examples of usage.
