package dataprocessing;

import fileio.DistributorInputData;

import java.util.ArrayList;

/**
 * The class contains a method that verify if all distributor
 * are bankrupt
 */
public final class CheckBankrupt {
    /**
     * The method iterates through all the distributors and return
     * "true" if they are all bankrupt and "false" otherwise
     */
    public boolean checkDistributorsBankrupt(final ArrayList<DistributorInputData> distributors) {

        for (DistributorInputData distributor : distributors) {
            if (!distributor.getIsBankrupt()) {
                return false;
            }
        }

        return true;
    }
}
