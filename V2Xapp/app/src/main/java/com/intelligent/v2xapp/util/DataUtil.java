/*
 *Copyright © 2018 CIDI
 *长沙智能驾驶研究院有限公司
 *http://cidi.ai
 *All rights reserved.
 */
package com.intelligent.v2xapp.util;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 常见的辅助类
 *
 * @author LiuYa
 * @since 2011-11-08
 */
public final class DataUtil {
    private DataUtil() {
    }
    /**
     * 判断是否是16进制协议定0x开头为16进制数据
     */
    public static boolean is0x(String  str){

        String regex=".*[0x]+.*";
        Matcher m= Pattern.compile(regex).matcher(str);
        return m.matches();


    }


    /**
     * char数组转byte数组
     *
     * @param chars
     * @return
     */
    public static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);

        return bb.array();
    }

    /**
     * char转byte
     *
     * @param c
     * @return
     */
    public static byte getByte(char c) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(1);
        cb.put(c);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);

        byte[] tmp = bb.array();
        return tmp[0];
    }

    /**
     * byte数组转char数组
     *
     * @param bytes
     * @return
     */
    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }

    /**
     * byte转char
     *
     * @param bytes
     * @return
     */
    public static char getChar(byte bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(1);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        char[] tmp = cb.array();

        return tmp[0];
    }

    /**
     * 将byte[]转化十六进制的字符串
     */
    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    public static Long getInter(String msg) {
        return Long.parseLong(msg, 16);
    }

    /**
     * 16进制转字符串
     *
     * @param s
     * @return 十进制字符串
     */
    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");//UTF-16le:Not

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 十进制字节数组转十六进制字符串
     *
     * @param b
     * @return
     */
    public static final String hex2String(byte[] b) { // 一个字节数，转成16进制字符串
        StringBuilder hs = new StringBuilder(b.length * 2);
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString(); // 转成大写
    }

    /**
     * 十六进制字符串转十进制字节数组
     *
     * @param hs
     * @return
     */
    public static final byte[] hex2byte(String hs) {
        byte[] b = hs.getBytes();
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个十进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;


    }

    /**
     * 十六进制转换字符串
     *
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 十六进制字符串转十进制字节数组
     *
     * @param hs
     * @return
     */
    public static final String hex2byte1(String hs) {
        byte[] b = hs.getBytes();
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个十进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }

        return new String(b2);
    }

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static final boolean isEmpty(Object pObj) {
        if (pObj == null || "".equals(pObj))
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).trim().length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection<?>) {
            if (((Collection<?>) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map<?, ?>) {
            if (((Map<?, ?>) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * JS输出含有\n的特殊处理
     *
     * @param pStr
     * @return
     */
    public static final String replace4JsOutput(String pStr) {
        pStr = pStr.replace("\r\n", "<br/>&nbsp;&nbsp;");
        pStr = pStr.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        pStr = pStr.replace(" ", "&nbsp;");
        return pStr;
    }


    public static Collection convert2Collection(String[] strs) {
        Collection result = null;
        if (strs == null) {
            result = new ArrayList(0);
        } else {
            result = new ArrayList(strs.length);
            for (int i = 0; i < strs.length; i++) {
                result.add(strs[i]);
            }
        }

        return result;
    }

    public static Collection convert2IntegerCollection(String[] strs) {
        Collection result = null;
        if (strs == null) {
            result = new ArrayList(0);
        } else {
            result = new ArrayList(strs.length);
            for (int i = 0; i < strs.length; i++) {
                // String s=strs[i];
                result.add(new Integer(strs[i]));
            }
        }

        return result;
    }

    public static Collection copy2Collection(Object[] strs) {
        Collection result = null;
        if (strs == null) {
            result = new ArrayList(0);
        } else {
            result = new ArrayList(strs.length);
            for (int i = 0; i < strs.length; i++) {
                // String s=strs[i];
                result.add(strs[i]);
            }
        }

        return result;
    }

    /**
     * 初始化设置默认值
     */
    public static final <K> K ifNull(K k, K defaultValue) {
        if (k == null) {
            return defaultValue;
        }
        return k;
    }
    /**
     * 从时间(毫秒)中提取出时间(时:分)
     * 时间格式:  时:分
     *
     * @param millisecond
     * @return
     */
    public static String getTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    public static int[] convert(String[] strs) {
        int[] result;
        if (strs == null) {
            result = new int[0];
        } else {
            result = new int[strs.length];
            for (int i = 0; i < strs.length; i++) {
                String s = strs[i];
                result[i] = Integer.parseInt(s);
            }
        }

        return result;
    }


}
