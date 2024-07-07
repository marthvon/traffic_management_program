package CustomTypes.CustomException;

public class PrioritisedTrafficControlError extends Exception {
    public PrioritisedTrafficControlError(final String intersection1, final float traffic_vol1, final String intersection2, final float traffic_vol2) {
        super("Priority Queue of Arranging Highest Traffic Volume Intersection is Incorrectly Ordered\n"+
            intersection1 + " with a traffic volume of " + traffic_vol1 + " is placed higher than\n" +
            intersection2 + " with a traffic volume of " + traffic_vol2
        );
    }
}
