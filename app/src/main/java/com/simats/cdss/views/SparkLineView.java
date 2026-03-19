package com.simats.cdss.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * A lightweight sparkline chart view for drawing ABG trend lines.
 * Draws a smooth line with gradient fill, data points, optional target range band,
 * and time labels below the chart.
 */
public class SparkLineView extends View {

    private List<Float> dataPoints = new ArrayList<>();
    private List<String> timeLabels = new ArrayList<>();
    private int lineColor = Color.parseColor("#EF4444");
    private int pointColor = Color.parseColor("#EF4444");
    private float targetMin = -1f;
    private float targetMax = -1f;

    private Paint linePaint;
    private Paint fillPaint;
    private Paint pointPaint;
    private Paint pointStrokePaint;
    private Paint targetBandPaint;
    private Paint targetLinePaint;
    private Paint gridPaint;
    private Paint timeLabelPaint;
    private Path linePath;
    private Path fillPath;

    private float padLeft = 8f;
    private float padRight = 8f;
    private float padTop = 20f;
    private float padBottom = 28f; // Extra space for time labels

    public SparkLineView(Context context) {
        super(context);
        init();
    }

    public SparkLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SparkLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        float density = getResources().getDisplayMetrics().density;
        padLeft *= density;
        padRight *= density;
        padTop *= density;
        padBottom *= density;

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2.5f * density);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setStyle(Paint.Style.FILL);

        pointStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointStrokePaint.setStyle(Paint.Style.STROKE);
        pointStrokePaint.setStrokeWidth(2f * density);
        pointStrokePaint.setColor(Color.WHITE);

        targetBandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetBandPaint.setStyle(Paint.Style.FILL);
        targetBandPaint.setColor(Color.parseColor("#10139487"));

        targetLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetLinePaint.setStyle(Paint.Style.STROKE);
        targetLinePaint.setStrokeWidth(1f * density);
        targetLinePaint.setColor(Color.parseColor("#40139487"));
        targetLinePaint.setPathEffect(new DashPathEffect(new float[]{6 * density, 4 * density}, 0));

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(0.5f * density);
        gridPaint.setColor(Color.parseColor("#15000000"));

        timeLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timeLabelPaint.setTextSize(10f * density);
        timeLabelPaint.setColor(Color.parseColor("#94A3B8"));
        timeLabelPaint.setTextAlign(Paint.Align.CENTER);

        linePath = new Path();
        fillPath = new Path();
    }

    public void setData(List<Float> points, int color) {
        this.dataPoints = points != null ? points : new ArrayList<>();
        this.lineColor = color;
        this.pointColor = color;
        linePaint.setColor(color);
        pointPaint.setColor(color);
        invalidate();
    }

    public void setData(List<Float> points, int color, List<String> labels) {
        this.dataPoints = points != null ? points : new ArrayList<>();
        this.timeLabels = labels != null ? labels : new ArrayList<>();
        this.lineColor = color;
        this.pointColor = color;
        linePaint.setColor(color);
        pointPaint.setColor(color);
        invalidate();
    }

    public void setTimeLabels(List<String> labels) {
        this.timeLabels = labels != null ? labels : new ArrayList<>();
        invalidate();
    }

    public void setTargetRange(float min, float max) {
        this.targetMin = min;
        this.targetMax = max;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataPoints == null || dataPoints.size() < 2) return;

        float w = getWidth() - padLeft - padRight;
        float h = getHeight() - padTop - padBottom;

        // Calculate min/max with padding
        float dataMin = Float.MAX_VALUE;
        float dataMax = Float.MIN_VALUE;
        for (float val : dataPoints) {
            dataMin = Math.min(dataMin, val);
            dataMax = Math.max(dataMax, val);
        }
        // Include target range in min/max calculation
        if (targetMin >= 0) dataMin = Math.min(dataMin, targetMin);
        if (targetMax >= 0) dataMax = Math.max(dataMax, targetMax);

        float range = dataMax - dataMin;
        if (range == 0) range = 1f;
        // Add 15% padding to range
        float paddedMin = dataMin - range * 0.15f;
        float paddedMax = dataMax + range * 0.15f;
        float paddedRange = paddedMax - paddedMin;

        // Draw horizontal grid lines
        for (int i = 0; i <= 3; i++) {
            float y = padTop + h - (h * i / 3f);
            canvas.drawLine(padLeft, y, padLeft + w, y, gridPaint);
        }

        // Draw target range band
        if (targetMin >= 0 && targetMax >= 0) {
            float yMin = padTop + h - ((targetMin - paddedMin) / paddedRange * h);
            float yMax = padTop + h - ((targetMax - paddedMin) / paddedRange * h);
            canvas.drawRect(padLeft, yMax, padLeft + w, yMin, targetBandPaint);
            canvas.drawLine(padLeft, yMin, padLeft + w, yMin, targetLinePaint);
            canvas.drawLine(padLeft, yMax, padLeft + w, yMax, targetLinePaint);
        }

        // Calculate points
        float[] xPoints = new float[dataPoints.size()];
        float[] yPoints = new float[dataPoints.size()];
        float step = w / (dataPoints.size() - 1);

        for (int i = 0; i < dataPoints.size(); i++) {
            xPoints[i] = padLeft + i * step;
            yPoints[i] = padTop + h - ((dataPoints.get(i) - paddedMin) / paddedRange * h);
        }

        // Draw smooth line path
        linePath.reset();
        fillPath.reset();
        linePath.moveTo(xPoints[0], yPoints[0]);
        fillPath.moveTo(xPoints[0], padTop + h);
        fillPath.lineTo(xPoints[0], yPoints[0]);

        for (int i = 1; i < dataPoints.size(); i++) {
            float cx = (xPoints[i - 1] + xPoints[i]) / 2f;
            linePath.cubicTo(cx, yPoints[i - 1], cx, yPoints[i], xPoints[i], yPoints[i]);
            fillPath.cubicTo(cx, yPoints[i - 1], cx, yPoints[i], xPoints[i], yPoints[i]);
        }

        fillPath.lineTo(xPoints[dataPoints.size() - 1], padTop + h);
        fillPath.close();

        // Draw gradient fill
        int fillColorStart = Color.argb(40, Color.red(lineColor), Color.green(lineColor), Color.blue(lineColor));
        int fillColorEnd = Color.argb(5, Color.red(lineColor), Color.green(lineColor), Color.blue(lineColor));
        fillPaint.setShader(new LinearGradient(0, padTop, 0, padTop + h,
                fillColorStart, fillColorEnd, Shader.TileMode.CLAMP));
        canvas.drawPath(fillPath, fillPaint);

        // Draw line
        canvas.drawPath(linePath, linePaint);

        // Draw data points and time labels
        float density = getResources().getDisplayMetrics().density;
        float outerRadius = 5f * density;
        for (int i = 0; i < dataPoints.size(); i++) {
            // Outer colored circle
            canvas.drawCircle(xPoints[i], yPoints[i], outerRadius, pointPaint);
            // White inner ring for the stroke effect
            canvas.drawCircle(xPoints[i], yPoints[i], outerRadius, pointStrokePaint);
            // Last point gets a larger highlight
            if (i == dataPoints.size() - 1) {
                Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                glowPaint.setColor(Color.argb(30, Color.red(lineColor), Color.green(lineColor), Color.blue(lineColor)));
                canvas.drawCircle(xPoints[i], yPoints[i], outerRadius * 2, glowPaint);
            }

            // Draw time labels below chart
            if (timeLabels != null && i < timeLabels.size() && timeLabels.get(i) != null) {
                float labelY = padTop + h + (18f * density);
                canvas.drawText(timeLabels.get(i), xPoints[i], labelY, timeLabelPaint);
            }
        }
    }
}
