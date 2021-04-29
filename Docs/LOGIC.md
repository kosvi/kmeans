## Simple TODO 

1. Create n dots in n*n space. 
2. Get center of all dots.  
3. Get m more center dots. 
4. Group all dots to groups by nearest center dot. 
5. Start moving those center dots to find optimal locations inside own cluster. 

## Structure

**charts/ScatterChart.java**

Handles drawing chart from the data stored in the chart-instance. 

**logic/DataGenerator.java**

Used to generate random XY-data. 

**logic/Distances.java**

Calculates distances between XY-data objects and calculates centers for given datasets. 

**model/XY.java**

Simple class that containes X- and Y-coordinates of single dot. 

**model/Cluster.java**

Contains a list of XY-dots that create the cluster and a single XY-dot that is the center of the cluster. 

**model/Clusters.java**

Simply wraps all the clusters in a single instance that can interact with the ScatterChart-class. 

**ui/UI.java**

Handles the user interface for the application. 



