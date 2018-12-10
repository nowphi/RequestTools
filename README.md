# RequestTools

A client to request results with choosen pattern from verivox

The client requests from verivox.de a benchmarking for a recent patter. The parser VeriVoxParser.java uses not the standard json format for parsing the response.

This code uses a reference implementation from https://github.com/stleary/JSON-java/blob/master/README.md and https://github.com/google/gson/blob/master/README.md. 

The package for json parsing compiles on Java 1.6-1.8 and is absolutely needed to run the application! Thanks to org.json and gson.

Release history: 10.12.2018 - Refactored.
Release history: 08.12.2018 - Version 1.0 is released.
