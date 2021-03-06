package ch.idsia.blip.core.common;

public class DataSet {

    // Number of variables
    public int n_var;

    // List of names of variables
    public String[] l_s_names;

    // List of arities of variables
    public int[] l_n_arity;

    // Number of datapoints in sample
    public int n_datapoints;

    // For each variable, and for each value, an array of all the row where that variable appears with that value.
    public int[][][] row_values;

    // For each variable with missing data, array of row with missing value
    public int[][] missing_l;

}
