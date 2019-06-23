package searchEngineSimulator;

import java.util.*;

public class Heap
{
    private int heapSize;
    private ArrayList<URL> heap;

    /**
     * Ctor for objects of type searchEngineSimulator.Heap
     * @param array : Passed-in array to heapify
     */
    public Heap(ArrayList<URL> array)
    {
        heap = array;
        heapSize = heap.size();
    }

    /**
     * Method that swaps two integers inside the heap
     * Used when A[child] > A[parent]
     * @param a : Index of parent
     * @param b : Index of child
     */
    public void swap(int a, int b)
    {
        URL temp = heap.get(a);
        heap.set(a, heap.get(b));   //heap[a] = heap.get(b);
        heap.set(b, temp);          //heap[b] = temp;
    }

    /**
     * Method that returns the element's parent node index
     * @param element : Index of child
     * @return
     */
    public static int getParent(int element)
    {
        return (int) Math.floor(element / 2);
    }

    /**
     * Method that returns the element's left child node index
     * Note: Indexing starts at 0
     * @param element : Index of parent
     * @return
     */
    public static int getLeftChild(int element)
    {
        return (2 * element) + 1;
    }

    /**
     * Method that returns the element's right child node index
     * Note: Indexing starts at 0
     * @param element : Index of parent
     * @return
     */
    public static int getRightChild(int element)
    {
        return (2 * element) + 2;
    }

    /**
     * Method that does simple check if element is a leaf node
     * I.E - no child nodes
     * @param element : The node to be checked
     * @return
     */
    public boolean isLeaf(int element)
    {
        if (element >= (heapSize / 2) && (element + 2) > heapSize)
        {
            return true;
        }
        return false;
    }

    /**
     * Recursive method that heapifies each sub-heap
     * starting from the last parent node w/ a child
     * @param array : Passed-in array that is heapified
     * @param index : Half of the size, index of
     */
    public void MaxHeapify(ArrayList<URL> array, int index)
    {
        int left = getLeftChild(index);
        int right = getLeftChild(index);        // This line works as a corner case.
                                                // In case the node only has 1 child, the comparison does nothing
                                                // In case the parent node has 2 children nodes, this value gets
                                                // override
        int largestElement = 0;

        if (this.isLeaf(left) == false)
        {
            right = getRightChild(index);       // Overrides right child index in case there is one
        }

        if (left < heapSize && heap.get(left).getPageRank() > heap.get(index).getPageRank())
        {
            largestElement = left;
        }
        else
        {
            largestElement = index;
        }

        if (right < heapSize && heap.get(right).getPageRank() > heap.get(largestElement).getPageRank())
        {
            largestElement = right;
        }

        if (heap.get(largestElement) != heap.get(index))
        {
            swap(index, largestElement);
            MaxHeapify(array, largestElement);  // Recursive call to heapify the sub-heap
        }
    }

    /**
     * Method that calls MaxHeapify on all elements to half of the size
     * @param array : Passed-in array to build upon
     */
    public void BuildMaxHeap(ArrayList<URL> array)
    {
        heapSize = heap.size();
        for (int i = (int) Math.floor(heapSize / 2) - 1; i >= 0; i--)
        {
            this.MaxHeapify(array, i);
        }
    }

    /**
     * Returns the maximum integer in the array, i.e - the root
     * @return max
     */
    public int heapMaximum()
    {
        return heap.get(0).getPageRank();
    }

    /**
     * Inserts a new searchEngineSimulator.URL into the list of web links
     * the new searchEngineSimulator.URL is also assigned a random score
     * for the functionality of the simulator
     * @param keyURL
     * @throws HeapException
     */
    public void MaxHeapInsert(String keyURL) throws HeapException
    {
        URL newLink = new URL(keyURL);
        newLink.setAdvertisementRank(Integer.MIN_VALUE);     // Sets sentinel value

        Random ranInt = new Random();
        heap.add(heapSize, newLink);

        this.HeapIncreaseKey(heapSize, ranInt.nextInt(401));
    }

    /**
     * Extracts the maximum value in the heap
     * The largest value will be put at the bottom
     * And while being part of the heap, it won't be
     * the largest current value at the moment.
     * @return max
     * @throws HeapException
     */
    public int HeapExtractMax() throws HeapException
    {
        if (heapSize < 1)
        {
            throw new HeapException("Error extracting Max: Heap doesn't enough elements");
        }
        int max = this.heapMaximum();

        swap(0, heapSize - 1);
        heapSize--;
        MaxHeapify(heap, 0);

        return max;
    }

    /**
     * Increases the rank of the selected searchEngineSimulator.URL
     * i.e - replaces the current rank
     * Very efficient since the HeapSort
     * operation is not needed
     * @param index - index of the to-be-changed searchEngineSimulator.URL
     * @param key - new score to be put
     * @throws HeapException
     */
    public void HeapIncreaseKey(int index, int key) throws HeapException
    {
        if (key < heap.get(index).getAdvertisementRank())
        {
            throw new HeapException("Can't decrease the current key");
        }

        URL current = heap.get(index);      // Gets the URL in question
        current.setAdvertisementRank(key);  // Sets its new value

        heap.set(index, current);           //heap[index] = key;

        while (index > 0 && (heap.get(getParent(index)).getPageRank() < heap.get(index).getPageRank()))
        {
            swap(index, getParent(index));      // If current node is greater than the parent
            index = getParent(index);           // child and parent will switch
        }
    }

    /**
     * Simple method that returns the ArrayList as a indexed list
     * with all the elements in it for display.
     */
    public void returnHeap()
    {
        int counter = 1;
        for (URL element : heap)
        {
            System.out.println(counter + ".- " + "URL:  " + element.getUrl() + " | PageRank: " + element.getPageRank());
            counter++;
        }
    }

    /**
     * Using the searchEngineSimulator.Heap property, this method sorts the
     * array in the parameter in ascending order
     * @param array
     */
    public void HeapSort(ArrayList<URL> array)
    {
        this.BuildMaxHeap(array);
        for (int i = heapSize - 1; i >= 1; i--)
        {
            swap(0, i);                     //Swaps root with smallest value
            this.heapSize--;                   //Decreases size
            this.MaxHeapify(heap, 0);   //Heapifies the array for next iteration
        }
    }

//    public static void main(String[] args) throws searchEngineSimulator.HeapException
//    {
//        //Data
//        ArrayList<searchEngineSimulator.URL> tBS = new ArrayList<>();
//
//        tBS.add(new searchEngineSimulator.URL("https://git-scm.com/"));
//        tBS.add(new searchEngineSimulator.URL("https://www.codeavengers.com/"));
//        tBS.add(new searchEngineSimulator.URL("https://messages.android.com/"));
//        tBS.add(new searchEngineSimulator.URL("https://web.whatsapp.com/"));
//        tBS.add(new searchEngineSimulator.URL("https://www.youtube.com"));
//        tBS.add(new searchEngineSimulator.URL("https://www.facebook.com"));
//
//        searchEngineSimulator.Heap test = new searchEngineSimulator.Heap(tBS);
//
//        //
//        System.out.println("\n***START***\n");
//
//        System.out.println("Given array: ");
//        test.returnHeap();
//
//        System.out.println();
//
//        test.BuildMaxHeap(tBS);
//        System.out.println("Heapified array: ");
//        test.returnHeap();
//
//        System.out.println();
//        test.HeapSort(tBS);
//        System.out.println("Sorted array:");
//        Collections.reverse(tBS);
//        test.returnHeap();
//
//        System.out.println();
//        test.BuildMaxHeap(tBS);
//        System.out.println("----------------------");
//        System.out.println("PRIORITY QUEUE TESTING");
//
//        System.out.println("Extracting Max: " + test.HeapExtractMax());
//        System.out.println("searchEngineSimulator.Heap after MaxExtract");
//        test.returnHeap();
//        System.out.println();
//        System.out.println("Increasing 9 to 786");
//        test.HeapIncreaseKey(3, 786);
//        System.out.println("searchEngineSimulator.Heap after KeyIncrease: ");
//        test.returnHeap();
//
//        System.out.println();
//        System.out.println("Inserting a new key: Bit.Ly");
//        test.MaxHeapInsert("https://bitly.com/");
//        System.out.println("searchEngineSimulator.Heap after KeyInsert: ");
//        test.returnHeap();
//        System.out.println("\n***END***\n");
//    }
}