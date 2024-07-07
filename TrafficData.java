public class TrafficData {
    public final String road_intersection;
    public final float traffic_volume;
    public final float road_capacity;
    public final String congestion_level;
    public final float congestion_factor;
    public final float distance_miles;

    public TrafficData(final String intersections, 
        final float distance_ml, final float traffic_vol, 
        final float road_cap, final String congestion_lvl
    ) {
        road_intersection = intersections;
        distance_miles = distance_ml;
        traffic_volume = traffic_vol;
        road_capacity = road_cap;

        float temp = 0f;
        if(congestion_lvl.contains("Low"))
            temp = 0.5f;
        else if(congestion_lvl.contains("High"))
            temp = 2f;
        else if(congestion_lvl.contains("Medium"))
            temp = 1f;
        
        congestion_level = temp == 0f? "Medium" : congestion_lvl;
        congestion_factor = temp == 0f? 1f : temp;
    }

    public String toString() {
        return road_intersection + 
            ": {\n\tdistance: "+ distance_miles + 
            " miles,\n\ttraffic volume: "+ traffic_volume + 
            ",\n\troad capacity: " + road_capacity + 
            ",\n\tcongestion level: " + congestion_level +
            "\n}";
    }

    public float getTrafficFactor() {
        return congestion_factor*traffic_volume/road_capacity;
    }

    public float getOverallWeight() {
        return distance_miles * getTrafficFactor();
    }
}
