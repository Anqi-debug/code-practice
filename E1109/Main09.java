package E1109;
import java.util.*;

public class Main09 {

}

class LRUCache {
    class Node {
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
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (cache.get(key) == null) {
            return -1;
        }
        else {
            Node node = cache.get(key);
            return node.value;
            moveToHead(node);// ToDo
        }
    }

    public void put(int key, int value) {
        if (cache.get(key) != null) {
            Node node = cache.get(key);
            node.value = value;
            addToHead(node); //ToDo
        }
        else {
            Node node = new Node(key, value);
            cache.put(key, node);
            moveToHead(node); //ToDO

            if (cache.size() > capacity) {
                removeTail(); //ToDo
            }
        }
    }
}
