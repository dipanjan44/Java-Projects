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

	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	private void sink(Comparable[] a, int k, int n) {
		// NullConditionCheck nc= new NullConditionCheck();
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(a, j, j + 1))
				j++;
			if (!less(a, k, j))
				break;
			exch(a, k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for comparison
	 ***************************************************************************/
	private boolean less(Comparable[] a, int i, int j) {
		Checker chk = new Checker();
		return chk.compare(a[i - 1], a[j - 1]) < 0;
	}
	/***************************************************************************
	 * Helper functions for swapping
	 ***************************************************************************/

	private void exch(Object[] a, int i, int j) {
		Object swap = a[i - 1];
		a[i - 1] = a[j - 1];
		a[j - 1] = swap;
	}

	/**
	 * This class performs the comparison between 2 instances of the collection
	 * 
	 * @author Dipanjan
	 *
	 */
	private class Checker implements Comparator<Comparable>

	{   
		// compare two instances of type comparable
		public int compare(Comparable a, Comparable b) {

			if (a == null && b == null)
				return 0;
			if (a == null)
				return -1;
			if (b == null)
				return 1;

			return a.compareTo(b);
		}
	}

}
