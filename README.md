underscore-kotlin 
=================
[![Maven Central](https://img.shields.io/maven-central/v/com.github.javadev/underscore-kotlin.svg)](https://central.sonatype.com/artifact/com.github.javadev/underscore-kotlin/1.7)
[![Java CI with Maven](https://github.com/kotlindev/underscore-kotlin/actions/workflows/maven.yml/badge.svg)](https://github.com/kotlindev/underscore-kotlin/actions/workflows/maven.yml)
[![javadoc](https://javadoc.io/badge2/com.github.javadev/underscore-kotlin/javadoc.svg)](https://javadoc.io/doc/com.github.javadev/underscore-kotlin)


Requirements
============

Koltin 1.9, [Java 11 and later](https://github.com/javadev/underscore-java) or [Java 17](https://github.com/javadev/underscore-java17)

## Installation

To configure your Maven project, add the following code to your pom.xml file:

```xml
<dependencies>
  <dependency>
    <groupId>com.github.javadev</groupId>
    <artifactId>underscore-kotlin</artifactId>
    <version>1.7</version>
  </dependency>
  ...
</dependencies>
```

Gradle configuration:

```groovy
implementation 'com.github.javadev:underscore-kotlin:1.7'
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

```java
String inventory =
    "{\n"
        + "  \"inventory\": {\n"
        + "    \"#comment\": \"Test is test comment\",\n"
        + "    \"book\": [\n"
        + "      {\n"
        + "        \"-year\": \"2000\",\n"
        + "        \"title\": \"Snow Crash\",\n"
        + "        \"author\": \"Neal Stephenson\",\n"
        + "        \"publisher\": \"Spectra\",\n"
        + "        \"isbn\": \"0553380958\",\n"
        + "        \"price\": \"14.95\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"-year\": \"2005\",\n"
        + "        \"title\": \"Burning Tower\",\n"
        + "        \"author\": [\n"
        + "          \"Larry Niven\",\n"
        + "          \"Jerry Pournelle\"\n"
        + "        ],\n"
        + "        \"publisher\": \"Pocket\",\n"
        + "        \"isbn\": \"0743416910\",\n"
        + "        \"price\": \"5.99\"\n"
        + "      },\n"
        + "      {\n"
        + "        \"-year\": \"1995\",\n"
        + "        \"title\": \"Zodiac\",\n"
        + "        \"author\": \"Neal Stephenson\",\n"
        + "        \"publisher\": \"Spectra\",\n"
        + "        \"isbn\": \"0553573862\",\n"
        + "        \"price\": \"7.50\"\n"
        + "      }\n"
        + "    ]\n"
        + "  }\n"
        + "}";
String title = U.selectToken(U.fromJsonMap(inventory), "//book[@year>2001]/title/text()");
// "Burning Tower"

String json =
    "{\n"
        + "  \"Stores\": [\n"
        + "    \"Lambton Quay\",\n"
        + "    \"Willis Street\"\n"
        + "  ],\n"
        + "  \"Manufacturers\": [\n"
        + "    {\n"
        + "      \"Name\": \"Acme Co\",\n"
        + "      \"Products\": [\n"
        + "        {\n"
        + "          \"Name\": \"Anvil\",\n"
        + "          \"Price\": 50\n"
        + "        }\n"
        + "      ]\n"
        + "    },\n"
        + "    {\n"
        + "      \"Name\": \"Contoso\",\n"
        + "      \"Products\": [\n"
        + "        {\n"
        + "          \"Name\": \"Elbow Grease\",\n"
        + "          \"Price\": 99.95\n"
        + "        },\n"
        + "        {\n"
        + "          \"Name\": \"Headlight Fluid\",\n"
        + "          \"Price\": 4\n"
        + "        }\n"
        + "      ]\n"
        + "    }\n"
        + "  ]\n"
        + "}";
List<String> names = U.selectTokens(U.fromJsonMap(json), "//Products[Price>=50]/Name/text()");
// [Anvil, Elbow Grease]
```
Simplify XML document creation by structuring your code like the final document.

This code:

```java
XmlBuilder builder = XmlBuilder.create("Projects")
    .e("underscore-kotlin").a("language", "Java").a("scm", "SVN")
        .e("Location").a("type", "URL")
            .t("https://github.com/kotlindev/underscore-kotlin/")
        .up()
    .up()
    .e("JetS3t").a("language", "Java").a("scm", "CVS")
        .e("Location").a("type", "URL")
            .t("https://jets3t.s3.amazonaws.com/index.html");
```

Generates the following XML document:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Projects>
    <underscore-kotlin language="Java" scm="SVN">
        <Location type="URL">https://github.com/kotlindev/underscore-kotlin/</Location>
    </underscore-kotlin>
    <JetS3t language="Java" scm="CVS">
        <Location type="URL">https://jets3t.s3.amazonaws.com/index.html</Location>
    </JetS3t>
</Projects>
```

Underscore-kotlin is a kotlin port of [Underscore.js](https://underscorejs.org/).

In addition to porting Underscore's functionality, Underscore-kotlin includes matching unit tests.

For docs, license, tests, and downloads, see:
[https://javadev.github.io/underscore-java](https://javadev.github.io/underscore-java)

Thanks to Jeremy Ashkenas and all contributors to Underscore.js.
