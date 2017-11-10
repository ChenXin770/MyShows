package myshow.cx.com.myshows.utils;

import java.util.Random;

public class Rand {
    static Random rand = new Random();
    private final static String SEPARATOR_OF_MAC = ":";

    public static String RaAm() {
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            localStringBuilder.append(new String[]{RaNum(), RaChar()}[rand.nextInt(2)]);
        }
        return localStringBuilder.toString();
    }

    public static String RaChar() {
        return new String[]{"b", "c", "d", "f"}[rand.nextInt(4)];
    }

    public static String RaNum() {
        return new StringBuilder(String.valueOf(rand.nextInt(9))).toString();
    }

    public static String RaNums(int paramInt) {
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < paramInt; i++) {
            localStringBuilder.append(RaNum());
        }
        return localStringBuilder.toString();
    }

    public static String RaMac() {
        String[] mac = {
                String.format("%02x", rand.nextInt(0xff)),
                String.format("%02x", rand.nextInt(0xff)),
                String.format("%02x", rand.nextInt(0xff)),
                String.format("%02x", rand.nextInt(0xff)),
                String.format("%02x", rand.nextInt(0xff)),
                String.format("%02x", rand.nextInt(0xff))
        };
        // return String.join(SEPARATOR_OF_MAC, mac);
        StringBuilder sb = new StringBuilder();
        for (String m : mac) {
            sb.append(m + ":");
        }
        return sb.substring(0, sb.length() - 1).toString();
    }

    public static String RaStrFromArr(String[] arr) {
        return arr[rand.nextInt(arr.length)];
    }

    public static String RaShou() {
        return new String[]{"SAMSUNG", "OPPO", "HUAWEI", "HTC", "ZTE", "DAXIAN", "Coolpad", "Lenovo", "HTC", "XIAOMI", "MEIZHU", "MEIZHU", "Lenovo", "VIVO"}[rand.nextInt(14)];
    }

    public static String RaShou_num() {
        String[] arrayOfString = new String[]{"NOTE 4", "NOTE 3", "NOTE 5", "NOTE 6", "NOTE 7", "S5", "S6", "S7", "A8", "C5", "P10 Plus", "P8", "P9", "P6", "Mate 9", "Mate 9 Pro", "Mate 8"};
        arrayOfString[16] = "Mate 7";
        arrayOfString[16] = "R9s";
        arrayOfString[16] = "R9";
        arrayOfString[16] = "A59s";
        arrayOfString[16] = "A59";
        arrayOfString[16] = "A57";
        arrayOfString[16] = "R7s";
        arrayOfString[16] = "A53";
        arrayOfString[0] = "R7";
        arrayOfString[1] = "NOTE 3";
        arrayOfString[2] = "A33";
        arrayOfString[3] = "4C";
        arrayOfString[4] = "4S";
        arrayOfString[5] = "5C";
        arrayOfString[6] = "5S";
        arrayOfString[7] = "5SPLUS";
        arrayOfString[8] = "MIX";
        arrayOfString[9] = "MAX";
        arrayOfString[10] = "NOTE4";
        arrayOfString[11] = "NOTE4X";
        arrayOfString[12] = "5";
        arrayOfString[13] = "A2580";
        arrayOfString[14] = "A2800";
        arrayOfString[15] = "A2860";
        arrayOfString[16] = "A330E";
        arrayOfString[16] = "A3500";
        arrayOfString[16] = "A355E";
        arrayOfString[16] = "A358T";
        arrayOfString[16] = "A360E";
        arrayOfString[16] = "A368T";
        arrayOfString[16] = "A3690";
        arrayOfString[16] = "A3800D";
        arrayOfString[16] = "V3MAX";
        arrayOfString[0] = "V3";
        arrayOfString[1] = "X3V";
        arrayOfString[2] = "X5Max L";
        arrayOfString[3] = "X5Max X";
        arrayOfString[4] = "X5Max V";
        arrayOfString[5] = "X5Max +";
        arrayOfString[6] = "AXON MAX";
        arrayOfString[7] = "Blade A1";
        arrayOfString[8] = "Blade A2";
        arrayOfString[9] = "A188";
        arrayOfString[10] = "A910";
        arrayOfString[11] = "A620";
        arrayOfString[12] = "Blade V8";
        arrayOfString[13] = "Blade A601";
        arrayOfString[14] = "Blade A510";
        arrayOfString[15] = "5261";
        arrayOfString[16] = "5263s";
        arrayOfString[16] = "5263";
        arrayOfString[16] = "5270";
        arrayOfString[16] = "5316";
        arrayOfString[16] = "5360";
        arrayOfString[16] = "5367";
        arrayOfString[16] = "5721";
        arrayOfString[16] = "5956";
        arrayOfString[16] = "7060s";
        arrayOfString[0] = "7105";
        arrayOfString[1] = "7605";
        arrayOfString[2] = "302u";
        arrayOfString[3] = "520";
        arrayOfString[4] = "550";
        arrayOfString[5] = "580";
        arrayOfString[6] = "750";
        arrayOfString[7] = "950";
        arrayOfString[8] = "p310c";
        arrayOfString[9] = "P307L";
        arrayOfString[10] = "MX5e";
        arrayOfString[11] = "MX5";
        arrayOfString[12] = "MX6";
        arrayOfString[13] = "Pro 5";
        arrayOfString[14] = "Pro 6";
        arrayOfString[15] = "PLUS 6";
        arrayOfString[16] = "Pro 6s";
        arrayOfString[16] = "3S";
        arrayOfString[16] = "5";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "Note2";
        arrayOfString[16] = "Note3";
        arrayOfString[16] = "Note5";
        arrayOfString[16] = "U10";
        arrayOfString[16] = "X";
        arrayOfString[0] = "metal";
        arrayOfString[1] = "NOTE 3";
        arrayOfString[2] = "P8";
        arrayOfString[3] = "N9";
        arrayOfString[4] = "S672";
        arrayOfString[5] = "Y11";
        arrayOfString[6] = "M7";
        arrayOfString[7] = "M56";
        arrayOfString[8] = "L1";
        arrayOfString[9] = "N11";
        arrayOfString[10] = "MTS 6";
        arrayOfString[11] = "325p";
        arrayOfString[12] = "8US";
        arrayOfString[13] = "MS16";
        arrayOfString[14] = "TUIP95";
        arrayOfString[15] = "PLUS 6";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "PLUS5";
        arrayOfString[16] = "PLUS5";
        return arrayOfString[rand.nextInt(arrayOfString.length)];
    }

    public static String RaShou_wh() {
        return new String[]{"854*480", "960*540", "1136*640", "1280*720", "1024*600", "1024*768", "1920*1080"}[rand.nextInt(7)];
    }

    public static int RaInt(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public static String getIMEI() {// calculator IMEI
        int r1 = 10000 + new Random().nextInt(90000);
        int r2 = 1000000 + new Random().nextInt(9000000);
        String input = "86" + r1 + "" + r2;
        char[] ch = input.toCharArray();
        int a = 0, b = 0;
        for (int i = 0; i < ch.length; i++) {
            int tt = Integer.parseInt(ch[i] + "");
            if (i % 2 == 0) {
                a = a + tt;
            } else {
                int temp = tt * 2;
                b = b + temp / 10 + temp % 10;
            }
        }
        int last = (a + b) % 10;
        if (last == 0) {
            last = 0;
        } else {
            last = 10 - last;
        }
        return input + last;
    }

    public static String getImsi() {
        // 460022535025034
        String title = "4600";
        int second = 0;
        do {
            second = new Random().nextInt(8);
        } while (second == 4);
        int r1 = 10000 + new Random().nextInt(90000);
        int r2 = 10000 + new Random().nextInt(90000);
        return title + "" + second + "" + r1 + "" + r2;
    }

    private static String getMac() {
        char[] char1 = "abcdef".toCharArray();
        char[] char2 = "0123456789".toCharArray();
        StringBuffer mBuffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int t = new Random().nextInt(char1.length);
            int y = new Random().nextInt(char2.length);
            int key = new Random().nextInt(2);
            if (key == 0) {
                mBuffer.append(char2[y]).append(char1[t]);
            } else {
                mBuffer.append(char1[t]).append(char2[y]);
            }
            if (i != 5) {
                mBuffer.append(":");
            }
        }
        return mBuffer.toString();
    }

    public static String[] raOsVersion() {
        String[] osTable = {
                "4.0,14",
                "4.0.1,14",
                "4.0.2,14",
                "4.0.3,15",
                "4.0.3,15",
                "4.1,16",
                "4.1.1,16",
                "4.2,17",
                "4.2.2,17",
                "4.3,18",
                "4.4,19",
                "5.0,21",
                "5.1,22",
                "6.0,23",
                "7.0,24",
                "7.1,25",
                "7.1.1,25",
        };
        return osTable[rand.nextInt(osTable.length)].split(",");
    }

    public static String genUserAgent(String brand, String type, String osVersion) {
        return "Mozilla/5.0 (Linux; Android " + osVersion + ";" + brand + " " + type + " Build/" + RaAm().substring(0, 5).toUpperCase() + ") AppleWebKit/" + (RaInt(1, 100) > 50 ? RaInt(533, 537) : RaInt(600, 603)) + '.' + RaInt(1, 50) + " (KHTML, like Gecko)  Chrome/" + RaInt(47, 55) + " Mobile Safari/" + (RaInt(1, 100) > 50 ? RaInt(533, 537) : RaInt(600, 603)) + "." + RaInt(0, 9);
    }

    public static String raUserAgent() {
        return "Mozilla/5.0 (Linux; Android " + raOsVersion()[0] + ";" + RaShou() + " " + RaShou_num() + " Build) AppleWebKit/" + (RaInt(1, 100) > 50 ? RaInt(533, 537) : RaInt(600, 603)) + '.' + RaInt(1, 50) + " (KHTML, like Gecko)  Chrome/" + RaInt(47, 55) + " Mobile Safari/" + (RaInt(1, 100) > 50 ? RaInt(533, 537) : RaInt(600, 603)) + "." + RaInt(0, 9);
    }

    public static void main(String[] args) {
        System.out.println(RaMac());
        String[] arr = new String[]{"hi", "hello", "sdf", "23"};
        System.out.println(RaStrFromArr(arr));
        System.out.println(RaStrFromArr(arr));
        System.out.println(getIMEI());
        System.out.println(genUserAgent("HUAWEI", "P9", "7.0"));
    }
}
