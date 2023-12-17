package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.base.OperableFunction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collection;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 14:00 2017/6/16
 */
public class NumberUtil {
    private final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };
    public static final int MIN_RADIX = 2;
    public static final int MAX_RADIX = digits.length;

    /**
     * 数字截取
     *
     * @param target
     * @param digits 位数，1-个位，2-十位，3-百位，... ...
     * @return
     */
    public static int truncate(int target, int digits) {
        Preconditions.checkArgument(digits > 0, "digits必须大于0");

        return new BigDecimal(target).setScale(-digits, RoundingMode.DOWN).intValue();
    }

    /**
     * 数字截取
     *
     * @param target
     * @param digits 位数，1-个位，2-十位，3-百位，... ...
     * @return
     */
    public static long truncate(long target, int digits) {
        Preconditions.checkArgument(digits > 0, "digits必须大于0");

        return new BigDecimal(target).setScale(-digits, RoundingMode.DOWN).longValue();
    }

    public static Long toLong(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Long) {
            return (Long) object;
        } else if (object instanceof Number) {
            return ((Number) object).longValue();
        } else if (object instanceof String) {
            return Long.valueOf(((String) object).trim());
        } else {
            return Long.valueOf(object.toString());
        }
    }

    public static Integer toInteger(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Integer) {
            return (Integer) object;
        } else if (object instanceof Number) {
            return ((Number) object).intValue();
        } else if (object instanceof String) {
            return Integer.valueOf(((String) object).trim());
        } else {
            return Integer.valueOf(object.toString());
        }
    }

    public static Short toShort(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Short) {
            return (Short) object;
        } else if (object instanceof Number) {
            return ((Number) object).shortValue();
        } else if (object instanceof String) {
            return Short.valueOf(((String) object).trim());
        } else {
            return Short.valueOf(object.toString());
        }
    }

    public static Byte toByte(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Byte) {
            return (Byte) object;
        } else if (object instanceof Number) {
            return ((Number) object).byteValue();
        } else if (object instanceof String) {
            return Byte.valueOf(((String) object).trim());
        } else {
            return Byte.valueOf(object.toString());
        }
    }

    public static Double toDouble(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Double) {
            return (Double) object;
        } else if (object instanceof Number) {
            return ((Number) object).doubleValue();
        } else if (object instanceof String) {
            return Double.valueOf(((String) object).trim());
        } else {
            return Double.valueOf(object.toString());
        }
    }

    public static Float toFloat(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Float) {
            return (Float) object;
        } else if (object instanceof Number) {
            return ((Number) object).floatValue();
        } else if (object instanceof String) {
            return Float.valueOf(((String) object).trim());
        } else {
            return Float.valueOf(object.toString());
        }
    }

    public static BigDecimal toBigDecimal(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof BigDecimal) {
            return (BigDecimal) object;
        } else if (object instanceof Number) {
            return new BigDecimal(object.toString());
        } else if (object instanceof String) {
            return new BigDecimal(((String) object).trim());
        } else {
            return new BigDecimal(object.toString());
        }
    }

    public static BigInteger toBigInteger(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof BigInteger) {
            return (BigInteger) object;
        } else if (object instanceof Number) {
            return new BigInteger(object.toString());
        } else if (object instanceof String) {
            return new BigInteger(((String) object).trim());
        } else {
            return new BigInteger(object.toString());
        }
    }

    @Deprecated
    public static Long toLongObject(Object object) {
        return toLong(object);
    }

    @Deprecated
    public static Integer toIntegerObject(Object object) {
        return toInteger(object);
    }

    @Deprecated
    public static Short toShortObject(Object object) {
        return toShort(object);
    }

    @Deprecated
    public static Byte toByteObject(Object object) {
        return toByte(object);
    }

    @Deprecated
    public static Double toDoubleObject(Object object) {
        return toDouble(object);
    }

    @Deprecated
    public static Float toFloatObject(Object object) {
        return toFloat(object);
    }

    /**
     * 求和，会跳过null值
     */
    public static BigDecimal sum(BigDecimal... sources) {
        return ArrayUtil.sum(sources, new OperableFunction<BigDecimal>() {
            @Override
            public BigDecimal apply(BigDecimal source) {
                return source;
            }
        });
    }

    /**
     * 求和，会跳过null值
     */
    public static BigDecimal sum(Collection<BigDecimal> sourceList) {
        return CollectionUtil.sum(sourceList, new OperableFunction<BigDecimal>() {
            @Override
            public BigDecimal apply(BigDecimal source) {
                return source;
            }
        });
    }

    public static boolean gtZero(Number target) {
        return target != null && new BigDecimal(target.toString()).compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean geZero(Number target) {
        return target != null && new BigDecimal(target.toString()).compareTo(BigDecimal.ZERO) >= 0;
    }

    public static boolean ltZero(Number target) {
        return target != null && new BigDecimal(target.toString()).compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean leZero(Number target) {
        return target != null && new BigDecimal(target.toString()).compareTo(BigDecimal.ZERO) <= 0;
    }

    public static String toString(long i, int radix) {
        if (radix < MIN_RADIX || radix > MAX_RADIX) {
            radix = 10;
        }
        if (radix == 10) {
            return Long.toString(i);
        }
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = digits[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (65 - charPos));
    }

    public static String to62String(long i) {
        return toString(i, 62);
    }

    private static int digit(char c, int radix) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        } else if (c >= 'a' && c <= 'z') {
            return c - 97 + 10;
        } else if (c >= 'A' && c <= 'Z') {
            return c - 65 + 36;
        } else {
            return -1;
        }
    }

    public static long parseLong(String s, int radix) throws NumberFormatException {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < MIN_RADIX) {
            throw new NumberFormatException(String.format("radix %s less than %s", radix, MIN_RADIX));
        }
        if (radix > MAX_RADIX) {
            throw new NumberFormatException(String.format("radix %s greater than %s", radix, MAX_RADIX));
        }

        long result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            // Possible leading "+" or "-"
            if (firstChar < '0') {
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+') {
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                }

                // Cannot have lone "+" or "-"
                if (len == 1) {
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                }
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = digit(s.charAt(i++), radix);
                if (digit < 0) {
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                }
                if (result < multmin) {
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                }
                result *= radix;
                if (result < limit + digit) {
                    throw new NumberFormatException("For input string: \"" + s + "\"");
                }
                result -= digit;
            }
        } else {
            throw new NumberFormatException("For input string: \"" + s + "\"");
        }
        return negative ? result : -result;
    }

    public static long parse62Long(String s) throws NumberFormatException {
        return parseLong(s, 62);
    }
}
