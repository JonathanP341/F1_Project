/*
 * Jonathan Peters
 * Gonna do everything in one class with methods for now, easier this way
 * Gonna do a mock version for now just to get hte idea working
**/

import java.util.*;
/**
 *
 * @author Jonathan Peters
 */
public class F1Project {
    /*
     * avgPositionValue
     * Finding the avg position the driver has acheived that year and 
    */
    public static double[] avgPositionValue(int[][] eachRacer, int numRacers, int numRaces) {
        double[] points = new double[numRacers];
        double temp = 0;
        for (int i = 0; i < numRacers; i++) {
            temp = 0; //Resetting the variable every loop
            for (int j = 0; j < numRaces; j++) {
                temp += eachRacer[i][j]; //Adding all of the positions of the racer
            }
            temp = temp / numRaces; //Dividing the value by the races
            points[i] = ((-1*temp)/2) + 10; //Getting the points of each racer
        }
        
        return points;
    }
    
    public static double[] avgTeamPositionValue(int[] allPositionsByRacer, int numRacers, int numRaces) {
        double[] points = new double[numRacers];
        
        double temp = 0; //total team points
        int ct = 0; 
        for (int i = 0; i < numRacers/2; i++) { //going team by team which is essentially every 10 races or 2 racers at a time
            temp = 0;
            for (int j = 0; j < numRaces*2; j++) { //going race by race so we look at all 10 races between the two racers 
                temp += allPositionsByRacer[j + ct];
            }
            ct += numRaces*2; //For the number of races per team
            temp = temp / (numRaces * 2);
            points[i] = (-9 * temp)/20 + 9; //adding the point formula
            points[i+1] = (-9 * temp)/20 + 9;    
        }
        return points;
    }
    
    public static double[] avgQualiPositionValue(int[] qualifyingPositionByRacer, int avgOverTakes,  int numRacers, int numQualis) {
        double[] points = new double[numRacers];
        double temp = 0;
        
        int ct = numQualis - 1; //start at the 6th index of the array
        for (int i = 0; i < numRacers; i++) {
            temp = qualifyingPositionByRacer[ct]; 
            temp = (-1*(temp+20)*(temp-20)) / (2*avgOverTakes); //Put through formula
            points[i] = temp;
            ct += numQualis; //add 6 because to see the next drivers quali position   
        }
        return points;
    }
    
    public static double[] avgRandomnessPoints(int[] qualifyingPositionByRacer, int numRacers, int numQualis) {
        double[] points = new double[numRacers];
        double temp = 0;
        
        int ct = numQualis - 1; //start at the 6th index
        for (int i = 0; i < numRacers; i++) {
            temp = qualifyingPositionByRacer[ct];
            temp = (0.05 * temp)*(temp - 20) + 5; //Putting through formula
            points[i] = temp;
            ct += numQualis;   //add 6 to see the next drivers quali position    
        }
        return points;
    }
    
    public static double[] avgWeatherPoints(boolean rain, int[] driverAge, int numRacers, int numQualis) {
        double[] points = new double[numRacers];
        double temp = 0;
        
        if (rain == true) { //If there is weather, then add the experience difference here
            int oldest = 0;
            for (int i = 0; i < numRacers; i++) {
                if (driverAge[i] > oldest){
                    oldest = driverAge[i];
                }
            }
            for (int i = 0; i < numRacers; i++) {
                temp = oldest - driverAge[i];
                temp = -1 * Math.sqrt(temp);
                points[i] = temp;
            }
        }
        return points;
    }
    
    public static double[] avgQualiTimePoints(double[] qualiTimes, int numRacers, int numQualis) {
        double[] points = new double[numRacers];
        double temp = 0;
        
        //Finding the fastest lap time 
        double fastest = 99999;
        for (int i = 0; i < qualiTimes.length; i++) {
            if (qualiTimes[i] < fastest) {
                fastest = qualiTimes[i];
            }
        }
        
        //Comparing the lap time of the driver to the fastest
        for (int i = 0; i < numRacers; i++) {
            temp = qualiTimes[i] - fastest; 
            temp = 5 * Math.cos((Math.PI * temp) / 6); //Putting through formula
            points[i] = temp;
        }
        
        return points;
    }
    
    
    public static double[] avgCarCostPoints(double[] carCost, int numRacers, int numRaces) {
        double[] points = new double[numRacers];
        double temp;
        
        //Finding the minimum amount spent on a car
        double min = carCost[0]; 
        for (int i = 0; i < numRacers; i++) {
            if (carCost[i] < min) {
                min = carCost[i];
            }
        }
        //Dividing by 10 to lower the value of the numbers
        min = min/10;
        //Getting the points for the car
        for (int i = 0; i < numRacers; i++) {
            temp = carCost[i]/10.0; //Making sure that you dont floor divide 
            if (temp <= min) {
                temp = 0;
            }
            else { //Making sure you dont divide by 0
                temp = Math.sqrt(temp - min);
            }
            
            points[i] = temp;
        }
        
        return points;
    }
    
    public static double[] avgPastTrackPoints(int[] pastTrackResults, int numRacers, int numQualis) {
        double[] points = new double[numRacers];
        double temp;
        
        //Starting at the 5th position in the array
        int ct = numQualis - 1;
        for (int i = 0; i < numRacers; i++) {
            temp = pastTrackResults[ct];
            temp = (-1/4 * temp) + 5;
            points[i] = temp; //Adding the points to the array
            ct += numQualis; //Looking at the most recent race past results
        }
        
        return points;  
    }
    
    /*
     * addingPoints
     * Meant to add the points from the temporary array to the final array
    */
    public static double[] addingPoints(double[] points, double[] pointsTemp, String[] playerNames, int numRacers, int numRaces) {
        for (int i = 0; i < points.length; i++) {
            points[i] += pointsTemp[i];
        }
        
        /*/Printing out each persons points, wont print out right now
        System.out.println("\n");
        for (int i = 0; i < numRacers; i++) {
            System.out.println(playerNames[i] + "'s points: " + points[i]);   
        }
        */
        return points;
    }
    
    public static int[][] arraySorter(int[] singleArray, int numRacers, int numRaces) {
        int[][] doubleArray = new int[numRacers][numRaces];
        
        //For loop to put the position of the racers in the correct 2d array 
        int ct = 0;
        for (int i = 0; i < numRacers; i++ ) {
            for (int j = 0; j < numRaces; j++) {
                doubleArray[i][j] = singleArray[j + ct];
            }
            ct += numRaces;
        }
        
        return doubleArray;
    }
    
    public static void finalStandings(double[] points, String[] playerNames, String grandPrix, int numRacers, int numRaces, int numQualis) {
        HashMap<Double, String> scores = new HashMap<Double, String>();
        TreeMap<Double, String> sorted = new TreeMap<>(); //Used to sort the hashmap
        
        //Adding the scores to a hashmap with the player name connected
        
        for (int i = 0; i < playerNames.length; i++) {
            scores.put(points[i], playerNames[i]);
            //System.out.println(playerNames[i] + ": " + points[i]);
        }
        
        //Adding all of the values of the hashmap into the treemap which automatically sorts it
        sorted.putAll(scores); //Wtf this is op
        
        int ct = numRacers; //For the placing of the racers
        System.out.println("\n---LIKELY WINNER OF " + grandPrix.toUpperCase() + " GRAND PRIX---");
        //Outputting the placement of the racers
        for (Map.Entry<Double, String> entry : sorted.entrySet()) {
            double rounded = (double) Math.round(entry.getKey()*100) / 100;
            System.out.println(ct + " place: " + entry.getValue() + " - " + rounded + " points.");
            ct -= 1; //Prints in order from numRacers -> 1
        }   
    }
    
    
    public static void main(String[] args) {
        int numRaces = 5;
        int numRacers = 20;
        int numQualis = numRaces + 1;
        int numTeams = 10;
        
        //                                Red Bull              Ferrari               Mercedes           Alpine                McLaren
        String[] playerNames = {"Verstappen", "Perez",   "Sainz", "Leclerc",    "Lewis", "Russel",  "Ocon", "Alonso",   "Norris", "Ricciardo", 
                                "Latifi", "Albon",    "Bottas", "Zhou",   "Magnussen", "Schumacher",    "Gasly", "Tsunoda",    "Vettel", "Stroll",};
        //                          Williams                Alfa Romeo              Haas                   Alphatauri              Aston Martin
        
        String[] grandPrixNames = {"Bahrain", "Saudi Arabia", "Australia", "Emilia Romagna", "Miami", "Spain"};
        
        //The arrays for the final position in the grand prixs, could update how it works
        //                          Verstappen       Perez         Sainz       Leclerc     Lewis         Russel        Ocon          Alonso      Norris     Ricciardo
        int[] allPositionsByRacer = {20,1,20,1,1,  20,4,2,2,4,  2,3,20,20,3,  1,2,1,6,2,  3,10,4,13,6,  4,5,3,4,5,  7,6,7,14,8,  9,20,17,20,11,  15,7,5,3,20,  14,20,6,18,13,
              16,20,16,16,14,  13,20,10,11,9,  6,20,8,5,7,  10,11,11,15,20,  5,9,14,9,16,  11,20,13,17,15,  20,8,9,12,20,  8,20,15,7,12,  20,8,20,11,17,  12,13,12,10,11};
        //       Latifi         Albon           Bottas         Zhou         Magnussen        Schumacher        Gasly         Tsunoda         Vettel           Stroll
        
        //The array for the qualifying position, where do they start on the grid
        //                                Verstappen        Perez          Sainz       Leclerc        Lewis         Russel         Ocon            Alsonso          Norris     Ricciardo
        int[] qualifyingPositionByRacer = {0,0,0,0,3,2,  0,0,0,0,4,5,  0,0,0,0,2,3,  0,0,0,0,1,1,  0,0,0,0,6,6,  0,0,0,0,12,4,  0,0,0,0,20,12,  0,0,0,0,11,20,  0,0,0,0,8,11, 0,0,0,0,14,9,
             0,0,0,0,19,19,  0,0,0,0,18,18,  0,0,0,0,5,7,  0,0,0,0,17,15,  0,0,0,0,16,8,  0,0,0,0,15,10,  0,0,0,0,7,14,  0,0,0,0,9,13,  0,0,0,0,13,16,  0,0,0,0,10,17};
        //     Latifi        Albon              Bottas           Zhou         Magnussen   Schumacher         Gasly         Tsunoda         Vettel         Stroll
        
        //Array holding the ages of each driver, could change to years in f1
        int[] driverAge = {24,32,  27,24,  37,24,  25,40,  22,32,  26,26,  32,22,  29,23,  26,21,  34,23};
        
        //Array holding the avg overtakes per track
        int[] avgOverTakes = {41, 20, 6, 22, 55, 23};
        
        //Array holding the cost of the car in millions
        //                 RedBull   Ferrari   Mercedes   Alpine    McLaren  Williams  AlfaRom.   Haas   Alphatauri  Aston Martin
        double[] costOfCar = {445,445,  463,463,  484,484,  272,272,  269,269,  141,141,  132,132,  173,173,  138,138,   188,188};
        
        //Array holding the qualifying times for the current race in the same order as the above in seconds, add 3 to the lowest time so they get 0
        //                     Ver.   Perez     Sainz   Leclerc    Lewis   Russel    Ocon   Alonso   Norris Ricciardo  Latifi   Albon   Bottas    Zhou
        double[] qualiTimes = {79.073, 79.420,  79.166,  78.750,  79.512,  79.393,  80.638, 78.750+3,  80.471,  80.297,  81.915,  81.645,  79.608,  81.094,  
                                79.682,  80.368,  80.861,  80.639,  80.954,  81.418};
        //                       Mag.     Schu.   Gasly    Tsunoda   Vettel   Stroll
        
        //Holds whether there will be or has been rain on the track at the time of the grand prix
        boolean[] rainOnTrack = {false, false, false, false, false, false};
        
        //Array holding the drivers previous race results on the tracks being driver than season, only looking at the previous season
        //                      Verstappen           Perez             Sainz          Leclerc        Lewis             Russel               Ocon          Alsonso           Norris       Ricciardo
        int[] pastTrackResults = {2,2,3,1,20,2,  5,20,13,11,20,5,  8,8,20,5,20,7,  6,7,5,4,20,4,  1,1,2,2,20,1,  14,20,16,20,20,14, 13,4,9,12,20,9,  20,13,5,10,20,17,  4,10,12,3,20,8, 7,5,20,6,20,6,
         20,12,20,20,20,16,  9,20,14,3,20,8,  3,3,1,20,20,3,  20,20,20,20,20,20,  17,20,6,20,20,15,  16,20,20,16,20,18,  20,6,11,7,20,10,  9,14,20,12,20,20,  15,20,4,20,20,13,  10,11,9,8,20,11};
        //  Latifi               Albon         Bottas               Zhou             Magnussen          Schumacher            Gasly         Tsunoda         Vettel          Stroll
        
        //List holding all of the players positions
        int[][] eachRacerPosition = new int[numRacers][numRaces];
        
        //List holding the actual result of the races
        //                       Verstappen         Perez          Sainz          Leclerc          Lewis        Russel          Ocon            Alonso          Norris          Ricciardo
        int[] finalPositions = {20,1,20,1,1,1,  20,4,2,2,4,2,  2,3,20,20,3,4,  1,2,1,6,2,20,  3,10,4,13,6,5,  4,5,3,4,5,3,  7,6,7,14,8,7,  9,20,17,20,11,9,  15,7,5,3,20,8,  14,20,6,18,13,12,
              16,20,16,16,14,16,  13,20,10,11,9,18,  6,20,8,5,7,6,  10,11,11,15,20,20,  5,9,14,9,16,17,  11,20,13,17,15,14,  20,8,9,12,20,13,  8,20,15,7,12,10,  20,8,20,11,17,11,  12,13,12,10,11,15};
        //          Latifi             Albon             Bottas            Zhou             Magnussen        Schumacher           Gasly             Tsunoda           Vettel              Stroll
        
        //Converting the arrays into a 2d form
        eachRacerPosition = arraySorter(allPositionsByRacer, numRacers, numRaces);
        
        //Finding the average position of the driver that season
        double[] points = new double[numRacers];
        //Finding the points gained from that particular category then adding it to points
        double[] pointsTemp = new double[numRacers];
        

        //Getting the points from the personal ranking
        pointsTemp = avgPositionValue(eachRacerPosition, numRacers, numRaces);
        points = addingPoints(points, pointsTemp, playerNames, numRacers, numRaces); //Adding it to the total points, maybe put in other methods?
        
        //Getting the points from the cars performance that year
        pointsTemp = avgTeamPositionValue(allPositionsByRacer, numRacers, numRaces);
        points = addingPoints(points, pointsTemp, playerNames, numRacers, numRaces);
        
        //Getting the points from the qualifying position
        pointsTemp = avgQualiPositionValue(qualifyingPositionByRacer, avgOverTakes[avgOverTakes.length-1], numRacers, numQualis);
        points = addingPoints(points, pointsTemp, playerNames, numRacers, numRaces);
        
        //Looking at the frequency of randomness for drivers based on starting position
        pointsTemp = avgRandomnessPoints(qualifyingPositionByRacer, numRacers, numQualis);
        points = addingPoints(points, pointsTemp, playerNames, numRacers, numRaces);
        
        //Getting the point loss from experience if there is rain, if not ignore
        pointsTemp = avgWeatherPoints(rainOnTrack[rainOnTrack.length - 1], driverAge, numRacers, numQualis);
        points = addingPoints(points, pointsTemp, playerNames, numRacers, numRaces);
        
        //Getting the points from the time of the qualifying lap
        pointsTemp = avgQualiTimePoints(qualiTimes, numRacers, numQualis);
        points = addingPoints(points, pointsTemp, playerNames, numRacers, numRaces);
       
        //Getting the points from cost of the car
        pointsTemp = avgCarCostPoints(costOfCar, numRacers, numRaces);
        points = addingPoints(points, pointsTemp, playerNames, numRacers, numRaces);
        
        //Getting the points from the racers past experience on the track
        pointsTemp = avgPastTrackPoints(pastTrackResults, numRacers, numRaces);
        points = addingPoints(points, pointsTemp, playerNames, numRacers, numRaces);
        
        //Printing out the final standings
        finalStandings(points, playerNames, grandPrixNames[grandPrixNames.length - 1], numRacers, numRaces, numQualis);
        
        
        /*
        Things i have yet to add:
        1. qualifying time - done
        2. cost of car - done
        3. history on track - done
        4. make it spiffy
        5. apply to actual f1 and start looking through the season to compare
        */
    }
    

}
