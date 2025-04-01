underscore-kotlin 
=================
[![Maven Central](https://img.shields.io/maven-central/v/com.github.javadev/underscore-kotlin.svg)](https://central.sonatype.com/artifact/com.github.javadev/underscore-kotlin/1.11)
[![Java CI with Maven](https://github.com/kotlindev/underscore-kotlin/actions/workflows/maven.yml/badge.svg)](https://github.com/kotlindev/underscore-kotlin/actions/workflows/maven.yml)
[![javadoc](https://javadoc.io/badge2/com.github.javadev/underscore-kotlin/javadoc.svg)](https://javadoc.io/doc/com.github.javadev/underscore-kotlin)


Requirements
============

Koltin 2.1, [Java 11 and later](https://github.com/javadev/underscore-java) or [Java 17](https://github.com/javadev/underscore-java17)

## Installation

To configure your Maven project, add the following code to your pom.xml file:

```xml
<dependencies>
  <dependency>
    <groupId>com.github.javadev</groupId>
    <artifactId>underscore-kotlin</artifactId>
    <version>1.11</version>
  </dependency>
  ...
</dependencies>
```

Gradle configuration:

```groovy
implementation 'com.github.javadev:underscore-kotlin:1.11'
```

### Usage

```java
U.of(/* array | list | set | map | anything based on Iterable interface */)
    .filter(..)
    .map(..)
    ...
    .sortWith()
    .forEach(..);
U.of(value1, value2, value3)...
U.range(0, 10)...

U.of(1, 2, 3) // or java.util.Arrays.asList(1, 2, 3) or new Integer[] {1, 2, 3}
    .filter(v -> v > 1)
    // 2, 3
    .map(v -> v + 1)
    // 3, 4
    .sortWith((a, b) -> b.compareTo(a))
    .forEach(System.out::println);
    // 4, 3
    
U.of(1, 2, 3) // or java.util.Arrays.asList(1, 2, 3) or new Integer[] {1, 2, 3}
    .mapMulti((num, consumer) -> {
        for (int i = 0; i < num; i++) {
            consumer.accept("a" + num);
        }
    })
    .forEach(System.out::println);
    // "a1", "a2", "a2", "a3", "a3", "a3"

U.formatXml("<a><b>data</b></a>", Xml.XmlStringBuilder.Step.TWO_SPACES);
    // <a>
    //   <b>data</b>
    // </a>

U.formatJson("{\"a\":{\"b\":\"data\"}}", Json.JsonStringBuilder.Step.TWO_SPACES);
    // {
    //   "a": {
    //     "b": "data"
    //   }
    // }

U.xmlToJson(
    "<mydocument has=\"an attribute\">\n"
        + "   <and>\n"
        + "   <many>elements</many>\n"
        + "    <many>more elements</many>\n"
        + "   </and>\n"
        + "   <plus a=\"complex\">\n"
        + "     element as well\n"
        + "   </plus>\n"
        + "</mydocument>",
    Json.JsonStringBuilder.Step.TWO_SPACES);
    // {
    //   "mydocument": {
    //     "-has": "an attribute",
    //     "and": {
    //       "many": [
    //         "elements",
    //         "more elements"
    //       ]
    //     },
    //     "plus": {
    //       "-a": "complex",
    //       "#text": "\n     element as well\n   "
    //     }
    //   },
    //   "#omit-xml-declaration": "yes"
    // }

U.xmlToJsonMinimum(
    "<data>\n"
        + "    <string>Example Text</string>\n"
        + "    <integer>42</integer>\n"
        + "    <float>3.14</float>\n"
        + "    <boolean>true</boolean>\n"
        + "    <date>2025-02-26</date>\n"
        + "    <time>14:30:00</time>\n"
        + "    <datetime>2025-02-26T14:30:00Z</datetime>\n"
        + "    <array>Item 1</array>\n"
        + "    <array>Item 2</array>\n"
        + "    <object>\n"
        + "        <key1>Value 1</key1>\n"
        + "        <key2>Value 2</key2>\n"
        + "    </object>\n"
        + "    <null/>\n"
        + "</data>\n",
    Json.JsonStringBuilder.Step.TWO_SPACES);
    // {
    //   "data": {
    //     "string": "Example Text",
    //     "integer": "42",
    //     "float": "3.14",
    //     "boolean": "true",
    //     "date": "2025-02-26",
    //     "time": "14:30:00",
    //     "datetime": "2025-02-26T14:30:00Z",
    //     "array": [
    //       "Item 1",
    //       "Item 2"
    //     ],
    //     "object": {
    //       "key1": "Value 1",
    //       "key2": "Value 2"
    //     },
    //     "null": ""
    //   }
    // }

U.jsonToXml(
    "{\n"
        + "  \"mydocument\": {\n"
        + "    \"-has\": \"an attribute\",\n"
        + "    \"and\": {\n"
        + "      \"many\": [\n"
        + "        \"elements\",\n"
        + "        \"more elements\"\n"
        + "      ]\n"
        + "    },\n"
        + "    \"plus\": {\n"
        + "      \"-a\": \"complex\",\n"
        + "      \"#text\": \"\\n     element as well\\n   \"\n"
        + "    }\n"
        + "  },\n"
        + "  \"#omit-xml-declaration\": \"yes\"\n"
        + "}",
    Xml.XmlStringBuilder.Step.TWO_SPACES);
    // <mydocument has="an attribute">
    //   <and>
    //     <many>elements</many>
    //     <many>more elements</many>
    //   </and>
    //   <plus a="complex">
    //      element as well
    //    </plus>
    // </mydocument>

U.jsonToXmlMinimum(
    "{\n"
        + "  \"data\": {\n"
        + "    \"string\": \"Example Text\",\n"
        + "    \"integer\": \"42\",\n"
        + "    \"float\": \"3.14\",\n"
        + "    \"boolean\": \"true\",\n"
        + "    \"date\": \"2025-02-26\",\n"
        + "    \"time\": \"14:30:00\",\n"
        + "    \"datetime\": \"2025-02-26T14:30:00Z\",\n"
        + "    \"array\": [\n"
        + "      \"Item 1\",\n"
        + "      \"Item 2\"\n"
        + "    ],\n"
        + "    \"object\": {\n"
        + "      \"key1\": \"Value 1\",\n"
        + "      \"key2\": \"Value 2\"\n"
        + "    },\n"
        + "    \"null\": \"\"\n"
        + "  }\n"
        + "}",
    Xml.XmlStringBuilder.Step.TWO_SPACES);
    // <data>
    //   <string>Example Text</string>
    //   <integer>42</integer>
    //   <float>3.14</float>
    //   <boolean>true</boolean>
    //   <date>2025-02-26</date>
    //   <time>14:30:00</time>
    //   <datetime>2025-02-26T14:30:00Z</datetime>
    //   <array>Item 1</array>
    //   <array>Item 2</array>
    //   <object>
    //     <key1>Value 1</key1>
    //     <key2>Value 2</key2>
    //   </object>
    //   <null string="true"/>
    // </data>

U.Builder builder = U.objectBuilder()
    .add("firstName", "John")
    .add("lastName", "Smith")
    .add("age", 25)
    .add("address", U.arrayBuilder()
        .add(U.objectBuilder()
            .add("streetAddress", "21 2nd Street")
            .add("city", "New York")
            .addNull("cityId")
            .add("state", "NY")
            .add("postalCode", "10021")))
    .add("phoneNumber", U.arrayBuilder()
        .add(U.objectBuilder()
            .add("type", "home")
            .add("number", "212 555-1234"))
        .add(U.objectBuilder()
            .add("type", "fax")
            .add("number", "646 555-4567")));
System.out.println(builder.toJson());
System.out.println(builder.toXml());
```
```javascript
{
  "firstName": "John",
  "lastName": "Smith",
  "age": 25,
  "address": [
    {
      "streetAddress": "21 2nd Street",
      "city": "New York",
      "cityId": null,
      "state": "NY",
      "postalCode": "10021"
    }
  ],
  "phoneNumber": [
    {
      "type": "home",
      "number": "212 555-1234"
    },
    {
      "type": "fax",
      "number": "646 555-4567"
    }
  ]
}
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
  <firstName>John</firstName>
  <lastName>Smith</lastName>
  <age number="true">25</age>
  <address array="true">
    <streetAddress>21 2nd Street</streetAddress>
    <city>New York</city>
    <cityId null="true"/>
    <state>NY</state>
    <postalCode>10021</postalCode>
  </address>
  <phoneNumber>
    <type>home</type>
    <number>212 555-1234</number>
  </phoneNumber>
  <phoneNumber>
    <type>fax</type>
    <number>646 555-4567</number>
  </phoneNumber>
</root>
```

Simplify XML document creation by structuring your code like the final document.

This code:

```java
XmlBuilder builder = XmlBuilder.create("Projects")
    .e("underscore-java").a("language", "Java").a("scm", "SVN")
        .e("Location").a("type", "URL")
            .t("https://github.com/javadev/underscore-java/")
        .up()
    .up()
    .e("JetS3t").a("language", "Java").a("scm", "CVS")
        .e("Location").a("type", "URL")
            .t("https://jets3t.s3.amazonaws.com/index.html");
System.out.println(builder.toXml(Xml.XmlStringBuilder.Step.TWO_SPACES));
System.out.println(builder.toJson(Json.JsonStringBuilder.Step.TWO_SPACES));
```

Generates the following XML and JSON documents:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Projects>
    <underscore-java language="Java" scm="SVN">
        <Location type="URL">https://github.com/javadev/underscore-java/</Location>
    </underscore-java>
    <JetS3t language="Java" scm="CVS">
        <Location type="URL">https://jets3t.s3.amazonaws.com/index.html</Location>
    </JetS3t>
</Projects>
```
```json
{
  "Projects": {
    "underscore-java": {
      "-language": "Java",
      "-scm": "SVN",
      "Location": {
        "-type": "URL",
        "#text": "https://github.com/javadev/underscore-java/"
      }
    },
    "JetS3t": {
      "-language": "Java",
      "-scm": "CVS",
      "Location": {
        "-type": "URL",
        "#text": "https://jets3t.s3.amazonaws.com/index.html"
      }
    }
  }
}
```

Underscore-kotlin is a kotlin port of [Underscore.js](https://underscorejs.org/).

In addition to porting Underscore's functionality, Underscore-kotlin includes matching unit tests.

For docs, license, tests, and downloads, see:
[https://javadev.github.io/underscore-java](https://javadev.github.io/underscore-java)

Thanks to Jeremy Ashkenas and all contributors to Underscore.js.
