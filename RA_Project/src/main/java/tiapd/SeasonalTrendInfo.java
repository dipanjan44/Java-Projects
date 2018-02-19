package tiapd;

/**
 * Seasonal Trend Information.
 *
 * @author Ken Baclawski
 * @version 1.0 05/20/2017
 */
public class SeasonalTrendInfo {
    /** The raw intensity of a stack segment. */
    private short rawIntensity;

    /** The smoothed intensity of a stack segment. */
    private double smoothedIntensity;

    /** The smoothed growth rate of a stack segment. */
    private double smoothedIntensityGrowthRate;

    /** The normalized residual of a stack segment. */
    private double normalizedResidual;
}
