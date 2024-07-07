
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Function; 
import java.util.Arrays;

import CustomTypes.DSA_GraphMatrix;
import CustomTypes.DSA_HeapMax;
import CustomTypes.DoubleEndedLinkedList;
import CustomTypes.HashEntry;
import CustomTypes.HashMap;
import CustomTypes.SingleEndedLinkedList;
import CustomTypes.CustomException.ConflictingKeyException;
import CustomTypes.CustomException.EmptyListException;
import CustomTypes.CustomException.NonExistentNodeLabel;

public class TrafficNetwork {

    private final DSA_GraphMatrix traffic_graph = new DSA_GraphMatrix(); // Task 1: Constructing a Traffic Network
    private final HashMap traffic_data = new HashMap(); // Task 4: Efficient Data Handling
    private final DSA_HeapMax priority_queue  = new DSA_HeapMax(); // Task 5: Prioritized Traffic Control

    public TrafficNetwork() throws FileNotFoundException, ConflictingKeyException, NonExistentNodeLabel {
        HashMap temp = new HashMap();
        boolean is_header;

        // Open Traffic Data
        Scanner fStream = new Scanner(new File("traffic_data.txt"));
        fStream.useDelimiter(", ");
        // Skip Headers
        do {
            String line = fStream.nextLine();
            if(line.length() == 0)
                break;
            is_header = line.charAt(0) == '#';
        } while(is_header);
        // Store Intersection data to traffic node
        while(fStream.hasNextLine())
            temp.put(fStream.next(), fStream.nextLine());
        fStream.close();
        
        // Open Traffic Network Layout
        fStream = new Scanner(new File("traffic_network.txt"));
        fStream.useDelimiter(", |\\n");
        // Skip Headers
        do {
            String line = fStream.nextLine();
            if(line.length() == 0)
                break;
            is_header = line.charAt(0) == '#';
        } while(is_header);
        
        // Add Nodes and Weighted Edges
        while(fStream.hasNextLine()) {
            final String road1 = fStream.next();
            final String road2 = fStream.next();
            
            if(!traffic_graph.hasVertex(road1))
                traffic_graph.addNode(road1);
            if(!traffic_graph.hasVertex(road2))
                traffic_graph.addNode(road2);
            traffic_graph.addEdge(road1, road2);
            
            String intersection_name = road1+'-'+road2;  
            HashEntry raw_args = temp.get(intersection_name);
            if(raw_args == null) {
                intersection_name = road2+'-'+road1;
                if((raw_args = temp.get(intersection_name)) == null) {
                    fStream.close();
                    throw new NonExistentNodeLabel(intersection_name+"\"or\""+road1+'-'+road2, "TrafficNetwork");
                }
            }
            
            Scanner args = new Scanner((String)raw_args.getValue());
            args.useDelimiter(", "); 
            args.skip("\\s*,\\s*");
            // Create TrafficData object
            final TrafficData node = new TrafficData(intersection_name, Float.parseFloat(fStream.next()), args.nextFloat(), args.nextFloat(), args.next());
            traffic_data.put(intersection_name, node);
            priority_queue.add(node.traffic_volume, node.road_intersection);
            args.close();
        }
        fStream.close();
    }

    public boolean isAdjacent(final String source, final String dest) throws NonExistentNodeLabel {
        return Arrays.asList(traffic_graph.getAdjacent(source)).contains(dest);
    }

    public void addNewRoad(final String road_name) throws ConflictingKeyException {
        traffic_graph.addNode(road_name);
    }

    public void addNewIntersection(final String to, final String from, 
        final float distance_miles, final int traffic_vol, 
        final int road_cap, final String congestion_lvl
    ) throws ConflictingKeyException, NonExistentNodeLabel {

        if(traffic_data.get(to+"-"+from) != null)
            throw new ConflictingKeyException(from+"-"+to);
        final String intersection_name = from+"-"+to;
        final TrafficData node = new TrafficData(intersection_name, distance_miles, traffic_vol, road_cap, congestion_lvl);
        traffic_data.put(intersection_name, node);
        traffic_graph.addEdge(from, to);
        priority_queue.add(node.traffic_volume, intersection_name);
    }

    public void displayAsList() {
        traffic_graph.displayAsList();
    }

    public void displayAsListInDetail() {
        try { for(String road_name: traffic_graph.getAllNodeLabels()) {
            System.out.println(road_name + " =>");
            for(String adjacent_road: traffic_graph.getAdjacent(road_name)) {
                System.out.println("\t>>>\t"+ geTrafficData(adjacent_road, road_name));
            }
            System.out.println("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- |>");
        }} catch(NonExistentNodeLabel e) {
            System.err.println(e.getMessage());
        }
    }

    public TrafficData[] PrioritiseTrafficVolume() {
        TrafficData[] res = new TrafficData[priority_queue.getSize()];
        DSA_HeapMax heap = new DSA_HeapMax(priority_queue);
        int i = 0;
        while(heap.getSize() != 0) {
            res[i] = (TrafficData)(traffic_data.get((String)(heap.remove().getValue())).getValue());
            ++i;
        }
        return res;
    }

    private TrafficData geTrafficData(final String to, final String from) {
        HashEntry val = traffic_data.get(to+"-"+from);
        if(val == null)
            if((val = traffic_data.get(from+"-"+to)) == null)
                return null;
        return (TrafficData)val.getValue();
    }

    public final String[] GetAllRoadNames() {
        return traffic_graph.getAllNodeLabels();
    }
    /* START Task 2: Traffic Flow Optimization */
    private TrafficFlow RecurseDFSTrafficNetwork(final String curr, final String dest, float accum_weight, HashMap visited, SingleEndedLinkedList list, final Function<TrafficData, Float> getWeight) throws NonExistentNodeLabel {
        TrafficFlow out = null;
        // pre build traffic flow and mark visited nodes
        try { visited.put(curr, null); } catch(ConflictingKeyException e) {return out;}
        list.insertFirst(curr);

        // as long as current node is not the destination keep recurring but avoid already visited node
        if(!(curr.contentEquals(dest))) {
            for(String adjacent : traffic_graph.getAdjacent(curr)) {
                if (visited.get(adjacent) != null)
                    continue;
                TrafficFlow temp = RecurseDFSTrafficNetwork(adjacent, dest, accum_weight + getWeight.apply(geTrafficData(curr, adjacent)), visited, list, getWeight);
                if(temp != null && (out == null || temp.total_weight < out.total_weight)) 
                    out = temp;
            }
        } 
        /* when dest node is found we assign the new output traffic flow but if an existing route already exist, we compare which route has a smaller weight  */
        else if(out == null || accum_weight < out.total_weight) { 
            TrafficData[] data = new TrafficData[list.size()-1];
            int i = list.size()-2;
            String prev_node = null;
            for(Object node_label: list.toArray()) {
                if(prev_node != null) {
                    data[i] = geTrafficData(prev_node, (String)node_label);
                    --i;
                }
                prev_node = (String)node_label;
            }
            out = new TrafficFlow(data, accum_weight);
        }

        // unmark visited and destroy built path for the purpose of checking for alternate path
        visited.remove(curr);
        try{ list.removeFirst(); } catch(EmptyListException e) { System.err.println(e.getMessage()); }
        return out;
    }

    public TrafficFlow DFSTrafficNetwork(final String from, final String to, Function<TrafficData, Float> getWeightCallback) throws NonExistentNodeLabel {
        if(from == to)
            return new TrafficFlow(0);
        TrafficFlow out = RecurseDFSTrafficNetwork(from, to, 0, new HashMap(), new SingleEndedLinkedList(), getWeightCallback);
        return out == null? new TrafficFlow(): out;
    }

    private void RecurseDFSTrafficNetworkAll(final String curr, final String dest, float accum_weight, HashMap visited, SingleEndedLinkedList list, SingleEndedLinkedList results, final Function<TrafficData, Float> getWeight) throws  NonExistentNodeLabel {
        try { visited.put(curr, null); } catch(ConflictingKeyException e) {return;}
        list.insertFirst(curr);

        // as long as current node is not the destination keep recurring but avoid already visited node
        if(!(curr.contentEquals(dest))) {
            for(String adjacent : traffic_graph.getAdjacent(curr))
                if (visited.get(adjacent) == null)
                    RecurseDFSTrafficNetworkAll(adjacent, dest, accum_weight + getWeight.apply(geTrafficData(curr, adjacent)), visited, list, results, getWeight);
        } else { 
            TrafficData[] data = new TrafficData[list.size()-1];
            int i = list.size()-2;
            String prev_node = null;
            for(Object node_label: list.toArray()) {
                if(prev_node != null) {
                    data[i] = geTrafficData(prev_node, (String)node_label);
                    --i;
                }
                prev_node = (String)node_label;
            }
            results.insertFirst(new TrafficFlow(data, accum_weight));
        }

        // unmark visited and destroy built path for the purpose of checking for alternate path
        visited.remove(curr);
        try{ list.removeFirst(); } catch(EmptyListException e) { System.err.println(e.getMessage()); }
    }

    public TrafficFlow[] DFSTrafficNetworkAllPath(final String from, final String to, Function<TrafficData, Float> getWeightCallback) throws ConflictingKeyException, NonExistentNodeLabel {
        SingleEndedLinkedList list = new SingleEndedLinkedList();
        TrafficFlow[] out = null;
        if(from == to) {
            out = new TrafficFlow[1];
            out[0] = new TrafficFlow(0);
            return out;
        }
        RecurseDFSTrafficNetworkAll(from, to, 0, new HashMap(), new SingleEndedLinkedList(), list, getWeightCallback);
        out = new TrafficFlow[list.size()];
        int i = 0;
        try { 
            while(!list.isEmpty()) {
                out[i] = (TrafficFlow)list.popFirst();
                ++i;
            }
        } catch(EmptyListException e) {
            System.err.println(e.getMessage());
        }
        return out;
    }
    /* END Task 2: Traffic Flow Optimization */
    /* START Task 6: Advanced Traffic Optimization */
    private class DijkstraData {
        public final float distance;
        public final String prev_node;
        public final TrafficData data;
        public DijkstraData(final float p_distance, final String p_prev_node, final TrafficData p_data) {
            distance = p_distance;
            prev_node = p_prev_node;
            data = p_data;
        }
    }

    private HashMap CreateBFSCacheNetwork(final String source, Function<TrafficData, Float> getWeight) throws ConflictingKeyException, NonExistentNodeLabel {
        HashMap visited = new HashMap();
        DoubleEndedLinkedList list = new DoubleEndedLinkedList();

        list.insertLast(source);
        visited.put(source, new DijkstraData(0, "", null));

        try { while(!list.isEmpty()) {
            String start = (String)list.popFirst();
            
            float accum_dist = 0;            
            for(String temp = start; !temp.contentEquals(source);) {
                DijkstraData data = (DijkstraData)visited.get(temp).getValue();
                accum_dist += data.distance;
                temp = data.prev_node;
            }

            for(String adjacent: traffic_graph.getAdjacent(start)) {
                TrafficData data = geTrafficData(start, adjacent);
                HashEntry entry = visited.get(adjacent);
                
                if(entry == null) { 
                    visited.put(adjacent, new DijkstraData(getWeight.apply(data), start, data));
                    list.insertLast(adjacent);
                } else {
                    float other_dist = 0;
                    for(String temp = adjacent; !temp.contentEquals(source);) {
                        DijkstraData other = (DijkstraData)visited.get(temp).getValue();
                        other_dist += other.distance;
                        temp = other.prev_node;
                    }
                    
                    if((getWeight.apply(data) + accum_dist) < other_dist) {
                        entry.setValue(new DijkstraData(getWeight.apply(data), start, data)); // follow the prev node path until reach a pointthat the new weight is greater than the other prev node
                        list.insertLast(adjacent);
                    }
                }
            }
        }} catch(EmptyListException e) {
            System.err.println(e.getMessage());
        }
        
        return visited;
    }

    private TrafficFlow _BFSTrafficNetwork(HashMap visited, final String from, final String to)  {
        // if dest is not connected to source return default TrafficFlow {TrafficData: TrafficData[0], float: 0} 
        if(from == to)
            return new TrafficFlow(0);
        
        // if dest is not connected to source return default TrafficFlow {TrafficData: null, float: -1}
        HashEntry to_entry = visited.get(to); 
        if(to_entry == null)
            return new TrafficFlow();
        
        float accum_dist = 0;
        SingleEndedLinkedList list = new SingleEndedLinkedList();
        DijkstraData dijkstra = (DijkstraData)to_entry.getValue();
        while(true) {
            list.insertFirst(dijkstra.data);
            accum_dist += dijkstra.distance;
            if(dijkstra.prev_node == from)
                break;
            dijkstra = (DijkstraData)visited.get(dijkstra.prev_node).getValue();
        }

        TrafficData[] flow = new TrafficData[list.size()];
        for(int i = 0; !list.isEmpty(); ++i) try {
            flow[i] = (TrafficData)list.popFirst();
        } catch(EmptyListException e) {
            System.err.println(e.getMessage());
        }
        
        return new TrafficFlow(flow, accum_dist);
    }

    public TrafficFlow[] BFSTrafficNetwork(final String from, final String[] to, Function<TrafficData, Float> getWeightCallback) throws NonExistentNodeLabel, ConflictingKeyException {
        HashMap bfsCache = CreateBFSCacheNetwork(from, getWeightCallback);
        TrafficFlow[] res = new TrafficFlow[to.length];
        
        int i = 0;
        for(String dest: to) {
            res[i] = _BFSTrafficNetwork(bfsCache, from, dest);
            ++i;
        }
        return res;
    }

    public TrafficFlow BFSTrafficNetwork(final String from, final String to, Function<TrafficData, Float> getWeightCallback) throws NonExistentNodeLabel, ConflictingKeyException {
        if(from == to)
            return new TrafficFlow(0);
        return _BFSTrafficNetwork(CreateBFSCacheNetwork(from, getWeightCallback), from, to);
    }
    /* END Task 6: Advanced Traffic Optimization */
}
