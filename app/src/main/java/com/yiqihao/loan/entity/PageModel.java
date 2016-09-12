package com.yiqihao.loan.entity;

/**
 * 分页
 * Created by 冯浩 on 16/8/10.
 */
public class PageModel {


	/**
	 * size : 10
	 * count : 168
	 * total : 17
	 * now : 1
	 * prev : 0
	 * next : 2
	 * up : 1
	 * down : 11
	 */

	private int size;
	private int count;
	private int total;
	private int now;
	private int prev;
	private int next;
	private int up;
	private int down;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNow() {
		return now;
	}

	public void setNow(int now) {
		this.now = now;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}
}
