package fileio;

import java.util.List;
/**
 * Entity that meets the output requirements for producers monthly stats
 */
public final class MonthlyStats {
    final int month;
    final List<Integer> distributorsIds;

    public MonthlyStats(final int month, final List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    public int getMonth() {
        return month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    /**
     * It helped me debugging
     */
    public String toString() {
        return "{"
                + " month = " + month + '\''
                + ", distributorsIds = " + distributorsIds + '}' + '\n';
    }
}
