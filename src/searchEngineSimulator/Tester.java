package searchEngineSimulator;

import java.util.*;

public class Tester
{

    public static void main(String[] args) throws HeapException
    {
        System.out.println("\n***STARTING SIMULATION***\n");
        System.out.print("Search: ");
        Scanner in = new Scanner(System.in);                    //Allows user input for desired search
        String userKeyword = in.nextLine();

        WebCrawler testing = new WebCrawler(userKeyword);       // Accepts a line of input
        testing.search();                                       // and searches Google for the results


        ArrayList<URL> links = new ArrayList<>(40);

        for (String url : testing.getUrls())
        {
            if (links.size() <= 29)
            {
                URL webLink = new URL(url);
                links.add(webLink);             // Populates ArrayList from Set
            }
            else break;
        }

        Heap heap = new Heap(links);           // Creates a new Heap based from ArrayList

        heap.returnHeap();                     // Prints the heap

        System.out.println("\nActions to do: ExtractMax (exmax), Insert (insert), IncreaseKey (key), View Scores (scores), Sort (sort), Quit (q):");
        System.out.print("What would you like to do?: ");
        String action = in.nextLine();
        while (!action.equalsIgnoreCase("q"))
        {
            if (action.equalsIgnoreCase("exmax"))   //EXTRACTMAX
            {
                heap.HeapSort(links);
                Collections.reverse(links);
                heap.BuildMaxHeap(links);
                if (links.size() > 1)
                {
                    System.out.println("\nExtracting Max: " + heap.HeapExtractMax());
                    System.out.println("URL list after MaxExtract: ");
                    heap.returnHeap();
                }
                else {throw new HeapException("Error extracting Max: Heap doesn't have enough elements");}

            }
            else if (action.equalsIgnoreCase("insert")) //INSERT
            {
                System.out.print("\nInserting a new URL: ");
                if (!in.hasNextInt())
                {
                    String toBeInserted = in.nextLine();
                    heap.MaxHeapInsert(toBeInserted);
                    System.out.println("URL list after KeyInsert: " + toBeInserted);
                    heap.returnHeap();
                }
                else{throw new HeapException("Not a URL");}
            }
            else if (action.equalsIgnoreCase("sort")) //SORT
            {
                System.out.print("\nSort scores by: Lo -> Hi or Hi -> Lo (please enter 'lo' or 'hi'): ");
                action = in.nextLine();

                if (action.equalsIgnoreCase("lo")) //TYPE-1
                {
                    heap.HeapSort(links);
                    heap.returnHeap();
                }
                else
                {
                    heap.HeapSort(links); //TYPE-2
                    Collections.reverse(links);
                    heap.returnHeap();
                }
            }
            else if (action.equalsIgnoreCase("key")) //INCREASE KEY
            {
                System.out.print("\nWhat URL's ad score would you like to increment?: ");
                int positionToIncrease = in.nextInt();
                System.out.println("- Advertisement Rank: " + links.get(positionToIncrease - 1).getAdvertisementRank());
                System.out.print("Insert new ad score: ");
                int newScore = in.nextInt();

                if (newScore < links.get(positionToIncrease - 1).getAdvertisementRank())
                {
                    throw new HeapException("Can't decrease the current ad score");
                }
                else
                {
                    System.out.println("Increasing " + links.get(positionToIncrease - 1).getAdvertisementRank() + " to " + newScore);
                    heap.HeapIncreaseKey(positionToIncrease - 1, links.get(positionToIncrease - 1).getPageRank());
                    System.out.println("Score is now: " + links.get(positionToIncrease - 1).getPageRank());
                    System.out.println("\nHeap after KeyIncrease: ");
                    heap.returnHeap();
                }
            }
            else if (action.equalsIgnoreCase("scores"))
            {
                System.out.print("\nWhat URL's scores would you like to see?: ");
                int positionToView = in.nextInt();
                System.out.println("\nScores for " + links.get(positionToView - 1).getUrl());
                System.out.println("- Frequency Rank: " + links.get(positionToView - 1).getFrequencyRank());
                System.out.println("- Longevity Rank: " + links.get(positionToView - 1).getLongevityRank());
                System.out.println("- Advertisement Rank: " + links.get(positionToView - 1).getAdvertisementRank());
                System.out.println("- Relevance Rank: " + links.get(positionToView - 1).getRelevanceRank());
            }
            else
            {
                in.close();
                break;
            }

            in.nextLine();
            System.out.print("\nWould you like to do something else? (y/n): ");
            action = in.nextLine();
            if (action.equalsIgnoreCase("y"))
            {
                System.out.println("\nActions to do: ExtractMax (exmax), Insert (insert), IncreaseKey (key), View Scores (scores), Sort (sort), Quit (q):");
                System.out.print("What would you like to do?: ");
                action = in.nextLine();

            }
            else if (action.equalsIgnoreCase("n"))
            {
                in.close();
                break;
            }
        }

        System.out.println("\n***ENDING SIMULATION***\n");
    }
}
