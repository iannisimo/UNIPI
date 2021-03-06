package worth.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card {
    private StringProperty card;
    private StringProperty description;

    public Card(String card, String description) {
        this.card = new SimpleStringProperty(card);
        this.description = new SimpleStringProperty(description);
    }

    public String getCard() {
        return this.card.getValue();
    }

    public String getDescription() {
        return this.description.getValue();
    }
}
