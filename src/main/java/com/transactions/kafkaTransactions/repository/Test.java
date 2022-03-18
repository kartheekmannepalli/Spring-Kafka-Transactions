package com.transactions.kafkaTransactions.repository;/*
 * Click `Run` to execute the snippet below!
 */

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Test {
    public static void main(String[] args) {
        List<Integer> a = Arrays.<Integer>asList(0, 1, 2, 3, 4, 5);
        List<Integer> b = Arrays.<Integer>asList();
        List<Integer> c = Arrays.<Integer>asList(1, 3, 5, 7);
        List<Integer> d = Arrays.<Integer>asList(5, 10, 100);

        MergedIterator mit = new MergedIterator(a.iterator(), b.iterator(), c.iterator(), d.iterator());

        while (mit.hasNext()) {
            System.out.println(mit.next());
        }
    }

    static class MergedIterator<T> implements Iterator<Integer> {
        Iterator<Integer> mIt;
        List<Integer> list;

        MergedIterator(Iterator<Integer>... in) {
            list = new ArrayList<>();
            Queue<Iterator<Integer>> q = new LinkedList<>(Arrays.asList(in));

            while(!q.isEmpty()) {
                Iterator<Integer> item = q.poll();
                if(item.hasNext()) {
                    list.add(item.next());
                    q.add(item);
                }
            }
            Collections.sort(list);
            mIt = list.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.mIt.hasNext();
        }

        @Override
        public Integer next() {
            return this.mIt.next();
        }
    }
}

/*
 * To demonstrate the expected behavior, I'll provide the following example data
 * set:
 *
 * Iterator #1 yields the sequence [0, 1, 2, 3, 4, 5]
 * Iterator #2 yields the sequence [] (e.g. no data)
 * Iterator #3 yields the sequence [1, 3, 5, 7]
 * Iterator #4 yields the sequence [5, 10, 100]
 *
 * The merged iterator should yield the following [0, 1, 1, 2, 3, 3, 4, 5, 5, 5,
 * 7, 10, 100].
 *

 * [0, 1, 2, 3, 4, 5]
 * [1, 3, 5, 7]
 * [5, 10, 100]
 *
 * (0, [1, 2, 3, 4, 5])
 * (1, [3, 5, 7])
 * (5, [10, 100])

 * (1, [2, 3, 4, 5])
 * (1, [3, 5, 7])
 * (5, [10, 100])

 * (1, [3, 5, 7])
 * (2, [3, 4, 5])
 * (5, [10, 100])

 */
