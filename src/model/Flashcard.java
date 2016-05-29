package model;

import exception.CardException;

import java.io.Serializable;
import java.util.Vector;

public class Flashcard implements Serializable 
{
    private int cardId;
    private String category;
    private String subCategory;
    private String front;
    private String back;
    private int calls;
    private int correctAnswered;

    public Flashcard(String category, String subCategory, String front, String back) throws CardException {
        this.cardId = FlashcardFactory.ID;
        setCategory(category);
        setSubCategory(subCategory);
        setFront(front);
        setBack(back);
        FlashcardFactory.ID++;
    }

    public Flashcard(String front, String back) throws CardException {
        this(FlashcardFactory.UNCATEGORIZED, FlashcardFactory.UNCATEGORIZED, front, back);
    }

    public int getCardId() {
        return cardId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if(category != null && !category.equals("")) this.category = category;
        else this.category = FlashcardFactory.UNCATEGORIZED;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        if(subCategory != null && !subCategory.equals("")) this.subCategory = subCategory;
        else this.subCategory = FlashcardFactory.UNCATEGORIZED;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) throws CardException {
        if(front != null && !front.equals("")) this.front = front;
        else throw new CardException("Fehlende Angabe zur Kartenvorderseite");
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) throws CardException {
        if(back != null && !back.equals("")) this.back = back;
        else throw new CardException("Fehlende Angabe zur Kartenrückseite");
    }

    public int getCalls() {
        return calls;
    }

    public void setCalls(int calls) {
        this.calls = calls;
    }

    public int getCorrectAnswered() {
        return correctAnswered;
    }

    public void setCorrectAnswered(int correctAnswered) {
        this.correctAnswered = correctAnswered;
    }

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(category).append(";");
        sb.append(subCategory).append(";");
        sb.append(front).append(";");
        sb.append(back).append("\n");
        return sb.toString();
    }

    public Vector<String> getDaten() {
        Vector<String> daten = new Vector<>();
        daten.add(String.valueOf(getCardId()));
        daten.add(getCategory());
        daten.add(getSubCategory());
        daten.add(getFront());
        daten.add(getBack());
        return daten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flashcard flashcard = (Flashcard) o;

        if (category != null ? !category.equals(flashcard.category) : flashcard.category != null) return false;
        if (subCategory != null ? !subCategory.equals(flashcard.subCategory) : flashcard.subCategory != null)
            return false;
        if (front != null ? !front.equals(flashcard.front) : flashcard.front != null) return false;
        return back != null ? back.equals(flashcard.back) : flashcard.back == null;

    }
}
