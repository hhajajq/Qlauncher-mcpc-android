package android.support.p000v4.view;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* renamed from: android.support.v4.view.ViewCompat */
public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    private static final long FAKE_FRAME_TIME = 10;
    static final ViewCompatImpl IMPL;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    private static final String TAG = "ViewCompat";

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$AccessibilityLiveRegion */
    private @interface AccessibilityLiveRegion {
    }

    /* renamed from: android.support.v4.view.ViewCompat$BaseViewCompatImpl */
    static class BaseViewCompatImpl implements ViewCompatImpl {
        private Method mDispatchFinishTemporaryDetach;
        private Method mDispatchStartTemporaryDetach;
        private boolean mTempDetachBound;
        WeakHashMap<View, ViewPropertyAnimatorCompat> mViewPropertyAnimatorCompatMap = null;

        BaseViewCompatImpl() {
        }

        private void bindTempDetach() {
            try {
                this.mDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
                this.mDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
            } catch (NoSuchMethodException e) {
                int e2 = Log.e("ViewCompat", "Couldn't find method", e);
            }
            this.mTempDetachBound = true;
        }

        private boolean canScrollingViewScrollHorizontally(ScrollingView scrollingView, int i) {
            ScrollingView scrollingView2 = scrollingView;
            int i2 = i;
            boolean z = true;
            int computeHorizontalScrollOffset = scrollingView2.computeHorizontalScrollOffset();
            int computeHorizontalScrollRange = scrollingView2.computeHorizontalScrollRange() - scrollingView2.computeHorizontalScrollExtent();
            if (computeHorizontalScrollRange == 0) {
                z = false;
            } else if (i2 < 0) {
                if (computeHorizontalScrollOffset <= 0) {
                    return false;
                }
            } else if (computeHorizontalScrollOffset >= computeHorizontalScrollRange - 1) {
                return false;
            }
            return z;
        }

        private boolean canScrollingViewScrollVertically(ScrollingView scrollingView, int i) {
            ScrollingView scrollingView2 = scrollingView;
            int i2 = i;
            boolean z = true;
            int computeVerticalScrollOffset = scrollingView2.computeVerticalScrollOffset();
            int computeVerticalScrollRange = scrollingView2.computeVerticalScrollRange() - scrollingView2.computeVerticalScrollExtent();
            if (computeVerticalScrollRange == 0) {
                z = false;
            } else if (i2 < 0) {
                if (computeVerticalScrollOffset <= 0) {
                    return false;
                }
            } else if (computeVerticalScrollOffset >= computeVerticalScrollRange - 1) {
                return false;
            }
            return z;
        }

        public ViewPropertyAnimatorCompat animate(View view) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat;
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat3 = new ViewPropertyAnimatorCompat(view);
            return viewPropertyAnimatorCompat2;
        }

        public boolean canScrollHorizontally(View view, int i) {
            View view2 = view;
            return (view2 instanceof ScrollingView) && canScrollingViewScrollHorizontally((ScrollingView) view2, i);
        }

        public boolean canScrollVertically(View view, int i) {
            View view2 = view;
            return (view2 instanceof ScrollingView) && canScrollingViewScrollVertically((ScrollingView) view2, i);
        }

        public int combineMeasuredStates(int i, int i2) {
            return i | i2;
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            View view2 = view;
            return windowInsetsCompat;
        }

        public void dispatchFinishTemporaryDetach(View view) {
            View view2 = view;
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchFinishTemporaryDetach != null) {
                try {
                    Object invoke = this.mDispatchFinishTemporaryDetach.invoke(view2, new Object[0]);
                } catch (Exception e) {
                    int d = Log.d("ViewCompat", "Error calling dispatchFinishTemporaryDetach", e);
                }
            } else {
                view2.onFinishTemporaryDetach();
            }
        }

        public boolean dispatchNestedFling(View view, float f, float f2, boolean z) {
            View view2 = view;
            float f3 = f;
            float f4 = f2;
            boolean z2 = z;
            if (view2 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view2).dispatchNestedFling(f3, f4, z2);
            }
            return false;
        }

        public boolean dispatchNestedPreFling(View view, float f, float f2) {
            View view2 = view;
            float f3 = f;
            float f4 = f2;
            if (view2 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view2).dispatchNestedPreFling(f3, f4);
            }
            return false;
        }

        public boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2) {
            View view2 = view;
            int i3 = i;
            int i4 = i2;
            int[] iArr3 = iArr;
            int[] iArr4 = iArr2;
            if (view2 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view2).dispatchNestedPreScroll(i3, i4, iArr3, iArr4);
            }
            return false;
        }

        public boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr) {
            View view2 = view;
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            int[] iArr2 = iArr;
            if (view2 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view2).dispatchNestedScroll(i5, i6, i7, i8, iArr2);
            }
            return false;
        }

        public void dispatchStartTemporaryDetach(View view) {
            View view2 = view;
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchStartTemporaryDetach != null) {
                try {
                    Object invoke = this.mDispatchStartTemporaryDetach.invoke(view2, new Object[0]);
                } catch (Exception e) {
                    int d = Log.d("ViewCompat", "Error calling dispatchStartTemporaryDetach", e);
                }
            } else {
                view2.onStartTemporaryDetach();
            }
        }

        public int getAccessibilityLiveRegion(View view) {
            View view2 = view;
            return 0;
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            View view2 = view;
            return null;
        }

        public float getAlpha(View view) {
            View view2 = view;
            return 1.0f;
        }

        public ColorStateList getBackgroundTintList(View view) {
            return ViewCompatBase.getBackgroundTintList(view);
        }

        public Mode getBackgroundTintMode(View view) {
            return ViewCompatBase.getBackgroundTintMode(view);
        }

        public Rect getClipBounds(View view) {
            View view2 = view;
            return null;
        }

        public float getElevation(View view) {
            View view2 = view;
            return 0.0f;
        }

        public boolean getFitsSystemWindows(View view) {
            View view2 = view;
            return false;
        }

        /* access modifiers changed from: 0000 */
        public long getFrameTime() {
            return ViewCompat.FAKE_FRAME_TIME;
        }

        public int getImportantForAccessibility(View view) {
            View view2 = view;
            return 0;
        }

        public int getLabelFor(View view) {
            View view2 = view;
            return 0;
        }

        public int getLayerType(View view) {
            View view2 = view;
            return 0;
        }

        public int getLayoutDirection(View view) {
            View view2 = view;
            return 0;
        }

        public int getMeasuredHeightAndState(View view) {
            return view.getMeasuredHeight();
        }

        public int getMeasuredState(View view) {
            View view2 = view;
            return 0;
        }

        public int getMeasuredWidthAndState(View view) {
            return view.getMeasuredWidth();
        }

        public int getMinimumHeight(View view) {
            return ViewCompatBase.getMinimumHeight(view);
        }

        public int getMinimumWidth(View view) {
            return ViewCompatBase.getMinimumWidth(view);
        }

        public int getOverScrollMode(View view) {
            View view2 = view;
            return 2;
        }

        public int getPaddingEnd(View view) {
            return view.getPaddingRight();
        }

        public int getPaddingStart(View view) {
            return view.getPaddingLeft();
        }

        public ViewParent getParentForAccessibility(View view) {
            return view.getParent();
        }

        public float getPivotX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getPivotY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getRotation(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getRotationX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getRotationY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getScaleX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getScaleY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public int getScrollIndicators(View view) {
            View view2 = view;
            return 0;
        }

        public String getTransitionName(View view) {
            View view2 = view;
            return null;
        }

        public float getTranslationX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getTranslationY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getTranslationZ(View view) {
            View view2 = view;
            return 0.0f;
        }

        public int getWindowSystemUiVisibility(View view) {
            View view2 = view;
            return 0;
        }

        public float getX(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getY(View view) {
            View view2 = view;
            return 0.0f;
        }

        public float getZ(View view) {
            View view2 = view;
            return getTranslationZ(view2) + getElevation(view2);
        }

        public boolean hasAccessibilityDelegate(View view) {
            View view2 = view;
            return false;
        }

        public boolean hasNestedScrollingParent(View view) {
            View view2 = view;
            if (view2 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view2).hasNestedScrollingParent();
            }
            return false;
        }

        public boolean hasOnClickListeners(View view) {
            View view2 = view;
            return false;
        }

        public boolean hasOverlappingRendering(View view) {
            View view2 = view;
            return true;
        }

        public boolean hasTransientState(View view) {
            View view2 = view;
            return false;
        }

        public boolean isAttachedToWindow(View view) {
            return ViewCompatBase.isAttachedToWindow(view);
        }

        public boolean isImportantForAccessibility(View view) {
            View view2 = view;
            return true;
        }

        public boolean isLaidOut(View view) {
            return ViewCompatBase.isLaidOut(view);
        }

        public boolean isNestedScrollingEnabled(View view) {
            View view2 = view;
            if (view2 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view2).isNestedScrollingEnabled();
            }
            return false;
        }

        public boolean isOpaque(View view) {
            Drawable background = view.getBackground();
            boolean z = false;
            if (background != null) {
                z = false;
                if (background.getOpacity() == -1) {
                    z = true;
                }
            }
            return z;
        }

        public boolean isPaddingRelative(View view) {
            View view2 = view;
            return false;
        }

        public void jumpDrawablesToCurrentState(View view) {
        }

        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            View view2 = view;
            return windowInsetsCompat;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            View view2 = view;
            int i2 = i;
            Bundle bundle2 = bundle;
            return false;
        }

        public void postInvalidateOnAnimation(View view) {
            view.invalidate();
        }

        public void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
            view.invalidate(i, i2, i3, i4);
        }

        public void postOnAnimation(View view, Runnable runnable) {
            boolean postDelayed = view.postDelayed(runnable, getFrameTime());
        }

        public void postOnAnimationDelayed(View view, Runnable runnable, long j) {
            boolean postDelayed = view.postDelayed(runnable, j + getFrameTime());
        }

        public void requestApplyInsets(View view) {
        }

        public int resolveSizeAndState(int i, int i2, int i3) {
            int i4 = i3;
            return View.resolveSize(i, i2);
        }

        public void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        }

        public void setAccessibilityLiveRegion(View view, int i) {
        }

        public void setActivated(View view, boolean z) {
        }

        public void setAlpha(View view, float f) {
        }

        public void setBackgroundTintList(View view, ColorStateList colorStateList) {
            ViewCompatBase.setBackgroundTintList(view, colorStateList);
        }

        public void setBackgroundTintMode(View view, Mode mode) {
            ViewCompatBase.setBackgroundTintMode(view, mode);
        }

        public void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
        }

        public void setClipBounds(View view, Rect rect) {
        }

        public void setElevation(View view, float f) {
        }

        public void setFitsSystemWindows(View view, boolean z) {
        }

        public void setHasTransientState(View view, boolean z) {
        }

        public void setImportantForAccessibility(View view, int i) {
        }

        public void setLabelFor(View view, int i) {
        }

        public void setLayerPaint(View view, Paint paint) {
        }

        public void setLayerType(View view, int i, Paint paint) {
        }

        public void setLayoutDirection(View view, int i) {
        }

        public void setNestedScrollingEnabled(View view, boolean z) {
            View view2 = view;
            boolean z2 = z;
            if (view2 instanceof NestedScrollingChild) {
                ((NestedScrollingChild) view2).setNestedScrollingEnabled(z2);
            }
        }

        public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        }

        public void setOverScrollMode(View view, int i) {
        }

        public void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
            view.setPadding(i, i2, i3, i4);
        }

        public void setPivotX(View view, float f) {
        }

        public void setPivotY(View view, float f) {
        }

        public void setRotation(View view, float f) {
        }

        public void setRotationX(View view, float f) {
        }

        public void setRotationY(View view, float f) {
        }

        public void setSaveFromParentEnabled(View view, boolean z) {
        }

        public void setScaleX(View view, float f) {
        }

        public void setScaleY(View view, float f) {
        }

        public void setScrollIndicators(View view, int i) {
        }

        public void setScrollIndicators(View view, int i, int i2) {
        }

        public void setTransitionName(View view, String str) {
        }

        public void setTranslationX(View view, float f) {
        }

        public void setTranslationY(View view, float f) {
        }

        public void setTranslationZ(View view, float f) {
        }

        public void setX(View view, float f) {
        }

        public void setY(View view, float f) {
        }

        public boolean startNestedScroll(View view, int i) {
            View view2 = view;
            int i2 = i;
            if (view2 instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view2).startNestedScroll(i2);
            }
            return false;
        }

        public void stopNestedScroll(View view) {
            View view2 = view;
            if (view2 instanceof NestedScrollingChild) {
                ((NestedScrollingChild) view2).stopNestedScroll();
            }
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$EclairMr1ViewCompatImpl */
    static class EclairMr1ViewCompatImpl extends BaseViewCompatImpl {
        EclairMr1ViewCompatImpl() {
        }

        public boolean isOpaque(View view) {
            return ViewCompatEclairMr1.isOpaque(view);
        }

        public void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
            ViewCompatEclairMr1.setChildrenDrawingOrderEnabled(viewGroup, z);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$GBViewCompatImpl */
    static class GBViewCompatImpl extends EclairMr1ViewCompatImpl {
        GBViewCompatImpl() {
        }

        public int getOverScrollMode(View view) {
            return ViewCompatGingerbread.getOverScrollMode(view);
        }

        public void setOverScrollMode(View view, int i) {
            ViewCompatGingerbread.setOverScrollMode(view, i);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$HCViewCompatImpl */
    static class HCViewCompatImpl extends GBViewCompatImpl {
        HCViewCompatImpl() {
        }

        public int combineMeasuredStates(int i, int i2) {
            return ViewCompatHC.combineMeasuredStates(i, i2);
        }

        public float getAlpha(View view) {
            return ViewCompatHC.getAlpha(view);
        }

        /* access modifiers changed from: 0000 */
        public long getFrameTime() {
            return ViewCompatHC.getFrameTime();
        }

        public int getLayerType(View view) {
            return ViewCompatHC.getLayerType(view);
        }

        public int getMeasuredHeightAndState(View view) {
            return ViewCompatHC.getMeasuredHeightAndState(view);
        }

        public int getMeasuredState(View view) {
            return ViewCompatHC.getMeasuredState(view);
        }

        public int getMeasuredWidthAndState(View view) {
            return ViewCompatHC.getMeasuredWidthAndState(view);
        }

        public float getPivotX(View view) {
            return ViewCompatHC.getPivotX(view);
        }

        public float getPivotY(View view) {
            return ViewCompatHC.getPivotY(view);
        }

        public float getRotation(View view) {
            return ViewCompatHC.getRotation(view);
        }

        public float getRotationX(View view) {
            return ViewCompatHC.getRotationX(view);
        }

        public float getRotationY(View view) {
            return ViewCompatHC.getRotationY(view);
        }

        public float getScaleX(View view) {
            return ViewCompatHC.getScaleX(view);
        }

        public float getScaleY(View view) {
            return ViewCompatHC.getScaleY(view);
        }

        public float getTranslationX(View view) {
            return ViewCompatHC.getTranslationX(view);
        }

        public float getTranslationY(View view) {
            return ViewCompatHC.getTranslationY(view);
        }

        public float getX(View view) {
            return ViewCompatHC.getX(view);
        }

        public float getY(View view) {
            return ViewCompatHC.getY(view);
        }

        public void jumpDrawablesToCurrentState(View view) {
            ViewCompatHC.jumpDrawablesToCurrentState(view);
        }

        public int resolveSizeAndState(int i, int i2, int i3) {
            return ViewCompatHC.resolveSizeAndState(i, i2, i3);
        }

        public void setActivated(View view, boolean z) {
            ViewCompatHC.setActivated(view, z);
        }

        public void setAlpha(View view, float f) {
            ViewCompatHC.setAlpha(view, f);
        }

        public void setLayerPaint(View view, Paint paint) {
            View view2 = view;
            setLayerType(view2, getLayerType(view2), paint);
            view2.invalidate();
        }

        public void setLayerType(View view, int i, Paint paint) {
            ViewCompatHC.setLayerType(view, i, paint);
        }

        public void setPivotX(View view, float f) {
            ViewCompatHC.setPivotX(view, f);
        }

        public void setPivotY(View view, float f) {
            ViewCompatHC.setPivotY(view, f);
        }

        public void setRotation(View view, float f) {
            ViewCompatHC.setRotation(view, f);
        }

        public void setRotationX(View view, float f) {
            ViewCompatHC.setRotationX(view, f);
        }

        public void setRotationY(View view, float f) {
            ViewCompatHC.setRotationY(view, f);
        }

        public void setSaveFromParentEnabled(View view, boolean z) {
            ViewCompatHC.setSaveFromParentEnabled(view, z);
        }

        public void setScaleX(View view, float f) {
            ViewCompatHC.setScaleX(view, f);
        }

        public void setScaleY(View view, float f) {
            ViewCompatHC.setScaleY(view, f);
        }

        public void setTranslationX(View view, float f) {
            ViewCompatHC.setTranslationX(view, f);
        }

        public void setTranslationY(View view, float f) {
            ViewCompatHC.setTranslationY(view, f);
        }

        public void setX(View view, float f) {
            ViewCompatHC.setX(view, f);
        }

        public void setY(View view, float f) {
            ViewCompatHC.setY(view, f);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$ICSMr1ViewCompatImpl */
    static class ICSMr1ViewCompatImpl extends ICSViewCompatImpl {
        ICSMr1ViewCompatImpl() {
        }

        public boolean hasOnClickListeners(View view) {
            return ViewCompatICSMr1.hasOnClickListeners(view);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$ICSViewCompatImpl */
    static class ICSViewCompatImpl extends HCViewCompatImpl {
        static boolean accessibilityDelegateCheckFailed = false;
        static Field mAccessibilityDelegateField;

        ICSViewCompatImpl() {
        }

        public ViewPropertyAnimatorCompat animate(View view) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
            WeakHashMap weakHashMap;
            View view2 = view;
            if (this.mViewPropertyAnimatorCompatMap == null) {
                WeakHashMap weakHashMap2 = weakHashMap;
                WeakHashMap weakHashMap3 = new WeakHashMap();
                this.mViewPropertyAnimatorCompatMap = weakHashMap2;
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = (ViewPropertyAnimatorCompat) this.mViewPropertyAnimatorCompatMap.get(view2);
            if (viewPropertyAnimatorCompat2 == null) {
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat3 = viewPropertyAnimatorCompat;
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat4 = new ViewPropertyAnimatorCompat(view2);
                viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat3;
                Object put = this.mViewPropertyAnimatorCompatMap.put(view2, viewPropertyAnimatorCompat2);
            }
            return viewPropertyAnimatorCompat2;
        }

        public boolean canScrollHorizontally(View view, int i) {
            return ViewCompatICS.canScrollHorizontally(view, i);
        }

        public boolean canScrollVertically(View view, int i) {
            return ViewCompatICS.canScrollVertically(view, i);
        }

        public boolean hasAccessibilityDelegate(View view) {
            View view2 = view;
            boolean z = true;
            if (accessibilityDelegateCheckFailed) {
                return false;
            }
            if (mAccessibilityDelegateField == null) {
                try {
                    mAccessibilityDelegateField = View.class.getDeclaredField("mAccessibilityDelegate");
                    mAccessibilityDelegateField.setAccessible(true);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    accessibilityDelegateCheckFailed = true;
                    return false;
                }
            }
            try {
                if (mAccessibilityDelegateField.get(view2) == null) {
                    z = false;
                }
                return z;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                accessibilityDelegateCheckFailed = true;
                return false;
            }
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            ViewCompatICS.onInitializeAccessibilityEvent(view, accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewCompatICS.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.getInfo());
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            ViewCompatICS.onPopulateAccessibilityEvent(view, accessibilityEvent);
        }

        public void setAccessibilityDelegate(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat) {
            AccessibilityDelegateCompat accessibilityDelegateCompat2 = accessibilityDelegateCompat;
            ViewCompatICS.setAccessibilityDelegate(view, accessibilityDelegateCompat2 == null ? null : accessibilityDelegateCompat2.getBridge());
        }

        public void setFitsSystemWindows(View view, boolean z) {
            ViewCompatICS.setFitsSystemWindows(view, z);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$ImportantForAccessibility */
    private @interface ImportantForAccessibility {
    }

    /* renamed from: android.support.v4.view.ViewCompat$JBViewCompatImpl */
    static class JBViewCompatImpl extends ICSMr1ViewCompatImpl {
        JBViewCompatImpl() {
        }

        public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
            AccessibilityNodeProviderCompat accessibilityNodeProviderCompat;
            Object accessibilityNodeProvider = ViewCompatJB.getAccessibilityNodeProvider(view);
            if (accessibilityNodeProvider == null) {
                return null;
            }
            AccessibilityNodeProviderCompat accessibilityNodeProviderCompat2 = accessibilityNodeProviderCompat;
            AccessibilityNodeProviderCompat accessibilityNodeProviderCompat3 = new AccessibilityNodeProviderCompat(accessibilityNodeProvider);
            return accessibilityNodeProviderCompat2;
        }

        public boolean getFitsSystemWindows(View view) {
            return ViewCompatJB.getFitsSystemWindows(view);
        }

        public int getImportantForAccessibility(View view) {
            return ViewCompatJB.getImportantForAccessibility(view);
        }

        public int getMinimumHeight(View view) {
            return ViewCompatJB.getMinimumHeight(view);
        }

        public int getMinimumWidth(View view) {
            return ViewCompatJB.getMinimumWidth(view);
        }

        public ViewParent getParentForAccessibility(View view) {
            return ViewCompatJB.getParentForAccessibility(view);
        }

        public boolean hasOverlappingRendering(View view) {
            return ViewCompatJB.hasOverlappingRendering(view);
        }

        public boolean hasTransientState(View view) {
            return ViewCompatJB.hasTransientState(view);
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return ViewCompatJB.performAccessibilityAction(view, i, bundle);
        }

        public void postInvalidateOnAnimation(View view) {
            ViewCompatJB.postInvalidateOnAnimation(view);
        }

        public void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
            ViewCompatJB.postInvalidateOnAnimation(view, i, i2, i3, i4);
        }

        public void postOnAnimation(View view, Runnable runnable) {
            ViewCompatJB.postOnAnimation(view, runnable);
        }

        public void postOnAnimationDelayed(View view, Runnable runnable, long j) {
            ViewCompatJB.postOnAnimationDelayed(view, runnable, j);
        }

        public void requestApplyInsets(View view) {
            ViewCompatJB.requestApplyInsets(view);
        }

        public void setHasTransientState(View view, boolean z) {
            ViewCompatJB.setHasTransientState(view, z);
        }

        public void setImportantForAccessibility(View view, int i) {
            View view2 = view;
            int i2 = i;
            if (i2 == 4) {
                i2 = 2;
            }
            ViewCompatJB.setImportantForAccessibility(view2, i2);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$JbMr1ViewCompatImpl */
    static class JbMr1ViewCompatImpl extends JBViewCompatImpl {
        JbMr1ViewCompatImpl() {
        }

        public int getLabelFor(View view) {
            return ViewCompatJellybeanMr1.getLabelFor(view);
        }

        public int getLayoutDirection(View view) {
            return ViewCompatJellybeanMr1.getLayoutDirection(view);
        }

        public int getPaddingEnd(View view) {
            return ViewCompatJellybeanMr1.getPaddingEnd(view);
        }

        public int getPaddingStart(View view) {
            return ViewCompatJellybeanMr1.getPaddingStart(view);
        }

        public int getWindowSystemUiVisibility(View view) {
            return ViewCompatJellybeanMr1.getWindowSystemUiVisibility(view);
        }

        public boolean isPaddingRelative(View view) {
            return ViewCompatJellybeanMr1.isPaddingRelative(view);
        }

        public void setLabelFor(View view, int i) {
            ViewCompatJellybeanMr1.setLabelFor(view, i);
        }

        public void setLayerPaint(View view, Paint paint) {
            ViewCompatJellybeanMr1.setLayerPaint(view, paint);
        }

        public void setLayoutDirection(View view, int i) {
            ViewCompatJellybeanMr1.setLayoutDirection(view, i);
        }

        public void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
            ViewCompatJellybeanMr1.setPaddingRelative(view, i, i2, i3, i4);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$JbMr2ViewCompatImpl */
    static class JbMr2ViewCompatImpl extends JbMr1ViewCompatImpl {
        JbMr2ViewCompatImpl() {
        }

        public Rect getClipBounds(View view) {
            return ViewCompatJellybeanMr2.getClipBounds(view);
        }

        public void setClipBounds(View view, Rect rect) {
            ViewCompatJellybeanMr2.setClipBounds(view, rect);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$KitKatViewCompatImpl */
    static class KitKatViewCompatImpl extends JbMr2ViewCompatImpl {
        KitKatViewCompatImpl() {
        }

        public int getAccessibilityLiveRegion(View view) {
            return ViewCompatKitKat.getAccessibilityLiveRegion(view);
        }

        public boolean isAttachedToWindow(View view) {
            return ViewCompatKitKat.isAttachedToWindow(view);
        }

        public boolean isLaidOut(View view) {
            return ViewCompatKitKat.isLaidOut(view);
        }

        public void setAccessibilityLiveRegion(View view, int i) {
            ViewCompatKitKat.setAccessibilityLiveRegion(view, i);
        }

        public void setImportantForAccessibility(View view, int i) {
            ViewCompatJB.setImportantForAccessibility(view, i);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$LayerType */
    private @interface LayerType {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$LayoutDirectionMode */
    private @interface LayoutDirectionMode {
    }

    /* renamed from: android.support.v4.view.ViewCompat$LollipopViewCompatImpl */
    static class LollipopViewCompatImpl extends KitKatViewCompatImpl {
        LollipopViewCompatImpl() {
        }

        public WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            return ViewCompatLollipop.dispatchApplyWindowInsets(view, windowInsetsCompat);
        }

        public boolean dispatchNestedFling(View view, float f, float f2, boolean z) {
            return ViewCompatLollipop.dispatchNestedFling(view, f, f2, z);
        }

        public boolean dispatchNestedPreFling(View view, float f, float f2) {
            return ViewCompatLollipop.dispatchNestedPreFling(view, f, f2);
        }

        public boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2) {
            return ViewCompatLollipop.dispatchNestedPreScroll(view, i, i2, iArr, iArr2);
        }

        public boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr) {
            return ViewCompatLollipop.dispatchNestedScroll(view, i, i2, i3, i4, iArr);
        }

        public ColorStateList getBackgroundTintList(View view) {
            return ViewCompatLollipop.getBackgroundTintList(view);
        }

        public Mode getBackgroundTintMode(View view) {
            return ViewCompatLollipop.getBackgroundTintMode(view);
        }

        public float getElevation(View view) {
            return ViewCompatLollipop.getElevation(view);
        }

        public String getTransitionName(View view) {
            return ViewCompatLollipop.getTransitionName(view);
        }

        public float getTranslationZ(View view) {
            return ViewCompatLollipop.getTranslationZ(view);
        }

        public float getZ(View view) {
            return ViewCompatLollipop.getZ(view);
        }

        public boolean hasNestedScrollingParent(View view) {
            return ViewCompatLollipop.hasNestedScrollingParent(view);
        }

        public boolean isImportantForAccessibility(View view) {
            return ViewCompatLollipop.isImportantForAccessibility(view);
        }

        public boolean isNestedScrollingEnabled(View view) {
            return ViewCompatLollipop.isNestedScrollingEnabled(view);
        }

        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
            return ViewCompatLollipop.onApplyWindowInsets(view, windowInsetsCompat);
        }

        public void requestApplyInsets(View view) {
            ViewCompatLollipop.requestApplyInsets(view);
        }

        public void setBackgroundTintList(View view, ColorStateList colorStateList) {
            ViewCompatLollipop.setBackgroundTintList(view, colorStateList);
        }

        public void setBackgroundTintMode(View view, Mode mode) {
            ViewCompatLollipop.setBackgroundTintMode(view, mode);
        }

        public void setElevation(View view, float f) {
            ViewCompatLollipop.setElevation(view, f);
        }

        public void setNestedScrollingEnabled(View view, boolean z) {
            ViewCompatLollipop.setNestedScrollingEnabled(view, z);
        }

        public void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            ViewCompatLollipop.setOnApplyWindowInsetsListener(view, onApplyWindowInsetsListener);
        }

        public void setTransitionName(View view, String str) {
            ViewCompatLollipop.setTransitionName(view, str);
        }

        public void setTranslationZ(View view, float f) {
            ViewCompatLollipop.setTranslationZ(view, f);
        }

        public boolean startNestedScroll(View view, int i) {
            return ViewCompatLollipop.startNestedScroll(view, i);
        }

        public void stopNestedScroll(View view) {
            ViewCompatLollipop.stopNestedScroll(view);
        }
    }

    /* renamed from: android.support.v4.view.ViewCompat$MarshmallowViewCompatImpl */
    static class MarshmallowViewCompatImpl extends LollipopViewCompatImpl {
        MarshmallowViewCompatImpl() {
        }

        public int getScrollIndicators(View view) {
            return ViewCompatMarshmallow.getScrollIndicators(view);
        }

        public void setScrollIndicators(View view, int i) {
            ViewCompatMarshmallow.setScrollIndicators(view, i);
        }

        public void setScrollIndicators(View view, int i, int i2) {
            ViewCompatMarshmallow.setScrollIndicators(view, i, i2);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$OverScroll */
    private @interface OverScroll {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$ResolvedLayoutDirectionMode */
    private @interface ResolvedLayoutDirectionMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.view.ViewCompat$ScrollIndicators */
    public @interface ScrollIndicators {
    }

    /* renamed from: android.support.v4.view.ViewCompat$ViewCompatImpl */
    interface ViewCompatImpl {
        ViewPropertyAnimatorCompat animate(View view);

        boolean canScrollHorizontally(View view, int i);

        boolean canScrollVertically(View view, int i);

        int combineMeasuredStates(int i, int i2);

        WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat);

        void dispatchFinishTemporaryDetach(View view);

        boolean dispatchNestedFling(View view, float f, float f2, boolean z);

        boolean dispatchNestedPreFling(View view, float f, float f2);

        boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2);

        boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr);

        void dispatchStartTemporaryDetach(View view);

        int getAccessibilityLiveRegion(View view);

        AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view);

        float getAlpha(View view);

        ColorStateList getBackgroundTintList(View view);

        Mode getBackgroundTintMode(View view);

        Rect getClipBounds(View view);

        float getElevation(View view);

        boolean getFitsSystemWindows(View view);

        int getImportantForAccessibility(View view);

        int getLabelFor(View view);

        int getLayerType(View view);

        int getLayoutDirection(View view);

        int getMeasuredHeightAndState(View view);

        int getMeasuredState(View view);

        int getMeasuredWidthAndState(View view);

        int getMinimumHeight(View view);

        int getMinimumWidth(View view);

        int getOverScrollMode(View view);

        int getPaddingEnd(View view);

        int getPaddingStart(View view);

        ViewParent getParentForAccessibility(View view);

        float getPivotX(View view);

        float getPivotY(View view);

        float getRotation(View view);

        float getRotationX(View view);

        float getRotationY(View view);

        float getScaleX(View view);

        float getScaleY(View view);

        int getScrollIndicators(View view);

        String getTransitionName(View view);

        float getTranslationX(View view);

        float getTranslationY(View view);

        float getTranslationZ(View view);

        int getWindowSystemUiVisibility(View view);

        float getX(View view);

        float getY(View view);

        float getZ(View view);

        boolean hasAccessibilityDelegate(View view);

        boolean hasNestedScrollingParent(View view);

        boolean hasOnClickListeners(View view);

        boolean hasOverlappingRendering(View view);

        boolean hasTransientState(View view);

        boolean isAttachedToWindow(View view);

        boolean isImportantForAccessibility(View view);

        boolean isLaidOut(View view);

        boolean isNestedScrollingEnabled(View view);

        boolean isOpaque(View view);

        boolean isPaddingRelative(View view);

        void jumpDrawablesToCurrentState(View view);

        WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat);

        void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

        void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent);

        boolean performAccessibilityAction(View view, int i, Bundle bundle);

        void postInvalidateOnAnimation(View view);

        void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4);

        void postOnAnimation(View view, Runnable runnable);

        void postOnAnimationDelayed(View view, Runnable runnable, long j);

        void requestApplyInsets(View view);

        int resolveSizeAndState(int i, int i2, int i3);

        void setAccessibilityDelegate(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat);

        void setAccessibilityLiveRegion(View view, int i);

        void setActivated(View view, boolean z);

        void setAlpha(View view, float f);

        void setBackgroundTintList(View view, ColorStateList colorStateList);

        void setBackgroundTintMode(View view, Mode mode);

        void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z);

        void setClipBounds(View view, Rect rect);

        void setElevation(View view, float f);

        void setFitsSystemWindows(View view, boolean z);

        void setHasTransientState(View view, boolean z);

        void setImportantForAccessibility(View view, int i);

        void setLabelFor(View view, int i);

        void setLayerPaint(View view, Paint paint);

        void setLayerType(View view, int i, Paint paint);

        void setLayoutDirection(View view, int i);

        void setNestedScrollingEnabled(View view, boolean z);

        void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener);

        void setOverScrollMode(View view, int i);

        void setPaddingRelative(View view, int i, int i2, int i3, int i4);

        void setPivotX(View view, float f);

        void setPivotY(View view, float f);

        void setRotation(View view, float f);

        void setRotationX(View view, float f);

        void setRotationY(View view, float f);

        void setSaveFromParentEnabled(View view, boolean z);

        void setScaleX(View view, float f);

        void setScaleY(View view, float f);

        void setScrollIndicators(View view, int i);

        void setScrollIndicators(View view, int i, int i2);

        void setTransitionName(View view, String str);

        void setTranslationX(View view, float f);

        void setTranslationY(View view, float f);

        void setTranslationZ(View view, float f);

        void setX(View view, float f);

        void setY(View view, float f);

        boolean startNestedScroll(View view, int i);

        void stopNestedScroll(View view);
    }

    static {
        BaseViewCompatImpl baseViewCompatImpl;
        EclairMr1ViewCompatImpl eclairMr1ViewCompatImpl;
        GBViewCompatImpl gBViewCompatImpl;
        HCViewCompatImpl hCViewCompatImpl;
        ICSViewCompatImpl iCSViewCompatImpl;
        ICSMr1ViewCompatImpl iCSMr1ViewCompatImpl;
        JBViewCompatImpl jBViewCompatImpl;
        JbMr1ViewCompatImpl jbMr1ViewCompatImpl;
        KitKatViewCompatImpl kitKatViewCompatImpl;
        LollipopViewCompatImpl lollipopViewCompatImpl;
        MarshmallowViewCompatImpl marshmallowViewCompatImpl;
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            MarshmallowViewCompatImpl marshmallowViewCompatImpl2 = marshmallowViewCompatImpl;
            MarshmallowViewCompatImpl marshmallowViewCompatImpl3 = new MarshmallowViewCompatImpl();
            IMPL = marshmallowViewCompatImpl2;
        } else if (i >= 21) {
            LollipopViewCompatImpl lollipopViewCompatImpl2 = lollipopViewCompatImpl;
            LollipopViewCompatImpl lollipopViewCompatImpl3 = new LollipopViewCompatImpl();
            IMPL = lollipopViewCompatImpl2;
        } else if (i >= 19) {
            KitKatViewCompatImpl kitKatViewCompatImpl2 = kitKatViewCompatImpl;
            KitKatViewCompatImpl kitKatViewCompatImpl3 = new KitKatViewCompatImpl();
            IMPL = kitKatViewCompatImpl2;
        } else if (i >= 17) {
            JbMr1ViewCompatImpl jbMr1ViewCompatImpl2 = jbMr1ViewCompatImpl;
            JbMr1ViewCompatImpl jbMr1ViewCompatImpl3 = new JbMr1ViewCompatImpl();
            IMPL = jbMr1ViewCompatImpl2;
        } else if (i >= 16) {
            JBViewCompatImpl jBViewCompatImpl2 = jBViewCompatImpl;
            JBViewCompatImpl jBViewCompatImpl3 = new JBViewCompatImpl();
            IMPL = jBViewCompatImpl2;
        } else if (i >= 15) {
            ICSMr1ViewCompatImpl iCSMr1ViewCompatImpl2 = iCSMr1ViewCompatImpl;
            ICSMr1ViewCompatImpl iCSMr1ViewCompatImpl3 = new ICSMr1ViewCompatImpl();
            IMPL = iCSMr1ViewCompatImpl2;
        } else if (i >= 14) {
            ICSViewCompatImpl iCSViewCompatImpl2 = iCSViewCompatImpl;
            ICSViewCompatImpl iCSViewCompatImpl3 = new ICSViewCompatImpl();
            IMPL = iCSViewCompatImpl2;
        } else if (i >= 11) {
            HCViewCompatImpl hCViewCompatImpl2 = hCViewCompatImpl;
            HCViewCompatImpl hCViewCompatImpl3 = new HCViewCompatImpl();
            IMPL = hCViewCompatImpl2;
        } else if (i >= 9) {
            GBViewCompatImpl gBViewCompatImpl2 = gBViewCompatImpl;
            GBViewCompatImpl gBViewCompatImpl3 = new GBViewCompatImpl();
            IMPL = gBViewCompatImpl2;
        } else if (i >= 7) {
            EclairMr1ViewCompatImpl eclairMr1ViewCompatImpl2 = eclairMr1ViewCompatImpl;
            EclairMr1ViewCompatImpl eclairMr1ViewCompatImpl3 = new EclairMr1ViewCompatImpl();
            IMPL = eclairMr1ViewCompatImpl2;
        } else {
            BaseViewCompatImpl baseViewCompatImpl2 = baseViewCompatImpl;
            BaseViewCompatImpl baseViewCompatImpl3 = new BaseViewCompatImpl();
            IMPL = baseViewCompatImpl2;
        }
    }

    public ViewCompat() {
    }

    public static ViewPropertyAnimatorCompat animate(View view) {
        return IMPL.animate(view);
    }

    public static boolean canScrollHorizontally(View view, int i) {
        return IMPL.canScrollHorizontally(view, i);
    }

    public static boolean canScrollVertically(View view, int i) {
        return IMPL.canScrollVertically(view, i);
    }

    public static int combineMeasuredStates(int i, int i2) {
        return IMPL.combineMeasuredStates(i, i2);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return IMPL.dispatchApplyWindowInsets(view, windowInsetsCompat);
    }

    public static void dispatchFinishTemporaryDetach(View view) {
        IMPL.dispatchFinishTemporaryDetach(view);
    }

    public static boolean dispatchNestedFling(View view, float f, float f2, boolean z) {
        return IMPL.dispatchNestedFling(view, f, f2, z);
    }

    public static boolean dispatchNestedPreFling(View view, float f, float f2) {
        return IMPL.dispatchNestedPreFling(view, f, f2);
    }

    public static boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2) {
        return IMPL.dispatchNestedPreScroll(view, i, i2, iArr, iArr2);
    }

    public static boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr) {
        return IMPL.dispatchNestedScroll(view, i, i2, i3, i4, iArr);
    }

    public static void dispatchStartTemporaryDetach(View view) {
        IMPL.dispatchStartTemporaryDetach(view);
    }

    public static int getAccessibilityLiveRegion(View view) {
        return IMPL.getAccessibilityLiveRegion(view);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return IMPL.getAccessibilityNodeProvider(view);
    }

    public static float getAlpha(View view) {
        return IMPL.getAlpha(view);
    }

    public static ColorStateList getBackgroundTintList(View view) {
        return IMPL.getBackgroundTintList(view);
    }

    public static Mode getBackgroundTintMode(View view) {
        return IMPL.getBackgroundTintMode(view);
    }

    public static Rect getClipBounds(View view) {
        return IMPL.getClipBounds(view);
    }

    public static float getElevation(View view) {
        return IMPL.getElevation(view);
    }

    public static boolean getFitsSystemWindows(View view) {
        return IMPL.getFitsSystemWindows(view);
    }

    public static int getImportantForAccessibility(View view) {
        return IMPL.getImportantForAccessibility(view);
    }

    public static int getLabelFor(View view) {
        return IMPL.getLabelFor(view);
    }

    public static int getLayerType(View view) {
        return IMPL.getLayerType(view);
    }

    public static int getLayoutDirection(View view) {
        return IMPL.getLayoutDirection(view);
    }

    public static int getMeasuredHeightAndState(View view) {
        return IMPL.getMeasuredHeightAndState(view);
    }

    public static int getMeasuredState(View view) {
        return IMPL.getMeasuredState(view);
    }

    public static int getMeasuredWidthAndState(View view) {
        return IMPL.getMeasuredWidthAndState(view);
    }

    public static int getMinimumHeight(View view) {
        return IMPL.getMinimumHeight(view);
    }

    public static int getMinimumWidth(View view) {
        return IMPL.getMinimumWidth(view);
    }

    public static int getOverScrollMode(View view) {
        return IMPL.getOverScrollMode(view);
    }

    public static int getPaddingEnd(View view) {
        return IMPL.getPaddingEnd(view);
    }

    public static int getPaddingStart(View view) {
        return IMPL.getPaddingStart(view);
    }

    public static ViewParent getParentForAccessibility(View view) {
        return IMPL.getParentForAccessibility(view);
    }

    public static float getPivotX(View view) {
        return IMPL.getPivotX(view);
    }

    public static float getPivotY(View view) {
        return IMPL.getPivotY(view);
    }

    public static float getRotation(View view) {
        return IMPL.getRotation(view);
    }

    public static float getRotationX(View view) {
        return IMPL.getRotationX(view);
    }

    public static float getRotationY(View view) {
        return IMPL.getRotationY(view);
    }

    public static float getScaleX(View view) {
        return IMPL.getScaleX(view);
    }

    public static float getScaleY(View view) {
        return IMPL.getScaleY(view);
    }

    public static int getScrollIndicators(@NonNull View view) {
        return IMPL.getScrollIndicators(view);
    }

    public static String getTransitionName(View view) {
        return IMPL.getTransitionName(view);
    }

    public static float getTranslationX(View view) {
        return IMPL.getTranslationX(view);
    }

    public static float getTranslationY(View view) {
        return IMPL.getTranslationY(view);
    }

    public static float getTranslationZ(View view) {
        return IMPL.getTranslationZ(view);
    }

    public static int getWindowSystemUiVisibility(View view) {
        return IMPL.getWindowSystemUiVisibility(view);
    }

    public static float getX(View view) {
        return IMPL.getX(view);
    }

    public static float getY(View view) {
        return IMPL.getY(view);
    }

    public static float getZ(View view) {
        return IMPL.getZ(view);
    }

    public static boolean hasAccessibilityDelegate(View view) {
        return IMPL.hasAccessibilityDelegate(view);
    }

    public static boolean hasNestedScrollingParent(View view) {
        return IMPL.hasNestedScrollingParent(view);
    }

    public static boolean hasOnClickListeners(View view) {
        return IMPL.hasOnClickListeners(view);
    }

    public static boolean hasOverlappingRendering(View view) {
        return IMPL.hasOverlappingRendering(view);
    }

    public static boolean hasTransientState(View view) {
        return IMPL.hasTransientState(view);
    }

    public static boolean isAttachedToWindow(View view) {
        return IMPL.isAttachedToWindow(view);
    }

    public static boolean isLaidOut(View view) {
        return IMPL.isLaidOut(view);
    }

    public static boolean isNestedScrollingEnabled(View view) {
        return IMPL.isNestedScrollingEnabled(view);
    }

    public static boolean isOpaque(View view) {
        return IMPL.isOpaque(view);
    }

    public static boolean isPaddingRelative(View view) {
        return IMPL.isPaddingRelative(view);
    }

    public static void jumpDrawablesToCurrentState(View view) {
        IMPL.jumpDrawablesToCurrentState(view);
    }

    public static void offsetLeftAndRight(View view, int i) {
        View view2 = view;
        int i2 = i;
        view2.offsetLeftAndRight(i2);
        if (i2 != 0 && VERSION.SDK_INT < 11) {
            view2.invalidate();
        }
    }

    public static void offsetTopAndBottom(View view, int i) {
        View view2 = view;
        int i2 = i;
        view2.offsetTopAndBottom(i2);
        if (i2 != 0 && VERSION.SDK_INT < 11) {
            view2.invalidate();
        }
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return IMPL.onApplyWindowInsets(view, windowInsetsCompat);
    }

    public static void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public static void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        IMPL.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
    }

    public static void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public static boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return IMPL.performAccessibilityAction(view, i, bundle);
    }

    public static void postInvalidateOnAnimation(View view) {
        IMPL.postInvalidateOnAnimation(view);
    }

    public static void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
        IMPL.postInvalidateOnAnimation(view, i, i2, i3, i4);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        IMPL.postOnAnimation(view, runnable);
    }

    public static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
        IMPL.postOnAnimationDelayed(view, runnable, j);
    }

    public static void requestApplyInsets(View view) {
        IMPL.requestApplyInsets(view);
    }

    public static int resolveSizeAndState(int i, int i2, int i3) {
        return IMPL.resolveSizeAndState(i, i2, i3);
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        IMPL.setAccessibilityDelegate(view, accessibilityDelegateCompat);
    }

    public static void setAccessibilityLiveRegion(View view, int i) {
        IMPL.setAccessibilityLiveRegion(view, i);
    }

    public static void setActivated(View view, boolean z) {
        IMPL.setActivated(view, z);
    }

    public static void setAlpha(View view, @FloatRange(from = 0.0d, mo15to = 1.0d) float f) {
        IMPL.setAlpha(view, f);
    }

    public static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        IMPL.setBackgroundTintList(view, colorStateList);
    }

    public static void setBackgroundTintMode(View view, Mode mode) {
        IMPL.setBackgroundTintMode(view, mode);
    }

    public static void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
        IMPL.setChildrenDrawingOrderEnabled(viewGroup, z);
    }

    public static void setClipBounds(View view, Rect rect) {
        IMPL.setClipBounds(view, rect);
    }

    public static void setElevation(View view, float f) {
        IMPL.setElevation(view, f);
    }

    public static void setFitsSystemWindows(View view, boolean z) {
        IMPL.setFitsSystemWindows(view, z);
    }

    public static void setHasTransientState(View view, boolean z) {
        IMPL.setHasTransientState(view, z);
    }

    public static void setImportantForAccessibility(View view, int i) {
        IMPL.setImportantForAccessibility(view, i);
    }

    public static void setLabelFor(View view, @IdRes int i) {
        IMPL.setLabelFor(view, i);
    }

    public static void setLayerPaint(View view, Paint paint) {
        IMPL.setLayerPaint(view, paint);
    }

    public static void setLayerType(View view, int i, Paint paint) {
        IMPL.setLayerType(view, i, paint);
    }

    public static void setLayoutDirection(View view, int i) {
        IMPL.setLayoutDirection(view, i);
    }

    public static void setNestedScrollingEnabled(View view, boolean z) {
        IMPL.setNestedScrollingEnabled(view, z);
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        IMPL.setOnApplyWindowInsetsListener(view, onApplyWindowInsetsListener);
    }

    public static void setOverScrollMode(View view, int i) {
        IMPL.setOverScrollMode(view, i);
    }

    public static void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
        IMPL.setPaddingRelative(view, i, i2, i3, i4);
    }

    public static void setPivotX(View view, float f) {
        IMPL.setPivotX(view, f);
    }

    public static void setPivotY(View view, float f) {
        IMPL.setPivotY(view, f);
    }

    public static void setRotation(View view, float f) {
        IMPL.setRotation(view, f);
    }

    public static void setRotationX(View view, float f) {
        IMPL.setRotationX(view, f);
    }

    public static void setRotationY(View view, float f) {
        IMPL.setRotationY(view, f);
    }

    public static void setSaveFromParentEnabled(View view, boolean z) {
        IMPL.setSaveFromParentEnabled(view, z);
    }

    public static void setScaleX(View view, float f) {
        IMPL.setScaleX(view, f);
    }

    public static void setScaleY(View view, float f) {
        IMPL.setScaleY(view, f);
    }

    public static void setScrollIndicators(@NonNull View view, int i) {
        IMPL.setScrollIndicators(view, i);
    }

    public static void setScrollIndicators(@NonNull View view, int i, int i2) {
        IMPL.setScrollIndicators(view, i, i2);
    }

    public static void setTransitionName(View view, String str) {
        IMPL.setTransitionName(view, str);
    }

    public static void setTranslationX(View view, float f) {
        IMPL.setTranslationX(view, f);
    }

    public static void setTranslationY(View view, float f) {
        IMPL.setTranslationY(view, f);
    }

    public static void setTranslationZ(View view, float f) {
        IMPL.setTranslationZ(view, f);
    }

    public static void setX(View view, float f) {
        IMPL.setX(view, f);
    }

    public static void setY(View view, float f) {
        IMPL.setY(view, f);
    }

    public static boolean startNestedScroll(View view, int i) {
        return IMPL.startNestedScroll(view, i);
    }

    public static void stopNestedScroll(View view) {
        IMPL.stopNestedScroll(view);
    }
}