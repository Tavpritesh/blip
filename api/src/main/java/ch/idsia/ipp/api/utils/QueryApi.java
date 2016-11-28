package ch.idsia.ipp.api.utils;

import ch.idsia.ipp.api.Api;
import ch.idsia.ipp.core.common.io.DataFileReader;
import ch.idsia.ipp.core.utils.Query;
import ch.idsia.ipp.core.utils.data.hash.TIntIntHashMap;
import org.kohsuke.args4j.Option;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import static ch.idsia.ipp.core.utils.RandomStuff.getDataFileReader;
import static ch.idsia.ipp.core.utils.RandomStuff.getReader;
import static ch.idsia.ipp.core.utils.RandomStuff.pf;

public class QueryApi extends Api {

    private static final Logger log = Logger.getLogger(QueryApi.class.getName());

    @Option(name="-d", required = true, usage="Dataset path")
    protected String ph_dat;

    @Option(name="-e", required = true, usage="Query path")
    protected String ph_evid;

    public static void main(String[] args) {
        defaultMain(args, new QueryApi(), log);
    }

    @Override
    public void exec() throws Exception {
        DataFileReader dat_rd = getDataFileReader(ph_dat);
        TIntIntHashMap q = getEvidence(ph_evid);

        double res = Query.ex(dat_rd, q);
        pf("%.10f", res);
    }

    public TIntIntHashMap getEvidence(String s) throws IOException {
        String[] g = getContent(s);

        TIntIntHashMap evid = new TIntIntHashMap();
        if (g.length <= 1)
            return evid;
        int n = Integer.valueOf(g[0]);
        for (int i = 0; i < n; i++) {
            evid.put(Integer.valueOf(g[1+2*i]), Integer.valueOf(g[2+2*i]));
        }
        return  evid;
    }

    private String[] getContent(String s) throws IOException {
        BufferedReader r = getReader(s);
        String l;
        String cnt = "";
        while ((l = r.readLine()) != null) {
            cnt += l + " ";
        }
        return cnt.trim().split("\\s+");
    }

}
