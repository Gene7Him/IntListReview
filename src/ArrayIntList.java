import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIntList implements IntList{

    // internal (private) representation
    private int[] buffer;
    private int size;
    private final static int INITIAL_CAPACITY = 10;

    public ArrayIntList(){
        buffer = new int[INITIAL_CAPACITY];
        size = 0;
    }


    /**
     * Prepends (inserts) the specified value at the front of the list (at index 0).
     * Shifts the value currently at the front of the list (if any) and any
     * subsequent values to the right.
     *
     * @param value value to be inserted
     */
    @Override
    public void addFront(int value) {
        // check to see if we still have room (capacity)
        if (size == buffer.length){
            resize(2 * buffer.length);
        }

        // open spot at index 0 where value will be saved
        // shift everything over to the right by 1 position
        for (int i = size; i >= 1; i--) {
            buffer[i] = buffer[i - 1];
        }

        // put value in position [0]
        buffer[0] = value;

    }

    /**
     * Appends (inserts) the specified value at the back of the list (at index size()-1).
     *
     * @param value value to be inserted
     */
    @Override
    public void addBack(int value) {
        // check to see if we still have room (capacity)
        if (size == buffer.length){
            // make the newSize twice the existing capacity
            resize(2 * buffer.length);
        }

        buffer[size] = value;
        size++;

    }

    /**
     * Inserts the specified value at the specified position in this list.
     * Shifts the value currently at that position (if any) and any subsequent
     * values to the right.
     *
     * @param index index at which the specified value is to be inserted
     * @param value value to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void add(int index, int value) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index out of range");
        }

        // shift elements to the right
        for (int i = size; i > index ; i--) {
            buffer[i] = buffer[i - 1];
        }

        // insert new value
        buffer[index] = value;
        size++;
    }

    /**
     * Removes the value located at the front of the list
     * (at index 0), if it is present.
     * Shifts any subsequent values to the left.
     */
    @Override
    public void removeFront() {
        if(size > 0) {
            for (int i = 0; i < size - 1; i++) {
                buffer[i] = buffer[i + 1];
            }

            buffer[size - 1] = 0;
            size--;
        }

    }

    /**
     * Removes the value located at the back of the list
     * (at index size()-1), if it is present.
     */
    @Override
    public void removeBack() {
        if(size == 0){
            throw new IllegalStateException("Already Empty!");
        }
        // size--;
        buffer[--size] = 0;

    }

    /**
     * Removes the value at the specified position in this list.
     * Shifts any subsequent values to the left. Returns the value
     * that was removed from the list.
     *
     * @param index the index of the value to be removed
     * @return the value previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public int remove(int index) {
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index is out of range");
        }

        int removedValue = buffer[index];

        // shift elements to the left
        for (int i = index; i < size; i++) {
            buffer[i] = buffer[i + 1];
        }

        // clear the last element and decrement size
        buffer[size - 1] = 0;
        size--;

        return removedValue;
    }

    /**
     * Returns the value at the specified position in the list.
     *
     * @param index index of the value to return
     * @return the value at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public int get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        return buffer[index];
    }

    /**
     * Returns true if this list contains the specified value.
     *
     * @param value value whose presence in this list is to be searched for
     * @return true if this list contains the specified value
     */
    @Override
    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            if (buffer[i] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the index of the first occurrence of the specified value
     * in this list, or -1 if this list does not contain the value.
     *
     * @param value value to search for
     * @return the index of the first occurrence of the specified value in this list
     * or -1 if this list does not contain the value
     */
    @Override
    public int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            if (buffer[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if this list contains no values.
     *
     * @return true if this list contains no values
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of values in this list.
     *
     * @return the number of values in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all the values from this list.
     * The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        buffer = new int[10];
        size = 0;

    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        // return new instance of the helper class
        return new ArrayIntListIterator();
    }

    public class ArrayIntListIterator implements Iterator<Integer> {
        private int currentPosition;

        public ArrayIntListIterator(){
            currentPosition = 0;
        }

        @Override
        public boolean hasNext() {
            if (currentPosition< size()){
                return true;
            }
            else {
                return false;
            }
        }

        public Integer next(){
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            int value = get(currentPosition);
            currentPosition++;
            return value;
        }
    }

    private void resize(int newSize){
        //  new array that is the new size
        int[] temp = new int[newSize];

        // copy over values from existing buffer
        for (int i = 0; i < size; i++) {
            temp[i] = buffer[i];
        }

        // make the switchover
        buffer = temp;
    }

    @Override
    public String toString(){
        if (size ==0){
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(buffer[0]);

        for (int i = 1; i < size; i++) {
            sb.append(", ");
            sb.append(buffer[i]);
        }

        sb.append("]");
        return sb.toString();

    }
}
