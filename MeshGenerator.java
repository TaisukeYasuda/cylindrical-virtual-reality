/**
* Mesh Generator Program that takes the number of vertices per side and outputs an object file 
* that contains the information for a square grid that is triangulated. It mimics the format of an
* object file created by the MeshLab grid generator
*/

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

public class MeshGenerator
{
    public static void main(String[] args) throws IOException
    {
        Scanner in =  new Scanner(System.in);
        System.out.println("This program generates an object file for a triangulated"); 
        System.out.println("square grid. How many vertices per side would you like?");

        int numSides = in.nextInt();

        PrintWriter outFile = new PrintWriter(new File("Grid" + numSides + "x" + numSides + ".obj"));

        double xMin = -0.3;
        double xMax = 0;
        double yMin = 0;
        double yMax = 0.3;

        double sideLength = xMax - xMin;
        double increment = sideLength/(numSides - 1);

        outFile.flush();
        outFile.println("####");
        outFile.println("#");
        outFile.println("# OBJ File");
        outFile.println("#");
        outFile.println("####");
        outFile.println("# Object Grid" + numSides + "x" + numSides + ".obj");
        outFile.println("#");
        outFile.println("# Vertices: " + Math.pow(numSides, 2));
        outFile.println("# Faces: " + 2 * Math.pow(numSides - 1, 2));
        outFile.println("#");
        outFile.println("####");

        for( int i = 0; i < numSides; i++ )
        {
            for( int j = 0; j < numSides; j++ )
            {
                outFile.println("vn 0 0 1");
                outFile.print("v ");
                outFile.print((xMax - increment * j) + " ");
                outFile.print((yMin + increment * i) + " ");
                outFile.println("0");
            }   
        }

        for( int i = (numSides + 2); i <= Math.pow(numSides, 2); i++ )
        {
            if((i - numSides - 1) % numSides != 0)
            {
                outFile.print("f ");
                outFile.print(i + "//" + i + " ");
                outFile.print((i - numSides) + "//" + (i - numSides) + " ");
                outFile.println((i - numSides - 1) + "//" + (i - numSides - 1));
                outFile.print("f ");
                outFile.print((i - numSides - 1) + "//" + (i- numSides - 1) + " ");
                outFile.print((i - 1) + "//" + (i - 1) + " ");
                outFile.println(i + "//" + i);
            }
        }
        
        outFile.println();
        outFile.println("#End of File");
        outFile.close();
        System.out.println("Done");
    }
}
