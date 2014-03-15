package com.github.bicoco;

import com.github.bicoco.collections.functions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ListTest {

    List<String> strings = new ArrayList<String>();
    List<Integer> numbers = new ArrayList<Integer>();

    @Before
    public void setup() {
        strings = new ArrayList<String>(
                Arrays.asList("A", "B", "C", "D", "DA", "ABC")
        );
        numbers = new ArrayList<Integer>(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
        );
    }

    public @Test void eachListOfStrings() {
        final StringBuilder result = new StringBuilder();
        $.each(strings, new EachFunction<String>() {
            @Override
            public void each(String s) {
                if (s.startsWith("A")) {
                    result.append(s);
                }
            }
        });

        assertEquals("AABC", result.toString());
    }

    public @Test void mapListOfStrings() {
        List<String> result = $.map(strings, new MapFunction<String>() {
            @Override
            public String map(String s) {
                if (s.startsWith("A")) {
                    return s.concat("B");
                }
                return s;
            }
        });

        List<String> expectedStrings = Arrays.asList(
                "A", "B", "C", "D", "DA", "ABC");
        List<String> expectedResult = Arrays.asList(
                "AB", "B", "C", "D", "DA", "ABCB");

        assertEquals(expectedStrings, strings);
        assertEquals(expectedResult, result);
    }

    public @Test void selectValuesGreaterThan4InNumberList() {
        List<Integer> list = $.select(numbers, new ConditionFunction<Integer>() {
            @Override
            public boolean condition(Integer i) {
                return i < 4;
            }
        });

        List<Integer> expectedResult = Arrays.asList(1, 2, 3);
        List<Integer> expectedValues = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        assertEquals(expectedResult, list);
        assertEquals(expectedValues, numbers);
    }

    public @Test void rejectValuesGreaterThan4InNumberList() {
        List<Integer> result = $.reject(numbers, new ConditionFunction<Integer>() {
            @Override
            public boolean condition(Integer i) {
                return i < 4;
            }
        });

        List<Integer> expectedResult = Arrays.asList(4, 5, 6, 7, 8, 9);
        List<Integer> expectedValues = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        assertEquals(expectedResult, result);
        assertEquals(expectedValues, numbers);
    }

    public @Test void select$ValuesGreaterThan4InNumberList() {
        $.select$(numbers, new ConditionFunction<Integer>() {
            @Override
            public boolean condition(Integer i) {
                return i < 4;
            }
        });

        List<Integer> expected = Arrays.asList(1, 2, 3);

        assertEquals(expected, numbers);
    }

    public @Test void reject$ValuesGreaterThan4InNumberList() {
        $.reject$(numbers, new ConditionFunction<Integer>() {
            @Override
            public boolean condition(Integer i) {
                return i < 4;
            }
        });

        List<Integer> expected = Arrays.asList(4, 5, 6, 7, 8, 9);

        assertEquals(expected, numbers);
    }

    public @Test void compact$ValuesGreaterThan4InNumberList() {

        $.push(numbers, null, null, null);
        $.compact$(numbers);

        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        assertEquals(expected, numbers);
    }

    public @Test void map$ListOfStrings() {
        $.map$(strings, new MapFunction<String>() {
            @Override
            public String map(String s) {
                if (s.startsWith("A")) {
                    return s.concat("B");
                }
                return s;
            }
        });

        List<String> expected = Arrays.asList(
                "AB", "B", "C", "D", "DA", "ABCB");

        assertEquals(expected, strings);
    }

    public @Test void transformStringsInIntegers() {
        List<Integer> result = $.transform(strings,
                new TransformFunction<String, Integer>() {
                    @Override
                    public Integer transform(String s) {
                        return s.length();
                    }
                }
        );

        List<Integer> expected = Arrays.asList(1, 1, 1, 1, 2, 3);

        assertEquals(expected, result);
    }

    public @Test void getFirstElement() {
        String s = $.first(strings);
        assertEquals("A", s);
    }

    public @Test void getLastElement() {
        String s = $.last(strings);
        assertEquals("ABC", s);
    }

    public @Test void invokeAtWithValidIndex() {
        String s = $.at(strings, 1);
        assertEquals("B", s);
    }

    public @Test void invokeAtWithInvalidIndex() {
        String s = $.at(strings, 10);
        assertNull(s);
    }

    public @Test void invokeFetchWithValidIndex() {
        String s = $.fetch(strings, 1);
        assertEquals("B", s);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void invokeFetchWithInvalidIndex() {
        String s = $.fetch(strings, 10);
        assertNull(s);
    }

    public @Test void invokeFetchWithInvalidIndexAndDefaultValue() {
        String s = $.fetch(strings, 10, "maoe");
        assertEquals("maoe", s);
    }

    public @Test void chainMethodPush() {
        $.push(strings, "F")
         .push("G")
         .push("H");

        List<String> expected = Arrays.asList(
                "A", "B", "C", "D", "DA", "ABC", "F", "G", "H");
        assertEquals(expected, strings);
    }

    public @Test void pushMultipleElements() {
        $.push(strings, "F", "G", "H");
        List<String> expected = Arrays.asList(
                "A", "B", "C", "D", "DA", "ABC", "F", "G", "H");
        assertEquals(expected, strings);
    }

    public @Test void take() {
        List<Integer> list = $.take(numbers, 3);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, list);
    }

    public @Test void drop() {
        List<Integer> list = $.drop(numbers, 3);
        List<Integer> expected = Arrays.asList(4, 5, 6, 7, 8, 9);
        assertEquals(expected, list);
    }

    public @Test void isEmpty() {
        assertFalse($.isEmpty(numbers));
    }

    public @Test void isNotEmpty() {
        assertTrue($.isNotEmpty(numbers));
    }

    public @Test void size() {
        int count = $.size(numbers);
        assertEquals(9, count);
    }

    public @Test void count() {
        int count = $.count(numbers);
        assertEquals(9, count);
    }

    public @Test void countValuesGreaterThan4() {
        int count = $.count(numbers, new ConditionFunction<Integer>() {
            @Override
            public boolean condition(Integer val) {
                return val > 4;
            }
        });
        assertEquals(5, count);
    }
    
    public @Test void all(){
    	boolean result = $.all(numbers, new ConditionFunction<Integer>(){

			@Override
			public boolean condition(Integer t) {
				return t < 10;
			}

		});
    	assertTrue(result);
    }
    
    public @Test void any(){
    	boolean result = $.any(numbers, new ConditionFunction<Integer>(){

			@Override
			public boolean condition(Integer t) {
				return t == 5;
			}

		});
    	assertTrue(result);
    }

    public @Test void readme() {
        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(new Person("David", 27));
        persons.add(new Person("André", 30));
        persons.add(new Person("Fernando", 25));
        persons.add(new Person("Lucas", 15));

        $.each(persons, new EachFunction<Person>() {
            public void each(Person person) {
                System.out.println(person.getName());
            }
        });

        List<Integer> ages = $.transform(persons, new TransformFunction<Person,Integer>() {
            public Integer transform(Person person) {
                return person.getAge();
            }
        });

        System.out.println(ages);

        List<Person> personsGreaterThan18YearsOld = $.select(persons, new ConditionFunction<Person>() {
            public boolean condition(Person person) {
                return person.getAge() > 18;
            }
        });

        $.each(personsGreaterThan18YearsOld, new EachFunction<Person>() {
            public void each(Person person) {
                System.out.println(person.getName());
            }
        });

    }

    public class Person {
        private String name;
        private Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }
    }

}
