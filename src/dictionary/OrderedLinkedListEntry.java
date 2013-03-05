package dictionary;


public class OrderedLinkedListEntry<K, V> implements DictionaryEntry<K, V> {

	private K key;
	private V value;
	private OrderedLinkedListEntry<K, V> next;
	
	public OrderedLinkedListEntry(K k, V v) {
		key = k;
		value = v;
	}
	
	@Override
	public K getKey() {
		return this.key;
	}

	@Override
	public V getValue() {
		return this.value;
	}
	
	public void setValue(V newValue) {
		this.value = newValue;
	}

	public void setNext(OrderedLinkedListEntry<K, V> node) {
		this.next = node;
	}
	
	public OrderedLinkedListEntry<K, V> getNext() {
		return this.next;
	}
}
