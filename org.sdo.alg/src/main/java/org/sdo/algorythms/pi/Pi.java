package org.sdo.algorythms.pi;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 1/23/13
 * Time: 9:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pi {

    public static Double pi(double precision) {
        int chunks = calcChunksCountForPrecision(precision);
        return pi(chunks);
    }

    public static double pi(int chunksCount) {
        double pi = 0.0;
        for (int currentChunk = 0; currentChunk < chunksCount; currentChunk++) {
            pi +=  squareOfChunk(chunksCount, currentChunk);
        }
        return pi;
    }

    private static double squareOfChunk(int stepSize, int chunk) {
        double x = offset(stepSize(stepSize), chunk) + halfOfStep(stepSize);
        double y = circleFunction(x);
        return adjustedRectangleSquare(stepSize(stepSize), y);
    }

    private static double halfOfStep(int n) {
        return stepSize(n) / 2;
    }

    private static double adjustedRectangleSquare(double delta_x, double y) {
        return delta_x * y * 4;
    }

    private static double circleFunction(double x) {
        return Math.sqrt(1 - Math.pow(x, 2));
    }

    private static double calcStepSize(int n) {
        return 1.0 / n;
    }

    private static double offset(double stepSize, int n) {
        return n * stepSize;
    }

    private static int calcChunksCountForPrecision(double precision) {
        return calcChunksForPrecisionInternal(1, precision);
    }

    private static int calcChunksForPrecisionInternal(int chunks, double precision) {
        double adjustedSquare = squareOfChunk(chunks, penultimate(chunks));
        if (adjustedSquare <= precision) {
            return chunks;
        } else {
            return calcChunksForPrecisionInternal(chunks + 1, precision);
        }
    }

    private static int penultimate(int chunks) {
        return chunks - 1;
    }

    private static double stepSize(int chunks) {
        return calcStepSize(chunks);
    }
}
