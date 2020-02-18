# Truffle based Lisp implementation

## Syntax

**Lisp** (**LIS**t **P**rocessor) is a programming language with a parenthesized prefix notation.

`( OPERATOR OPERAND1 OPERAND2 ... )`

Lists are also operands, which means they can be nested.

`( OPERATOR1 ( OPERATOR2 OPERAND21 OPERAND22 ... ) OPERAND12 ... )`

### Operators:
 - arithmetic: `+` `-` `/` `*`
 - comparison: `==` `eq` `!=` `>` `>=` `<` `<=`
 - conjunction: `!` `not` `&&` `and` `||` `or`
 - control flow: `(if CONDITION TRUEBRANCH FALSEBRANCH )`

### Types:
 - numbers: floating point numbers (may start with `.`)
 - booleans: `TRUE` `FALSE`

## Usage

> Note: all the commands may be entered manually, but Makefile usage is simpler (GNU Make is required)

### Building project

> Note: changing Makefile variables JAVAHOME and MX may be required

Run `make build`.

### Reading commands from the file

Modify `sample.lisp` file.

Run `make script`.

### Running Lisp REPL (*R*ead *E*val *P*rint *L*oop)

Run `make repl`.

`Ctrl+D` (**EOF**) to exit.

### Remove all class files, images and executables

Run `make clean`.

## License and Copyright

This project is licensed under the [MIT License](https://choosealicense.com/licenses/mit/).

© 2020 Andrii Dmytruk, Volodymyr Chernetskyi

# Lisp Lambdas Implementation

We also implemented the lambdas and scope support for lisp (but in another branch and didn't merge them yet).
The branch [lambdas](https://github.com/KangaroosInAntarcitica/LispTruffle/tree/lambdas) has following syntax supported.

* Opertaions: `(+ 1 2 3 (* 2 3))`
* Defining variables: `(define a value)`
* Lambda functions (with scopes): `(lambda arg arg)` or `(lambda (arg1, arg2) (* arg1 2 arg2))`
* Printing: `println (+ 1 2)`
* If: `(if 2 3 0)`

