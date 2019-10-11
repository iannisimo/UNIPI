class GenericContainer<T> {
  private T element;

  public T getElement() {
    return element;
  }

  public void setElement(T element) {
    this.element = element;
  }
}


// usage


GenericContainer<String> stringContainer = new GenericContainer<>();
stringContainer.setElement("Jack");
String string = stringContainer.getElement();
System.out.println(string);

//Generic list

public class GenericList<E> {
  // class constant
  private static final int DEFAULT_CAP = 10;

  // instance variables
  protected E[] container; // the array is NOT the list
  private int listSize;

  public boolean equals(Object obj){
    assert this != null;
    if(obj == null)
    return false;
    else if (this == obj)
    return true;
    else if( this.getClass() != obj.getClass() )
    return false;
    else{
      // obj is a non null GenericList
      GenericList list = (GenericList)obj;
      if( list.size() != size() )
      return false;
      for(int i = 0; i < size(); i++)
      if( (get(i) == null && list.get(i) != null) || !get(i).equals(list.get(i)) )
      return false;
      return true;
    }


  }


  // creates an empty IntList
  public GenericList(){
    this(DEFAULT_CAP);
  }

  //initialCap >= 0
  public GenericList(int initialCap){
    assert initialCap >= 0 : "failed precondition";
    container = (E[])(new Object[initialCap]);
    listSize = 0;
  }

  public void insertAll(int pos, GenericList<E> otherList){

    for(int i = 0; i < otherList.listSize; i++){
      this.insert(pos + i, otherList.container[i]);
    }
  }

  // 0 <= pos < size()
  public E remove(int pos){
    assert 0 <= pos && pos <= size();
    E result = container[pos];
    listSize--;
    for(int index = pos; index < size(); index++){
      container[index] = container[index + 1];
    }
    container[listSize] = null;
    return result;
  }

  // 0 <= pos <= size()
  public void insert(int pos, E element){
    assert 0 <= pos && pos <= size();
    if( size() == container.length )
    resize();
    for(int index = size(); index > pos; index--){
      assert index > 0;
      container[index] = container[index - 1];
    }
    container[pos] = element;
    listSize++;
  }

  // get size of list
  public int size(){
    return listSize;
  }

  // access elements
  // 0 <= position < size()
  public E get(int position){
    assert 0 <= position && position < size();
    return container[position];
  }

  public void add(E element){
    insert(size(), element);
  }

  private void resize() {
    E[] temp = (E[])(new Object[container.length * 2 + 1]);
    System.arraycopy(container, 0, temp, 0, size());
    container = temp;
  }

  public String toString(){
    StringBuffer result = new StringBuffer("[");
    final int LIMIT = size() - 1;
    for(int i = 0; i < LIMIT; i++){
      if( this == this.get(i) )
      result.append("this list");
      else{
        result.append(get(i));
      }
      result.append(", ");
    }
    if( size() != 0)
    result.append(get(size() - 1));
    result.append("]");
    return result.toString();
  }

}
