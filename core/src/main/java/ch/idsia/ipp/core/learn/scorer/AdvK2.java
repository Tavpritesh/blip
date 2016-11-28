package ch.idsia.ipp.core.learn.scorer;


import ch.idsia.ipp.core.common.io.DataFileReader;
import ch.idsia.ipp.core.utils.data.array.TIntArrayList;

import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static ch.idsia.ipp.core.utils.RandomStuff.getWriter;


public class AdvK2 extends BaseScorer {

    private static final Logger log = Logger.getLogger(AdvK2.class.getName());

    private AdvK2(int maxExec) {
        super(maxExec);
    }

    @Override
    protected String getName() {
        return "Advanced K2 selection";
    }

    public AdvK2() {
        super();
    }

    @Override
    public void go(DataFileReader in_dat) throws Exception {

        dat = in_dat;

        Writer writer = getWriter(ph_scores + ".jkl");

        if (verbose > 1) {
            System.out.println("Reading from data...");
        }

        this.dat.readMetaData();
        this.dat.readValuesCache();

        writer.write(String.format("%d\n", this.dat.n_var));

        // Compute maximum execution time
        max_exec_time = (max_exec_time * dat.n_var) / thread_pool_size;

        ExecutorService executor = Executors.newFixedThreadPool(thread_pool_size);

        for (int i = 0; i < thread_pool_size; i++) {
            executor.execute(new AdvK2Searcher(0, max_exec_time));
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        for (int n = 0; n < dat.n_var; n++) {
            // writeScores(writer, n, sc);
        }
    }

    @Override
    protected void prepareSearch() {}

    @Override
    public AdvK2Searcher getNewSearcher(int n) {
        return null;
    }

    private class AdvK2Searcher extends BaseSearcher {

        private final double exec_time;

        public AdvK2Searcher(int n, double in_max_exec_time) {
            super(n);
            exec_time = in_max_exec_time;
        }

        public void run() {

            if (verbose > 2) {
                log.info(
                        String.format("Starting with max time: %.2f",
                        max_exec_time));
            }

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            double start = bean.getCurrentThreadCpuTime();
            double elapsed = 0;

            Random r = new Random((long) start);

            // Base ord
            TIntArrayList ord = new TIntArrayList();

            for (int n1 = 0; n1 < dat.n_var; n1++) {
                ord.add(n1);
            }

            boolean cnt = true;

            while (cnt) {

                ord.shuffle(r);

                for (int n = 0; (n < dat.n_var) && cnt; n++) {
                    findBestParentSet(ord, n);

                    elapsed = (bean.getCurrentThreadCpuTime() - start)
                            / 1000000000;
                    cnt = elapsed < max_exec_time;
                }
            }

            if (verbose > 2) {
                log.info(
                        String.format(
                                "ending with: elapsed: %.2f, num evaluated %d",
                                elapsed, score.numEvaluated));
            }
        }

        /**
         * Find the best parent set for the variable with index n, from the ordered array
         *
         * @param ord ordered variables
         * @param i   index of target variable
         */
        private void findBestParentSet(TIntArrayList ord, int i) {

            int v = ord.get(i);

            double bestScore;

            bestScore = voidSk;

            // Size of the parent set
            int s = 0;

            TIntArrayList bestPset = new TIntArrayList();

            boolean bProgress = true;

            while (bProgress) {
                int bestP = -1;
                double prevSk = bestScore;

                for (int j = 0; j < i; j++) {

                    int p = ord.get(j);

                    if (bestPset.contains(p)) {
                        continue;
                    }

                    TIntArrayList newPset = new TIntArrayList(bestPset.toArray());

                    newPset.add(p);
                    newPset.sort();

                    int[][] p_values = score.computeParentSetValues(
                            newPset.toArray());
                    double sk = score.computeScore(dat.sample[v], v, p_values,
                            newPset.toArray());

                    if ((sk > bestScore) && (sk > prevSk)) {
                        addScore(newPset.toArray(), sk);
                    }

                    if (sk > bestScore) {
                        bestScore = sk;
                        bestP = p;
                    }
                }

                if (bestP != -1) {
                    bestPset.add(bestP);
                    bestPset.sort();
                    s++;

                    bProgress = s < max_pset_size;
                } else {
                    bProgress = false;
                }

            }

        }
    }
}
