import java.util.NoSuchElementException;

class SinglyLinkedList<E> {
    Element<E> head;
    int size;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    public SinglyLinkedList(SinglyLinkedList<E> orig) {
        head = null;
        Element<E> tail = null;
        size = 0;
        Element<E> p = orig.head;
        while (p != null){
            if (head==null){                 // list is empty
                head = new Element<E>();
                head.data = p.data;
                head.next = null;
                tail = head;
            } else {                           // list not empty
                tail.next = new Element<E>();
                tail = tail.next;
                tail.data = p.data;
                tail.next = null;
            }
            p = p.next;
        }
        size = orig.size;
    }

    E getFirst() {
        if (head == null) {
            throw new IllegalStateException();
        }
        return head.data;
    }

    E get(int index) {
        if (index < 0 || index > size-1) throw new IndexOutOfBoundsException();
        Element<E> elem = head;
        for (int i = 0; i < index; i++){
            elem = elem.next;
        }
        return elem.data;
    }

    public int size() {
        return size;
    }

    void insertFirst(E e) {
        Element<E> elem = new Element<>();
        elem.data = e;
        elem.next = head;
        head = elem;
        size++;
    }

    void insertAfter(E e, int index) {
        if (index < 0 || index > size-1) throw new IndexOutOfBoundsException();
        Element<E> elem = new Element<>();
        elem.data = e;
        Element<E> r = head;
        for (int i = 0; i < index; i++){
            r = r.next;
        }
        elem.next = r.next;
        r.next = elem;
        size++;
    }

    void removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        head = head.next;
        size--;
    }

    void remove(int index){
        if (index < 0 || index > size-1) throw new IndexOutOfBoundsException();
        if (index == 0) {
            head = head.next;
        } else {
            Element<E> elem = head;
            for (int i = 0; i < index-1; i++){
                elem.next = elem.next.next;
            }
        }
        size--;

    }

    void removeAll(E e){
        Element<E> elem = null, s = head;
        while (s != null){
            if (s.data == e){
                if (elem == null){
                    head = s.next;
                    s = head;
                } else {
                    elem.next = s.next;
                    s = elem.next;
                }
            } else {
                elem = s;
                s = s.next;
            }
        }
    }

    boolean exists(E e) {
        Element<E> elem = head;
        while (elem != null) {
            if (elem.data.equals(e)) return true;
            elem = elem.next;
        }
        return false;
    }

    private static class Element<T>{
        T data;
        Element<T> next;
    }

    String show(){
        String result = "";
        Element<E> elem = head;
        while (elem != null){
            result += elem.data + " ";
            elem = elem.next;
        }
        return result;
    }
}