/**
 * The measurements and values we used are in inches.
 */

import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;

public class LeftScreen
{
    public static void main(String[] args) throws IOException {
        String meshData = "";
        
        double fieldOfView = 75; //in degrees
        double radius = 15;
        double length = 44.0;
        double vradius = 0.5 / Math.tan(Math.toRadians(fieldOfView/2));
        double height = 15;
        double mouseHeight = 7.5;
        int numberOfFaces = 200;
                                
        ArrayList<String> verticesData = new ArrayList();
        ArrayList<String> trianglesData = new ArrayList();
        
        //read in the square grid object file
        File fileName = new File("Grid" + numberOfFaces + "x" + numberOfFaces + ".obj");
        Scanner inFile = new Scanner(fileName);
        
        //sort the information in the object file
        while (inFile.hasNextLine())
        {
            meshData = inFile.nextLine();
            if (meshData.equals(""))
                continue;
            else if (meshData.substring(0,1).equals("#"))
                continue;
            else if (meshData.substring(0,2).equalsIgnoreCase("v ")) 
                verticesData.add(meshData);
                
            else if (meshData.substring(0,1).equalsIgnoreCase("f"))
                trianglesData.add(meshData);            
        }
        inFile.close();
        
        //assign vertices to an ArrayList
        ArrayList<float[]> vectorCoordinates = VirtualEye.vertexCoordinatesArrayList(verticesData);
        int vectorCoordinatesSize = vectorCoordinates.size();
        //assign the face data to an ArrayList
        ArrayList<Integer> newTrianglesData = VirtualEye.triangleFacesArrayList(trianglesData);
        int newTrianglesDataSize = newTrianglesData.size();
        
        //create the new object file
        PrintWriter outFile = new PrintWriter(new File("Warped_Mesh_Left.obj"));
        outFile.println("#Coordinates of Vertices (x,y,z)");
       
        //vertex texture coordinates 
        //color the mesh white
        ArrayList textureCoordinates = new ArrayList();
        double first = 0;
        double second = 0;
        while (second <= 1.0) {
            while (first <= 1.0) {
                textureCoordinates.add("vt " + first + " " + second + " 0");
                first += (1/(double)numberOfFaces);
            }
            first = 0;
            second += (1/(double)numberOfFaces);
        }
                    
        int count = 0;
        while (count < (vectorCoordinatesSize)) {
            float[] dot;
            dot = vectorCoordinates.get(count);
            dot[0] = dot[0] + (float)0.15;
            dot[1] = dot[1] - (float)0.15;
            //correct the grid so that it is one by one
            float correctionFactor = (float)10.0 / (float)3.0;
            dot[0] *= correctionFactor;
            dot[1] *= correctionFactor;
            //warp around mouse as a cylinder
            dot[0] = VirtualEye.warp1(dot[0], vradius, fieldOfView);
            dot[1] = VirtualEye.warp2(dot[1], dot[0], vradius, fieldOfView);
            dot[0] = VirtualEye.warp3Left(dot[0], vradius, fieldOfView);
            //height center adjustment
            double heightRatio = mouseHeight / height;
            heightRatio -= 0.5;
            dot[1] += heightRatio;
            //amplify the image to model real setup
            double amplificationFactor = radius / vradius;
            dot[0] *= amplificationFactor;
            dot[1] *= amplificationFactor;
            //transform the image to create the warped image to project to the cylinder screen
            dot[1] *= VirtualEye.transformation1( radius, dot[0] ) * VirtualEye.transformation3( length, radius, dot[0] );
            dot[0] *= VirtualEye.transformation1( radius, dot[0] ) * VirtualEye.transformation2( length, radius, dot[0] );           
            //print the results to the object file
            outFile.println("v " + dot[0]  + " " + dot[1]  + " " + dot[2] );
            
            count++;
        }
        
        count = 0;
        while (count < (vectorCoordinatesSize)) {
            outFile.println("vn 0 1 0");
            outFile.println(textureCoordinates.get(count));
            count++;
         }
        
        outFile.println("#Triangle Indices");
        
        //print face data to the object file
        count = 0;
        while (count < newTrianglesData.size()) {
            outFile.println("f " + newTrianglesData.get(count) + "/" + newTrianglesData.get(count) + "/" + newTrianglesData.get(count) + " " + newTrianglesData.get(count+1) + "/" + newTrianglesData.get(count+1) + "/" + newTrianglesData.get(count+1) + " " + newTrianglesData.get(count+2) + "/" + newTrianglesData.get(count+2) + "/" + newTrianglesData.get(count+2) );          
            count += 3;
        }
        
        outFile.println("#End of File");
        
        outFile.close();
        
        System.out.println("Done");
    }
}
