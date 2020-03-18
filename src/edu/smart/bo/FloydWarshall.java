package edu.smart.bo;
//

import org.springframework.beans.factory.annotation.Autowired;

import edu.smart.model.CourseDetailsModel;

public class FloydWarshall{

	@Autowired
	Runner runner;
	
    public  double distancematrix[][];
    public double keymatrix[][];
    public  int numberofvertices;
    public static final int INFINITY = 999;
    public FloydWarshall() {}
    public FloydWarshall(int numberofvertices)
    {
        distancematrix = new double[numberofvertices + 1][numberofvertices + 1];
        this.numberofvertices = numberofvertices;
    }
 
    public double floydwarshall(double adjacencymatrix[][],int path[][],int keyIndices[], CourseDetailsModel courseDetailsModel)
    {
    	
        for (int source = 0; source <numberofvertices; source++)
        {
            for (int destination = 0; destination <numberofvertices; destination++)
            {
                distancematrix[source][destination] = adjacencymatrix[source][destination];
            }
        }
 
        for (int intermediate = 0; intermediate <numberofvertices; intermediate++)
        {
            for (int source = 0; source < numberofvertices; source++)
            {
                for (int destination = 0; destination <numberofvertices; destination++)
                {
                    if (distancematrix[source][intermediate] + distancematrix[intermediate][destination]
                         < distancematrix[source][destination])
                    {
                        distancematrix[source][destination] = distancematrix[source][intermediate] 
                            + distancematrix[intermediate][destination];
                        path[source][destination] = path[intermediate][destination];
                    }
                }
            }
        }
 
        /*System.out.println("\n\nShaortest Distance Matrix\n");
        for (int source = 0; source < numberofvertices; source++)
           System.out.print("\t" + source);
 
       System.out.println();
        for (int source = 0; source <numberofvertices; source++)
        {
            System.out.print(source + "\t");
            for (int destination = 0; destination <numberofvertices; destination++)
            {
                System.out.print(distancematrix[source][destination] + "\t");
            }
            System.out.println();
        }*/
        
      //  System.out.println("Paths between KeyConcept Pairs");
      //  Scanner stdin = new Scanner(System.in);
        
      //  int start = stdin.nextInt();
    	//int end = stdin.nextInt();
      //  String myPath = end + "";
        keymatrix=new double[keyIndices.length][keyIndices.length];
        for(int h=0;h<keyIndices.length;h++)
        {
        	int start=keyIndices[h];
        	int c=0;
        	for(int i=h;i<keyIndices.length;i++)
        	{
        		int end=keyIndices[i];
        		String myPath = end + "";
        		if(h==i)
        		{
        			keymatrix[h][i]=0;
        			continue;
        		}
        		else
        		{
        			while (path[start][end] != start) {  

        		        	myPath = path[start][end] + "^" + myPath;
        		        	c++;

        	    		end = path[start][end];
        	    	}
        			if(c==0)
        			
        			keymatrix[h][i]=keymatrix[i][h]=1;
        			
        			else
        			keymatrix[h][i]=keymatrix[i][h]=0;
        		}
        		myPath = start + "^" + myPath;
            System.out.println("Here's the path "+myPath);
            	courseDetailsModel.getPathList().add(myPath);
            	//System.out.println("getPathList "+courseDetailsModel.getPathList().get(0));
            	c=0;
        	}
        }
      /*  System.out.println("\n\n Key Concept Adjacency matrix\n");
        for (int j = 0; j <keymatrix.length; j++)
        {
            System.out.print(keyIndices[j] + "\t");
            for (int l = 0; l <keymatrix.length; l++)
            {
                System.out.print(keymatrix[j][l] + "\t");
            }
            System.out.println();
        }*/
        courseDetailsModel.setKeymatrix(keymatrix);
    	// Loop through each previous vertex until you get back to start.
    /*	while (path[start][end] != start) {
    		myPath = path[start][end] + " -> " + myPath;
    		end = path[start][end];
    	}*/
    	
    	// Just add start to our string and print.
    	//myPath = start + " -> " + myPath;
    	//System.out.println("Here's the path "+myPath);
        int reachableNodes=0;
        int meandistance=0;
        for(int i=0;i<numberofvertices;i++)
        {
        
			for(int j=0;j<numberofvertices;j++)
			        
			{
				meandistance+= distancematrix[i][j];
			if(distancematrix[i][j] >0 && distancematrix[i][j]<999)
			        
			reachableNodes++;
			        
			}
		 }
        
        
		        //System.out.println("reachableNodes :" +reachableNodes);
		        double N=(numberofvertices)*(numberofvertices-1);
		       double  MD=meandistance/N;
		       System.out.println("Mean Distance " +MD);
		       return MD;
    }

}
