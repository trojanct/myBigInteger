import java.io.*;
import java.lang.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;



public class bigIntTestMain
{


    static String ResultsFolderPath = "/home/cody/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;
    static int numberOfTrials = 100;


    public static void main(String[] args) {


        // test before hand
        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
        runFullExperiment("BigInt-Exp1-ThrowAway.txt");
        runFullExperiment("BigInt-Exp1.txt");
        runFullExperiment("BigInt-Exp3.txt");

    }



    static void runFullExperiment(String resultsFileName){

        int MAXINPUTSIZE = 1048577;

        try {

            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);

        } catch(Exception e) {

            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);
            return; // not very foolproof... but we do expect to be able to create/open the file...

        }



        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial


        resultsWriter.println("#InputSize    AverageTime"); // # marks a comment in gnuplot data
        resultsWriter.flush();
        /* for each size of input we want to test: in this case starting small and doubling the size each time */
        for(int inputSize=1 ;inputSize<=MAXINPUTSIZE; inputSize*=2) {
            // progress message...
            System.out.println("Running test for input size "+inputSize+" ... ");


            /* repeat for desired number of trials (for a specific size of input)... */
            long batchElapsedTime = 0;
            // generate a list of randomly spaced integers in ascending sorted order to use as test input
            // In this case we're generating one list to use for the entire set of trials (of a given input size)
            // but we will randomly generate the search key for each trial
            System.out.print("    Generating test data...");



            System.out.println("...done.");
            System.out.print("    Running trial batch...");


            /* force garbage collection before each batch of trials run so it is not included in the time */
            System.gc();




            // instead of timing each individual trial, we will time the entire set of trials (for a given input size)
            // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the
            // stopwatch methods themselves
            BatchStopwatch.start(); // comment this line if timing trials individually


            // run the tirals
            test(inputSize);


            batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double)numberOfTrials; // calculate the average time per trial in this batch


            /* print data for this size of input */
            resultsWriter.printf("%12d  %15.2f \n",inputSize, averageTimePerTrialInBatch); // might as well make the columns look nice
            resultsWriter.flush();
            System.out.println(" ....done.");

        }

    }





    public static void test(int size) {
        String a = "551344";
        String b = "1391";
        String c;

        myBigInteger bigInt1 = new myBigInteger(randomBig(size));
        myBigInteger bigInt2 = new myBigInteger(randomBig(size));

        c = bigInt1.Plus(bigInt2);

        //System.out.println(bigInt1.ToString());
        //System.out.println(bigInt2.ToString());
        //System.out.println(c);


    }
    public static String randomBig(int size)
    {
        long MAXV =  9;
        long MINV =  0;
        int randomInt = 0;

        StringBuilder randomBigNumber = new StringBuilder();

        for(int j=0; j<size;j++)
        {
            if(j==0)
            {
                randomInt = (int) (Math.random() * (MAXV - MINV+1));
                randomBigNumber.append(randomInt);
            }
            else {
                randomInt = (int) (Math.random() * (MAXV - MINV));
                randomBigNumber.append(randomInt);
            }
        }


        return randomBigNumber.toString();



    }



}

