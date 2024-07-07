import java.util.Arrays;

public class TrafficFlow {
    public final TrafficData[] flow;
    public final float total_weight;    

    public TrafficFlow() {
        flow = null;
        total_weight = -1;
    }
    public TrafficFlow(final float weight) {
        flow = new TrafficData[0];
        total_weight = weight;
    }
    public TrafficFlow(final TrafficData[] traffic_flow, final float weight) {
        flow = traffic_flow;
        total_weight = weight;
    }

    public String toString() {
        return Arrays.toString(flow) + "\nTotal Weight: " + total_weight;
    }

    public String inBrief() {
        String[] res = new String[flow.length];
        int i = 0;
        for(TrafficData data: flow) {
            res[i] = data.road_intersection;
            ++i;
        }
        return Arrays.toString(res) + " Total Weight: " + total_weight;
    }
}
