package com.mapfinal.animation;

/**
 * An estimator for the movements of a pointer based on a polynomial model.
 * 基于多项式模型的指针运动估计器
 *
 * The last recorded position of the pointer is at time zero seconds.
 * 指针的最后记录位置是在零时秒
 * Past estimated positions are at negative times and future estimated positions
 * are at positive times.
 * 过去的估计位置处于负时区，而未来的估计位置处于正时区
 *
 * First coefficient is position (in pixels), second is velocity (in pixels per second),
 * third is acceleration (in pixels per second squared).
 * 第一个系数是位置(以像素为单位)
 * 第二是速度(以像素/秒为单位)
 * 第三是加速度(单位是像素/秒的平方)
 *
 * @hide For internal use only.  Not a final API.
 * @author yangyong
 */
public class Estimator {
	// Must match VelocityTracker::Estimator::MAX_DEGREE
    private static final int MAX_DEGREE = 4;

    /**
     * Polynomial coefficients describing motion in X.
     * 描述X中运动的多项式系数
     */
    public final float[] xCoeff = new float[MAX_DEGREE + 1];

    /**
     * Polynomial coefficients describing motion in Y.
     * 描述Y中运动的多项式系数
     */
    public final float[] yCoeff = new float[MAX_DEGREE + 1];

    /**
     * Polynomial degree, or zero if only position information is available.
     * 多项式次，或零，如果只提供位置信息
     */
    public int degree;

    /**
     * Confidence (coefficient of determination), between 0 (no fit) and 1 (perfect fit).
     * 判定系数 0 - 1 (不吻合 和 全吻合)
     */
    public float confidence;

    /**
     * Gets an estimate of the X position of the pointer at the specified time point.
     * 获取指针在指定时间点处的X位置的估计值
     * @param time The time point in seconds, 0 is the last recorded time.
     *             时间点以秒为单位，0是最后记录的时间
     * @return The estimated X coordinate.估计的X坐标
     */
    public float estimateX(float time) {
        return estimate(time, xCoeff);
    }

    /**
     * Gets an estimate of the Y position of the pointer at the specified time point.
     * @param time The time point in seconds, 0 is the last recorded time.
     * @return The estimated Y coordinate.
     */
    public float estimateY(float time) {
        return estimate(time, yCoeff);
    }

    /**
     * Gets the X coefficient with the specified index.
     * 获取具有指定索引的X系数
     * @param index The index of the coefficient to return.
     *              返回index 的 系数
     * @return The X coefficient, or 0 if the index is greater than the degree.
     *         X系数，如果index大于度，则为0
     */
    public float getXCoeff(int index) {
        return index <= degree ? xCoeff[index] : 0;
    }

    /**
     * Gets the Y coefficient with the specified index.
     * @param index The index of the coefficient to return.
     * @return The Y coefficient, or 0 if the index is greater than the degree.
     */
    public float getYCoeff(int index) {
        return index <= degree ? yCoeff[index] : 0;
    }

    //估计值的算法
    private float estimate(float time, float[] c) {
        float a = 0;
        float scale = 1;
        for (int i = 0; i <= degree; i++) {
            a += c[i] * scale;
            scale *= time;
        }
        return a;
    }
}
