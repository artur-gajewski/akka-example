# Akka worker example

This example contain an Actor that listens to imcoming processes and distributes them into ten different workers in Round-robin fashion.

## Usage

    $ scalac akka-example.scala
    $ scala Main