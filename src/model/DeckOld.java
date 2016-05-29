package model;

import exception.CardException;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

public class DeckOld implements Serializable {
    private String name;
    private List<Flashcard> cards;

    public DeckOld(String name, List<Flashcard> cards) throws CardException {
        setName(name);
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws CardException {
        if(name != null && !name.equals("")) this.name = name;
        else throw new CardException("Ungültiger Name: '" + name + "'");
    }

    public List<Flashcard> getCards() {
        return cards;
    }

    public Vector<String> getDaten() {
        Vector<String> daten = new Vector<>();
        daten.add(name);
        daten.add(String.valueOf(cards.size()));
        return daten;
    }
}
