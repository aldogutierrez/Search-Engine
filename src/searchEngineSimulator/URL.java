package searchEngineSimulator;

import java.util.Random;

public class URL
{
    private String url;
    private int PageRank;
    private int frequencyRank;
    private int longevityRank;
    private int relevanceRank;
    private int advertisementRank;


    public URL(String url)
    {
        Random ranInt = new Random();
        this.frequencyRank = ranInt.nextInt(101); // Generates a Random Score for it
        this.longevityRank = ranInt.nextInt(101); // Generates a Random Score for it
        this.relevanceRank = ranInt.nextInt(101); // Generates a Random Score for it
        this.relevanceRank = ranInt.nextInt(101); // Generates a Random Score for it
        this.advertisementRank = ranInt.nextInt(101); // Generates a Random Score for it

        this.PageRank = frequencyRank + longevityRank + relevanceRank + advertisementRank;
        this.url = url;
    }

    /**
     * Replaces and sets a new PageRank
     * value for the URL in question
     * @param advertisementRank
     */
    public void setAdvertisementRank(int advertisementRank)
    {
        this.advertisementRank = advertisementRank;
        this.PageRank = frequencyRank + longevityRank + relevanceRank + advertisementRank;
    }

    /**
     * Returns the Advertisement Rank
     * value for the URL in question
     * @return
     */
    public int getAdvertisementRank()
    {
        return advertisementRank;
    }

    /**
     * Returns the Advertisement Rank
     * value for the URL in question
     * @return
     */
    public int getFrequencyRank()
    {
        return frequencyRank;
    }

    /**
     * Returns the Longevity Rank
     * value for the URL in question
     * @return
     */
    public int getLongevityRank()
    {
        return longevityRank;
    }

    /**
     * Returns the Relevance Rank
     * value for the URL in question
     * @return
     */
    public int getRelevanceRank()
    {
        return relevanceRank;
    }

    /**
     * Outputs the current PageRank
     * value for the URL in question
     * @return
     */
    public int getPageRank()
    {
        return PageRank;
    }

    /**
     * Returns the URL string
     * that denotes the URL
     * in question
     * @return
     */
    public String getUrl()
    {
        return url;
    }
}
