package ch.idsia.ipp.core.utils;


import ch.idsia.ipp.core.common.BayesianNetwork;
import ch.idsia.ipp.core.common.Worker;
import ch.idsia.ipp.core.common.io.*;
import ch.idsia.ipp.core.utils.data.array.TIntArrayList;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@SuppressWarnings("ALL")
public class RandomStuff {

    private static final Logger log = Logger.getLogger(
            RandomStuff.class.getName());

    private static Random rand = new Random(System.currentTimeMillis());

    public static String rmExt(String str) {
        if (str.contains("."))
            return str.substring(0, str.lastIndexOf('.'));
        else
            return str;
    }

    public static int index(int s, int v1, int v2) {
        if (v1 > v2) {
            int t = v1;

            v1 = v2;
            v2 = t;
        }

        int n = 0;

        for (int i = 0; i < v1; i++) {
            n += (s - i - 2);
        }
        n += (v2 - 1);
        return n;
    }

    public static int waitForProc(Process process, long timeout) {

        Worker worker = new Worker(process);
        worker.start();
        try {
            worker.join(timeout);
            if (worker.exit != null)
                return worker.exit;
        } catch (InterruptedException ex) {
            worker.interrupt();
            Thread.currentThread().interrupt();
            logExp(log, ex);
        } finally {
            process.destroy();
        }
        return -1;
    }


    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);

        java.util.Collections.sort(list);
        return list;
    }

    public static <K, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        // LinkedHashMap will keep the keys in the order they are inserted
        // which is currently sorted on natural ordering
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static <K, V extends Comparable> List<K> sortByValuesList(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        // LinkedHashMap will keep the keys in the order they are inserted
        // which is currently sorted on natural ordering
        List<K> sortedList = new ArrayList<K>();

        for (Map.Entry<K, V> entry : entries) {
            sortedList.add(entry.getKey());
        }

        return sortedList;
    }

    public static boolean doubleEquals(double s, double v) {
        return doubleEquals(s, v, 5);
    }

    public static boolean doubleEquals(double s, double v, int p) {
        if (!(Math.abs(s - v) < Math.pow(2, -p))) {
            return false;
        } else {
            return true;
        }
    }

    public static <K, V extends Comparable> Map<K, V> sortInvByValues(Map<K, V> map) {

        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });

        // LinkedHashMap will keep the keys in the order they are inserted
        // which is currently sorted on natural ordering
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static <K, V extends Comparable> List<K> sortInvByValuesList(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });

        // LinkedHashMap will keep the keys in the order they are inserted
        // which is currently sorted on natural ordering
        List<K> sortedList = new ArrayList<K>();

        for (Map.Entry<K, V> entry : entries) {
            sortedList.add(entry.getKey());
        }

        return sortedList;
    }

    public static float listMeanL(List<? extends Number> list) {
        long sum = 0;

        for (Number l : list) {
            sum += l.doubleValue();
        }
        return sum / list.size();
    }

    public static float listMeanF(List<Float> list) {
        float sum = 0;

        for (float l : list) {
            sum += l;
        }
        return sum / list.size();
    }

    public static float listMeanD(List<Double> list) {
        float sum = 0;

        for (double l : list) {
            sum += l;
        }
        return sum / list.size();
    }

    public static float listMeanInt(List<Integer> list) {
        Integer sum = 0;

        for (Integer l : list) {
            sum += l;
        }
        return sum / list.size();
    }

    /**
     * Re-print a message on the current screen line
     *
     * @param msg Useful message for the user
     */
    public static void printR(String msg) {
        System.out.print('\r' + msg);
    }

    public static long factorial(int n) {
        long fact = 1; // this  will be the result

        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public static String printOrdMap(Map<Integer, Integer> map) {
        String stri = "{ ";
        boolean first = true;
        List<Integer> keys = new ArrayList<Integer>(map.keySet());

        Collections.sort(keys);
        for (int key : keys) {
            if (first) {
                first = false;
            } else {
                stri += ", ";
            }

            stri += String.format("%d: %d", key, map.get(key));
        }
        stri += " }";
        return stri;
    }

    public static boolean different(TIntArrayList v1, TIntArrayList v2) {
        for (int e1 : v1.toArray()) {
            if (!v2.contains(e1)) {
                return true;
            }
        }
        for (int e2 : v2.toArray()) {
            if (!v1.contains(e2)) {
                return true;
            }
        }

        return false;
    }

    public static void logExp(Throwable trw) {
        log.log(Level.SEVERE, trw.toString(), trw);
    }

    public static void logExp(Logger log, Throwable trw) {
        log.log(Level.SEVERE, trw.toString(), trw);
    }

    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static String readFile(String s) throws IOException {

        File f = new File(s);

        StringBuilder fileContents = new StringBuilder((int) f.length());
        Scanner scanner = new Scanner(f);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    /**
     * Get a new data file reader from the argument
     *
     * @param ph path to the file
     * @return data file reader
     * @throws IncorrectCallException        file path not valid
     * @throws java.io.FileNotFoundException data file not found
     */
    public static DataFileReader getDataFileReader(String ph) {

        try {
            BufferedReader reader;

            if (ph == null) {
                throw new IncorrectCallException(
                        "No data point path provided: " + ph);
            }
            File f_dat = new File(ph);

            if (!f_dat.isFile()) {
                throw new IncorrectCallException(
                        "No valid data point path provided: " + ph);
            }

            return new DataFileReader(ph);
        } catch (Exception ex) {
            logExp(log, ex);
        }

        return null;
    }

    /**
     * Get writer for the output scores
     *
     * @param ph (optional) file path
     * @return writer to use to output result
     * @throws java.io.UnsupportedEncodingException error in stream creation
     * @throws java.io.FileNotFoundException        file path not valid
     */
    public static Writer getWriter(String ph)
            throws UnsupportedEncodingException, FileNotFoundException {
        Writer writer;

        if (ph != null) {
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(ph), "utf-8"));
        } else {
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
        }
        return writer;
    }

    public static BufferedReader getReader(String ph) throws FileNotFoundException {
        return new BufferedReader(new FileReader(ph));
    }

    /**
     * Get directory
     *
     * @param ph dir path
     * @return directory
     * @throws java.io.UnsupportedEncodingException error in stream creation
     * @throws java.io.FileNotFoundException        file path not valid
     */
    public static File getDirectory(String ph) throws IncorrectCallException {

        if (ph == null) {
            throw new IncorrectCallException("No path provided: " + ph);
        }
        File f = new File(ph);

        if (!(f.exists() && f.isDirectory())) {
            throw new IncorrectCallException("No valid path provided: " + ph);
        }
        return f;
    }

    /**
     * Get a new score file reader from the argument
     *
     * @param ph path to the file
     * @return score file reader
     * @throws IncorrectCallException        file path not valid
     * @throws java.io.FileNotFoundException if file is not found
     */
    public static ScoreReader getScoreReader(String ph, int debug)
            throws IncorrectCallException, FileNotFoundException {

        if (ph == null) {
            throw new IncorrectCallException("No score path provided: " + ph);
        }

        return new ScoreReader(ph, 10);
    }

    public static void closeIt(Logger log, Closeable writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (Exception exp) {
            logExp(log, exp);
        }
    }

    public static void checkPath(String ph) throws IncorrectCallException, IOException {
        if (ph == null) {
            throw new IncorrectCallException("No path provided: " + ph);
        }
        File f = new File(ph);

        if (!f.createNewFile()) {
            throw new IncorrectCallException(
                    "Impossible to create path provided: " + ph);
        }
        f.delete();
    }

    public static <K, V extends Comparable<? super V>>
    SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        return entriesSortedByValues(map, false);
    }

    public static <K, V extends Comparable<? super V>>
    SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map, final boolean invert) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {

                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());

                        if (invert) {
                            res = -res;
                        }
                        return res != 0 ? res : 1;
                    }
                });

        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public static <K, V> void p(SortedSet<Map.Entry<K, V>> entries) {
        for (Map.Entry<K, V> entry : entries) {
            pf("%s - %s \n", entry.getKey().toString(),
                    entry.getValue().toString());
        }
    }

    public static <K extends Comparable<? super K>, V>
    SortedSet<Map.Entry<K, V>> entriesSortedByKey(Map<K, V> map) {
        return entriesSortedByKey(map, false);
    }

    public static <K extends Comparable<? super K>, V>
    SortedSet<Map.Entry<K, V>> entriesSortedByKey(Map<K, V> map, final boolean b) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {

                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        int res = e1.getKey().compareTo(e2.getKey());

                        if (b) {
                            res = -res;
                        }
                        return res != 0 ? res : 1;
                    }
                });

        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    static public String cmd(String cmd) throws IOException {

        // p(cmd);

        Process proc = Runtime.getRuntime().exec(cmd);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = null;
        StringBuilder out = new StringBuilder();

        while ((s = stdInput.readLine()) != null) {
            out.append(s);
        }

        // read any errors from the attempted command
        StringBuilder err = new StringBuilder();
        boolean e = false;

        while ((s = stdError.readLine()) != null) {
            err.append(s);
            System.out.println(s);
            e = true;
        }

        if (e) {
            System.out.println("errors: " + err);
        }

        return out.toString();
    }

    static public void cmd(ProcessBuilder pb, String s) throws IOException {
        pb.directory(new File(s));

        // redirect stdout, stderr, etc
        procOutput(pb.start());
    }

    static public void cmdOutput(String cmd) throws IOException {

        // read the output from the command
        System.out.println("cmd: " + cmd + "\noutput: ");

        procOutput(Runtime.getRuntime().exec(cmd));
    }

    static public void procOutput(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = null;

        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.printf("errors: ");
        StringBuilder st = new StringBuilder();
        while ((s = stdError.readLine()) != null) {
            st.append(s);
        }

        if (st.length() > 0)
           f("errors: %s \n", st.toString());
    }

    public static void pf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public static String f(String format, Object... args) {
        return String.format(format, args);
    }

    public static void wf(Writer writer, String format, Object... args) throws IOException {
        writer.write(String.format(format, args));
    }

    public static void p(String s) {
        System.out.println(s);
    }

    public static void p(Object s) {
        System.out.println(String.valueOf(s));
    }

    public static void p(int[] s) {
        System.out.println(Arrays.toString(s));
    }

    public static boolean exists(String s) {
        File f = new File(s);

        return f.exists() && !f.isDirectory();
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();

        if (files != null) { // some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }


    public static void copyDirectory(File src, File trg) {
        try {
            if (src.isDirectory()) {
                if (!trg.exists()) {
                    trg.mkdir();
                }

                for (File f : src.listFiles()) {
                    String s = f.getName();
                    if (f.isFile()) {
                        copySomething(new File(src, s), new File(trg, s));
                    } else {
                        copyDirectory(new File(src, s), new File(trg, s));
                    }

                }
                //              String[] children = sourceLocation.listFiles();
                //                for (int thread = 0; thread < children.length; thread++) {
                //                      copyDirectory(new File(sourceLocation, children[thread]),
                //                                new File(targetLocation, children[thread]));

            } else {

                copySomething(src, trg);
            }
        } catch (IOException ex) {
            logExp(log, ex);
        }
    }

    public static void copySomething(String f1, String f2) throws IOException {
        copySomething(new File(f1), new File(f2));
    }
    public static void copySomething(File f1, File f2) throws IOException {
        InputStream in = new FileInputStream(f1);
        OutputStream out = new FileOutputStream(f2);

        // Copy the bits from instream to outstream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }



    public static BayesianNetwork getBayesianNetwork(String s_bn) {

        BufferedReader rd_bn = null;
        try {
            rd_bn = new BufferedReader(new FileReader(s_bn));
        } catch (FileNotFoundException e) {
            p("Warning! Bayesian network not found! " + s_bn);
            return null;
        }

        BayesianNetwork bn = null;
        if (s_bn.endsWith(".net"))
            bn = BnNetReader.ex(rd_bn);
        else if (s_bn.endsWith(".uai"))
            bn = BnUaiReader.ex(rd_bn);
        else if (s_bn.endsWith(".erg"))
            bn = BnErgReader.ex(rd_bn);
        else
            bn = BnResReader.ex(rd_bn);
        return bn;
    }

    public static void writeBayesianNetwork(BayesianNetwork bn, String path) {

        if (path.endsWith(".net"))
            BnNetWriter.ex(bn, path);
        else if (path.endsWith(".uai"))
            BnUaiWriter.ex(path, bn);
        else if (path.endsWith(".erg"))
            BnErgWriter.ex(path, bn);

    }

    public static String clean(String s) {
        return s.trim().toLowerCase();
    }

}
