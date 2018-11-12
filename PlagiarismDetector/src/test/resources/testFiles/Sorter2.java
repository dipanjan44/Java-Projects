package sorters;

import java.util.Comparator;

import util.Sorter;

// This class implements the logic for MergeSorter
// Citation - Implementation of mergesorter follows : https://algs4.cs.princeton.edu/22mergesort/Merge.java.html- Used as part of
// Coursera learning
public class MergeSorter<T> implements Sorter {
	
	// merge opration of the sorted array
	private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

		// copy to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}

		// merge back to a[]
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}

	}

	// mergesort a[lo..hi] using auxiliary array aux[lo..hi]
	private void mergesort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (hi <= lo)
			return;
		int mid = lo + (hi - lo) / 2;
		mergesort(a, aux, lo, mid);
		mergesort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}

	/**
	 * the sorting method for the given input.
	 * 
	 * @param a
	 *            the array to be sorted
	 */
	@Override
	public void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		mergesort(a, aux, 0, a.length - 1);

	}

	/***************************************************************************
	 * Helper sorting function.
	 ***************************************************************************/

	// is v < w ?
	private boolean less(Comparable v, Comparable w) {
		Checker ck= new Checker();
		return ck.compare(v,w)<0;
	}
	
	
	/**
	 * This class performs the comparison between 2 instances of the collection
	 * 
	 * @author Dipanjan
	 *
	 */
	private class Checker implements Comparator<Comparable>

	{  // compares 2 instances of type comparable
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
