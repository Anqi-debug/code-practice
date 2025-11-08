package E1105;
import java.util.*;

public class Main05 {
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
            this. value = value;
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
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);//ToDo
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
        }else {
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToHead(newNode);//ToDo

            if (cache.size() > capacity) {
                Node lru = removeTail(); //ToDo
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

    private Node removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return node;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
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
            System.out.println("key: " + cur.key + " value: " + cur.value);
            cur = cur.next;
        }
        System.out.println();
    }
}
