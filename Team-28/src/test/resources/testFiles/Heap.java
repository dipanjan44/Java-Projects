package sorters;

import util.Sorter;

import java.util.Comparator;

// The HeapSorter Class implements the heap sort algorithm.
// Algorithm Description Citation: https://algs4.cs.princeton.edu/24pq/Heap.java.html - Used in Coursera Algorithm course
public class HeapSorter<T> implements Sorter {

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * 
	 * @param a
	 *            the array to be sorted
	 */

	@Override
	public void sort(Comparable[] a) {
		int n = a.length;
		for (int k = n / 2; k >= 1; k--)
			sink(a, k, n);
		while (n > 1) {
			exch(a, 1, n--);
			sink(a, 1, n);
		}
	}
}
