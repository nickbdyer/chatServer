#ChatServer in Java

[![Coverage Status](https://coveralls.io/repos/github/nickbdyer/chatServer/badge.svg)](https://coveralls.io/github/nickbdyer/chatServer) [![Build Status](https://travis-ci.org/nickbdyer/chatServer.svg?branch=master)](https://travis-ci.org/nickbdyer/chatServer)

###Clone

```shell
$ cd <folder where you want to store the project>

$ git clone https://github.com/nickbdyer/chatServer.git

$ cd chatServer/
```

This project has a Gradle Wrapper embedded, so you can run the project and tests without having Gradle on your path.

###Compile
```shell
$ ./gradlew build
```

###Run Program
```shell
$ java -jar build/libs/chatServer.jar <port_number>
```

###Test Program
To see the results in the command line:
```shell
$ ./gradlew cleanTest test
```
Or in your browser:
```shell
$ open reports/tests/index.html
```

