package borneofresh.system;

import borneofresh.model.Promotion;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all promotions available in the BorneoFresh Market system.
 * Provides functionality to add promotions, retrieve active promotions,
 * look up a specific promotion by ID, and display all promotions.
 */
public class PromotionCatalogue {

    private List<Promotion> promotions = new ArrayList<>();

    /**
     * Adds a new promotion to the catalogue.
     *
     * @param p the Promotion to add
     */
    public void addPromotion(Promotion p) {
        promotions.add(p);
    }

    /**
     * Returns a filtered list of only the promotions that are currently active.
     *
     * @return a list of active promotions
     */
    public List<Promotion> getActivePromotions() {
        List<Promotion> active = new ArrayList<>();
        for (Promotion p : promotions) {
            if (p.isActive()) active.add(p);
        }
        return active;
    }

    /**
     * Searches for a promotion by its unique ID.
     *
     * @param promotionId the ID to search for
     * @return the matching Promotion, or null if not found
     */
    public Promotion getPromotionById(String promotionId) {
        for (Promotion p : promotions) {
            if (p.getPromotionId().equals(promotionId)) return p;
        }
        return null;
    }

    /**
     * Displays all promotions in the catalogue to the console,
     * including both active and inactive ones.
     */
    public void displayAllPromotions() {
        System.out.println("===== Promotions =====");
        if (promotions.isEmpty()) {
            System.out.println("No promotions available.");
        } else {
            for (Promotion p : promotions) {
                p.displayInfo();
                System.out.println("-----------------------------");
            }
        }
    }
}