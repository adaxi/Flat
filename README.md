Flat
====

[![Build Status](https://travis-ci.org/adaxi/Flat.svg?branch=master)](https://travis-ci.org/adaxi/Flat) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/6076ee95c7314c9d8ffab07e490c0555)](https://www.codacy.com/app/dev_25/Flat?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=adaxi/Flat&amp;utm_campaign=Badge_Grade) [![Coverage Status](https://coveralls.io/repos/github/adaxi/Flat/badge.svg?branch=master)](https://coveralls.io/github/adaxi/Flat?branch=master)

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

The library can do the revese operation as well: unflatten.


Use the library
---------------

The [documentation page](http://adaxi.github.io/Flat/dependency-info.html) lists how you can add this library
as a dependency of your project.

Credit
------

Based on the work of [Jeffrey Blattman](https://zerocredibility.wordpress.com/tag/flatten/).
Created to be compatible with [NPM flat](https://www.npmjs.com/package/flat).
