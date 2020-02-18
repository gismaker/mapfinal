package com.mapfinal.kit;

import java.util.LinkedList;

/**
 * 双向队列,使用LinkedList实现
 * 
 * @author proxyme
 * 
 * @param <T>
 */
public class DQueue<T> {
	private LinkedList<T> deque = new LinkedList<T>();

	public void addFirst(T e) {
		deque.addFirst(e);
	}
	public void addLast(T e) {
		deque.addLast(e);
	}

	public T get(int position) {
		return deque.get(position);
	}

	public T getFirst() {
		return deque.getFirst();
	}

	public T getLast() {
		return deque.getLast();
	}

	public T removeFirst() {
		return deque.removeFirst();
	}

	public T removeLast() {
		return deque.removeLast();
	}

	public int size() {
		return deque.size();
	}

	public String toString() {
		return deque.toString();
	}
}