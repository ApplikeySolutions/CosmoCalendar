package com.applikeysolutions.cosmocalendar.view.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.selection.SelectionState;
import com.applikeysolutions.cosmocalendar.utils.CalendarUtils;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

public class CircleAnimationTextView extends AppCompatTextView {

    private SelectionState selectionState;
    private CalendarView calendarView;

    private int animationProgress;
    private boolean clearView;
    private boolean stateChanged;

    //Circle
    private Paint circlePaint;
    private Paint circleUnderPaint;
    private Day day;
    private int circleColor;

    //Variable to fix bugs when cannot start animation during scroll/fast scroll
    //seems like animation can't be done on views that are not visible on screen
    private boolean animationStarted;
    private long animationStartTime;

    //Start/End range half rectangle
    private Paint rectanglePaint;
    private Rect rectangle;

    //Rectangle
    private Paint backgroundRectanglePaint;
    private Rect backgroundRectangle;

    public static final int DEFAULT_PADDING = 10;
    public static final int MAX_PROGRESS = 100;
    public static final long SELECTION_ANIMATION_DURATION = 300;

    public CircleAnimationTextView(Context context) {
        super(context);
    }

    public CircleAnimationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleAnimationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Square view
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            //For making all day views same height (ex. screen width 1080 and we have days with width 154/154/155/154/154/155/154)
            super.onMeasure(widthMeasureSpec, CalendarUtils.getCircleWidth(getContext()) + MeasureSpec.EXACTLY);
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (clearView) {
            clearVariables();
        }

        if (selectionState != null) {
            switch (selectionState) {
                case START_RANGE_DAY:
                case END_RANGE_DAY:
                    drawRectangle(canvas);
                    drawCircleUnder(canvas);
                    drawCircle(canvas);
                    break;

                case START_RANGE_DAY_WITHOUT_END:
                    drawCircle(canvas);
                    break;

                case SINGLE_DAY:
                    //Animation not started yet
                    //progress not MAX_PROGRESS
                    boolean condition1 = !animationStarted
                            && animationProgress != MAX_PROGRESS;

                    //Animation started
                    //but was terminated (more than SELECTION_ANIMATION_DURATION have passed from animationStartTime)
                    //progress not MAX_PROGRESS
                    long currentTime = System.currentTimeMillis();
                    boolean condition2 = animationStarted
                            && (currentTime > animationStartTime + SELECTION_ANIMATION_DURATION)
                            && animationProgress != MAX_PROGRESS;

                    if (condition1 || condition2) {
                        animateView();
                    } else {
                        drawCircle(canvas);
                    }
                    break;

                case RANGE_DAY:
                    drawBackgroundRectangle(canvas);
                    break;
            }
        }
        super.draw(canvas);
    }

    private void drawCircle(Canvas canvas) {
        if (animationProgress == 100) {
            if (day != null) {
                day.setSelectionCircleDrawed(true);
            }
        }
        if (circlePaint == null || stateChanged) {
            createCirclePaint();
        }

        final int diameter = getWidth() - DEFAULT_PADDING * 2;
        final int diameterProgress = animationProgress * diameter / MAX_PROGRESS;

        setBackgroundColor(Color.TRANSPARENT);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, diameterProgress / 2, circlePaint);
    }

    private void drawCircleUnder(Canvas canvas) {
        if (circleUnderPaint == null || stateChanged) {
            createCircleUnderPaint();
        }
        final int diameter = getWidth() - DEFAULT_PADDING * 2;
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, diameter / 2, circleUnderPaint);
    }

    private void createCirclePaint() {
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void createCircleUnderPaint() {
        circleUnderPaint = new Paint();
        circleUnderPaint.setColor(calendarView.getSelectedDayBackgroundColor());
        circleUnderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void drawRectangle(Canvas canvas) {
        if (rectanglePaint == null) {
            createRectanglePaint();
        }
        if (rectangle == null) {
            rectangle = getRectangleForState();
        }
        canvas.drawRect(rectangle, rectanglePaint);
    }

    private void createRectanglePaint() {
        rectanglePaint = new Paint();
        rectanglePaint.setColor(calendarView.getSelectedDayBackgroundColor());
        rectanglePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void drawBackgroundRectangle(Canvas canvas) {
        if (backgroundRectanglePaint == null) {
            createBackgroundRectanglePaint();
        }
        if (backgroundRectangle == null) {
            backgroundRectangle = getRectangleForState();
        }
        canvas.drawRect(backgroundRectangle, backgroundRectanglePaint);
    }

    private void createBackgroundRectanglePaint() {
        backgroundRectanglePaint = new Paint();
        backgroundRectanglePaint.setColor(calendarView.getSelectedDayBackgroundColor());
        backgroundRectanglePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public SelectionState getSelectionState() {
        return selectionState;
    }

    public void setSelectionStateAndAnimate(SelectionState state, CalendarView calendarView, Day day) {
        isStateChanged(state);
        selectionState = state;
        this.calendarView = calendarView;
        day.setSelectionState(state);
        this.day = day;

        if (selectionState != null && calendarView != null) {
            switch (selectionState) {
                case START_RANGE_DAY:
                    circleColor = calendarView.getSelectedDayBackgroundStartColor();
                    break;

                case END_RANGE_DAY:
                    circleColor = calendarView.getSelectedDayBackgroundEndColor();
                    break;

                case START_RANGE_DAY_WITHOUT_END:
                    setBackgroundColor(Color.TRANSPARENT);
                    circleColor = calendarView.getSelectedDayBackgroundStartColor();
                    break;

                case SINGLE_DAY:
                    circleColor = calendarView.getSelectedDayBackgroundColor();
                    setBackgroundColor(Color.TRANSPARENT);
                    break;
            }
        }
        animateView();
    }

    private Rect getRectangleForState() {
        switch (selectionState) {
            case START_RANGE_DAY:
                return new Rect(getWidth() / 2, DEFAULT_PADDING, getWidth(), getHeight() - DEFAULT_PADDING);

            case END_RANGE_DAY:
                return new Rect(0, DEFAULT_PADDING, getWidth() / 2, getHeight() - DEFAULT_PADDING);

            case RANGE_DAY:
                return new Rect(0, DEFAULT_PADDING, getWidth(), getHeight() - DEFAULT_PADDING);

            default:
                return null;
        }
    }

    private void animateView() {
        CircularFillAnimation animation = new CircularFillAnimation();
//        animation.setInterpolator(new BounceInterpolator()); //just for fun
        animation.setDuration(SELECTION_ANIMATION_DURATION);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationStarted = true;
                animationStartTime = System.currentTimeMillis();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationStarted = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);
        invalidate();
    }

    private void isStateChanged(SelectionState newState) {
        stateChanged = (selectionState == null || selectionState != newState);
    }

    public void setAnimationProgress(int animationProgress) {
        this.animationProgress = animationProgress;
    }

    public void clearView() {
        if (selectionState != null) {
            clearView = true;
            invalidate();
        }
    }

    private void clearVariables() {
        selectionState = null;
        calendarView = null;
        circlePaint = null;
        rectanglePaint = null;
        rectangle = null;

        stateChanged = false;

        circleColor = 0;

        animationProgress = 0;
        animationStarted = false;
        animationStartTime = 0;

        setBackgroundColor(Color.TRANSPARENT);

        clearView = false;
    }

    public void showAsCircle(int circleColor) {
        this.circleColor = circleColor;
        animationProgress = 100;
        setWidth(CalendarUtils.getCircleWidth(getContext()));
        setHeight(CalendarUtils.getCircleWidth(getContext()));
        requestLayout();
    }

    public void showAsSingleCircle(CalendarView calendarView) {
        clearVariables();
        selectionState = SelectionState.SINGLE_DAY;
        showAsCircle(calendarView.getSelectedDayBackgroundColor());
    }

    public void showAsStartCircle(CalendarView calendarView, boolean animate) {
        if (animate) {
            clearVariables();
        }
        this.calendarView = calendarView;
        selectionState = SelectionState.START_RANGE_DAY;
        showAsCircle(calendarView.getSelectedDayBackgroundStartColor());
    }

    public void showAsStartCircleWithouEnd(CalendarView calendarView, boolean animate) {
        if (animate) {
            clearVariables();
        }
        this.calendarView = calendarView;
        selectionState = SelectionState.START_RANGE_DAY_WITHOUT_END;
        showAsCircle(calendarView.getSelectedDayBackgroundStartColor());
    }

    public void showAsEndCircle(CalendarView calendarView, boolean animate) {
        if (animate) {
            clearVariables();
        }
        this.calendarView = calendarView;
        selectionState = SelectionState.END_RANGE_DAY;
        showAsCircle(calendarView.getSelectedDayBackgroundEndColor());
    }

    public void showAsRange(CalendarView calendarView) {
        clearVariables();
        this.calendarView = calendarView;
        selectionState = SelectionState.RANGE_DAY;
        setWidth(CalendarUtils.getCircleWidth(getContext()) / 2);
        setHeight(CalendarUtils.getCircleWidth(getContext()));
        requestLayout();
    }

    //Animation
    class CircularFillAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            int progress = (int) (interpolatedTime * 100);
            CircleAnimationTextView.this.setAnimationProgress(progress);
            CircleAnimationTextView.this.requestLayout();
        }
    }
}
