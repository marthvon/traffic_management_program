import java.io.FileNotFoundException;
import java.util.function.Function;

import CustomTypes.CustomException.ConflictingKeyException;
import CustomTypes.CustomException.IncorrectShortestPathError;
import CustomTypes.CustomException.NonExistentNodeLabel;
import CustomTypes.CustomException.PrioritisedTrafficControlError;
import CustomTypes.CustomException.RetrieveNoneExistingPathError;

// Task 7: Program Implementation and Testing
public class TestHarness {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println(
                "Test Harness Manual\nOptions:\n"+
                "\t-q\tQuiet. Doesn't display stdout to console but only the stderr\n"+
                "\t-d\tDisplay Road Intersection Information in Detail, otherwise print minimally\n"+
                "\t-l\tPrint out the Traffic Network Adjacency List\n"+
                "\t-p\tPrint sorted Traffic Data Intersections from Highest to Lowest Traffic Volume\n"+
                "\t-s<... optional>\tPrint the Best Path between Source and Destination\n"+ 
                "\t<default>\tBest Path is based on the intersection Overall Weight\n"+
                "\t\tt\tBest Path is based on the intersection Traffic Factor\n"+
                "\t\tm\tBest Path is based on the intersection Distance in Miles\n"+
                "\t\tv\tBest Path is based on the intersection Traffic Volume\n"+
                "\t-a\tWhen '-s' is flagged, it also prints other non optimal paths\n"
                );
            return;
        }
        boolean detailedPrint = false;
        boolean printNetwork = false;
        char findBestPath = '\0';
        boolean printAllPath = false;
        boolean printPrioritised = false;
        boolean quiet = false;
        for(final String arg: args) {
            if(arg.charAt(0) != '-')
                continue;
            switch (arg.charAt(1)) {
                case 'q':
                    quiet = true;
                case 'd':
                    detailedPrint = true;
                    break;
                case 'l':
                    printNetwork = true;
                    break;
                case 'p':
                    printPrioritised = true;
                    break;
                case 'a':
                    printAllPath = true;
                    break;
                case 's':
                    if(arg.length() < 3)
                        findBestPath = ' ';
                    else
                        findBestPath = arg.charAt(2);
                    break;
            }
        }

        try {
            TrafficNetwork net = new TrafficNetwork();

            if(printNetwork) {
                if(!quiet)
                    System.out.println("\nDisplaying Traffic Network Graph as Adjacent List\n");
                if(detailedPrint) 
                    net.displayAsListInDetail();
                else 
                    net.displayAsList();
            }

            if(printPrioritised) {
                System.out.println("\nDisplaying Traffic Network Prioritised Intersection Based on Traffic Volume\n");
                float prev_vol = 4294967295f;
                String prev_intersection = null;
                for(TrafficData data: net.PrioritiseTrafficVolume()) {
                    if(!quiet)
                        System.out.println((detailedPrint? data : (data.road_intersection + " => " + data.traffic_volume)));
                    if(prev_vol < data.traffic_volume)
                        throw new PrioritisedTrafficControlError(prev_intersection, prev_vol, data.road_intersection, data.traffic_volume);
                    prev_vol = data.traffic_volume;
                    prev_intersection = data.road_intersection;
                }
            }

            if(findBestPath == '\0')
                return;
                
            Function<TrafficData, Float> getter = null;
            switch (findBestPath) {
                case ' ':
                    if(!quiet)
                        System.out.println("\nDisplaying Best Path base on Weighted formula (distance*congestion_lvl*traffic_vol/road_cap) from Source to Destination\n");
                    getter = parameter -> parameter.getOverallWeight();
                    break;
                case 't':
                    if(!quiet)
                        System.out.println("\nDisplaying Best Path base on Traffic factor (congestion_lvl*traffic_vol/road_cap) from Source to Destination\n");
                    getter = parameter -> parameter.getTrafficFactor();
                    break;
                case 'm':
                    if(!quiet)
                        System.out.println("\nDisplaying Shortest Distance from Source to Destination\n");
                    getter = parameter -> parameter.distance_miles;
                    break;
                case 'v':
                    if(!quiet)
                        System.out.println("\nDisplaying Least Traffic Volume from Source to Destination\n");
                    getter = parameter -> parameter.traffic_volume;
                    break;
            }       

            final String[] roads = net.GetAllRoadNames();
            for(final String source : roads) {
                TrafficFlow[] flow = net.BFSTrafficNetwork(source, roads, getter);
                for(int i = 0; i < roads.length; ++i) {
                    if(source == roads[i])
                        continue;
                    if(!quiet)
                        System.out.println("\n==============================================\nFROM\t" + source + "\tTO\t" + roads[i] + "\n");
                    if(printAllPath) {
                        TrafficFlow[] temp = net.DFSTrafficNetworkAllPath(source, roads[i], getter);
                        if(temp.length > 1) {
                            if(!quiet)
                                System.out.println("=============== A L L  P A T H ===============");
                            for(TrafficFlow other: temp) {
                                if(!quiet)
                                    System.out.println(detailedPrint? (other+"\n") : other.inBrief());
                                for(TrafficData data : other.flow) {
                                    String[] road_names = data.road_intersection.split("-");
                                    if(!net.isAdjacent(road_names[0], road_names[1]))
                                        throw new RetrieveNoneExistingPathError(other.inBrief(), data.toString());
                                }
                                if(other.total_weight < (flow[i].total_weight - 0.00001))
                                    throw new IncorrectShortestPathError(flow[i].inBrief(), other.inBrief());
                            }
                        }
                        if(!quiet)
                            System.out.println("============== B E S T  P A T H ==============");
                    }
                    if(!quiet) {
                        System.out.println(detailedPrint? flow[i] : flow[i].inBrief());
                        System.out.println("==============================================");
                    }
                }
            }
        } catch(PrioritisedTrafficControlError | IncorrectShortestPathError | RetrieveNoneExistingPathError | FileNotFoundException | NonExistentNodeLabel | ConflictingKeyException e) {
            System.err.println(e.getMessage());
        }
    }
}
