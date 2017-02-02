package ch.idsia.ipp.api.utils;


import ch.idsia.ipp.api.Api;
import ch.idsia.ipp.core.common.BayesianNetwork;
import ch.idsia.ipp.core.common.io.DataFileReader;
import ch.idsia.ipp.core.learn.param.ParLe;
import org.kohsuke.args4j.Option;

import java.util.logging.Logger;

import static ch.idsia.ipp.core.utils.RandomStuff.*;


/**
 * ParLe - Parameter Learner
 */

public class ParLeApi extends Api {

    private static final Logger log = Logger.getLogger(ParLeApi.class.getName());

    @Option(name="-r", required = true, usage="Network structure")
    protected String ph_res;

    @Option(name="-d", required = true, usage="Dataset path")
    protected String ph_dat;

    @Option(name="-n", required = true, usage="Output path")
    protected String ph_network;

    @Option(name="-a", usage="Equivalent sample size")
    protected double d_alpha = 1.0;

    @Option(name="-e", usage="Epsilon")
    protected double epsilon = 0.5;

    @Option(name="-t", usage="Method for learning")
    protected String s_method = "bayes";

    public static void main(String[] args) {
        defaultMain(args, new ParLeApi(), log);
    }

    /**
     * Get the correct value for the algorithm to use from a description.
     *
     * @param t description of the method
     * @return correct method enum
     */
    private static Method getMethodValueOf(String t) {
        if ("bayes".equals(t)) {
            return Method.Bayes;
        } else if ("mle".equals(t)) {
            return Method.Mle;
        } else if ("weight".equals(t)) {
            return Method.Ent;
        } else if ("avg".equals(t)) {
            return Method.Avg;
        } else if ("min".equals(t)) {
            return Method.Min;
        } else if ("cano".equals(t)) {
            return Method.Cano;
        }

        log.severe("No valid method selected!");
        return null;
    }


    @Override
    public void exec() throws Exception {

        BayesianNetwork res = getBayesianNetwork(ph_res);
        DataFileReader dat_rd = getDataFileReader(ph_dat);

        ParLe parLe = new ParLe.ParLeBayes(d_alpha);
        parLe.verbose = verbose;
               /*
                Method method = getMethodValueOf(s_method);

         if (method == Method.Ent) {
                parLe = new ParLe.ParLeEnt(d_alpha, epsilon);
            } else if (method == Method.Mle) {
                parLe = new ParLe.ParLeMle();
            } else if (method == Method.Avg) {
                parLe = new ParLe.ParLeAvg(5);
            } else if (method == Method.Cano) {
                parLe = new ParLe.ParLeCano(5);
            } else {
                parLe = new ParLe.ParLeBayes(d_alpha);
            } */

        BayesianNetwork newBn = parLe.go(res, dat_rd);

        for (String ext : new String[]{".net", ".erg", ".uai"})
            writeBayesianNetwork(newBn, ph_network + ext);
    }

    public enum Method {
        Bayes, Ent, Mle, Avg, Min, Cano
    }

}