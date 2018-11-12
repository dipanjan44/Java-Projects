package sorters;

import java.util.Comparator;

import util.Sorter;

// This class implements the logic for MergeSorter
// Citation - Implementation of mergesorter follows : https://algs4.cs.princeton.edu/22mergesort/Merge.java.html- Used as part of
// Coursera learning
public class MergeSorter<T> implements Sorter {
	

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
}


