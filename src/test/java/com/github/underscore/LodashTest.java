/*
 * The MIT License (MIT)
 *
 * Copyright 2015-2025 Valentyn Kolesnikov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.underscore;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Underscore library unit test.
 *
 * @author Valentyn Kolesnikov
 */
@SuppressWarnings("java:S2699")
class LodashTest {

    /*
    _.chunk(['a', 'b', 'c', 'd'], 2);
    // → [['a', 'b'], ['c', 'd']]

    _.chunk(['a', 'b', 'c', 'd'], 3);
    // → [['a', 'b', 'c'], ['d']]
    */
    @Test
    void chunk() {
        assertEquals("[[a, b], [c, d]]", U.chunk(asList("a", "b", "c", "d"), 2).toString());
        assertEquals("[[a, b], [c, d]]", new U<>(asList("a", "b", "c", "d")).chunk(2).toString());
        assertEquals(
                "[[a, b], [c, d]]",
                U.chain(asList("a", "b", "c", "d")).chunk(2).value().toString());
        assertEquals("[[a, b, c], [d]]", U.chunk(asList("a", "b", "c", "d"), 3).toString());
    }

    /*
    _.drop([1, 2, 3]);
    // → [2, 3]

    _.drop([1, 2, 3], 2);
    // → [3]

    _.drop([1, 2, 3], 5);
    // → []

    _.drop([1, 2, 3], 0);
    // → [1, 2, 3]
    */
    @Test
    void drop() {
        assertEquals("[2, 3]", U.drop(asList(1, 2, 3)).toString());
        assertEquals("[2, 3]", new U<>(asList(1, 2, 3)).drop().toString());
        assertEquals("[2, 3]", U.chain(asList(1, 2, 3)).drop().value().toString());
        assertEquals("[3]", U.drop(asList(1, 2, 3), 2).toString());
        assertEquals("[3]", new U<>(asList(1, 2, 3)).drop(2).toString());
        assertEquals("[3]", U.chain(asList(1, 2, 3)).drop(2).value().toString());
        assertEquals("[]", U.drop(asList(1, 2, 3), 5).toString());
        assertEquals("[1, 2, 3]", U.drop(asList(1, 2, 3), 0).toString());
    }

    /*
    _.dropRight([1, 2, 3]);
    // → [1, 2]

    _.dropRight([1, 2, 3], 2);
    // → [1]

    _.dropRight([1, 2, 3], 5);
    // → []

    _.dropRight([1, 2, 3], 0);
    // → [1, 2, 3]
    */
    @Test
    void dropRight() {
        assertEquals("[1, 2]", U.dropRight(asList(1, 2, 3)).toString());
        assertEquals("[1, 2]", new U<>(asList(1, 2, 3)).dropRight().toString());
        assertEquals("[1, 2]", U.chain(asList(1, 2, 3)).dropRight().value().toString());
        assertEquals("[1]", U.dropRight(asList(1, 2, 3), 2).toString());
        assertEquals("[1]", new U<>(asList(1, 2, 3)).dropRight(2).toString());
        assertEquals("[1]", U.chain(asList(1, 2, 3)).dropRight(2).value().toString());
        assertEquals("[]", U.dropRight(asList(1, 2, 3), 5).toString());
        assertEquals("[1, 2, 3]", U.dropRight(asList(1, 2, 3), 0).toString());
    }

    /*
    _.dropWhile([1, 2, 3], function(n) {
      return n < 3;
    });
    // → [3]
    */
    @Test
    void dropWhile() {
        assertEquals("[3]", U.dropWhile(asList(1, 2, 3), n -> n < 3).toString());
        assertEquals("[3]", new U<>(asList(1, 2, 3)).dropWhile(n -> n < 3).toString());
        assertEquals("[3]", U.chain(asList(1, 2, 3)).dropWhile(n -> n < 3).value().toString());
    }

    /*
    _.dropRightWhile([1, 2, 3], function(n) {
      return n > 2;
    });
    // → [1, 2]
    */
    @SuppressWarnings("unchecked")
    @Test
    void dropRightWhile() {
        assertEquals("[1, 2]", U.dropRightWhile(asList(1, 2, 3), n -> n > 2).toString());
        assertEquals("[1, 2]", new U<>(asList(1, 2, 3)).dropRightWhile(n -> n > 2).toString());
        assertEquals(
                "[1, 2]", U.chain(asList(1, 2, 3)).dropRightWhile(n -> n > 2).value().toString());
    }

    /*
    _.fill([1, 2, 3], 4)
    // → [4, 4, 4]
    _.fill(["test1", "test2", "test3"], "res")
    // → ["res", "res", "res"]
    */
    @Test
    void fill() {
        assertEquals(
                "[2, 2, 2]",
                U.fill(new ArrayList<Number>(Collections.nCopies(3, 0)), 2).toString());
        List<Object> array = new ArrayList<>(asList(4, 6, 8));
        U.fill(array, "*", 1, 2);
        assertEquals("[4, *, 8]", array.toString());
        array = new ArrayList<>(asList(1, 2, 3));
        new U<>(array).fill("a");
        assertEquals("[a, a, a]", array.toString());
        array = new ArrayList<>(asList(1, 2, 3));
        U.chain(array).fill("a");
        array = new ArrayList<>(asList(4, 6, 8));
        new U<>(array).fill("*", 1, 2);
        assertEquals("[4, *, 8]", array.toString());
        array = new ArrayList<>(asList(4, 6, 8));
        U.chain(array).fill("*", 1, 2);
        assertEquals("[4, *, 8]", array.toString());
        List<Number> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        final List<Number> result1 = U.fill(list, 4);
        assertEquals("[4, 4, 4]", result1.toString());
        List<String> list1 = new ArrayList<>();
        list1.add("test1");
        list1.add("test1");
        list1.add("test1");
        final List<String> result2 = U.fill(list1, "res");
        assertEquals("[res, res, res]", result2.toString());
        final Number[] result3 = U.fill(new Number[] {1, 2, 3}, 4);
        assertEquals("[4, 4, 4]", asList(result3).toString());
    }

    /*
    _.flattenDeep([1, [2, 3, [4]]]);
    // → [1, 2, 3, 4]
    */
    @SuppressWarnings("unchecked")
    @Test
    void flattenDeep() {
        final List<Integer> result =
                U.flattenDeep(asList(1, asList(2, 3, singletonList(singletonList(4)))));
        assertEquals("[1, 2, 3, 4]", result.toString());
        final List<Object> result2 =
                new U<>(asList(1, asList(2, 3, singletonList(singletonList(4))))).flattenDeep();
        assertEquals("[1, 2, 3, 4]", result2.toString());
        final List<?> resultChain =
                U.chain(asList(1, asList(2, 3, singletonList(singletonList(4)))))
                        .flattenDeep()
                        .value();
        assertEquals("[1, 2, 3, 4]", resultChain.toString());
    }

    /*
    var array = [1, 2, 3, 1, 2, 3];

    _.pull(array, 2, 3);
    console.log(array);
    // → [1, 1]
    */
    @Test
    void pull() {
        List<Object> array = new ArrayList<>(asList(1, 2, 3, 1, 2, 3));
        U.pull(array, 2, 3);
        assertEquals("[1, 1]", array.toString());
        array = new ArrayList<>(asList(1, 2, 3, 1, 2, 3));
        new U<>(array).pull(2, 3);
        assertEquals("[1, 1]", array.toString());
        array = new ArrayList<>(asList(1, 2, 3, 1, 2, 3));
        U.chain(array).pull(2, 3);
        assertEquals("[1, 1]", array.toString());
    }

    /*
    var array = [5, 10, 15, 20];
    var evens = _.pullAt(array, 1, 3);

    console.log(array);
    // → [5, 15]

    console.log(evens);
    // → [10, 20]
    */
    @Test
    void pullAt() {
        List<Object> array = new ArrayList<>(asList(5, 10, 15, 20));
        List<Object> events = U.pullAt(array, 1, 3);
        assertEquals("[5, 15]", array.toString());
        assertEquals("[10, 20]", events.toString());
        array = new ArrayList<>(asList(5, 10, 15, 20));
        events = new U<>(array).pullAt(1, 3);
        assertEquals("[5, 15]", array.toString());
        assertEquals("[10, 20]", events.toString());
        array = new ArrayList<>(asList(5, 10, 15, 20));
        events = U.chain(array).pullAt(1, 3).value();
        assertEquals("[5, 15]", array.toString());
        assertEquals("[10, 20]", events.toString());
    }

    /*
    var array = [1, 2, 3, 4];
    var evens = _.remove(array, function(n) {
      return n % 2 == 0;
    });

    console.log(array);
    // → [1, 3]

    console.log(evens);
    // → [2, 4]
    */
    @Test
    void remove() {
        List<Integer> array = new ArrayList<>(asList(1, 2, 3, 4));
        List<Integer> evens = U.remove(array, n -> n % 2 == 0);
        assertEquals("[1, 3]", array.toString());
        assertEquals("[2, 4]", evens.toString());
        array = new ArrayList<>(asList(1, 2, 3, 4));
        evens = new U<>(array).remove(n -> n % 2 == 0);
        assertEquals("[1, 3]", array.toString());
        assertEquals("[2, 4]", evens.toString());
        array = new ArrayList<>(asList(1, 2, 3, 4));
        evens = U.chain(array).remove(n -> n % 2 == 0).value();
        assertEquals("[1, 3]", array.toString());
        assertEquals("[2, 4]", evens.toString());
    }

    /*
    _.take([1, 2, 3]);
    // → [1]

    _.take([1, 2, 3], 2);
    // → [1, 2]

    _.take([1, 2, 3], 5);
    // → [1, 2, 3]

    _.take([1, 2, 3], 0);
    // → []
    */
    @Test
    void take() {
        assertEquals("[1]", U.take(asList(1, 2, 3)).toString());
        assertEquals("[1]", new U<>(asList(1, 2, 3)).take().toString());
        assertEquals("[1]", U.chain(asList(1, 2, 3)).take().value().toString());
        assertEquals("[1, 2]", U.take(asList(1, 2, 3), 2).toString());
        assertEquals("[1, 2]", new U<>(asList(1, 2, 3)).take(2).toString());
        assertEquals("[1, 2]", U.chain(asList(1, 2, 3)).take(2).value().toString());
        assertEquals("[1, 2, 3]", U.take(asList(1, 2, 3), 5).toString());
        assertEquals("[]", U.take(asList(1, 2, 3), 0).toString());
    }

    /*
    _.takeRight([1, 2, 3]);
    // → [3]

    _.takeRight([1, 2, 3], 2);
    // → [2, 3]

    _.takeRight([1, 2, 3], 5);
    // → [1, 2, 3]

    _.takeRight([1, 2, 3], 0);
    // → []
    */
    @Test
    void takeRight() {
        assertEquals("[3]", U.takeRight(asList(1, 2, 3)).toString());
        assertEquals("[3]", new U<>(asList(1, 2, 3)).takeRight().toString());
        assertEquals("[3]", U.chain(asList(1, 2, 3)).takeRight().value().toString());
        assertEquals("[2, 3]", U.takeRight(asList(1, 2, 3), 2).toString());
        assertEquals("[2, 3]", new U<>(asList(1, 2, 3)).takeRight(2).toString());
        assertEquals("[2, 3]", U.chain(asList(1, 2, 3)).takeRight(2).value().toString());
        assertEquals("[1, 2, 3]", U.takeRight(asList(1, 2, 3), 5).toString());
        assertEquals("[]", U.takeRight(asList(1, 2, 3), 0).toString());
    }

    /*
    _.takeWhile([1, 2, 3], function(n) {
      return n < 3;
    });
    // → [1, 2]
    */
    @Test
    void takeWhile() {
        assertEquals("[1, 2]", U.takeWhile(asList(1, 2, 3), n -> n < 3).toString());
        assertEquals("[1, 2]", new U<>(asList(1, 2, 3)).takeWhile(n -> n < 3).toString());
        assertEquals("[1, 2]", U.chain(asList(1, 2, 3)).takeWhile(n -> n < 3).value().toString());
    }

    /*
    _.takeRightWhile([1, 2, 3], function(n) {
      return n > 1;
    });
    // → [2, 3]
    */
    @Test
    void takeRightWhile() {
        assertEquals("[2, 3]", U.takeRightWhile(asList(1, 2, 3), n -> n > 1).toString());
        assertEquals("[2, 3]", new U<>(asList(1, 2, 3)).takeRightWhile(n -> n > 1).toString());
        assertEquals(
                "[2, 3]", U.chain(asList(1, 2, 3)).takeRightWhile(n -> n > 1).value().toString());
    }

    /*
    _.xor([1, 2], [4, 2]);
    // → [1, 4]
    */
    @SuppressWarnings("unchecked")
    @Test
    void xor() {
        assertEquals("[1, 4]", U.xor(asList(1, 2), asList(4, 2)).toString());
        assertEquals("[1, 4]", new U<>(asList(1, 2)).xor(asList(4, 2)).toString());
        assertEquals("[1, 4]", U.chain(asList(1, 2)).xor(asList(4, 2)).value().toString());
    }

    /*
    _.at(['a', 'b', 'c'], 0, 2);
    // → ['a', 'c']
    */
    @Test
    void at() {
        assertEquals("[a, c]", U.at(asList("a", "b", "c"), 0, 2).toString());
        assertEquals("[a, c]", new U<>(asList("a", "b", "c")).at(0, 2).toString());
        assertEquals("[a, c]", U.chain(asList("a", "b", "c")).at(0, 2).value().toString());
    }

    /*
    _.get({"a":[{"b":{"c":"d"}}]}, "a[0].b.c");
    // → "d"
    */
    @SuppressWarnings("unchecked")
    @Test
    void get() {
        assertEquals("d", U.<String>get(U.fromJson("{\"a\":[{\"b\":{\"c\":\"d\"}}]}"), "a[0].b.c"));
    }

    /*
    _.set({"a":[{"b":{"c":"d"}}]}, "a[0].b.c", "e");
    // → "{a=[{b={c=e}}]}"
    */
    @SuppressWarnings("unchecked")
    @Test
    void set() {
        assertEquals(
                "d", U.<String>set(U.fromJson("{\"a\":[{\"b\":{\"c\":\"d\"}}]}"), "a[0].b.c", "e"));
        assertEquals(
                "{b={c=d}}",
                U.set(U.fromJson("{\"a\":[{\"b\":{\"c\":\"d\"}}]}"), "a[0]", "e").toString());
        Map<String, Object> map = new LinkedHashMap<>();
        Map<String, Object> map2 = new LinkedHashMap<>();
        Map<String, Object> map3 = new LinkedHashMap<>();
        map.put("a", map2);
        map2.put("#item", map3);
        map3.put("b", "c");
        assertEquals("c", U.<String>set(map, "a.b", "b"));
        assertNull(U.<String>set(null, "a", "b"));
        assertNull(U.<String>set(new LinkedHashMap<>(), "a.b", "b"));
        Map<String, Object> map4 = new LinkedHashMap<>();
        map4.put("a", "b");
        assertNull(U.<String>set(map4, "a.b", "b"));
        Map<String, Object> map5 = new LinkedHashMap<>();
        map5.put("a", "b");
        assertNull(U.chain(map5.entrySet()).set(asList("a", "b"), "b").value());
    }

    @Test
    @SuppressWarnings("serial")
    void getNull() {
        assertNull(U.<String>get(null, "a[0].b.c"));
        assertNull(
                U.<String>get(
                        new LinkedHashMap<>() {
                            {
                                put("b", LodashTest.class);
                            }
                        },
                        "a[0].b.c"));
        assertNull(
                U.<String>get(
                        new LinkedHashMap<>() {
                            {
                                put("a", LodashTest.class);
                            }
                        },
                        "a[0].b.c"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void getNotFound() {
        assertNull(U.<String>get(U.fromJson("{\"a\":[{\"b\":{\"c\":\"d\"}}]}"), "a[0].b.d"));
        assertNull(U.<String>get(U.fromJson("{\"a\":[{\"b\":{\"c\":\"d\"}}]}"), "a[0].d.c"));
    }

    @Test
    void selectTokenGetBookTitleWrittenAfter2001() {
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
        Map<String, Object> objectMap = U.fromJsonMap(inventory);
        assertEquals("Burning Tower", U.selectToken(objectMap, "//book[@year>2001]/title/text()"));
        assertNull(U.selectToken(objectMap, "//book[@year>2001]/title1/text()"));
        assertThrows(IllegalArgumentException.class, () -> U.selectToken(objectMap, "\\"));
    }

    @Test
    void selectTokenManufacturerWithName() {
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
        Map<String, Object> objectMap = U.fromJsonMap(json);
        assertEquals(
                "Anvil",
                U.selectToken(objectMap, "//Manufacturers[Name='Acme Co']/Products/Name/text()"));
        assertEquals(
                "50",
                U.selectToken(objectMap, "//Manufacturers[Name='Acme Co']/Products/Price/text()"));
        assertEquals("Anvil", U.selectToken(objectMap, "//Products[Price>=50]/Name/text()"));
        assertEquals(
                Arrays.asList("Anvil", "Elbow Grease"),
                U.selectTokens(objectMap, "//Products[Price>=50]/Name/text()"));
    }

    @Test
    void selectTokensGetAllWriters() {
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
        Map<String, Object> objectMap = U.fromJsonMap(inventory);
        assertEquals(
                "[Neal Stephenson, Larry Niven, Jerry Pournelle, Neal Stephenson]",
                U.selectTokens(objectMap, "//book/author/text()").toString());
        assertEquals("[]", U.selectTokens(objectMap, "//book/author1/text()").toString());
        assertThrows(IllegalArgumentException.class, () -> U.selectTokens(objectMap, "\\"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void fetchGet() {
        U.FetchResponse result =
                U.fetch(
                        "https://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json");
        result.json();
        result.jsonMap();
        assertEquals(
                "{\n"
                        + "    \"fruit\": \"Apple\",\n"
                        + "    \"size\": \"Large\",\n"
                        + "    \"color\": \"Red\"\n"
                        + "}",
                result.text());
        assertEquals("Apple", U.get((Map<String, Object>) result.json(), "fruit"));
        U.Chain<?> resultChain =
                U.chain(
                                "https://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json")
                        .fetch();
        assertEquals(
                "{\n"
                        + "    \"fruit\": \"Apple\",\n"
                        + "    \"size\": \"Large\",\n"
                        + "    \"color\": \"Red\"\n"
                        + "}",
                resultChain.item());
        U.chain(
                        "https://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json")
                .fetch();
    }

    @Test
    void downloadUrl() throws IOException, URISyntaxException {
        try {
            long result =
                    U.downloadUrl(
                            "https://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json",
                            "test.json");
            assertEquals(65, result);
        } catch (javax.net.ssl.SSLHandshakeException ignored) {
            // ignored
        }
    }

    @Test
    void decompressGzip() throws IOException {
        Path path = Paths.get("file.txt");
        Files.deleteIfExists(path);
        U.decompressGzip("src/test/resources/file.gz", "file.txt");
        assertEquals(65, Files.readAllBytes(path).length);
    }

    @Test
    void fetchGetWithTimeouts() {
        U.FetchResponse result =
                U.fetch(
                        "https://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json",
                        30000,
                        30000);
        assertEquals(
                "{\n"
                        + "    \"fruit\": \"Apple\",\n"
                        + "    \"size\": \"Large\",\n"
                        + "    \"color\": \"Red\"\n"
                        + "}",
                result.text());
    }

    @Test
    void fetchGetWithTimeoutsAndRetry() {
        U.FetchResponse result =
                U.fetch(
                        "https://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json",
                        30000,
                        30000,
                        1,
                        1000);
        assertEquals(200, result.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    void fetchGetXml() {
        U.FetchResponse result = U.fetch("https://www.w3schools.com/xml/note.xml");
        assertEquals("Tove", U.get((Map<String, Object>) result.xml(), "note.to"));
        assertEquals("Tove", U.get(result.xmlMap(), "note.to"));
    }

    @Test
    void fetchResponseBlob() {
        java.io.ByteArrayOutputStream stream = new java.io.ByteArrayOutputStream();
        assertArrayEquals(new byte[0], new U.FetchResponse(true, 100, null, stream).blob());
        assertNull(new U.FetchResponse(true, 100, null, stream).getHeaderFields());
        assertTrue(new U.FetchResponse(true, 100, null, stream).isOk());
        assertEquals(100, new U.FetchResponse(true, 100, null, stream).getStatus());
    }

    @Test
    void fetchGetHttps() {
        U.FetchResponse result = U.fetch("https://api.lob.com/v1/addresses");
        assertEquals(
                "{\n"
                        + "    \"error\": {\n"
                        + "        \"message\": \"Missing authentication\",\n"
                        + "        \"status_code\": 401,\n"
                        + "        \"code\": \"unauthorized\"\n"
                        + "    }\n"
                        + "}",
                result.text());
    }

    @Test
    void fetchPut() {
        U.FetchResponse result =
                U.fetch(
                        "https://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json",
                        "PUT",
                        "{"
                                + "    \"dragon\": {"
                                + "        \"scaleThickness\": 4,"
                                + "        \"clawSharpness\": 2,"
                                + "        \"wingStrength\": 4,"
                                + "        \"fireBreath\": 10"
                                + "    }"
                                + "}");
        assertEquals(403, result.getStatus());
        U.FetchResponse result2 =
                U.fetch(
                        "https://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json",
                        "PUT",
                        "{"
                                + "    \"dragon\": {"
                                + "        \"scaleThickness\": 4,"
                                + "        \"clawSharpness\": 2,"
                                + "        \"wingStrength\": 4,"
                                + "        \"fireBreath\": 10"
                                + "    }"
                                + "}",
                        null,
                        null,
                        null);
        assertEquals(403, result2.getStatus());
        U.Chain<String> resultChain =
                U.chain(
                                "http://support.oneskyapp.com/hc/en-us/article_attachments/202761627/example_1.json")
                        .fetch(
                                "PUT",
                                "{"
                                        + "    \"dragon\": {"
                                        + "        \"scaleThickness\": 4,"
                                        + "        \"clawSharpness\": 2,"
                                        + "        \"wingStrength\": 4,"
                                        + "        \"fireBreath\": 10"
                                        + "    }"
                                        + "}");
        assertEquals(
                "<html>\n"
                        + "<head><title>301 Moved Permanently</title></head>\n"
                        + "<body>\n"
                        + "<center><h1>301 Moved Permanently</h1></center>\n"
                        + "<hr><center>cloudflare</center>\n"
                        + "</body>\n"
                        + "</html>\n",
                resultChain.item().replace("\r\n", "\n"));
    }

    @Test
    void fetchWrongUrl() {
        assertThrows(IllegalArgumentException.class, () -> U.fetch("ttt"));
    }

    @Test
    void fetchWrongUrlWithRetry() {
        assertThrows(IllegalArgumentException.class, () -> U.fetch("ttt", 30000, 30000, 1, 100));
    }

    @Test
    void fetchWrongUrl2() {
        assertThrows(UnsupportedOperationException.class, () -> U.fetch("test://ttt"));
    }

    @Test
    void fetchWrongUrlWithRetry2() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> U.fetch("test://ttt", 30000, 30000, 1, 100));
    }

    @Test
    void toJson() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(null, 3);
        assertEquals("{\n  \"null\": 3\n}", U.toJson(map));
        Map<Object, Object> map2 = new LinkedHashMap<>();
        map2.put(1, 3);
        assertEquals("{\n  \"1\": 3\n}", U.toJson(map2));
        Map<Object, Object> map3 = new LinkedHashMap<>();
        map3.put(true, 3);
        assertEquals("{\n  \"true\": 3\n}", U.toJson(map3));
    }

    @Test
    void toXml() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(null, 3);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<null number=\"true\">3</null>",
                U.toXml(map));
        Map<Object, Object> map2 = new LinkedHashMap<>();
        map2.put(1, 3);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<__GE__ number=\"true\">3</__GE__>",
                U.toXml(map2));
        Map<Object, Object> map3 = new LinkedHashMap<>();
        map3.put(true, 3);
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<true number=\"true\">3</true>",
                U.toXml(map3));
    }

    @Test
    void xmlToJson() {
        assertEquals(
                "{\n"
                        + "  \"root\": [\n"
                        + "    \"1\",\n"
                        + "    \"2\"\n"
                        + "  ],\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson("<root><element>1</element><element>2</element></root>"));
        assertEquals(
                "{\n"
                        + "  \"root\": [\n"
                        + "    \"1\",\n"
                        + "    \"2\"\n"
                        + "  ],\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson(
                        "<root><element>1</element><element>2</element></root>",
                        Json.JsonStringBuilder.Step.TWO_SPACES));
        assertEquals(
                "{\n"
                        + "  \"root\": [\n"
                        + "    \"1\",\n"
                        + "    \"2\"\n"
                        + "  ],\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.chain("<root><element>1</element><element>2</element></root>")
                        .xmlToJson()
                        .item());
        assertEquals(
                "{\n"
                        + "  \"a\": {\n"
                        + "    \"b\": [\n"
                        + "      {\n"
                        + "      },\n"
                        + "      {\n"
                        + "      }\n"
                        + "    ]\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson("<a>\n  <b></b>\n  <b></b>\n</a>"));
        assertEquals(
                "[\n]",
                U.xmlToJson(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                                + "<root empty-array=\"true\"></root>"));
        assertEquals(
                "{\n  \"a\": null,\n  \"#omit-xml-declaration\": \"yes\"\n}",
                U.xmlToJson("<a/>", U.XmlToJsonMode.REPLACE_SELF_CLOSING_WITH_NULL));
        assertEquals(
                "{\n"
                        + "  \"c\": {\n"
                        + "    \"b\": null,\n"
                        + "    \"a\": null\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson("<c><b></b><a/></c>", U.XmlToJsonMode.REPLACE_EMPTY_TAG_WITH_NULL));
        assertEquals(
                "{\n"
                        + "  \"c\": {\n"
                        + "    \"b\": [\n"
                        + "      \"\",\n"
                        + "      \"\"\n"
                        + "    ],\n"
                        + "    \"a\": \"\"\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson(
                        "<c><b></b><b></b><a/></c>",
                        U.XmlToJsonMode.REPLACE_EMPTY_TAG_WITH_STRING));
        assertEquals(
                "{\n  \"a\": \"\",\n  \"#omit-xml-declaration\": \"yes\"\n}",
                U.xmlToJson("<a/>", U.XmlToJsonMode.REPLACE_SELF_CLOSING_WITH_STRING));
        assertEquals(
                "{\n"
                        + "  \"a\": {\n"
                        + "    \"-b\": \"c\"\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson("<a b=\"c\"/>", U.XmlToJsonMode.REPLACE_SELF_CLOSING_WITH_NULL));
        assertEquals(
                "{\n"
                        + "  \"a\": {\n"
                        + "    \"b\": [\n"
                        + "      null,\n"
                        + "      null\n"
                        + "    ]\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson("<a><b/><b/></a>", U.XmlToJsonMode.REPLACE_SELF_CLOSING_WITH_NULL));
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("-self-closing", "false");
        U.replaceSelfClosingWithNull(map);
        Map<String, Object> mapEmpty = new LinkedHashMap<>();
        mapEmpty.put("-self-closing", "true");
        U.replaceSelfClosingWithEmpty(mapEmpty);
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<>());
        map2.put("list", list);
        U.replaceSelfClosingWithNull(map2);
        assertEquals(
                "{\n"
                        + "  \"a\": {\n"
                        + "    \"b\": [\n"
                        + "      null,\n"
                        + "      null\n"
                        + "    ]\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson(
                        "<a><b></b><b></b></a>", U.XmlToJsonMode.REPLACE_EMPTY_VALUE_WITH_NULL));
        Map<String, Object> map3 = new LinkedHashMap<>();
        List<Object> list2 = new ArrayList<>();
        list2.add(new ArrayList<>());
        map3.put("list", list2);
        U.replaceEmptyValueWithNull(map3);
        U.replaceEmptyValueWithNull(null);
        Map<String, Object> map4 = new LinkedHashMap<>();
        List<Object> list3 = new ArrayList<>();
        list3.add(new ArrayList<>());
        map4.put("list", list3);
        U.replaceEmptyValueWithEmptyString(map4);
    }

    @Test
    void xmlToJson2() {
        assertEquals(
                "{\n  \"debug\": \"&amp;\"\n}",
                U.xmlToJson(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<debug>&amp;amp;</debug>"));
    }

    @Test
    void xmpToJson3() {
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<>());
        map2.put("list", list);
        U.replaceMinusWithAt(map2);
        assertEquals(
                "{\n"
                        + "  \"a\": {\n"
                        + "    \"@c\": \"1\",\n"
                        + "    \"b\": [\n"
                        + "      {\n"
                        + "      },\n"
                        + "      {\n"
                        + "      }\n"
                        + "    ]\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson(
                        "<a c=\"1\"><b></b><b></b></a>", U.XmlToJsonMode.REPLACE_MINUS_WITH_AT));
        Map<String, Object> map3 = new LinkedHashMap<>();
        List<Object> list2 = new ArrayList<>();
        list2.add(new ArrayList<>());
        map3.put("list", list2);
        U.replaceMinusWithAt(map3);
        U.replaceMinusWithAt(null);
        U.xmlToJson(
                "<a c=\"1\"><b></b><b></b></a>",
                U.XmlToJsonMode.REPLACE_EMPTY_TAG_WITH_NULL_AND_MINUS_WITH_AT);
    }

    @Test
    void xmpToJson4() {
        assertEquals(
                "{\n"
                        + "  \"z:catalog\": {\n"
                        + "    \"-xmlns:xsi\": \"http://www.w3.org/2001/XMLSchema-instance\",\n"
                        + "    \"-xmlns:z\": \"www.microsoft.com/zzz\",\n"
                        + "    \"book\": {\n"
                        + "      \"-xsi:noNamespaceSchemaLocation\": \"http://www.example.com/MyData.xsd\",\n"
                        + "      \"-id\": \"bk101\",\n"
                        + "      \"title\": \"Presenting XML\",\n"
                        + "      \"author\": \"Richard Light\"\n"
                        + "    }\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson(
                        "<z:catalog xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                                + "xmlns:z=\"www.microsoft.com/zzz\">\n"
                                + "   <book xsi:noNamespaceSchemaLocation=\"http://www.example.com/MyData.xsd\"\r\n"
                                + "         id=\"bk101\">\n"
                                + "      <title>Presenting XML</title>\n"
                                + "      <author>Richard Light</author>\n"
                                + "   </book>\n"
                                + "</z:catalog>"));
    }

    @Test
    void xmpToJson5() {
        assertEquals(
                "{\n"
                        + "  \"Comment\": {\n"
                        + "    \"-stringValue\": \"============================\",\n"
                        + "    \"-self-closing\": \"true\"\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlToJson("<Comment stringValue=\"============================\"/>"));
    }

    @Test
    void xmlToJsonMinimum() {
        assertEquals(
                "{\n"
                        + "  \"root\": {\n"
                        + "    \"element\": [\n"
                        + "      \"1\",\n"
                        + "      \"2\"\n"
                        + "    ],\n"
                        + "    \"a\": \"\"\n"
                        + "  }\n"
                        + "}",
                U.xmlToJsonMinimum("<root><element>1</element><element>2</element><a/></root>"));
        assertEquals(
                "[\n" + "  \"a\",\n" + "  \"b\"\n" + "]",
                U.xmlToJsonMinimum(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                + "<root>\n"
                                + "  <element>a</element>\n"
                                + "  <element>b</element>\n"
                                + "</root>"));
    }

    @Test
    void xmlOrJsonToJson() {
        assertEquals(
                "{\n"
                        + "  \"a\": {\n"
                        + "    \"-self-closing\": \"true\"\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.xmlOrJsonToJson("<a/>"));
        assertEquals(
                "[\n]",
                U.xmlOrJsonToJson(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                + "<root empty-array=\"true\"></root>"));
        assertEquals("{\n  \"a\": 1\n}", U.xmlOrJsonToJson("{\"a\":1}"));
        assertEquals("[\n]", U.xmlOrJsonToJson("[]"));
        assertEquals("", U.xmlOrJsonToJson(""));
    }

    @Test
    void xmlOrJsonToXml() {
        assertEquals("<a/>", U.xmlOrJsonToXml("<a/>"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<root empty-array=\"true\"></root>",
                U.xmlOrJsonToXml(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                + "<root empty-array=\"true\"></root>"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a number=\"true\">1</a>",
                U.xmlOrJsonToXml("{\"a\":1}"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<root empty-array=\"true\"></root>",
                U.xmlOrJsonToXml("[]"));
        assertEquals("", U.xmlOrJsonToXml(""));
    }

    @Test
    void mergeXmlsOrJsonsToJson() {
        assertEquals(
                "{\n"
                        + "  \"a\": {\n"
                        + "    \"-self-closing\": \"true\"\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\"\n"
                        + "}",
                U.mergeXmlsOrJsonsToJson(List.of("<a/>")));
        assertEquals(
                "{\n"
                        + "  \"a\": {\n"
                        + "    \"-self-closing\": \"true\"\n"
                        + "  },\n"
                        + "  \"#omit-xml-declaration\": \"yes\",\n"
                        + "  \"b\": {\n"
                        + "    \"-self-closing\": \"true\"\n"
                        + "  }\n"
                        + "}",
                U.mergeXmlsOrJsonsToJson(List.of("<a/>", "<b/>")));
        assertEquals(
                "{\n" + "  \"value\": [\n" + "  ]\n" + "}",
                U.mergeXmlsOrJsonsToJson(
                        List.of(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                        + "<root empty-array=\"true\"></root>")));
        assertEquals("{\n  \"a\": 1\n}", U.mergeXmlsOrJsonsToJson(List.of("{\"a\":1}")));
        assertEquals(
                "{\n" + "  \"value\": [\n" + "  ]\n" + "}",
                U.mergeXmlsOrJsonsToJson(List.of("[]")));
        assertEquals("", U.mergeXmlsOrJsonsToJson(List.of("")));
    }

    @Test
    void mergeXmlsOrJsonsToXml() {
        assertEquals("<a/>", U.mergeXmlsOrJsonsToXml(List.of("<a/>")));
        assertEquals(
                "<root>\n" + "  <a/>\n" + "  <b/>\n" + "</root>",
                U.mergeXmlsOrJsonsToXml(List.of("<a/>", "<b/>")));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<value empty-array=\"true\"></value>",
                U.mergeXmlsOrJsonsToXml(
                        List.of(
                                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                        + "<root empty-array=\"true\"></root>")));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a number=\"true\">1</a>",
                U.mergeXmlsOrJsonsToXml(List.of("{\"a\":1}")));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<value empty-array=\"true\"></value>",
                U.mergeXmlsOrJsonsToXml(List.of("[]")));
        assertEquals("", U.mergeXmlsOrJsonsToXml(List.of("")));
    }

    @Test
    void removeMapKey() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("-self-closing", "false");
        U.remove(map, "test");
        U.remove(map, "-self-closing");
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new LinkedHashMap<>());
        map2.put("list", list);
        U.remove(map2, "test");
        map2.put("list2", new LinkedHashMap<>());
        U.remove(map2, "test");
        U.remove(map2, "list.0");
    }

    @Test
    void updateMapValue() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("-self-closing1", "true");
        map.put("-self-closing2", "false");
        U.update(map, "-self-closing1", "false");
        U.update(map, "-self-closing3", "true");
        U.update(map, asList("-self-closing1"), "false");
        U.update(map, asList("-self-closing3"), "true");
        assertTrue(
                U.toJson(map)
                        .startsWith(
                                "{\n"
                                        + "  \"-self-closing1\": \"true\",\n"
                                        + "  \"-self-closing2\": \"false\",\n"));
    }

    @Test
    void renameMapKey() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("-self-closing", "false");
        U.rename(map, "test", "test1");
        Map<String, Object> newMap = U.rename(map, "-self-closing", "-self-closing1");
        assertEquals("{\n  \"-self-closing1\": \"false\"\n}", U.toJson(newMap));
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new LinkedHashMap<>());
        map2.put("list", list);
        U.rename(map2, "test", "test1");
        map2.put("list", new LinkedHashMap<>());
        U.rename(map2, "test", "test1");
    }

    @Test
    void renameRoot() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<json></json>",
                U.jsonToXml("{}", "json"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<json>\n"
                        + "  <a>b</a>\n"
                        + "  <c>d</c>\n"
                        + "</json>",
                U.jsonToXml("{\"a\": \"b\", \"c\": \"d\"}", "json"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<json a=\"b\" c=\"d\"></json>",
                U.jsonToXml(
                        "{\"a\": \"b\", \"c\": \"d\"}",
                        U.JsonToXmlMode.FORCE_ATTRIBUTE_USAGE,
                        "json"));
    }

    @Test
    void forceAddRoot() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<root>\n"
                        + "  <data array=\"true\">\n"
                        + "    <a>b</a>\n"
                        + "  </data>\n"
                        + "</root>",
                U.jsonToXml(
                        "{\n"
                                + "  \"data\": [\n"
                                + "    {\n"
                                + "      \"a\": \"b\"\n"
                                + "    }\n"
                                + "  ]\n"
                                + "}",
                        U.JsonToXmlMode.ADD_ROOT));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<newroot>\n"
                        + "  <data array=\"true\">\n"
                        + "    <a>b</a>\n"
                        + "  </data>\n"
                        + "</newroot>",
                U.jsonToXml(
                        "{\n"
                                + "  \"data\": [\n"
                                + "    {\n"
                                + "      \"a\": \"b\"\n"
                                + "    }\n"
                                + "  ]\n"
                                + "}",
                        U.JsonToXmlMode.ADD_ROOT,
                        "newroot"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<root>\n"
                        + "  <element array=\"true\">\n"
                        + "    <a>b</a>\n"
                        + "  </element>\n"
                        + "</root>",
                U.jsonToXml(
                        "{\n"
                                + "  \"root\": [\n"
                                + "    {\n"
                                + "      \"a\": \"b\"\n"
                                + "    }\n"
                                + "  ]\n"
                                + "}",
                        U.JsonToXmlMode.ADD_ROOT));
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new LinkedHashMap<>());
        map2.put("list", list);
        U.replaceNumberAndBooleanWithString(map2);
        map2.put("list", new LinkedHashMap<>());
        U.replaceNumberAndBooleanWithString(map2);
    }

    @Test
    void forceRemoveArrayTrue() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<data>\n"
                        + "  <a>b</a>\n"
                        + "</data>",
                U.jsonToXml(
                        "{\n"
                                + "  \"data\": [\n"
                                + "    {\n"
                                + "      \"a\": \"b\"\n"
                                + "    }\n"
                                + "  ]\n"
                                + "}",
                        U.JsonToXmlMode.REMOVE_ARRAY_ATTRIBUTE));
    }

    @Test
    void forceRemoveArrayTrueBooleanAndNumber() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<root>\n"
                        + "  <data>\n"
                        + "    <a>b</a>\n"
                        + "    <c>true</c>\n"
                        + "    <d>1</d>\n"
                        + "    <e></e>\n"
                        + "    <f>null</f>\n"
                        + "  </data>\n"
                        + "  <data2>true</data2>\n"
                        + "  <data2>1</data2>\n"
                        + "  <data2>null</data2>\n"
                        + "</root>",
                U.jsonToXml(
                        "{\n"
                                + "  \"data\": [\n"
                                + "    {\n"
                                + "      \"a\": \"b\",\n"
                                + "      \"c\": true,\n"
                                + "      \"d\": 1,\n"
                                + "      \"e\": {},\n"
                                + "      \"f\": null\n"
                                + "    }\n"
                                + "  ],\n"
                                + "  \"data2\": [\n"
                                + "      true,\n"
                                + "      1,\n"
                                + "      null\n"
                                + "  ]\n"
                                + "}",
                        U.JsonToXmlMode.REMOVE_ATTRIBUTES));
    }

    @Test
    void updateMapKey() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("-self-closing", "false");
        U.rename(map, "test", "test1");
        Map<String, Object> newMap = U.update(map, map);
        assertEquals("{\n  \"-self-closing\": \"false\"\n}", U.toJson(newMap));
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new LinkedHashMap<>());
        map2.put("list", list);
        U.update(map2, map2);
        map2.put("list", new LinkedHashMap<>());
        U.update(map2, map2);
        U.update(map2, map);
        Map<String, Object> map3 = new LinkedHashMap<>();
        map3.put("list", new ArrayList<>());
        U.update(map2, map3);
        U.update(map3, map2);
    }

    @Test
    void setValue() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("-self-closing", "false");
        U.setValue(map, "test", "test1");
        Map<String, Object> newMap = U.setValue(map, "-self-closing", "true");
        assertEquals("{\n  \"-self-closing\": \"true\"\n}", U.toJson(newMap));
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new LinkedHashMap<>());
        map2.put("list", list);
        U.setValue(map2, "test", "test1");
        map2.put("list", new LinkedHashMap<>());
        U.setValue(map2, "test", "test1");
    }

    @Test
    void jsonToXml() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>",
                U.jsonToXml("{\n  \"a\": {\n  }\n}"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>",
                U.jsonToXml("{\n  \"a\": {\n  }\n}", Xml.XmlStringBuilder.Step.TWO_SPACES));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<a></a>",
                U.chain("{\n  \"a\": {\n  }\n}").jsonToXml().item());
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root empty-array=\"true\"></root>",
                U.jsonToXml("[]"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<root>\n"
                        + "  <a>\n"
                        + "    <b>v1</b>\n"
                        + "  </a>\n"
                        + "  <c>v1</c>\n"
                        + "  <c>v2</c>\n"
                        + "  <c>v3</c>\n"
                        + "</root>",
                U.jsonToXml("{\"a\" : {\n \"b\" : \"v1\" }, \"c\" : [\"v1\", \"v2\", \"v3\"]}"));
    }

    @Test
    void jsonToXmlMinimum() {
        assertEquals("<a></a>", U.jsonToXmlMinimum("{\n  \"a\": {\n  }\n}"));
        assertEquals(
                "<a></a>",
                U.jsonToXmlMinimum("{\n  \"a\": {\n  }\n}", Xml.XmlStringBuilder.Step.TWO_SPACES));
        assertEquals("<root empty-array=\"true\"></root>", U.jsonToXmlMinimum("[]"));
        assertEquals(
                "<root>\n"
                        + "  <a>\n"
                        + "    <b>1</b>\n"
                        + "  </a>\n"
                        + "  <c>v1</c>\n"
                        + "  <c>v2</c>\n"
                        + "  <c>v3</c>\n"
                        + "</root>",
                U.jsonToXmlMinimum("{\"a\" : {\n \"b\" : 1 }, \"c\" : [\"v1\", \"v2\", \"v3\"]}"));
    }

    @Test
    void getTextType() {
        assertEquals(
                U.TextType.OTHER,
                U.getTextType("<root><element>1</element><element>2</element></root"));
        assertEquals(
                U.TextType.OTHER,
                U.getTextType("root><element>1</element><element>2</element></root>"));
        assertEquals(U.TextType.OTHER, U.getTextType("{\n  \"a\": {\n  }\n"));
        assertEquals(U.TextType.OTHER, U.getTextType("\n  \"a\": {\n  }\n}"));
        assertEquals(U.TextType.OTHER, U.getTextType("[\n  1.00\n"));
        assertEquals(U.TextType.OTHER, U.getTextType("\n  1.00\n]"));
    }

    @Test
    void formatJsonOrXml() {
        assertEquals(
                "<root>\n  <element>1</element>\n  <element>2</element>\n</root>",
                U.formatJsonOrXml("<root><element>1</element><element>2</element></root>"));
        assertEquals(
                "<a>\n    <b></b>\n    <b></b>\n</a>",
                U.formatJsonOrXml("<a>\n  <b></b>\n  <b></b>\n</a>", "FOUR_SPACES"));
        assertEquals("{\n  \"a\": {\n  }\n}", U.formatJsonOrXml("{\n  \"a\": {\n  }\n}"));
        assertEquals("[\n]", U.formatJsonOrXml("[]"));
        assertEquals("[\n  1.00\n]", U.formatJsonOrXml("[1.00]"));
        assertEquals(
                "{\n    \"a\": {\n    }\n}",
                U.formatJsonOrXml("{\n  \"a\": {\n  }\n}", "FOUR_SPACES"));
        assertEquals("text", U.formatJsonOrXml("text", "FOUR_SPACES"));
    }

    @Test
    void formatXml() {
        assertEquals(
                "<root>\n  <element>1</element>\n  <element>2</element>\n</root>",
                U.formatXml("<root><element>1</element><element>2</element></root>"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n  <element>1</element>\n"
                        + "  <element>2</element>\n</root>",
                U.formatXml(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root><element>1</element>"
                                + "<element>2</element></root>"));
        assertEquals(
                "<a>\n  <b></b>\n  <b></b>\n</a>", U.formatXml("<a>\n  <b></b>\n  <b></b>\n</a>"));
        assertEquals(
                "<a>\n   <b></b>\n   <b></b>\n</a>",
                U.formatXml(
                        "<a>\n  <b></b>\n  <b></b>\n</a>", Xml.XmlStringBuilder.Step.THREE_SPACES));
        assertEquals(
                "<a>\n    <b></b>\n    <b></b>\n</a>",
                U.formatXml(
                        "<a>\n  <b></b>\n  <b></b>\n</a>", Xml.XmlStringBuilder.Step.FOUR_SPACES));
        assertEquals(
                "<a><b></b><b></b></a>",
                U.formatXml("<a>\n  <b></b>\n  <b></b>\n</a>", Xml.XmlStringBuilder.Step.COMPACT));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a><b></b><b></b></a>",
                U.formatXml(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a>\n  <b></b>\n  <b></b>\n</a>",
                        Xml.XmlStringBuilder.Step.COMPACT));
        assertEquals(
                "<a>\n\t<b></b>\n\t<b></b>\n</a>",
                U.formatXml("<a>\n  <b></b>\n  <b></b>\n</a>", Xml.XmlStringBuilder.Step.TABS));
        assertEquals("<a number=\"true\">1.00</a>", U.formatXml("<a number=\"true\">1.00</a>"));
        assertEquals("<a number=\"true\">2.01</a>", U.formatXml("<a number=\"true\">2.01</a>"));
    }

    @Test
    void forceAttributeUsage() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<RootElm>\n"
                        + "  <author DOB=\"\" EMailID=\"\" PlaceId=\"\" SSN=\"\">\n"
                        + "    <extn externalSystemCode=\"\"/>\n"
                        + "  </author>\n"
                        + "</RootElm>",
                U.jsonToXml(
                        "{\n"
                                + "  \"RootElm\": {\n"
                                + "    \"author\": {\n"
                                + "      \"DOB\": \"\",\n"
                                + "      \"EMailID\": \"\",\n"
                                + "      \"PlaceId\": \"\",\n"
                                + "      \"SSN\": \"\",\n"
                                + "      \"extn\": {\n"
                                + "        \"externalSystemCode\": \"\",\n"
                                + "        \"-self-closing\": \"true\"\n"
                                + "      }\n"
                                + "    }\n"
                                + "  }\n"
                                + "}",
                        U.JsonToXmlMode.FORCE_ATTRIBUTE_USAGE));
        Map<String, Object> map = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new LinkedHashMap<>());
        map.put("list", list);
        U.forceAttributeUsage(map);
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list2 = new ArrayList<>();
        list2.add(new ArrayList<>());
        map2.put("list", list2);
        U.forceAttributeUsage(map2);
    }

    @Test
    void replaceNullWithEmptyValue() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<RootElm>\n"
                        + "  <author>\n"
                        + "    <DOB></DOB>\n"
                        + "    <value string=\"true\"/>\n"
                        + "  </author>\n"
                        + "</RootElm>",
                U.jsonToXml(
                        "{\n"
                                + "  \"RootElm\": {\n"
                                + "    \"author\": {\n"
                                + "      \"DOB\": null,\n"
                                + "      \"value\": \"\"\n"
                                + "    }\n"
                                + "  }\n"
                                + "}",
                        U.JsonToXmlMode.REPLACE_NULL_WITH_EMPTY_VALUE));
        Map<String, Object> map = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new LinkedHashMap<>());
        map.put("list", list);
        U.replaceNullWithEmptyValue(map);
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list2 = new ArrayList<>();
        list2.add(new ArrayList<>());
        map2.put("list", list2);
        U.replaceNullWithEmptyValue(map2);
    }

    @Test
    void replaceNilWithNull() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<RootElm>\n"
                        + "  <author>\n"
                        + "    <DOB nil=\"true\"></DOB>\n"
                        + "    <value null=\"true\"/>\n"
                        + "  </author>\n"
                        + "</RootElm>",
                U.toXml(
                        U.replaceNilWithNull(
                                U.fromXmlMap(
                                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                                + "<RootElm>\n"
                                                + "  <author>\n"
                                                + "    <DOB nil=\"true\"></DOB>\n"
                                                + "    <value nil=\"true\"/>\n"
                                                + "  </author>\n"
                                                + "</RootElm>"))));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<RootElm xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                        + "  <author>\n"
                        + "    <DOB xsi:nil=\"true\"></DOB>\n"
                        + "    <value null=\"true\"/>\n"
                        + "    <DOB2 xsi:nil=\"true1\"></DOB2>\n"
                        + "    <value2 xsi:nil=\"true1\"/>\n"
                        + "  </author>\n"
                        + "</RootElm>",
                U.toXml(
                        U.replaceNilWithNull(
                                U.fromXmlMap(
                                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                                + "<RootElm xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                                                + "  <author>\n"
                                                + "    <DOB xsi:nil=\"true\"></DOB>\n"
                                                + "    <value xsi:nil=\"true\"/>\n"
                                                + "    <DOB2 xsi:nil=\"true1\"></DOB2>\n"
                                                + "    <value2 xsi:nil=\"true1\"/>\n"
                                                + "  </author>\n"
                                                + "</RootElm>"))));
        Map<String, Object> map = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new LinkedHashMap<>());
        map.put("list", list);
        U.replaceNilWithNull(map);
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list2 = new ArrayList<>();
        list2.add(new ArrayList<>());
        map2.put("list", list2);
        U.replaceNilWithNull(map2);
        Map<String, Object> map3 = new LinkedHashMap<>();
        map3.put("-nil", "true");
        map3.put("-self-closing", "true1");
        Map<String, Object> map4 = new LinkedHashMap<>();
        map4.put("map", map3);
        U.replaceNilWithNull(map4);
    }

    @Test
    void replaceEmptyStringWithEmptyValue() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<RootElm>\n"
                        + "  <author>\n"
                        + "    <DOB null=\"true\"/>\n"
                        + "    <value></value>\n"
                        + "  </author>\n"
                        + "</RootElm>",
                U.jsonToXml(
                        "{\n"
                                + "  \"RootElm\": {\n"
                                + "    \"author\": {\n"
                                + "      \"DOB\": null,\n"
                                + "      \"value\": \"\"\n"
                                + "    }\n"
                                + "  }\n"
                                + "}",
                        U.JsonToXmlMode.REPLACE_EMPTY_STRING_WITH_EMPTY_VALUE));
        Map<String, Object> map = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new LinkedHashMap<>());
        map.put("list", list);
        U.replaceEmptyStringWithEmptyValue(map);
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list2 = new ArrayList<>();
        list2.add(new ArrayList<>());
        map2.put("list", list2);
        U.replaceEmptyStringWithEmptyValue(map2);
    }

    @Test
    void changeXmlEncoding() {
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n<a>Test</a>",
                U.changeXmlEncoding(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a>Test</a>", "windows-1251"));
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"windows-1251\"?><a>Test</a>",
                U.changeXmlEncoding(
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><a>Test</a>",
                        Xml.XmlStringBuilder.Step.COMPACT,
                        "windows-1251"));
        assertNull(U.changeXmlEncoding(null, Xml.XmlStringBuilder.Step.COMPACT, "windows-1251"));
    }

    @Test
    void formatJson() {
        assertEquals("{\n  \"a\": {\n  }\n}", U.formatJson("{\n  \"a\": {\n  }\n}"));
        assertEquals("[\n]", U.formatJson("[]"));
        assertEquals("[\n  1.00\n]", U.formatJson("[1.00]"));
        assertEquals(
                "{\n    \"a\": {\n    }\n}",
                U.formatJson("{\n  \"a\": {\n  }\n}", Json.JsonStringBuilder.Step.FOUR_SPACES));
        assertEquals(
                "{\n   \"a\": {\n   }\n}",
                U.formatJson("{\n  \"a\": {\n  }\n}", Json.JsonStringBuilder.Step.THREE_SPACES));
        assertEquals(
                "{\"a\":{}}",
                U.formatJson("{\n  \"a\": {\n  }\n}", Json.JsonStringBuilder.Step.COMPACT));
        assertEquals(
                "{\n\t\"a\": {\n\t}\n}",
                U.formatJson("{\n  \"a\": {\n  }\n}", Json.JsonStringBuilder.Step.TABS));
    }

    @Test
    @SuppressWarnings("unchecked")
    void removeMinusesAndConvertNumbers() {
        Map<String, Object> result = U.removeMinusesAndConvertNumbers(U.fromXml("<a/>"));
        assertEquals("{a={}}", result.toString());
        Map<String, Object> result2 = U.removeMinusesAndConvertNumbers(U.fromXml("<a b=\"c\"/>"));
        assertEquals("{a={b=c}}", result2.toString());
        Map<String, Object> result3 =
                U.removeMinusesAndConvertNumbers(U.fromXml("<a><b/><b/></a>"));
        assertEquals("{a={b=[{}, {}]}}", result3.toString());
        Map<String, Object> result4 =
                U.removeMinusesAndConvertNumbers(U.fromXml("<a><b c=\"1\"/></a>"));
        assertEquals("{a={b={c=1}}}", result4.toString());
        Map<String, Object> result5 =
                U.removeMinusesAndConvertNumbers(U.fromXml("<a><b c=\"-1e1\"/></a>"));
        assertEquals("{a={b={c=-10.0}}}", result5.toString());
        Map<String, Object> result6 =
                U.removeMinusesAndConvertNumbers(U.fromXml("<a><b c=\"-1E1\"/></a>"));
        assertEquals("{a={b={c=-10.0}}}", result6.toString());
        Map<String, Object> result7 =
                U.removeMinusesAndConvertNumbers(U.fromXml("<a><b c=\"1.a\"/></a>"));
        assertEquals("{a={b={c=1.a}}}", result7.toString());
        Map<String, Object> result8 =
                U.removeMinusesAndConvertNumbers(U.fromXml("<a><b c=\"1.-\"/></a>"));
        assertEquals("{a={b={c=1.-}}}", result8.toString());
        Map<String, Object> result9 =
                U.removeMinusesAndConvertNumbers(U.fromXml("<a><b c=\"+1ee\"/></a>"));
        assertEquals("{a={b={c=+1ee}}}", result9.toString());
        Map<String, Object> map = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<>());
        map.put("list", list);
        Map<String, Object> result10 = U.removeMinusesAndConvertNumbers(map);
        assertEquals("{list=[[]]}", result10.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    void replaceFirstLevel() {
        Map<String, Object> result = U.replaceFirstLevel(U.fromXml("<a/>"));
        assertEquals("{}", result.toString());
        Map<String, Object> result2 = U.replaceFirstLevel(U.fromXml("<a><b>c</b></a>"));
        assertEquals("{b=c}", result2.toString());
        String result3 = U.xmlToJson("<a><b>c</b></a>", U.XmlToJsonMode.REMOVE_FIRST_LEVEL);
        assertEquals("{\n  \"b\": \"c\"\n}", result3);
        Map<String, Object> map = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new LinkedHashMap<>());
        map.put("list", list);
        U.replaceFirstLevel(map);
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list2 = new ArrayList<>();
        list2.add(new ArrayList<>());
        map2.put("list", list2);
        U.replaceFirstLevel(map2);
        Map<String, Object> result4 = U.fromXml("<a/>");
        ((Map<String, Object>) result4.get("a")).put("-self-closing", "false");
        U.replaceFirstLevel(result4);
    }

    @Test
    @SuppressWarnings("unchecked")
    void deepCopyMap() {
        Map<String, Object> result = U.deepCopyMap(U.fromXml("<a/>"));
        assertEquals("{a={-self-closing=true}, #omit-xml-declaration=yes}", result.toString());
        Map<String, Object> result2 = U.deepCopyMap(U.fromXml("<a><b>c</b></a>"));
        assertEquals("{a={b=c}, #omit-xml-declaration=yes}", result2.toString());
        Map<String, Object> map = new LinkedHashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(new LinkedHashMap<>());
        map.put("list", list);
        U.deepCopyMap(map);
        Map<String, Object> map2 = new LinkedHashMap<>();
        List<Object> list2 = new ArrayList<>();
        list2.add(new ArrayList<>());
        map2.put("list", list2);
        U.deepCopyMap(map2);
        Map<String, Object> result3 = U.fromXml("<a/>");
        ((Map<String, Object>) result3.get("a")).put("-self-closing", "false");
        U.deepCopyMap(result3);
    }

    @Test
    void objectBuilder() {
        U.Builder builder = U.objectBuilder().add("1", "2").add("2");
        builder.add(builder);
        builder.toJson();
        U.Builder.fromJson("{}");
        builder.toXml();
        U.Builder.fromXml("<a/>");
        U.Builder.fromMap(new LinkedHashMap<>());
        builder.add(new LinkedHashMap<>());
        builder.update(new LinkedHashMap<>());
        builder.set("1", "3");
        builder.toString();
        assertEquals("{1=3}", builder.build().toString());
        assertEquals("3", builder.<String>get("1"));
        builder.remove("1");
        builder.set(asList("2"), "4");
        assertEquals("{2=4}", builder.build().toString());
        assertEquals("4", builder.<String>get(asList("2")));
        builder.remove(asList("2"));
        assertEquals("{}", builder.build().toString());
        builder.clear();
        builder.isEmpty();
        builder.size();
        assertEquals("{}", builder.build().toString());
        builder.toChain();
        builder.addNull("key");
        assertNull(builder.<String>get("key"));
        Map<String, Object> value =
                U.objectBuilder()
                        .add("firstName", "John")
                        .add("lastName", "Smith")
                        .add("age", 25)
                        .add(
                                "address",
                                U.objectBuilder()
                                        .add("streetAddress", "21 2nd Street")
                                        .add("city", "New York")
                                        .add("state", "NY")
                                        .add("postalCode", "10021"))
                        .add(
                                "phoneNumber",
                                U.arrayBuilder()
                                        .add(
                                                U.objectBuilder()
                                                        .add("type", "home")
                                                        .add("number", "212 555-1234"))
                                        .add(
                                                U.objectBuilder()
                                                        .add("type", "fax")
                                                        .add("number", "646 555-4567")))
                        .build();
        assertEquals(
                "{firstName=John, lastName=Smith, age=25, address={streetAddress=21 2nd Street, "
                        + "city=New York, state=NY, postalCode=10021}, phoneNumber=[{type=home, number=212 555-1234}, "
                        + "{type=fax, number=646 555-4567}]}",
                value.toString());
    }

    @Test
    void arrayBuilder() {
        U.ArrayBuilder builder = U.arrayBuilder().add("1").add("2");
        builder.add(builder);
        builder.toJson();
        assertEquals("1", builder.<String>get("0"));
        assertEquals("1", builder.<String>get(asList("0")));
        U.ArrayBuilder.fromJson("[]");
        builder.toXml();
        U.ArrayBuilder.fromXml(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root empty-array=\"true\"></root>");
        builder.set(1, "3");
        builder.toString();
        assertEquals("[1, 3, 1, 2]", builder.build().toString());
        builder.remove(1);
        assertEquals("[1, 1, 2]", builder.build().toString());
        builder.merge(new ArrayList<>());
        builder.clear();
        builder.isEmpty();
        builder.size();
        assertEquals("[]", builder.build().toString());
        builder.toChain();
        builder.addNull();
        Map<String, Object> value =
                U.objectBuilder()
                        .add("firstName", "John")
                        .add("lastName", "Smith")
                        .add("age", 25)
                        .add(
                                "address",
                                U.arrayBuilder()
                                        .add(
                                                U.objectBuilder()
                                                        .add("streetAddress", "21 2nd Street")
                                                        .add("city", "New York")
                                                        .add("state", "NY")
                                                        .add("postalCode", "10021")))
                        .add(
                                "phoneNumber",
                                U.arrayBuilder()
                                        .add(
                                                U.objectBuilder()
                                                        .add("type", "home")
                                                        .add("number", "212 555-1234"))
                                        .add(
                                                U.objectBuilder()
                                                        .add("type", "fax")
                                                        .add("number", "646 555-4567")))
                        .build();
        assertEquals(
                "{firstName=John, lastName=Smith, age=25, address=[{streetAddress=21 2nd Street, "
                        + "city=New York, state=NY, postalCode=10021}], phoneNumber=[{type=home, number=212 555-1234}, "
                        + "{type=fax, number=646 555-4567}]}",
                value.toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testMain() {
        new U<>(new ArrayList<String>());
        new U<>("");
        new U<>(Collections.emptyList()).chain();
        new U<>(Collections.emptyList()).of();
        U.chain(new ArrayList<String>());
        U.chain(new ArrayList<String>(), 1);
        U.chain(new HashSet<String>());
        U.chain(new String[] {});
        U.chain("");
        U.of(new ArrayList<String>());
        U.of(new ArrayList<String>(), 1);
        U.of(new HashSet<String>());
        U.of(new String[] {});
        U.of(new int[] {});
        U.of("");
        U.of(new LinkedHashMap<>());
        new LinkedHashSet<>();
    }

    @SuppressWarnings("unchecked")
    @Test
    void chain() {
        U.chain(new String[] {""}).first();
        U.chain(new String[] {""}).first(1);
        U.chain(new String[] {""}).firstOrNull();
        U.chain(new String[] {""}).firstOrNull(str -> true);
        U.chain(new String[] {""}).initial();
        U.chain(new String[] {""}).initial(1);
        U.chain(new String[] {""}).last();
        U.chain(new String[] {""}).last(1);
        U.chain(new String[] {""}).lastOrNull();
        U.chain(new String[] {""}).lastOrNull(str -> true);
        U.chain(new String[] {""}).rest();
        U.chain(new String[] {""}).rest(1);
        U.chain(new String[] {""}).compact();
        U.chain(new String[] {""}).compact("1");
        U.chain(new String[] {""}).flatten();
        U.chain(new Integer[] {0}).map(value -> value);
        U.chain(new Integer[] {0}).mapMulti((integer, consumer) -> consumer.accept(integer));
        U.chain(new Integer[] {0}).mapIndexed((index, value) -> value);
        U.chain(new String[] {""}).filter(str -> true);
        U.chain(new String[] {""}).filterIndexed((index, str) -> true);
        U.chain(new String[] {""}).reject(str -> true);
        U.chain(new String[] {""}).rejectIndexed((index, str) -> true);
        U.chain(new String[] {""}).filterFalse(str -> true);
        U.chain(new String[] {""}).reduce((accum, str) -> null, "");
        U.chain(new String[] {""}).reduce((accum, str) -> null);
        U.chain(new String[] {""}).reduceRight((accum, str) -> null, "");
        U.chain(new String[] {""}).reduceRight((accum, str) -> null);
        U.chain(new String[] {""}).find(str -> true);
        U.chain(new String[] {""}).findLast(str -> true);
        U.chain(new Integer[] {0}).max();
        U.chain(new Integer[] {0}).max(value -> value);
        U.chain(new Integer[] {0}).min();
        U.chain(new Integer[] {0}).min(value -> value);
        U.chain(new Integer[] {0}).sort();
        U.chain(new Integer[] {0}).sortWith((Comparator<Integer>) (value1, value2) -> value1);
        U.chain(new Integer[] {0}).sortBy(value -> value);
        U.chain(new LinkedHashMap<Integer, Integer>().entrySet()).sortBy("");
        U.chain(new Integer[] {0}).groupBy(value -> value);
        U.chain(new Integer[] {0}).associateBy(value -> value);
        U.chain(new Integer[] {0}).groupBy(num -> num, (a, b) -> a);
        U.chain(new Integer[] {0}).indexBy("");
        U.chain(new Integer[] {0}).countBy(value -> value);
        U.chain(new Integer[] {0}).countBy();
        U.chain(new Integer[] {0}).shuffle();
        U.chain(new Integer[] {0}).sample();
        U.chain(new Integer[] {0}).sample(1);
        U.chain(new int[] {0}).value();
        U.chain(new String[] {""}).tap(str -> {
        });
        U.chain(new String[] {""}).forEach(str -> {
        });
        U.chain(new String[] {""}).forEachRight(str -> {
        });
        U.chain(new String[] {""}).every(str -> true);
        U.chain(new String[] {""}).some(str -> true);
        U.chain(new String[] {""}).count(str -> true);
        U.chain(new String[] {""}).contains("");
        U.chain(new String[] {""}).containsWith("");
        U.chain(new String[] {""}).invoke("toString", Collections.emptyList());
        U.chain(new String[] {""}).invoke("toString");
        U.chain(new String[] {""}).pluck("toString");
        U.chain(new String[] {""}).where(Collections.<Map.Entry<String, String>>emptyList());
        U.chain(new String[] {""}).findWhere(Collections.<Map.Entry<String, String>>emptyList());
        U.chain(new Integer[] {0}).uniq();
        U.chain(new Integer[] {0}).uniq(value -> value);
        U.chain(new Integer[] {0}).distinct();
        U.chain(new Integer[] {0}).distinctBy(value -> value);
    }

    @SuppressWarnings("unchecked")
    @Test
    void chain2() {
        U.chain(new String[] {""}).union();
        U.chain(new String[] {""}).intersection();
        U.chain(new String[] {""}).difference();
        U.chain(new String[] {""}).range(0);
        U.chain(new String[] {""}).range(0, 0);
        U.chain(new String[] {""}).range(0, 0, 1);
        U.chain(new String[] {""}).chunk(1);
        U.chain(new String[] {""}).chunk(1, 2);
        U.chain(new String[] {""}).chunkFill(1, "");
        U.chain(new String[] {""}).chunkFill(1, 2, "");
        U.chain(new String[] {""}).cycle(1);
        U.chain(new String[] {""}).interpose("");
        U.chain(new String[] {""}).interposeByList(Collections.emptyList());
        U.chain(new String[] {""}).concat();
        U.chain(new String[] {""}).slice(0);
        U.chain(new String[] {""}).slice(0, 0);
        U.chain(new String[] {""}).reverse();
        U.chain(new String[] {""}).join();
        U.chain(new String[] {""}).join("");
        U.chain(new String[] {""}).skip(0);
        U.chain(new String[] {""}).limit(0);
        U.chain(new LinkedHashMap<Integer, Integer>().entrySet()).toMap();
    }

    @Test
    @SuppressWarnings("unchecked")
    void chainMap() {
        assertEquals(
                "{name1=one, name2=two}",
                Underscore.chain(
                                new LinkedHashMap<>() {
                                    {
                                        put("name1", "one");
                                        put("name2", "two");
                                    }
                                })
                        .map()
                        .toString());
        assertEquals(
                "{name1=one, name2=two, 1=2}",
                com.github
                        .underscore
                        .U
                        .chain(
                                new LinkedHashMap<>() {
                                    {
                                        put("name1", "one");
                                        put("name2", "two");
                                    }
                                })
                        .set("1", "2")
                        .map()
                        .toString());
        assertEquals(
                "{name1=one, name2=two, 1=2}",
                com.github
                        .underscore
                        .U
                        .of(
                                new LinkedHashMap<>() {
                                    {
                                        put("name1", "one");
                                        put("name2", "two");
                                    }
                                })
                        .set("1", "2")
                        .map()
                        .toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    void stackoverflow() {
        // http://stackoverflow.com/questions/443499/convert-json-to-map
        String json =
                "{"
                        + "    \"data\" :"
                        + "    {"
                        + "        \"field1\" : \"value1\","
                        + "        \"field2\" : \"value2\""
                        + "    }"
                        + "}";

        Map<String, Object> data = U.get(U.fromJson(json), "data");
        assertEquals("{field1=value1, field2=value2}", data.toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    void stackoverflow2() {
        // http://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
        String json =
                "{"
                        + "\"name\" : \"abc\" ,"
                        + "\"email id \" : [\"abc@gmail.com\",\"def@gmail.com\",\"ghi@gmail.com\"]"
                        + "}";
        String data = U.get(U.fromJson(json), "email id .1");
        assertEquals("def@gmail.com", data);
    }

    @Test
    void stackoverflow3() {
        // http://stackoverflow.com/questions/4550662/how-do-you-find-the-sum-of-all-the-numbers-in-an-array-in-java
        int sum = U.sum(Arrays.asList(1, 2, 3, 4));
        assertEquals(10, sum);
    }

    @SuppressWarnings("unchecked")
    @Test
    void sqlrujava() {
        // http://www.sql.ru/forum/1232207/kak-pravilno-razobrat-json-org-json-simple
        String json =
                "{"
                        + "  \"from_excel\":["
                        + "    {"
                        + "      \"solution\":\"Fisrt\","
                        + "      \"num\":\"1\""
                        + "    },"
                        + "    {"
                        + "      \"solution\":\"Second\","
                        + "      \"num\":\"2\""
                        + "    },"
                        + "    {"
                        + "      \"solution\":\"third\","
                        + "      \"num\":\"3\""
                        + "    },"
                        + "    {"
                        + "      \"solution\":\"fourth\","
                        + "      \"num\":\"4\""
                        + "    },"
                        + "    {"
                        + "      \"solution\":\"fifth\","
                        + "      \"num\":\"5\""
                        + "    }"
                        + "  ]"
                        + "}";

        List<Map<String, Object>> fromExcelData = U.get(U.fromJson(json), "from_excel");
        assertEquals(
                "[{solution=Fisrt, num=1}, {solution=Second, num=2}, {solution=third, num=3}, "
                        + "{solution=fourth, num=4}, {solution=fifth, num=5}]",
                fromExcelData.toString());
        List<String> solutions = U.map(fromExcelData, item -> (String) item.get("solution"));
        assertEquals("[Fisrt, Second, third, fourth, fifth]", solutions.toString());
    }

    @Test
    void stackoverflow4() {
        // http://stackoverflow.com/questions/25085399/converting-xml-into-java-mapstring-integer
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                        + "<root>\n"
                        + "   <Durapipe type=\"int\">1</Durapipe>\n"
                        + "   <EXPLAIN type=\"int\">2</EXPLAIN>\n"
                        + "   <woods type=\"int\">2</woods>\n"
                        + "   <hanging type=\"int\">3</hanging>\n"
                        + "   <hastily type=\"int\">2</hastily>\n"
                        + "   <localized type=\"int\">1</localized>\n"
                        + "   <Schuster type=\"int\">5</Schuster>\n"
                        + "   <regularize type=\"int\">1</regularize>\n"
                        + "   <LASR type=\"int\">1</LASR>\n"
                        + "   <LAST type=\"int\">22</LAST>\n"
                        + "   <Gelch type=\"int\">2</Gelch>\n"
                        + "   <Gelco type=\"int\">26</Gelco>\n"
                        + "</root>";

        assertEquals(
                "{Durapipe={-type=int, #text=1}, EXPLAIN={-type=int, #text=2}, "
                        + "woods={-type=int, #text=2}, hanging={-type=int, #text=3}, "
                        + "hastily={-type=int, #text=2}, localized={-type=int, #text=1}, "
                        + "Schuster={-type=int, #text=5}, regularize={-type=int, #text=1}, "
                        + "LASR={-type=int, #text=1}, LAST={-type=int, #text=22}, "
                        + "Gelch={-type=int, #text=2}, Gelco={-type=int, #text=26}}",
                U.fromXml(xml).toString());
    }

    @Test
    void stackoverflow5() {
        // https://stackoverflow.com/questions/59429211/
        // convert-xml-to-json-and-vice-versa-and-also-how-to-identify-rest-endpoint-while
        String xmlData =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
                        + " xmlns:urn=\"urn:ahc.com:dms:wsdls:organization\">\n"
                        + "   <soapenv:Header/>\n"
                        + "   <soapenv:Body>\n"
                        + "      <urn:getRoles>\n"
                        + "         <getRolesRequest>\n"
                        + "            <Type>ABC</Type>\n"
                        + "         </getRolesRequest>\n"
                        + "      </urn:getRoles>\n"
                        + "   </soapenv:Body>\n"
                        + "</soapenv:Envelope>";
        Map<String, Object> jsonData =
                U.get(U.fromXmlWithoutNamespaces(xmlData), "Envelope.Body.getRoles");
        assertEquals("{getRolesRequest={Type=ABC}}", jsonData.toString());
    }

    @Test
    void stackoverflow6() {
        // https://stackoverflow.com/questions/59585708/getting-null-pointer-while-reading-the-fileds-from-json-to-pojo
        String jsonData =
                "{\n"
                        + "    \"TEST\": {\n"
                        + "        \"NAME\": \"PART_TRAN\",\n"
                        + "        \"VERSION\": \"9.0\",\n"
                        + "        \"ID\": \"----\",\n"
                        + "        \"SEGMENT\": {\n"
                        + "            \"TYPE\": \"R\",\n"
                        + "            \"CLIENT_ID\": \"----\",\n"
                        + "            \"UN_NUM\": \"UN\"\n"
                        + "        }"
                        + "    }"
                        + "}";
        Map<String, Object> jsonObject = U.fromJsonMap(jsonData);
        assertEquals("R", U.<String>get(jsonObject, "TEST.SEGMENT.TYPE"));
        assertEquals("UN", U.<String>get(jsonObject, "TEST.SEGMENT.UN_NUM"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void sqlru1() {
        // http://www.sql.ru/forum/1296485/poluchit-nazvaniya-iz-json-v-jsp
        String json =
                "{\"memory\":[{\"alert\":\"false\",\"value\":\"50%\"}],\"cpu\":"
                        + "[{\"alert\":\"true\",\"value\":\"100%\"}],\"hdd\":"
                        + "[{\"alert\":\"false\",\"value\":\"80%\"}]}";

        assertEquals(
                "[memory, cpu, hdd]", U.keys((Map<String, Object>) U.fromJson(json)).toString());
    }

    @Test
    void sqlru2() {
        // https://www.sql.ru/forum/1321326/kolichestvo-naydennyh-slov-v-stroke
        assertEquals(
                2, U.countBy(U.words("Маша ищет Мишу а Миша ищет Машу")).get("ищет").intValue());
    }

    @Test
    void stackoverflow7() {
        String json =
                U.objectBuilder()
                        .add("key1", "value1")
                        .add("key2", "value2")
                        .add("key3", U.objectBuilder().add("innerKey1", "value3"))
                        .toJson();
        assertEquals(
                "{\n  \"key1\": \"value1\",\n"
                        + "  \"key2\": \"value2\",\n"
                        + "  \"key3\": {\n"
                        + "    \"innerKey1\": \"value3\"\n"
                        + "  }\n"
                        + "}",
                json);
    }

    @Test
    void stackoverflow8() {
        class Customer {
            String name;
            int age;
            int id;
        }
        Customer customer = new Customer();
        customer.name = "John";
        customer.age = 30;
        customer.id = 12345;
        String xml =
                U.objectBuilder()
                        .add(
                                "customer",
                                U.objectBuilder()
                                        .add("name", customer.name)
                                        .add("age", customer.age)
                                        .add("id", customer.id))
                        .toXml();
        assertEquals(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<customer>\n"
                        + "  <name>John</name>\n"
                        + "  <age number=\"true\">30</age>\n"
                        + "  <id number=\"true\">12345</id>\n"
                        + "</customer>",
                xml);
    }

    @Test
    void issue306() {
        String json =
                U.objectBuilder().add("firstName", "John").add("lastName", (Object) null).toJson();
        assertEquals("{\n  \"firstName\": \"John\",\n  \"lastName\": null\n}", json);
    }

    @Test
    void issue308() {
        String xml =
                "<some_root>\n"
                        + "  <ABC some_attribute=\"attribute\">\n"
                        + "    <another_tag>some_value</another_tag>\n"
                        + "  </ABC>\n"
                        + "</some_root>";
        Map<String, Object> data = U.fromXmlMap(xml);
        U.set(data, "some_root.ABC.#text", U.get(data, "some_root.ABC.another_tag"));
        U.remove(data, "some_root.ABC.another_tag");
        String newXml = U.toXml(data);
        assertEquals(
                "<some_root>\n"
                        + "  <ABC some_attribute=\"attribute\">some_value</ABC>\n"
                        + "</some_root>",
                newXml);
    }
}
