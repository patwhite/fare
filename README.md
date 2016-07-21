# FARE

## Overview

FARE is a platform for Augmeneted Reality and location based applications.

## Getting Started

### Start the Server
To start the server, run:

    ./fare

This will spin up a server, in a default configuration, with 100,000 Non Player Actors randomly roaming the world.
Feel free to play with the number of NPA's to see how FARE scales.

    ./fare --npa 1500000

### Command Line Arguments

```
Usage: fare [options]
  -n, --npa <value>  Count of Non Player Actors to spin up. Defaults to 100,000
  --help             Prints this usage text.
```

### Performance
Currently, FARE is using the Concurrent Mark Sweep garbage collector with a parallel runner, and requests up to 8GB of RAM.

In the future, we'll add the configuration settings for
