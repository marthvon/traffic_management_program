# Synopsis

Final Assessment of Data Structures and Algorithms - DSA1002

It takes in a csv file or a text file using ',' as delimeters to create a graph network. It uses the raw data to interpret the shortest path or best paht between a source to its destination. Therefore, it contributes in creating simulations that can assists in dynamically managing traffic and optimising traffic flow. Additionally, it assists in analysing traffic management strategies by sorting traffic data to show the intersections with the highest expected traffic volume. Hence, it is a valuable resource in helping deploy traffic control measures

# How To Use?

A Test Harness can be used to test the data in the "traffic_data.txt" and "traffic_network.txt". Command => "java TestHarness.java <... options>". This throws IncorrectShortestPathError, RetrieveNoneExistingPathError, and PrioritisedTrafficControlError if the test harness fails the assertions

Test Harness Manual<br>
Options:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**-q**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Quiet. Doesn't display stdout to console but only stderr from assertions<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**-d**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Display Road Intersection Information in Detail, otherwise print minimally<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**-l**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Print out the Traffic Network Adjacency List<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**-p**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Print sorted Traffic Data Intersections from Highest to Lowest Traffic Volume<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**-s**\<... optional\>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Print the Best Path between Source and Destination<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;default&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Best Path is based on the intersection Overall Weight<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**t**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Best Path is based on the intersection Traffic Factor<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**m**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Best Path is based on the intersection Distance in Miles<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**v**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Best Path is based on the intersection Traffic Volume<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**-a**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;When '-s' is flagged, it also prints other non optimal paths<br>

Also, "bash sweep.sh" can be ran to sweep the Test Harness to output text files of the network adjacency list, the shortest path of every path to every other path, and the best path to take based on the traffic_data, additionally it outputs the priority queue based on the intersection with the most traffic volume. All the output is stored in the "./docs/" directory

The Traffic Menu is an user-friendly console interface to manipulate the traffic network
to create simulations and perform all the above features in the Test Harness.
Command => "java TrafficMenu.java"

## Traffic Management Program, include features of:
    1. Display Traffic Network Adjacency List
    2. Find Path from Two Locations
    3. Display Intersection in order of Traffic Volume
    4. Add New Road
    5. Add New Intersection
    6. Toggle Display Traffic Data in Detail

Running 'bash sweep.sh' produces the following files:

**adjacency_list.txt**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Prints the adjacency list of the network<br>
**adjacency_list_in_detail.txt**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Prints the adjacency list of the network along with the intersection traffic data<br>
**best_path.txt**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Prints the best path base on the equation (distance*congestion_lvl*traffic_vol/road_cap)<br>
**best_path_in_detail.txt**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Prints the best path base on the equation (distance*congestion_lvl*traffic_vol/road_cap) with the traffic data information<br>
**high_traffic_volume_priority_list.txt**&nbsp;&nbsp;&nbsp;- Prints the list of intersections prioritising high traffic volume<br>
**shortest_path.txt**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Print the shortest path base on miles<br>
**shortest_path_in_detail.txt**&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Print the shortest path base on miles with traffic data information<br>

Within, the docs folder executing 'bash find_diff.sh' creates a 'path_difference.txt' listing the paths with different optimized/best path
from the path generated from the shortest path

## Contents

README - readme file for Final Assessment<br>
CustomTypes/CustomException/IncorrectShortestPathError.java<br>
CustomTypes/CustomException/RetrieveNoneExistingPathError.java<br>
CustomTypes/CustomException/PrioritisedTrafficControlError.java<br>
CustomTypes/CustomException/ConflictingKeyException.java<br>
CustomTypes/CustomException/EmptyListException.java<br>
CustomTypes/CustomException/NonExistentNodeLabel.java<br>
CustomTypes/DSA_GraphMatrix.java<br>
CustomTypes/DSA_HeapMax.java<br>
CustomTypes/DSA_HeapEntry.java<br>
CustomTypes/DoubleEndedLinkedList.java<br>
CustomTypes/DoublyLinkedList.java<br>
CustomTypes/HashEntry.java<br>
CustomTypes/HashMap.java<br>
CustomTypes/IDSA_Graph.java<br>
CustomTypes/ILinkedList.java<br>
CustomTypes/SingleEndedLinkedList.java<br>
TestHarness.java<br>
TrafficFlow.java<br>
TrafficData.java<br>
TrafficMenu.java<br>
TrafficNetwork.java<br>
traffic_data.txt<br>
traffic_network.txt<br>
sweep.sh

## Author

name: Mamert Vonn G. Santelices<br>
id:   90026174

## Dependencies

Linux OS<br>
openjdk 19.0.2<br>
OpenJDK Runtime Environment<br>
OpenJDK 64-Bit Server VM

## Version Information

19/01/2024 - final version of final assessment

Curtin College<br>
Bentley
