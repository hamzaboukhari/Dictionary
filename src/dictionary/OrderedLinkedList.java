package dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Ordered linked list based implementation of the Dictionary
 * interface. The nodes of the list are ordered in ascending order by
 * the key-attribute of type K. Duplicate keys are not permitted.
 */
public class OrderedLinkedList<K extends Comparable<? super K>, V> implements
        Dictionary<K, V> {
	
	private OrderedLinkedListEntry<K, V> head = null;
	private int numElems = 0;
	
	public void createOrderedList() {
		
	}

	@Override
	public Iterator<DictionaryEntry<K, V>> iterator() {
		return new ListIterator<K, V>();
	}
	
	public class ListIterator<K, V> implements Iterator<dictionary.DictionaryEntry<K, V>> {

		private OrderedLinkedListEntry<K, V> current;
		
		private ListIterator<K, V>() {
			current = head;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		@Override
		public DictionaryEntry<K, V> next() {
			if (current == null) {
				return null;
			} else {
				result = current.getElem();
				current = current.getNext();
				return result;
			}
		
		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Override
	public int size() {
		return numElems;
	}

	@Override
	public boolean isEmpty() {
		return numElems == 0;
	}

	@Override
	public V get(K key) throws NoSuchElementException {
		OrderedLinkedListEntry<K, V> prev = head;
		if (prev == null) {
			return null;
		} else if (prev.getKey().equals(key)) {
			return prev.getValue();
		} else {
			OrderedLinkedListEntry<K, V> curr = prev.getNext();
			while (curr != null && curr.getKey().compareTo(key) == -1) {
				prev = curr;
				curr = curr.getNext();	
			}
			if (curr != null && curr.getKey().compareTo(key) == 0) {
				return curr.getValue();
			}
		}
		return null;
	}
	
	private OrderedLinkedListEntry<K, V> findPrev(K searchKey) {
		OrderedLinkedListEntry<K, V> prev = head;
		if (prev != null && prev.getKey().compareTo(searchKey) < 0) {
			OrderedLinkedListEntry<K, V> curr = prev.getNext();
			while (curr != null && curr.getKey().compareTo(searchKey) <= 0) {
				prev = curr;
				curr = curr.getNext();
			}
		}
		return prev;
	}

	@Override
	public void put(K key, V value) {
		OrderedLinkedListEntry<K, V> prev = findPrev(key);
		if (prev == null) {
			OrderedLinkedListEntry<K, V> newNode = new OrderedLinkedListEntry<K, V>(key, value);
			newNode.setNext(head);
			numElems++;
		} else if (prev.getKey().equals(key)) {
			prev.setValue(value);
		} else if (prev.getKey().compareTo(key) == -1) {
			OrderedLinkedListEntry<K, V> newNode = new OrderedLinkedListEntry<K, V>(key, value);
			numElems++;
			newNode.setNext(prev.getNext());
			prev.setNext(newNode);
	    } else {
	    	OrderedLinkedListEntry<K, V> newNode = new OrderedLinkedListEntry<K, V>(key, value);
	    	numElems++;
			newNode.setNext(head);
	    }
		
	}

	@Override
	public void remove(K key) throws NoSuchElementException {
		OrderedLinkedListEntry<K, V> prev = findPrev(key);
		if (prev == null) {
			head = head.getNext();
		} else if (prev.getNext().getKey().equals(key)) {
	    	findPrev(prev.getKey()).setNext(prev.getNext());
	    } 
	}

	@Override
	public void clear() {
		head = null;
	}

}
