/**
 * A set of methods that are useful in the VirtualEyeMesh program
 * 
 * @author Taisuke Yasuda
 * @version 08/01/2014
 */
import java.util.ArrayList;
public class VirtualEye 
{
    /**
     * This method takes in an ArrayList of Strings that represent vertices and assigns them to
     * an ArrayList of float arrays.
     * 
     * @param ArrayList of Strings that represent vertices
     * @return ArrayList of float arrays of size three that represent vertices
     */
    public static ArrayList<float[]> vertexCoordinatesArrayList(ArrayList<String> verticesData)
    {
        float[] coordinates = new float[3];
        ArrayList<float[]>  vectorCoordinates = new ArrayList();
        
        String vertexData = "";
        
        int verticesSize = verticesData.size();
        
        // assign vertex data from text file to a list of coordinates of vertices
        int count = 0;
        while (count < verticesSize) 
        {
            vertexData = verticesData.get(count);
            vertexData = vertexData.replace( "v", "" );
            vertexData = vertexData + " ";
            String point;
            float pointFloat;
            int counter = 0;
            while (counter < 3) {
                vertexData = vertexData.substring(1);
                int spaceIndex = vertexData.indexOf(" ");
                point = vertexData.substring(0,spaceIndex);
                vertexData = vertexData.substring(spaceIndex);
                pointFloat = Float.parseFloat(point);                
                coordinates[counter] = pointFloat;                
                counter++;
            }            
            vectorCoordinates.add(coordinates);
            count++;
            coordinates = new float[3];
        }
        
        return vectorCoordinates;
    }
    
    /**
     * This method takes in an ArrayList of Strings that represent the faces of the desired mesh
     * and returns an ArrayList of Integers that represent the indices of the vertices that are
     * used to form each face
     * 
     * @param ArrayList of String that represents the faces of the desired mesh
     * @return ArrayList of Integers that represent the indices of the vertices that are used to
     * form each face
     */
    public static ArrayList<Integer> triangleFacesArrayList(ArrayList<String> trianglesData)
    {
        int counted = 0;
        int count = 0;
        int pointDotInt;
        int numberOfDigits;
        String subString;
        String triangle;
        ArrayList<Integer> newTrianglesData = new ArrayList();
        int trianglesSize = trianglesData.size();
        
        //extract the faces data by manipulating the string
        while (counted < trianglesSize) {
            triangle = trianglesData.get(counted);
            triangle = triangle.replace( "f", "" );
            triangle = triangle + "                  ";
            pointDotInt = 0;
            numberOfDigits = 0;
            subString = "";
            count = 0;
            while (count < 3) {
                triangle = triangle.substring(1);          
                if (triangle.substring(0,6).equals(triangle.substring(8,14))) {
                    subString = triangle.substring(0,14);
                    triangle = triangle.substring(14);
                    subString = subString.replaceAll( "/", "" );
                    subString = subString.replace( " ", "" );
                    numberOfDigits = subString.length();
                    subString = subString.substring(0, (numberOfDigits / 2));
                    pointDotInt = Integer.parseInt(subString);
                    newTrianglesData.add(pointDotInt);
                    count++;
                }
                else if (triangle.substring(0,6).equals(triangle.substring(8,14))) {
                    subString = triangle.substring(0,14);
                    triangle = triangle.substring(14);
                    subString = subString.replaceAll( "/", "" );
                    subString = subString.replace( " ", "" );
                    numberOfDigits = subString.length();
                    subString = subString.substring(0, (numberOfDigits / 2));
                    pointDotInt = Integer.parseInt(subString);
                    newTrianglesData.add(pointDotInt);
                    count++;
                }
                else if (triangle.substring(0,5).equals(triangle.substring(7,12))) {
                    subString = triangle.substring(0,12);
                    triangle = triangle.substring(12);
                    subString = subString.replaceAll( "/", "" );
                    subString = subString.replace( " ", "" );
                    numberOfDigits = subString.length();
                    subString = subString.substring(0, (numberOfDigits / 2));
                    pointDotInt = Integer.parseInt(subString);
                    newTrianglesData.add(pointDotInt);
                    count++;
                }
                else if (triangle.substring(0,4).equals(triangle.substring(6,10))) {
                    subString = triangle.substring(0,10);
                    triangle = triangle.substring(10);
                    subString = subString.replaceAll( "/", "" );
                    subString = subString.replace( " ", "" );
                    numberOfDigits = subString.length();
                    subString = subString.substring(0, (numberOfDigits / 2));
                    pointDotInt = Integer.parseInt(subString);
                    newTrianglesData.add(pointDotInt);
                    count++;
                }
                else if (triangle.substring(0,3).equals(triangle.substring(5,8))) {
                    subString = triangle.substring(0,8);
                    triangle = triangle.substring(8);
                    subString = subString.replaceAll( "/", "" );
                    subString = subString.replace( " ", "" );
                    numberOfDigits = subString.length();
                    subString = subString.substring(0, (numberOfDigits / 2));
                    pointDotInt = Integer.parseInt(subString);
                    newTrianglesData.add(pointDotInt);
                    count++;
                }
                else if (triangle.substring(0,2).equals(triangle.substring(4,6))) {
                    subString = triangle.substring(0,6);
                    triangle = triangle.substring(6);
                    subString = subString.replaceAll( "/", "" );
                    subString = subString.replace( " ", "" );
                    numberOfDigits = subString.length();
                    subString = subString.substring(0, (numberOfDigits / 2));
                    pointDotInt = Integer.parseInt(subString);
                    newTrianglesData.add(pointDotInt);
                    count++;
                }
                else  {
                    subString = triangle.substring(0,4);
                    triangle = triangle.substring(4);
                    subString = subString.replaceAll( "/", "" );
                    subString = subString.replace( " ", "" );
                    numberOfDigits = subString.length();
                    subString = subString.substring(0, (numberOfDigits / 2));
                    pointDotInt = Integer.parseInt(subString);
                    newTrianglesData.add(pointDotInt);
                    count++;
                }
            }
            counted++;
        }
        
        return newTrianglesData;
    }
    
    /**
     * This method warps the x axis of a plane into a cylindrical shape
     * 
     * @param double coordinate is the x values to be modified
     * @param double vraidus is the virtual radius
     * @param double fieldOfView is the desired field of view in degrees
     * @return the warped x value
     */
    public static float warp1(double coordinate, double vradius, double fieldOfView) {
        double endpoint = 2*Math.PI*vradius*(fieldOfView/2/360);
        double k = Math.toRadians(fieldOfView/2) / endpoint;
        return (float)(Math.atan2(coordinate, vradius) / k);
    }
    
    /**
     * This method warps the y axis of a plane into a cylindrical shape
     * 
     * @param double coordinate is the y values to be modified
     * @param double vraidus is the virtual radius
     * @param double fieldOfView is the desired field of view in degrees
     * @return the warped y value
     */
    public static float warp2(double coordinate, float xValue, double vradius, double fieldOfView) {
        double factor = vradius / (Math.sqrt(Math.pow(vradius, 2) + Math.pow(xValue, 2)));
        return (float)(coordinate * factor);
    }
    
    /**
     * This method shifts the right mesh to the right for easy placement in the Unity Game Engine
     * 
     * @param double coordinate is the coordinate to be shifted
     * @param double vradius is the virtual radius
     * @param double fieldOfView is the desired field of view in degrees
     * @return the shifted x value
     */
    public static float warp3Right(double coordinate, double vradius, double fieldOfView) {
        //right version moves right
        return (float)(coordinate + 2*Math.PI*vradius*(fieldOfView/2)/360);
    }
    
    /**
     * This method shifts the left mesh to the left for easy placement in the Unity Game Engine
     * 
     * @param double coordinate is the coordinate to be shifted
     * @param double vradius is the virtual radius
     * @param double fieldOfView is the desired field of view in degrees
     * @return the shifted x value
     */
    public static float warp3Left(double coordinate, double vradius, double fieldOfView) {
        //left version moves left
        return (float)(coordinate - 2*Math.PI*vradius*(fieldOfView/2)/360);
    }
    
    /**
     * This method represents the transformation from the points on the original plane to the cylindrical screen
     * It applies for both the x and z directions
     * 
     * @param double radius is the radius of the cylindrical screen 
     * @param double coordinate is the x value of the original image
     * @return the factor that will transform the point from the original plane to the cylindrical screen
     */
    public static double transformation1(double radius, double xValue) {
        return radius / Math.sqrt(Math.pow(radius, 2) + Math.pow(xValue, 2));
    }
    
    /**
     * This method represents the factor that transforms the original image to the projector image along with
     * transformation1 for the x direction
     * 
     * @param double length is the length from the projector to the screen
     * @param double radius is the radius of the screen
     * @param double xValue is the x value of the original image
     * @return the factor that will transform the original image to the projector image along with transformation 1
     * for the x direction
     */
    public static double transformation2(double length, double radius, double xValue) {
        return length / (length + radius  - Math.sqrt(Math.pow(radius, 2) -  Math.pow(xValue, 2) * Math.pow(radius, 2) / (Math.pow(xValue, 2) + Math.pow(radius, 2))));
    }
    
    /**
     * This method represents the factor that transforms the original image to the projector image along with
     * transformation1 for the z direction
     * 
     * @param double length is the length from the projector to the screen
     * @param double radius is the radius of the screen
     * @param double xValue is the x value of the original image
     * @return the factor that will transform the original image to the projector image along with transformation 1
     * for the z direction
     */
    public static double transformation3(double length, double radius, double xValue) {
        return length / (length + Math.sqrt(Math.pow(radius, 2) + Math.pow(xValue, 2)) - radius);
    }
}
