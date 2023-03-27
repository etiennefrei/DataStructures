public class Stack<E> {
    private SinglyLinkedList<E> list;

    public Stack(SinglyLinkedList<E> data) {
        this.list = data;
    }

    public Stack() {
        this.list = new SinglyLinkedList<>();
    }

    E top(){
        return list.getFirst();
    }

    void push(E data){
        list.insertFirst(data);
    }

    E pop(){
        E result = list.getFirst();
        list.removeFirst();
        return result;
    }

    boolean isEmpty(){
        return list.size() == 0;
    }

    String show(){
        return list.show();
    }
}
