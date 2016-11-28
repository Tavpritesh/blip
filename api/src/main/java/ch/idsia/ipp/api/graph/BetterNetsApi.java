package ch.idsia.ipp.api.graph;


import ch.idsia.ipp.core.common.graph.BetterNets;

import java.util.logging.Logger;

/**
 * Update a network file with position conclude (requires graphviz)
 */

public class BetterNetsApi extends NetToGraphApi {

    private static final Logger log = Logger.getLogger(NetToGraphApi.class.getName());

    public BetterNetsApi() {
        ntg = new BetterNets();
    }

    public static void main(String[] args) {
        defaultMain(args, new BetterNetsApi(), log);
    }
}
