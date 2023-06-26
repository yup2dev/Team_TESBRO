package com.team.tesbro.file;
import java.text.SimpleDateFormat;

public class Util {
    public static String getFileExtFromFileName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        String ext = fileName.substring(pos + 1);

        return ext;
    }

    public static String getFileExtTypeCodeFromFileName(String fileName) {
        String ext = getFileExtFromFileName(fileName).toLowerCase();

        switch (ext) {
            case "jpeg":
            case "jpg":
            case "gif":
            case "png":
                return "img";
            case "mp4":
            case "avi":
            case "mov":
                return "video";
            case "mp3":
                return "audio";
        }

        return "etc";
    }

    public static String getFileExtType2CodeFromFileName(String fileName) {
        String ext = getFileExtFromFileName(fileName).toLowerCase();

        switch (ext) {
            case "jpeg":
            case "jpg":
                return "jpg";
            case "gif":
            case "png":
            case "mp4":
            case "mov":
            case "avi":
            case "mp3":
                return ext;
        }

        return "etc";
    }

    public static String getNowYearMonthDateStr() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM");

        String dateStr = format1.format(System.currentTimeMillis());

        return dateStr;
    }
}