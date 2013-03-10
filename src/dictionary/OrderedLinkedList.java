package dictionary;

import java.util.ConcurrentModificationException;
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

	@Override
	public Iterator<DictionaryEntry<K, V>> iterator() {
		return new ListIterator();
	}
	
	public class ListIterator implements Iterator<dictionary.DictionaryEntry<K, V>> {

		private OrderedLinkedListEntry<K, V> current;
		
		private ListIterator() {
			current = head;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		@Override
		public DictionaryEntry<K, V> next() {
			try {
				OrderedLinkedList.this.get(current.getKey());
				} catch (NoSuchElementException e) {
					throw new ConcurrentModificationException();
			}
			
			if (current != null) {
				OrderedLinkedListEntry<K, V> result = current;
				current = current.getNext();
				return result;
			} else {
				throw new UnsupportedOperationException();
			}
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
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
			throw new NoSuchElementException();
		} else if (prev.getKey().equals(key)) {
			return prev.getValue();
		} else {
			OrderedLinkedListEntry<K, V> curr = prev.getNext();
			while (curr != null && curr.getKey().compareTo(key) < 0) {
				prev = curr;
				curr = curr.getNext();	
			}
			if (curr != null && curr.getKey().compareTo(key) == 0) {
				return curr.getValue();
			} else {
				throw new NoSuchElementException();
			}
		}
	}
	
	@Override
	public void put(K key, V value) {
		
		OrderedLinkedListEntry<K, V> prevNode = null;
		OrderedLinkedListEntry<K, V> nextNode = head;
				
		while(nextNode != null && nextNode.getKey().compareTo(key) < 0) {
			prevNode = nextNode;
			nextNode = nextNode.getNext();
		}
					
		OrderedLinkedListEntry<K, V> newNode = new OrderedLinkedListEntry<K, V>(key, value);
		if (prevNode == null) {
			newNode.setNext(nextNode);
			head = newNode;				
			numElems++;
		} else if (nextNode != null && nextNode.getKey().equals(key)) {
			nextNode.setValue(value);
		} else {
			newNode.setNext(nextNode);
			prevNode.setNext(newNode);
			numElems++;
		}
		
	}

	@Override
	public void remove(K key) throws NoSuchElementException {
		OrderedLinkedListEntry<K, V> prevNode = null;
		OrderedLinkedListEntry<K, V> nextNode = head;
				
		while(nextNode != null && nextNode.getKey().compareTo(key) < 0) {
			prevNode = nextNode;
			nextNode = nextNode.getNext();
		}
		
		if (nextNode != null && nextNode.getKey().equals(key)) {
			if (nextNode == head) {
				head = head.getNext();				
		    } else {
		    	prevNode.setNext(nextNode.getNext());
		    }
			numElems--;
		} else {
	    	throw new NoSuchElementException();
	    }
	}

	@Override
	public void clear() {
		head = null;
	}

}
