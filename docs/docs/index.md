[![CircleCI branch](https://img.shields.io/circleci/project/mplatvoet/kovenant/master.svg)](https://circleci.com/gh/mplatvoet/kovenant/tree/master) [![Maven Central](https://img.shields.io/maven-central/v/nl.mplatvoet.komponents/kovenant.svg)](http://search.maven.org/#browse%7C-339523586) [![DUB](https://img.shields.io/dub/l/vibe-d.svg)](https://github.com/mplatvoet/kovenant/blob/master/LICENSE)

#Kovenant
[Promises](http://en.wikipedia.org/wiki/Futures_and_promises) for [Kotlin](http://kotlinlang.org)

```kt
async {
	//some (long running) operation, or just:
	1 + 1
} then { 
	i -> "result: $i"	
} success { 
	msg -> println(msg)
}
```

Developed with the following [goals](goals.md) in mind.

* **Easy to use**: Function above anything else
* **Runtime agnostic**: API layer must be pure Kotlin
* **Memory efficient**: trying to reduce the overhead as much as possible
* **Non blocking**: besides being thread safe everything should be non blocking (JVM)
* **Dependency free**: when not counting kotlin std 

## Getting started
This version is build against `Java 7` and `kotlin-stdlib:0.11.91`.


###Gradle
```groovy
dependencies {
    compile 'nl.mplatvoet.komponents:kovenant:0.1.+'
}
```

###Maven
```xml
<dependency>
	<groupId>nl.mplatvoet.komponents</groupId>
	<artifactId>kovenant</artifactId>
	<version>[0.1.0,0.2.0)</version>
</dependency>
```
##Issues
Issues are tracked in [Youtrack](http://komponents.myjetbrains.com/youtrack/issues?q=project%3A+Kovenant)