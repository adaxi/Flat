Flat
====

[![Build Status](https://travis-ci.org/adaxi/Flat.svg?branch=master)](https://travis-ci.org/adaxi/Flat)

This library flattens JSON files.

Limitations
-----------

The following example is not possible to unflatten with the current version of the library.
The code will try to create an array with value 1 to index 1, then set the value 1 to index 'x' which fails.
The result of such a JSON should be an object containing two keys: '1' and 'x'.

```json

{
	"a.1": 1,
	"a.x": 1
}

```


Credit
------

Based on the work of [Jeffrey Blattman](https://zerocredibility.wordpress.com/tag/flatten/)
