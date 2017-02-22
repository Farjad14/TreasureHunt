package org.csc301;

public class Heap<T extends HeapItem> {
	
	// Note the T is a parameter representing a type that extends the HeapItem interface
	// This a new way to use inheritance!

	protected T[] items; // Array that is used to store heap items. items[0] is the highest priority element.
	protected int maxHeapSize; // The capacity of the heap
	protected int currentItemCount; // How many elements we have currently on the heap

	public Heap(int maxHeapSize) {
		this.maxHeapSize = maxHeapSize;
		items = (T[]) new HeapItem[maxHeapSize];
		currentItemCount = 0; // heap is empty!
	}

	public boolean isEmpty() {
		return currentItemCount == 0;
	}

	public boolean isFull() {
		return currentItemCount == maxHeapSize;
	}

	public void add(T item) throws HeapFullException
	// Adds item T to its correct position on the heap
	{
		if (isFull())
			throw new HeapFullException();
		else {
			item.setHeapIndex(currentItemCount);
			items[currentItemCount] = item;  // the element is added to the bottom
			sortUp(item); // Move the element up to its legitimate place. Check the diagram on the handout!
			currentItemCount++;
		}
	}

	public boolean contains(T item)
	// Returns true if item is on the heap
	// Otherwise returns false
	{
		return items[item.getHeapIndex()].equals(item);
	}

	public int count() {
		return currentItemCount;
	}

	public void updateItem(T item) {
		sortUp(item);
	}

	public T removeFirst() throws HeapEmptyException
	// Removes and returns the element sitting on top of the heap
	{
		if (isEmpty())
			throw new HeapEmptyException();
		else {
			T firstItem = items[0]; // element of top of the heap is stored in firstItem variable
			currentItemCount--;
			items[0] = items[currentItemCount]; //last element moves on top
			items[0].setHeapIndex(0);
			sortDown(items[0]); // move the element to its legitimate position. Please check the diagram on the handout.
			return firstItem;
		}
	}
	
	private void sortUp(T item) {
		// Implement this method according to the diagram on the handout.
		// Also: the indices of children and parent elements satisfy some relationships.
		// The formulas are on the handout.
		if (contains(item)){
			int pindex = (item.getHeapIndex() - 1) / 2;
			
			if(pindex != 0){
				while(true){
					T p = items[pindex];
					if(item.compareTo(p) > 0){
						swap(item, p);
					}
					else{
						break;
					}
				}
				pindex = (item.getHeapIndex() - 1) / 2;
			}
			
		}
		
		
	}
	
	private void sortDown(T item) {
		// Implement this method according to the diagram on the handout.
				// Also: the indices of children and parent elements satisfy some relationships.
				// The formulas are on the handout.
		while(true){
			if(contains(item)){
				int index = 0;
				int l = (item.getHeapIndex() *2) + 1;
				int r = (item.getHeapIndex() *2 ) + 2;
				
				//Has left child
				if(l < this.currentItemCount){
					index = l;
					//has right child
					if(r < this.currentItemCount){
						//use right child, less than left child
						if(items[l].compareTo(items[r])< 0){
							index = r;
						}
					}
					if(item.compareTo(items[index])<0){
						swap(item, items[index]);
					}
					else{
						return;
					}
				}
				else{
					return;
				}
			}
			else{
				return;
			}
		}
		
		
	}
	
	private void swap(T item1, T item2){
		items[item2.getHeapIndex()] = item1;
		items[item1.getHeapIndex()] = item2;
		int index = item2.getHeapIndex();
		item2.setHeapIndex(item1.getHeapIndex());
		item2.setHeapIndex(index);
	}
	
	// You may implement additional helper methods if desired. Make sure to make them private!
}
