com.io7m.lwjgl.examples
============

[![Build Status](https://img.shields.io/travis/io7m/com.io7m.lwjgl.examples.svg?style=flat-square)](https://travis-ci.org/io7m/com.io7m.lwjgl.examples)

![com.io7m.lwjgl.examples](./src/site/resources/examples.jpg?raw=true)

This repository contains a set of modules containing example code
for each of the LWJGL modules. None of the code is intended to be
explanatory in any way. Instead, the code in each module typically
contains an OSGi component that will try to make calls to the
respective LWJGL module. This can be used to test that the OSGi bundles
correctly resolve and that native library loading works correctly.

