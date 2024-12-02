package it.creeper.roman.math;

public class Mathemathics {
    //TODO: Mathematics expressions. needed for reach checks
    public float deviation(float[] data) {
        float sum = 0.0f, mid, result = 0.0f;
        for(float d : data) {
            sum =+ d;
        }
        mid = sum / data.length;
        result = mid / data.length;
        return result;
    }
}
