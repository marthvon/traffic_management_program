import java.util.Scanner;
import java.util.function.Function;

import CustomTypes.CustomException.ConflictingKeyException;
import CustomTypes.CustomException.NonExistentNodeLabel;

// Task 3: Dynamic Traffic Management
public class TrafficMenu {
    private static final int DISPLAY_TRAFFIC_NETWORK = 1;
    private static final int FIND_OPTIMIZED_TRAFFIC_FLOW = 2;
    private static final int DISPLAY_PRIORITISED_INTERSECTIONS = 3;
    private static final int ADD_NEW_ROAD = 4;
    private static final int ADD_NEW_INTERSECTION = 5;
    private static final int TOGGLE_DETAIL = 6;
    private static final int EXIT = 7;
    
    private static final int WEIGHTED_BY_OVERALL_WEIGHT = 1;
    private static final int WEIGHTED_BY_TRAFFIC_FACTOR = 2;
    private static final int WEIGHTED_BY_DISTANCE = 3;
    private static final int WEIGHTED_BY_TRAFFIC_VOLUME = 4;
    
    public static void main(String[] args) {
        Scanner inStream = new Scanner(System.in);
        try { 
            TrafficNetwork net = new TrafficNetwork();
            boolean inDetail = false;
            System.out.println("=============== Traffic Management Program ===============");
            while(true) {
            System.out.print(
                "\n\t1) Display Traffic Network Adjacency List\n"+
                "\t2) Find Path from Two Locations\n"+
                "\t3) Display Intersection in order of Traffic Volume\n"+
                "\t4) Add New Road\n"+
                "\t5) Add New Intersection\n"+
                (inDetail? "\t6) Toggle Display Minimal Detail\n" : "\t6) Toggle Display Traffic Data in Detail\n")+
                "\t7) Exit\n"+
                "Enter option: "
            );
            switch (inStream.nextInt()) {
                case DISPLAY_TRAFFIC_NETWORK: {
                    if(inDetail)
                        net.displayAsListInDetail();
                    else
                        net.displayAsList();
                } break;
                case FIND_OPTIMIZED_TRAFFIC_FLOW: {
                    while(true) { try {
                        inStream.nextLine();
                        System.out.print("Enter Starting Road Name: ");
                        final String from = inStream.nextLine();
                        System.out.print("Enter Destination's Road Name: ");
                        final String to = inStream.nextLine();

                        System.out.print(
                            "Select which Weight to Apply When Finding a Path:"+
                            "\n\t1) distance*congestion_lvl*traffic_vol/road_cap"+
                            "\n\t2) congestion_lvl*traffic_vol/road_cap"+
                            "\n\t3) distance\n\t4) traffic volume\nEnter option: "
                        );
                        Function<TrafficData, Float> getter;
                        switch (inStream.nextInt()) {
                            case WEIGHTED_BY_OVERALL_WEIGHT:
                                getter = parameter -> parameter.getOverallWeight();
                                break;
                            case WEIGHTED_BY_TRAFFIC_FACTOR:
                                getter = parameter -> parameter.getTrafficFactor();
                                break;
                            case WEIGHTED_BY_DISTANCE:
                                getter = parameter -> parameter.distance_miles;
                                break;
                            case WEIGHTED_BY_TRAFFIC_VOLUME:
                                getter = parameter -> parameter.traffic_volume;
                                break;
                            default:
                                throw new Exception("Invalid Option Entered... Try Again");
                        }
                        System.out.print("Display All Possible Paths [Y/N]: ");
                        if(inStream.next().charAt(0) == 'Y') {
                            TrafficFlow[] temp = net.DFSTrafficNetworkAllPath(from, to, getter);
                            if(temp.length > 1) {
                                System.out.println("=============== A L L  P A T H ===============");
                                for(TrafficFlow other: temp)
                                    System.out.println(inDetail? (other+"\n") : other.inBrief());
                            }
                        }
                        System.out.println("============== B E S T  P A T H ==============");
                        System.out.println(inDetail? net.BFSTrafficNetwork(from, to, getter) : net.BFSTrafficNetwork(from, to, getter).inBrief());
                        break;
                    } catch(NonExistentNodeLabel e) {
                        System.err.println(e.getMessage());
                    }}
                } break;
                case DISPLAY_PRIORITISED_INTERSECTIONS: {
                    System.out.println("\nDisplaying Traffic Network Prioritised Intersection Based on Traffic Volume\n");
                    for(TrafficData data: net.PrioritiseTrafficVolume())
                        System.out.println(inDetail? data : data.road_intersection);
                } break;
                case ADD_NEW_ROAD: {
                    inStream.nextLine();
                    System.out.print("Enter Road Name: ");
                    net.addNewRoad(inStream.nextLine());
                } break;
                case ADD_NEW_INTERSECTION: {
                    inStream.nextLine();
                    while(true) { try {
                        System.out.print("Enter Road Name: ");
                        final String road1 = inStream.nextLine();
                        System.out.print("Enter Another Road Name: ");
                        final String road2 = inStream.nextLine();
                        System.out.print("Enter Distance between Intersection: ");
                        final float dist = inStream.nextFloat();
                        System.out.print("Enter Traffic Volume between Intersection: ");
                        final int traf_vol = inStream.nextInt();
                        System.out.print("Enter Road Capacity between Intersection: ");
                        final int road_cap = inStream.nextInt();
                        System.out.print("Enter Congestion Level (Low/<default>Medium/High): ");
                        final String cong_lvl = inStream.next();
                        net.addNewIntersection(road1, road2, dist, traf_vol, road_cap, cong_lvl);
                        break;
                    } catch(ConflictingKeyException | NonExistentNodeLabel e) {
                        System.err.println(e.getMessage());
                    }}
                } break;
                case TOGGLE_DETAIL:
                    inDetail = !inDetail;
                    break;
                case EXIT:
                    inStream.close();
                return;        
            }
        }} catch(Exception e) {
            System.err.println(e.getMessage());
        }
        inStream.close();
    }
}
