package model;

import exception.CardException;

import javax.swing.*;
import java.io.*;
import java.util.*;

public final class FlashcardFactory implements Serializable {
    public static int ID = 1;
    public final static String UNCATEGORIZED = "unkategorisiert";
    private final List<Flashcard> cards = new ArrayList<>();
    private List<DeckOld> decks = new ArrayList<>();

    public static FlashcardFactory createOnStart() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("box.ex"))) {
            FlashcardFactory factory = (FlashcardFactory) ois.readObject();
            if(factory.getCards().size() == 0) ID = 1;
            else ID = id(factory);
            return factory;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "box.ex konnte nicht gefunden werden. Es konnten keine flashcards geladen werden.","Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new FlashcardFactory();
    }

    public void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("box.ex"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFromFile(File fileName) throws CardException {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();

            while(line != null) {
                String[] attributes = line.split(";");
                if(attributes.length == 4) {
                    Flashcard card = new Flashcard(attributes[0], attributes[1], attributes[2], attributes[3]);
                    addFlashcard(card);
                }
                else if(attributes.length == 2) {
                    Flashcard card = new Flashcard(attributes[0], attributes[1]);
                    addFlashcard(card);
                }
                else throw new CardException("ungültiges Format");
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportAsCsv(String filename) throws CardException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename));){
            for(Flashcard card : cards) {
                writer.write(card.toCsv());
            }
        } catch (IOException e) {
            throw new CardException("Fehler beim Schreiben " + e.getMessage());
        }
    }

    public void addFlashcard(Flashcard card) throws CardException {
        if(card != null) {
            if(!cards.contains(card)) cards.add(card);
            else throw new CardException("Karte existiert bereits!");
        }
        else throw new CardException("Fehlerhafte Daten!");
    }

    public List<Flashcard> getCards() {
        return cards;
    }

    public List<DeckOld> getDecks() {
        return decks;
    }

    public Vector<String> getTitelCards() {
        Vector<String> titel = new Vector<>();
        titel.add("ID");
        titel.add("Kategorie");
        titel.add("Subkategorie");
        titel.add("Vorderseite");
        titel.add("Rückseite");
        return titel;
    }

    public Vector<Vector<String>> getDatenCards() {
        Vector<Vector<String>> daten = new Vector<>();
        for(Flashcard card : cards) {
            daten.add(card.getDaten());
        }
        return daten;
    }

    public Vector<String> getTitelDecks() {
        Vector<String> titel = new Vector<>();
        titel.add("Name");
        titel.add("Anzahl Karten");
        return titel;
    }

    public Vector<Vector<String>> getDatenDecks() {
        Vector<Vector<String>> daten = new Vector<>();
        for(DeckOld deck : decks) {
            daten.add(deck.getDaten());
        }
        return daten;
    }

    private static int id(FlashcardFactory factory) {
        int max = 0;
        for(Flashcard c : factory.getCards()) {
            if(c.getCardId() > max) max = c.getCardId();
        }
        return ++max;
    }
}
