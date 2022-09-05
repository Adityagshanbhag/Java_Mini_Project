import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Main class.
 */
public class MainClass
{
    /**
	 * A dynamically programmed function that returns the maximum possible profit from given ArrayList of Offers.
	 * The ArrayList of Offers must be sorted according to ascending end time beforehand.
	 * Time complexity: O(N * log(N))
	 * @param offers An ArrayList which holds the Offers.
	 * @param n Total number of Offers.
	 * @return Maximum profit attainable from given ArrayList of Offers.
	 */
    public static int getMaxProfit(final ArrayList<Offer> offers, final int n)
    {
        int maxProfitTable[] = new int[n];    
        maxProfitTable[0] = offers.get(0).getProfit();
        for(int i=1;i<n;i++) 
        {
            // Find profit including the current offer.
            int includedProfit = offers.get(i).getProfit();
            final int startTime = offers.get(i).getStartTime();
                for(int j=i-1;j>=0;j--)
                    if(offers.get(j).getEndTime() <=startTime) 
                    {
                        includedProfit += maxProfitTable[j];
                        break;
                    }
                // Store the maximum of the included and excluded profits.
            maxProfitTable[i] = Math.max(includedProfit,maxProfitTable[i-1]);
        }
        return maxProfitTable[n-1];
    }
    /**
	 * Main method.
	 * @param args[]
	 * @return void
	 */
    public static void main(String args[])
    {
        // Read input.	
		// Create a new Scanner object to read data from the user.
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------------------------");
        System.out.println("Enter Product Types: ('s' for Solid, 'l' for Liquid)");
        final char[] prod_types = sc.nextLine().replaceAll(" ","").toCharArray();
        final int totalOffer = prod_types.length;
        int[] process_times_A = new int[totalOffer];
        int[] process_times_B = new int[totalOffer];
        int[] factory_gains = new int[totalOffer];
        int[] arrival_times = new int[totalOffer];
        System.out.println("Enter process A time:");
        for(int i=0;i < totalOffer;++i)
            process_times_A[i] = sc.nextInt();
        System.out.println("Enter process B time:");
        for(int i=0;i < totalOffer;++i)
            process_times_B[i] = sc.nextInt();
        System.out.println("Enter profits gained:");
        for(int i=0;i < totalOffer;++i)
            factory_gains[i] = sc.nextInt();
        System.out.println("Enter arrival time:");
        for(int i=0;i < totalOffer;++i)
            arrival_times[i] = sc.nextInt();
        System.out.println("-----------------------------");
        sc.close();       // Closes the Scanner object.
        
        // List of objects containing start time, end time, and profit of each order. It is sorted based on end time.
        ArrayList<Offer> offers = new ArrayList<Offer>(totalOffer);
        for(int i=0;i<totalOffer;i++) 
        {
            final int startTime = arrival_times[i];
            final int processTime = prod_types[i] == 's' ? process_times_A[i] : process_times_B[i];
            final int endTime = startTime + processTime;
            final int profit = factory_gains[i];
            Offer offerObj = new Offer(startTime,endTime,profit,prod_types[i]);
            offers.add(offerObj);
        }
        display(offers,totalOffer);
        
        Collections.sort(offers);
        final int maxProfit = getMaxProfit(offers,totalOffer);
        // Write output.
        System.out.println("\n\n-------------------------------");
        System.out.println("Maximum Profit: "+maxProfit);
        System.out.println("-------------------------------\n\n");
    }
    /**
	 * A function to display start time, end time of products along with profits in tabular format.
	 * @param offers An ArrayList which holds the Offers.
	 * @param n Total number of Offers.
	 * @return void
	 */
    public static void display(ArrayList<Offer> offers,int n)
    {
        System.out.println("\n\n-------------------------------------------------------------------------------------------");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |\n","PRODUCTS","PRODUCT TYPE","TIME IN","TIME OUT","PROFITS");
        System.out.println("-------------------------------------------------------------------------------------------");
        for(int i=0;i<n;i++)
        {
            System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |\n",i+1,offers.get(i).getProductType(),
                                offers.get(i).getStartTime(),offers.get(i).getEndTime(),offers.get(i).getProfit());
        }
        System.out.println("-------------------------------------------------------------------------------------------");    
    }
}
/**
 * Offer class.
 */
class Offer implements Comparable<Offer> 
{
    //Start time of the offer.
    private int startTime;
    //End time of the offer.
    private int endTime;
    //Profit of the offer.
    private int profit;
    //Type of product of the offer.
    private char productType;
    /**
	 * Offer constructor with 4 parameters; namely startTime, endTime, profit and productType.
	 * @param startTime Start time of the offer.
	 * @param endTime End time of the offer.
	 * @param profit Profit of the offer.
	 * @param productType Product type of the offer.
     */
    public Offer(final int startTime,final int endTime,final int profit,final char productType) 
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.profit = profit;
        this.productType = productType;
    }
    /**
	 * Overrides the compareTo method to sort Offers according to ascending endTime order.
	 * @param o Other Offer.
	 * @return 1 if this Offer is of top priority, -1 if the other Offer is of top priority, 0 if they have the same priority.
	 */
    @Override
    public int compareTo(final Offer o) 
    {
        if(endTime < o.endTime) return -1;
        if(endTime > o.endTime) return 1;
        return 0;
    }
    /**
	 * Getter method for the field "startTime".
	 * @param void
	 * @return start time of the offer.
	 */
    public int getStartTime() 
    {
        return startTime;
    }
    /**
	 * Getter method for the field "endTime".
	 * @param void
	 * @return end time of the offer.
	 */
    public int getEndTime() 
    {
        return endTime;
    }
    /**
	 * Getter method for the field "profit".
	 * @param void
	 * @return profit of the offer.
	 */
    public int getProfit() 
    {
        return profit;
    }
    /**
	 * Getter method for the field "productType".
	 * @param void
	 * @return product type "Solid" or "Liquid".
	 */
    public String getProductType() 
    {
        if(productType=='s') 
            return "Solid";
        else 
            return "Liquid";
    } 
}




