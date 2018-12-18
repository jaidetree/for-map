# for-map

A Clojure library that provides a map-comprehension macro.

```clj
(for-map [x (range 27)]
  {x (str (char (+ 97 x)))})
```

[![Clojars Project](https://img.shields.io/clojars/v/jayzawrotny/for-map.svg)](https://clojars.org/jayzawrotny/for-map) [![Build Status](https://travis-ci.com/jayzawrotny/for-map.svg?branch=master)](https://travis-ci.com/jayzawrotny/for-map)

[![Clojars Project](http://clojars.org/jayzawrotny/for-map/latest-version.svg)](http://clojars.org/jayzawrotny/for-map)

## Installation

### Clojure deps.edn

Add the following to your deps.edn:

```clojure
{jayzawrotny/for-map {:mvn/version "0.1.0"}}
```

### Leiningen

Add the following to your project.clj:

```clojure
[jayzawrotny/for-map "0.1.0"]
```

## Usage

```clojure
(ns my-app.core
  (:require [for-map.core :refer [for-map]]))

(for-map [x (range 27)
            :let [c (str (char (+ 97 x)))]]
  {x c})
```

## Contributing

Pull requests and contributors are welcome :smile:

### REPL
Run the following code in your terminal to start an nREPL server with cider middleware.

```shell
clj -Arepl
```

### Testing

Run unit tests by executing the following command in your terminal:

```shell
clj -Atest:runner
```

## License

Copyright © 2018 Jay Zawrotny

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
