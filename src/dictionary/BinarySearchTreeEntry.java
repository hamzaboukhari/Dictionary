package dictionary;

import dictionary.DictionaryEntry;

// Implementation class representing nodes of the binary search tree.
public class BinarySearchTreeEntry<K, V> implements DictionaryEntry<K, V> {

	private K key;
	private V value;
	private BinarySearchTreeEntry<K, V> left;
	private BinarySearchTreeEntry<K, V> right;
	
	public BinarySearchTreeEntry(K k, V v, BinarySearchTreeEntry<K, V> l, BinarySearchTreeEntry<K, V> r) {
		key = k;
		value = v;
		left = l;
		right = r;
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
	
	public void setLeft(BinarySearchTreeEntry<K, V> newNode) {
		this.left = newNode;
	}
	
	public void setRight(BinarySearchTreeEntry<K, V> newNode) {
		this.right = newNode;
	}
	
	public BinarySearchTreeEntry<K, V> getLeft() {
		return this.left;
	}
	
	public BinarySearchTreeEntry<K, V> getRight() {
		return this.right;
	}
}
