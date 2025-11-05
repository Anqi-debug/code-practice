package E1104;
import java.util.*;
/*
Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
Implement the LRUCache class:
LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
int get(int key) Return the value of the key if the key exists, otherwise return -1.
void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache.
If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 */
public class Main04 {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println("get(1) = " + cache.get(1));

        cache.put(3, 3);
        System.out.println("get(2) = " + cache.get(2));

        cache.put(4, 4);
        System.out.println("get(1) = " + cache.get(1));
        System.out.println("get(3) = " + cache.get(3));
        System.out.println("get(4) = " + cache.get(4));

        cache.put(3, 30);
        System.out.println("get(3) = " + cache.get(3));
    }
}

class LRUCache {
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> cache;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        //link head and tail
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        //key does not exist
        if (node == null) {
            return -1;
        }
        //update the cache
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        //if key exists, update value
        if (node != null) {
            node.value = value;
            moveToHead(node); // update cache
        }else{
            //key does not exist, add Node with key value pair
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToHead(newNode);

            //check the cache size
            //if exceed cap, remove the least recently used
            if(cache.size() > capacity){
                Node lru = removeTail();
                cache.remove(lru.key);
            }
        }
    }

    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private Node moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
        return node;
    }

    private Node removeTail() {
        Node lru = tail.prev;
        removeNode(lru);
        return lru;
    }

    public int size() {
        return cache.size();
    }

    public void display() {
        System.out.println("Cache: ");
        Node cur = head.next;
        while (cur != tail) {
            System.out.print(cur.key + ": " + cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }
}
