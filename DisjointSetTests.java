package com.jwetherell.algorithms.data_structures.test;

import org.junit.Assert;
import org.junit.Test;

import com.jwetherell.algorithms.data_structures.DisjointSet;

@SuppressWarnings("unchecked")
public class DisjointSetTests {

    private static boolean DEBUG = false;

    @Test
    public void testDisjointSet1() {
        final int maximum = 10;
        final int[] array = new int[maximum];
        for (int i = 0; i < array.length; i++)
            array[i] = i + 1;

        final DisjointSet.Item<Integer>[] items = new DisjointSet.Item[array.length];
        for (int i = 0; i < items.length; i++) {
            final int v = array[i];
            final DisjointSet.Item<Integer> s = DisjointSet.makeSet(v);
            items[i] = s;
            final DisjointSet.Item<Integer> f = DisjointSet.find(s);
            Assert.assertTrue(f != null);
            Assert.assertTrue(f.getValue() == v);
        }
        if (DEBUG)
            System.out.println(toString(items));

        final int half = items.length / 2;

        // first half set
        DisjointSet.Item<Integer> first = items[0];
        for (int i = 1; i < half; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            first = DisjointSet.union(first, item);
        }
        if (DEBUG)
            System.out.println(toString(items));
        // second half set
        DisjointSet.Item<Integer> second = items[half];
        for (int i = half + 1; i < items.length; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            second = DisjointSet.union(second, item);
        }
        if (DEBUG)
            System.out.println(toString(items));

        // Make sure all items in first set are actually in first set and vice versa
        for (int i = 0; i < half; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            final DisjointSet.Item<Integer> result = DisjointSet.find(item);
            Assert.assertTrue(result.equals(first));
        }
        for (int i = half; i < items.length; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            final DisjointSet.Item<Integer> result = DisjointSet.find(item);
            Assert.assertTrue(result.equals(second));
        }

        // merge second set and the first set and make sure the superset is created
        DisjointSet.Item<Integer> superSet = first;
        for (int i = 0; i < items.length; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            superSet = DisjointSet.union(superSet, item);
        }
        if (DEBUG)
            System.out.println(toString(items));
        for (int i = 0; i < items.length; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            final DisjointSet.Item<Integer> result = DisjointSet.find(item);
            Assert.assertTrue(result.equals(superSet));
        }
    }

    @Test
    public void testDisjointSet2() {
        final int maximum = 10;
        final int[] array = new int[maximum];
        for (int i = 0; i < array.length; i++)
            array[i] = i + 1;

        final DisjointSet.Item<Integer>[] items = new DisjointSet.Item[array.length];
        for (int i = 0; i < items.length; i++) {
            final int v = array[i];
            final DisjointSet.Item<Integer> s = DisjointSet.makeSet(v);
            items[i] = s;
            final DisjointSet.Item<Integer> f = DisjointSet.find(s);
            Assert.assertTrue(f != null);
            Assert.assertTrue(f.getValue() == v);
        }
        if (DEBUG)
            System.out.println(toString(items));

        final int quarter = items.length / 4;

        DisjointSet.Item<Integer> i1 = items[0];
        for (int i = 1; i < quarter; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            i1 = DisjointSet.union(i1, item);
        }
        DisjointSet.Item<Integer> i2 = items[quarter];
        for (int i = quarter + 1; i < 2 * quarter; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            i2 = DisjointSet.union(i2, item);
        }
        DisjointSet.Item<Integer> i3 = items[2 * quarter];
        for (int i = 2 * quarter + 1; i < 3 * quarter; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            i3 = DisjointSet.union(i3, item);
        }
        DisjointSet.Item<Integer> i4 = items[3 * quarter];
        for (int i = 3 * quarter + 1; i < array.length; i++) {
            final DisjointSet.Item<Integer> item = items[i];
            i4 = DisjointSet.union(i4, item);
        }
        if (DEBUG)
            System.out.println(toString(items));

        DisjointSet.Item<Integer> s1 = DisjointSet.union(i1, i2);
        DisjointSet.Item<Integer> s2 = DisjointSet.union(i3, i4);
        DisjointSet.Item<Integer> s3 = DisjointSet.union(s1, s2);
        Assert.assertTrue(s3.getValue() == 1 && s3.getRank() == 3);
        if (DEBUG)
            System.out.println(toString(items));
    }

    private static final String toString(DisjointSet.Item<Integer>[] items) {
        final StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        for (DisjointSet.Item<Integer> item : items) {
            builder.append('\t').append(item.toString()).append('\n');
        }
        builder.append("}\n");
        return builder.toString();
    }

    @Test
    public void testDisjointSet3() {
        final int maximum = 10;
        final int[] arr = new int[maximum];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i + 1;
        final DisjointSet.Item<Integer>[] items = new DisjointSet.Item[arr.length];
        for (int i = 0; i < items.length; i++) {
            final int v = arr[i];
            final DisjointSet.Item<Integer> s = DisjointSet.makeSet(v);
            items[i] = s;
        }
        DisjointSet.Item<Integer> i1 = items[0];
        DisjointSet.Item<Integer> i2 = items[1];
        DisjointSet.Item<Integer> i3 = items[2];
        DisjointSet.Item<Integer> i4 = items[3];

        final DisjointSet.Item<Integer> xRoot = DisjointSet.union(null, i1);
        Assert.assertTrue(xRoot.getValue() == i1.getValue());

        final DisjointSet.Item<Integer> yRoot = DisjointSet.union(i2, null);
        Assert.assertTrue(yRoot.getValue() == i2.getValue());

        final DisjointSet.Item<Integer> equalRoots = DisjointSet.union(i2, i2);
        Assert.assertTrue(equalRoots.getValue() == i2.getValue());

        Assert.assertNull(DisjointSet.find(null));

        Assert.assertNull(DisjointSet.union(null, null));

        DisjointSet.Item<Integer> unequalIt1 = DisjointSet.union(i1, i2);
        Assert.assertNotNull(unequalIt1);
        DisjointSet.Item<Integer> unequalIt2 = DisjointSet.union(i1, i3);
        Assert.assertNotNull(unequalIt2);
        DisjointSet.Item<Integer> unequalIt3 = DisjointSet.union(i4, i1);
        Assert.assertNotNull(unequalIt3);

        Assert.assertFalse(unequalIt1.equals("False"));

        String iString = i2.toString();
        String expected = "parent=1 value=2";
        Assert.assertTrue(iString.equals(expected));

    }
}
