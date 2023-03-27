import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ComparableList<T extends Comparable<T>> implements Iterable<T>{
    int size;
    Element<T> head;
    Element<T> tail;

    public ComparableList() {
        head = new Element();
        tail = new Element();
        head.data = null;
        tail.data = null;
        head.next = tail;
        head.previous = head;
        tail.previous = head;
        tail.next = tail;
        size = 0;
    }

    int getSize() {
        return size;
    }

    void addHead(T t){
        size++;
        Element<T> elem = new Element();
        elem.data = t;
        elem.next = head.next;
        elem.previous = head;
        elem.next.previous = elem;
        head.next = elem;
    }

    void addTail(T t){
        Element<T> elem = new Element();
        elem.data = t;
        elem.next = tail;
        elem.previous = tail.previous;
        elem.previous.next = elem;
        tail.previous = elem;
    }

    T removeHead(){
        if (size == 0) return null;
        size--;
        Element<T> elem = head.next;
        head.next = elem.next;
        head.next.previous = head;
        return elem.data;
    }

    T removeTail(){
        if (size == 0) return null;
        size--;
        Element<T> elem = tail.previous;
        tail.previous = elem.previous;
        tail.previous.next = tail;
        return elem.data;
    }

    @Override
    public ListIterator<T> iterator() {
        return new CListIterator();
    }

    public ListIterator<T> iterator(int index) {
        if (index >= size)  throw new IndexOutOfBoundsException();
        CListIterator ret = new CListIterator();
        Element<T> r = head.next;
        for (int i=0; i<index; ++i)
            r = r.next;
        ret.next = r;
        ret.index = index;
        return ret;
    }


    private void remove(Element<T> e){          // assignment 3.2
        if (e!=null && e!=head && e!=tail){
            e.previous.next = e.next;
            e.next.previous = e.previous;
            size--;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Element<T> elem = head.next;
        while (elem != tail){
            sb.append(elem.data);
            sb.append(" ");
            elem = elem.next;
        }
        return sb.toString();
    }

    static class Element<T>{
        T data;
        Element<T> previous;
        Element<T> next;
    }

    private class CListIterator implements ListIterator<T>{

        private Element<T> returned = null;
        private Element<T> next = null;
        private int index;

        public CListIterator(){
            next = head.next;
            returned = null;
        }

        public CListIterator(CListIterator i){
            next = i.next;
            returned = null;
            index = i.index;
        }

        @Override
        public boolean hasNext() {
            return next != next.next;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            returned = next;
            next = next.next;
            index++;
            return returned.data;
        }

        @Override
        public boolean hasPrevious() {
            return next.previous != next.previous.previous;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            next = next.previous;
            returned = next;
            index++;
            return returned.data;
        }

        @Override
        public int nextIndex() {
            if (hasNext()) return index;
            else return getSize();
        }

        @Override
        public int previousIndex() {
            if (hasPrevious()) return nextIndex()-1;
            else return -1;
        }

        @Override
        public void remove() {
            if (returned == null) {
                throw new IllegalStateException();
            } else {
                if (returned == next) {
                    next = returned.next;
                } else index--;
                ComparableList.this.remove(returned);
                returned = null;
            }
        }

        @Override
        public void set(T data){
            if (returned == null)
                throw new IllegalStateException();
            else{
                if (! (data instanceof Comparable<?>)){
                    throw new IllegalArgumentException();
                }
                returned.data = data;
            }
        }

        @Override
        public void add(T t) {
            Element<T> r = new Element<T>();
            r.data = t;
            r.previous = next.previous;
            r.next = next;
            next.previous.next = r;
            next.previous = r;
            ++size;
        }
    }

    void mergeSort(){
        if (this.size > 1){
            ComparableList<T> leftPart = this.split();
            leftPart.mergeSort();
            this.mergeSort();
            merge(leftPart);
        }
    }

    void merge(ComparableList<T> other){
        ListIterator<T> group1 = this.iterator();
        ListIterator<T> group2 = other.iterator();
        ListIterator<T> insert = this.iterator();
        T element1, element2;
        while (group1.hasNext() || group2.hasNext()){
            if (group1.hasNext() && group2.hasNext()){
                element1 = group1.next();
                element2 = group2.next();
                if (element1.compareTo(element2) < 0){
                    insert.add(element1);
                    group2.previous();
                }else{
                    insert.add(element2);
                    group1.previous();
                }
            }else{
                if (group1.hasNext()){
                    insert.add(group1.next());
                }else{
                    insert.add(group2.next());
                }
            }
        }
        while (insert.hasNext()){
            insert.next();
            insert.remove();
        }
    }

    ComparableList<T> split(){
        ComparableList<T> leftPart = new ComparableList<T>();
        int size = this.size / 2;
        for (int i=0; i<size; ++i){
            leftPart.addTail(this.removeHead());
        }
        return leftPart;
    }
}
