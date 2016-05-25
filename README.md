Flat
====

[![Build Status](https://travis-ci.org/adaxi/Flat.svg?branch=master)](https://travis-ci.org/adaxi/Flat)

This library flattens JSON files.

Before:

```json
{
	"key1": {
		"keyA": "valueI"
	},
	"key2": {
		"keyB": "valueII"
	},
	"key3": { "a": { "b": { "c": 2 } } }
}
```

After:

```json
{
   "key1.keyA": "valueI",
   "key2.keyB": "valueII",
   "key3.a.b.c": 2
}
```

It can also unflatten.


Use the library
---------------

The [documentation page](http://adaxi.github.io/Flat/dependency-info.html) list how you can add this library
as a dependency of your project.


Limitations
-----------

The following example is not possible to unflatten with the current version of the library.

```json

{
	"a.1": 1,
	"a.x": 1
}

```

The code will try to create an array with value 1 to index 1, then set the value 1 to index 'x' which fails.
The result of such a JSON should be an object containing two keys: '1' and 'x'.

Credit
------

Based on the work of [Jeffrey Blattman](https://zerocredibility.wordpress.com/tag/flatten/).
Created to be compatible with [NPM flat](https://www.npmjs.com/package/flat).
