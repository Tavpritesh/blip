package ch.idsia.ipp.core.learn.solver.brtl;

import ch.idsia.ipp.core.learn.solver.SkelSolver;
import ch.idsia.ipp.core.learn.solver.src.BrutalOldGreedySearcher;
import ch.idsia.ipp.core.learn.solver.src.Searcher;

/**
 * BRTL approach, A*
 */
public class BrutalPcGreedySolver extends SkelSolver {

    protected int tw = 3;

    @Override
    protected String name() {
        return "Greedy on a skeleton";
    }

    @Override
    protected Searcher getSearcher() {
        return new BrutalOldGreedySearcher(this, tw);
    }

    public void init(int time, String s, int tw) {
        this.tw = tw;
        init(time, s);
    }
}
