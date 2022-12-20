package com.wangdifu.commonutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Describe: 将日志堆栈信息输出到文件
 * @Author: wangdifu
 * @Date: 2022-11-2 , 0002 20:25
 * @Version: 1.0
 */
public class ExceptionUtil {

  public static String getMessage(Exception e) {
    StringWriter sw = null;
    PrintWriter pw = null;
    try {
      sw = new StringWriter();
      pw = new PrintWriter(sw);
      // 将出错的栈信息输出到printWriter中
      e.printStackTrace(pw);
      pw.flush();
      sw.flush();
    } finally {
      if (sw != null) {
        try {
          sw.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
      if (pw != null) {
        pw.close();
      }
    }
    return sw.toString();
  }
}
