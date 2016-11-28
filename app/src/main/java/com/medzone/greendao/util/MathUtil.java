package com.medzone.greendao.util;

import android.util.Range;
import android.util.Rational;

/**
 * Created by Administrator on 2016/11/23.
 */

public final class MathUtil {
    public static Range<Integer> intersectionOf(Range<Integer> range1, Range<Integer> range2) {
        return range1.intersect(range2);
    }

    public static Rational plus(Rational rational1, Rational rational2) {
        return new Rational(rational1.getNumerator() * rational2.getDenominator() + rational2.getNumerator() * rational1.getDenominator(),
                rational1.getDenominator() * rational2.getDenominator());
    }
}
