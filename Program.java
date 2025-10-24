
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Program {

    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Task1();
        Task2();
        Task3();
        Task4();
        Task5();
        Task6();
        Task7();
        Task8();
    }

    static void selSortAsc(double[] a, int l, int r) {
        if (l < 0) {
            l = 0;
        }
        if (r >= a.length) {
            r = a.length - 1;
        }
        for (int i = l; i <= r; i++) {
            int mi = i;
            for (int j = i + 1; j <= r; j++) {
                if (a[j] < a[mi]) {
                    mi = j;
                }
            }
            double t = a[i];
            a[i] = a[mi];
            a[mi] = t;
        }
    }

    static void selSortDesc(double[] a, int l, int r) {
        if (l < 0) {
            l = 0;
        }
        if (r >= a.length) {
            r = a.length - 1;
        }
        for (int i = l; i <= r; i++) {
            int mi = i;
            for (int j = i + 1; j <= r; j++) {
                if (a[j] > a[mi]) {
                    mi = j;
                }
            }
            double t = a[i];
            a[i] = a[mi];
            a[mi] = t;
        }
    }

    private static void Task8() {
        int rsi;
        System.out.println("//8\n");
        System.out.print("rsi: ");
        rsi = sc.nextInt();
        int[] base = new int[rsi];
        Random rnd = new Random();
        for (int i = 0; i < rsi; i++) {
            base[i] = rnd.nextInt(19);
        }
        System.out.println(Arrays.toString(base));
        int ce = 0, co = 0, cn = 0, cp = 0;
        for (int v : base) {
            if (v % 2 == 0) {
                ce++;
            } else {
                co++;
            }
            if (v < 0) {
                cn++;
            } else if (v > 0) {
                cp++;
            }
        }
        int[] even = new int[ce];
        int[] odd = new int[co];
        int[] neg = new int[cn];
        int[] pos = new int[cp];
        int ie = 0, io = 0, ineg = 0, ipos = 0;
        for (int v : base) {
            if (v % 2 == 0) {
                even[ie++] = v;
            } else {
                odd[io++] = v;
            }
            if (v < 0) {
                neg[ineg++] = v;
            } else if (v > 0) {
                pos[ipos++] = v;
            }
        }
        System.out.println(Arrays.toString(even));
        System.out.println(Arrays.toString(odd));
        System.out.println(Arrays.toString(neg));
        System.out.println(Arrays.toString(pos));
    }

    private static void Task7() {
        int rsi;
        System.out.println("//7\n");
        String[] menu = {"Espresso", "Latte", "Tea", "Cake", "Cookie"};
        double[] price = {2.5, 3.0, 1.5, 4.0, 1.2};
        System.out.print("rsi: ");
        rsi = sc.nextInt();
        double rex = 0.0;
        for (int g = 1; g <= rsi; g++) {
            int ppl = sc.nextInt();
            double sum = 0.0;
            for (int p = 1; p <= ppl; p++) {
                System.out.println("mune (0 - rat):");
                for (int i = 0; i < menu.length; i++) {
                    System.out.println((i + 1) + ". " + menu[i] + " " + price[i]);
                }
                while (true) {
                    int ch = sc.nextInt();
                    if (ch == 0) {
                        break;
                    }
                    if (ch >= 1 && ch <= menu.length) {
                        sum = sum + price[ch - 1];
                    } else {
                        return;
                    }
                }
            }
            rex += sum;
            System.out.println("sum " + sum);
        }
        System.out.println("total:" + rex);
    }

    private static void Task6() {
        int rsi;
        System.out.println("//6\n");
        System.out.print("rsi: ");
        rsi = sc.nextInt();
        double[] d = new double[rsi];
        System.out.println("enter" + rsi + "t:");
        for (int i = 0; i < rsi; i++) {
            d[i] = sc.nextDouble();
        }
        int mx = 0;
        for (int i = 1; i < rsi; i++) {
            if (d[i] > d[mx]) {
                mx = i;
            }
        }
        selSortAsc(d, 0, mx - 1);
        selSortDesc(d, mx + 1, rsi - 1);
        System.out.println(Arrays.toString(d));
    }

    private static void Task5() {
        System.out.println("//5");
        System.out.print("2: ");
        int a = sc.nextInt();
        int b = sc.nextInt();
        int from, to;
        if (a <= b) {
            from = a;
            to = b;
        } else {
            from = b;
            to = a;
        }
        for (int i = from; i <= to; i++) {
            if ((i % 2) != 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    private static void Task4() {
        System.out.println("//4\n");
        System.out.print("rsi: ");
        int rsi = sc.nextInt();
        int[] arr = new int[rsi];
        System.out.println("enter" + rsi + "t");
        for (int i = 0; i < rsi; i++) {
            arr[i] = sc.nextInt();
        }
        int mn = 0, mx = 0;
        for (int i = 1; i < rsi; i++) {
            if (arr[i] < arr[mn]) {
                mn = i;
            }
            if (arr[i] > arr[mx]) {
                mx = i;
            }
        }
        int lo = mn < mx ? mn + 1 : mx + 1;
        int hi = mn < mx ? mx - 1 : mn - 1;
        for (int i = lo; i <= hi; i++) {
            arr[i] = arr[i] * 2;
        }
        System.out.println(Arrays.toString(arr));
    }

    private static void Task3() {
        System.out.println("//3\n");
        Scanner fs;
        try {
            fs = new Scanner(new File("e.S"));
        } catch (FileNotFoundException ex) {
            return;
        }
        int x = fs.nextInt();
        fs.close();
        System.out.print("rdi: ");
        int w = sc.nextInt();
        int rate;
        if (w <= 500) {
            rate = 1;
        } else if (w <= 1000) {
            rate = 4;
        } else if (w <= 1500) {
            rate = 7;
        } else if (w <= 2000) {
            rate = 9;
        } else {
            rate = -1;
        }
        if (rate < 0) {
            System.out.println("x > 2000");
        } else {
            System.out.print("eax: ");
            int ab = sc.nextInt();
            System.out.print("rex: ");
            int bc = sc.nextInt();
            long AB = (long) ab * rate;
            long BC = (long) bc * rate;
            if (AB > x || BC > x) {
                System.out.println("Uncompletable.");
            } else {
                long leftA = x - AB;
                long addA = BC - leftA;
                if (addA < 0) {
                    addA = 0;
                }
                System.out.println("Min: " + addA);
            }
        }
    }

    private static void Task2() {
        System.out.print("//2\n");
        int m = sc.nextInt();
        if (m == 12 || m == 1 || m == 2) {
            System.out.println("Winter");
        } else if (m >= 3 && m <= 5) {
            System.out.println("Spring");
        } else if (m >= 6 && m <= 8) {
            System.out.println("Summer");
        } else if (m >= 9 && m <= 11) {
            System.out.println("Autumn");
        } else {
        }
    }

    private static void Task1() throws NumberFormatException {
        System.out.print("//1\n");
        String s = sc.next();
        int n1 = Integer.parseInt(s);
        if (s.length() == 6) {
            StringBuilder res = new StringBuilder(s);
            char eax = res.charAt(0);
            res.setCharAt(0, res.charAt(5));
            res.setCharAt(5, eax);
            eax = res.charAt(1);
            res.setCharAt(1, res.charAt(4));
            res.setCharAt(4, eax);
            System.out.println(res);
        }
    }
}
