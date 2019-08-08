package com.van.app;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: cec
 * Date: 2019/8/5
 * Time: 1:27 PM
 */
public class LRUDemo<K , V> {

    private HashMap<K, Node> caches;
    private int capacity;
    private Node first;
    private Node last;

    public LRUDemo(int capacity) {
        this.capacity = capacity;
        caches = new HashMap<>();
    }

    public void put(K k, V v) {
        Node node = caches.get(k);
        if (node == null) {
            if (caches.size() >= capacity) {
                caches.remove(last.key);
                removeLast();
            }
           node = new Node();
           node.key = k;
        }
        node.value = v;
        moveToFirst(node);
        caches.put(k, node);

    }

    public Object get(K k) {
        Node node = caches.get(k);
        if (node != null) {
            moveToFirst(node);
        } else {
            return null;
        }

        return node.value;
    }

    public Object remove(K k) {
        Node node = caches.get(k);
        if (node != null) {
            if (node.pre != null) {
                node.pre.next = node.next;
            }
            if (node.next != null) {
                node.next.pre = node.pre;
            }

            if (node == first) {
                first = node.next;
            }

            if (node == last) {
                last = node.pre;
            }

        }
        return caches.remove(k);
    }


    public boolean clear() {
        first = null;
        last = null;
        caches.clear();
        capacity = 0;
        return true;
    }

    private void moveToFirst(Node node) {
        if (first == node) {
            return;
        }

        if (node.next != null) {
            node.next.pre = node.pre;
        }

        if (node.pre != null) {
            node.pre.next = node.next;
        }

        if (node == last) {
            last = last.pre;
        }

        if (first == null || last == null) {
            first = last = node;
            return;
        }

            node.next = first;
            first.pre = node;
            first = node;
            node.pre = null;

    }

    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Node node = first;
        while (node != null) {
            sb.append(String.format("%s:%s", node.key, node.value));
            node = node.next;
        }
        return sb.toString();
    }

    class Node {
        Node pre;
        Node next;
        Object key;
        Object value;
    }

}
