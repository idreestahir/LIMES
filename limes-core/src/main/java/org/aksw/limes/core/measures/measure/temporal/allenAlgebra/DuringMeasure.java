package org.aksw.limes.core.measures.measure.temporal.allenAlgebra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aksw.limes.core.io.cache.Instance;
import org.aksw.limes.core.measures.measure.temporal.TemporalMeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the temporal during measure class.
 *
 * @author Kleanthi Georgala (georgala@informatik.uni-leipzig.de)
 * @version 1.0
 */
public class DuringMeasure extends TemporalMeasure {
    private static final Logger logger = LoggerFactory.getLogger(DuringMeasure.class.getName());

    // EE1 \ (BB0 U BB1)
    /**
     * {@inheritDoc}
     */
    @Override
    public double getSimilarity(Object object1, Object object2) {
        double sim = 0;
        String split1[] = ((String) object1).split("\\|");
        String split2[] = ((String) object2).split("\\|");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date1, date2;
        long beginDate1 = 0, endDate1 = 0, beginDate2 = 0, endDate2 = 0;
        try {
            date1 = df.parse(split1[0]);
            beginDate1 = date1.getTime();
        } catch (ParseException e) {
            System.err.println("Exiting..");
        }
        try {
            date1 = df.parse(split1[1]);
            endDate1 = date1.getTime();
        } catch (ParseException e) {
            System.err.println("Exiting..");
        } //////////////////
        try {
            date2 = df.parse(split2[0]);
            beginDate2 = date2.getTime();
        } catch (ParseException e) {
            System.err.println("Exiting..");
        }
        try {
            date2 = df.parse(split2[1]);
            endDate2 = date2.getTime();
        } catch (ParseException e) {
            System.err.println("Exiting..");
        }
        if ((endDate1 < endDate2) && (beginDate1 > beginDate2))
            sim = 1;
        else
            sim = 0;

        return sim;
    }

    /**
     * Returns the similarity between two instances given their begin and end
     * dates. If the first instance has a end date lower than the end date of
     * the second instance and its begin date is higher than the begin date of
     * the second instance, then their similarity is 1, and 0 otherwise.
     * 
     *
     * @return The similarity of the instances
     */
    @Override
    public double getSimilarity(Instance instance1, Instance instance2, String property1, String property2) {
        String beginDate1 = this.getFirstProperty(property1);
        String beginDate2 = this.getFirstProperty(property2);

        String endDate1 = null;
        try {
            endDate1 = this.getSecondProperty(property1);
        } catch (IllegalArgumentException e) {
            logger.error(
                    "During measure requires both begin and end date of the event. End date property is missing. Exiting..");
            System.exit(1);
        }

        String endDate2 = null;
        try {
            endDate2 = this.getSecondProperty(property2);
        } catch (IllegalArgumentException e) {
            logger.error(
                    "During measure requires both begin and end date of the event. End date property is missing. Exiting..");
            System.exit(1);
        }
        String s1 = new String(
                instance1.getProperty(beginDate1).first() + "|" + instance1.getProperty(endDate1).first());
        String s2 = new String(
                instance2.getProperty(beginDate2).first() + "|" + instance2.getProperty(endDate2).first());

        return this.getSimilarity(s1, s2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getRuntimeApproximation(double mappingSize) {
        return mappingSize / 1000d;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "During";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "temporal";

    }

}