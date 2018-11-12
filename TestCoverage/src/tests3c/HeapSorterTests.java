package tests3c;
import util.Sorter;
import sorters.HeapSorter;
import tests.Student;

import static org.junit.Assert.assertArrayEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HeapSorterTests {
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public  void setUp() throws Exception {
	}

	@After
	public  void tearDown() throws Exception {
	}
	


	  /**
     * Method for creating a sorter.
     * @return
     */
    private static <T extends Comparable<T>> Sorter<T> createSorter(){
      return new HeapSorter<T>();
}
	// -----------------------------------------------
	// sort a list with one String element
	@Test
	public void testOneElement() {
		Sorter<String> sorter = createSorter();
		String[] animals = { "dipanjan" };
		sorter.sort(animals);
		String[] sorted = { "dipanjan" };
		assertArrayEquals(animals, sorted);
	}

	// ----------------------------------------
	// sort list with multiple String element
	@Test
	public void testMultipleElement() {
		String[] input = { "apple", "mango", "grape" };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { "apple", "grape", "mango" };
		assertArrayEquals(input, sorterOutput);

	}

	// test a string array with no element- empty
	// returns an array with no element
	@Test
	public void testEmptyElement() {
		String[] input = {};
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = {};
		assertArrayEquals(input, sorterOutput);

	}

	// test a string array with null value
	// returns an array with null value
	@Test
	public void testNull() {
		String[] input = { null };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { null };
		assertArrayEquals(input, sorterOutput);

	}

	// test a string array with one valid element and other element as empty
	// the sorted array should be have empty at the start followed by the
	// element
	@Test
	public void validEmpty() {
		String[] input = { "dipanjan", " " };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { " ", "dipanjan" };
		assertArrayEquals(input, sorterOutput);

	}

	// test a list of string elements which have different trailing spaces
	// it should return the array sorted in ascending order with string without
	// spaces at the first position
	@Test
	public void trailingSpaces() {
		String[] input = { "dipanjan ", "dipanjan  ", " ", "dipanjan" };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { " ", "dipanjan", "dipanjan ", "dipanjan  " };
		assertArrayEquals(input, sorterOutput);

	}

	// test a list of string elements which have different leading spaces
	// it should return the array sorted in ascending order with string without
	// spaces at the first position
	@Test
	public void leadingSpaces() {
		String[] input = { " dipanjan", "  dipanjan", " ", "   dipanjan" };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { " ", "   dipanjan", "  dipanjan", " dipanjan" };
		assertArrayEquals(input, sorterOutput);

	}

	// test a list of string elements which have special characters
	// it should return the sorted string array in ascending following the
	// in-built order of precedence for the special characters

	@Test
	public void specialCharacter() {
		String[] input = { "%dipanjan", "$dipanjan", "&dipanjan" };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { "$dipanjan", "%dipanjan", "&dipanjan" };
		assertArrayEquals(input, sorterOutput);

	}

	// test a list of String where each the characters of each string are
	// numbers

	@Test
	public void numbersInString() {
		String[] input = { "54", "38", "0", "-1","10" };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { "-1", "0", "10","38", "54"};
		assertArrayEquals(input, sorterOutput);

	}

	// test a list of String where each string have a combination of letter and
	// number as its character

	// sorter will return the array in ascending order with the smaller number
	// in the first position

	@Test
	public void numberStringcombo() {
		String[] input = { "2dipanjan1", "dipan3jan", "dipan1jan" };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { "2dipanjan1", "dipan1jan", "dipan3jan" };
		assertArrayEquals(input, sorterOutput);

	}

	// test a list of String which have upper-case and Lower-case

	// the sorted array will return the string array in ascending order starting
	// with Upper-case

	@Test
	public void caseSensitivity() {
		String[] input = { "dipanjan", "DIPANJAN", "diPanjan" };
		Sorter<String> sorter = createSorter();
		sorter.sort(input);
		String[] sorterOutput = { "DIPANJAN", "diPanjan", "dipanjan" };
		assertArrayEquals(input, sorterOutput);

	}

	// test the sorting of a user-defined type Student based. The students are
	// sorted in ascending order of student Id

	@Test
	public void testOneUserDefinedType() {
		Sorter<Student> sorter = createSorter();
		Student[] input = { new Student(1, "Blake"), new Student(3, "Doe"), new Student(2, "Dan") };
		sorter.sort(input);
		Student[] sorted = { new Student(1, "Blake"), new Student(2, "Dan"), new Student(3, "Doe") };
		assertArrayEquals(input,sorted);
	}

	// test to sort a list of integers with a combination of positive and
	// negative
	// sorted in ascending order
	@Test
	public void integerArray() {
		Integer[] input = { -1, 1, 0, -10, 5, 100, 20 };
		Sorter<Integer> sorter = createSorter();
		sorter.sort(input);
		Integer[] sorterOutput = { -10, -1, 0, 1, 5, 20, 100 };
		assertArrayEquals(input, sorterOutput);

	}

	// test an empty integer array
	// sort method returns an empty integer array

	@Test
	public void emptyintegerArray() {
		Integer[] input = {};
		Sorter<Integer> sorter = createSorter();
		sorter.sort(input);
		Integer[] sorterOutput = {};
		assertArrayEquals(input, sorterOutput);

	}

	// test a null integer array
	// sort method returns a null array

	@Test
	public void nullintegerArray() {
		Integer[] input = { null };
		Sorter<Integer> sorter = createSorter();
		sorter.sort(input);
		Integer[] sorterOutput = { null };
		assertArrayEquals(input, sorterOutput);

	}

	// test an integer array which is already sorted in ascending order
	// sort method returns the same array

	@Test
	public void sortedArrayASC() {
		Integer[] input = { -1, 0, 1, 2, 3, 4 };
		Sorter<Integer> sorter = createSorter();
		sorter.sort(input);
		Integer[] sorterOutput = { -1, 0, 1, 2, 3, 4 };
		assertArrayEquals(input, sorterOutput);

	}

	// test an integer array which has all negative and 0 elements
	// sort method returns the array in ascending order with the 0's at the end

	@Test
	public void negativeZeroArray() {
		Integer[] input = { -1, 0, -11, -32, 0, 0 };
		Sorter<Integer> sorter = createSorter();
		sorter.sort(input);
		Integer[] sorterOutput = { -32, -11, -1, 0, 0, 0 };
		assertArrayEquals(input, sorterOutput);

	}

	// test an integer array with max_val and min_val of integers
	@Test
	public void testLimits() {
		Sorter<Integer> sorter = createSorter();
		// int MIN_SIZE=Integer.MIN_VALUE;
		int MAX_SIZE = 200000;
		Integer[] input = new Integer[MAX_SIZE];
		Integer[] sorted = new Integer[MAX_SIZE];
		for (int i = 0; i < MAX_SIZE; i++) {
			sorted[i] = i;
			input[MAX_SIZE - i - 1] = i;
		}
		sorter.sort(input);
		assertArrayEquals(input, sorted);
	}

	// test an integer array with duplicate elements
	@Test
	public void testDuplicateElementArray() {
		Sorter<Integer> sorter = createSorter();
		Integer[] input = { -1, 0, 0, 1, 1, 22, 3, 333, 123 };
		sorter.sort(input);
		Integer[] sorted = { -1, 0, 0, 1, 1, 3, 22, 123, 333 };
		assertArrayEquals(input, sorted);
	}

	// test an integer array with null and some valid elements
	// returns an array in ascending order with null as starting

	@Test
	public void nullValidCombo() {
		Sorter<Integer> sorter = createSorter();
		Integer[] input = { -1, 0, 0, 1, 1, 22, 3, null,123,null,333};
		sorter.sort(input);
		Integer[] sorted = {null, null, -1, 0, 0, 1, 1, 3, 22, 123, 333 };
		assertArrayEquals(input, sorted);
	}

	// test an integer array with Integer.Max val as the highest element and
	// Integer.MaxVal+1 as min element
	// returns an array in ascending order with null as starting

	@Test
	public void boundaryTest() {
		Sorter<Integer> sorter = createSorter();
		Integer var = Integer.MAX_VALUE;
		Integer[] input = { -1, 0, 0, 1, 1, 22, 3, 123,333, var, var + 1 };
		sorter.sort(input);
		Integer[] sorted = {var + 1, -1, 0, 0, 1, 1, 3, 22, 123, 333, var };
		assertArrayEquals(input, sorted);
	}
	@Test
	public void descnullTest() {
		Sorter<Integer> sorter = createSorter();
		Integer[] input = {5,2,1,null,0 };
		sorter.sort(input);
		Integer[] sorted = {null,0,1,2,5 };
		assertArrayEquals(input, sorted);
	}


}
