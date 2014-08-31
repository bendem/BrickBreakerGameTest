# PongGameTest

This is a test to write a little game by myself, while discovering some new java 8 features and how to not use awt.

## Compilation

As always the easiest way to compile this is using maven (``mvn clean package``).

Note that this project is written using java 8 new features, you won't be able to compile/use it using an earlier
version.

## Running

I'll be using OpenGL31 and lwjgl so when launching the application, you'll need ``-Djava.library.path=target\natives``
in your startup command.
