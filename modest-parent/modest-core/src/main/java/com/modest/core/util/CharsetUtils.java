package com.modest.core.util;

import java.nio.charset.Charset;

public interface CharsetUtils {
    String US_ASCII = "US-ASCII";
    String ISO_LATIN1 = "ISO-8859-1";
    String GBK = "GBK";
    String GB2312 = "GB2312";
    String UTF8 = "UTF-8";
    String UTF16 = "UTF-16";
    String UTF16BE = "UTF-16BE";
    String UTF16LE = "UTF-16LE";
    String UTF32 = "UTF-32";
    String UTF32BE = "UTF-32BE";
    String UTF32LE = "UTF-32LE";
    String SHIFT_JIS = "Shift_JIS";
    String EBCDIC_SUBSET = "IBM037";
    Charset UTF_8 = Charset.forName(UTF8);
}