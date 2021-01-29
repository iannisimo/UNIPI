package worth.server.Utils;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ConcurrentHashSet<E> extends AbstractSet<E> {
   
   private final ConcurrentMap<E, Object> map;
   private static final Object DUMMY = new Object();

   public ConcurrentHashSet(){
      map = new ConcurrentHashMap<E, Object>();
   }

   @Override
   public int size() {
      return map.size();
   }

   @Override
   public Iterator<E> iterator(){
      return map.keySet().iterator();
   }

   @Override
   public boolean isEmpty(){
      return map.isEmpty();
   }

   @Override
   public boolean add(final E o){
      return map.put(o, ConcurrentHashSet.DUMMY) == null;
   }

   @Override
   public boolean contains(final Object o){
      return map.containsKey(o);
   }

   @Override
   public void clear(){
      map.clear();
   }

   @Override
   public boolean remove(final Object o){
      return map.remove(o) == ConcurrentHashSet.DUMMY;
   }

   public boolean addIfAbsent(final E o){
      Object obj = map.putIfAbsent(o, ConcurrentHashSet.DUMMY);
      return obj == null;
   }
}