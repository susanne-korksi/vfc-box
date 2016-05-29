package view;

import components.*;
import exception.CardException;
import model.Box;
import model.FlashcardFactory;
import model.User;
import model.service.UserModelService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainWindow extends JFrame {
    private JMenuBar menuBar;
    private JMenu file, show, mode, exit, create , csv;
    private JMenuItem card, deck, anImport, anExport, clear, currentCards, currentDecks, learn, test, end;
    private FlashcardFactory flashcardFactory;
    private CardOverview cardOverview;
    private DeckOverview deckOverview;
    private ChooseLoginDialog loginFrame = new ChooseLoginDialog(this);
    private LoginDialog login;
    private UserModelService userService;
    private String dbPath;
    private User user;

    private CardCreator cardCreator;
    private DeckCreator deckCreator;
    private DeleteDialog deleteDialog;
    private ChooseDialog chooseDialog;
    private LearnModeDialog learnModeDialog;
    private TestModeDialog testModeDialog;

    public MainWindow(FlashcardFactory flashcardFactory, Box box, String dbPath) {
        this.flashcardFactory = flashcardFactory;
//        this.cardOverview = new CardOverview(flashcardFactory);
        this.deckOverview = new DeckOverview(flashcardFactory);
        this.dbPath = dbPath;
//        this.userService = new UserModelService(dbPath);
        setTitle("virtual flash-card box");
        setSize((int)(getToolkit().getScreenSize().getWidth() / 2), (int)(getToolkit().getScreenSize().getHeight() / 2));
        getContentPane().setBackground(Color.DARK_GRAY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        addComponents();
        addToolTipps();
        addListener();
        setVisible(true);
    }

    private void initComponents() {
        this.cardOverview = new CardOverview(flashcardFactory, this);
        this.deckOverview = new DeckOverview(flashcardFactory);
        this.cardCreator = new CardCreator(flashcardFactory, this);
        this.deckCreator = new DeckCreator(cardOverview, flashcardFactory, this);
        this.deleteDialog = new DeleteDialog(flashcardFactory, this);
        this.learnModeDialog = new LearnModeDialog(this);
        this.testModeDialog = new TestModeDialog(this);
        this.chooseDialog = new ChooseDialog(flashcardFactory, this, cardOverview, deckOverview, learnModeDialog, testModeDialog);
        menuBar = new JMenuBar();

        file = new JMenu("Datei");
        create = new JMenu("Create");
        card = new JMenuItem("Flashcard");
        deck = new JMenuItem("Sammlung");

        csv = new JMenu("CSV");
        anImport = new JMenuItem("Import");
        anExport = new JMenuItem("Export");

        clear = new JMenuItem("Löschen");

        show = new JMenu("Anzeigen");
        currentCards = new JMenuItem("Karten");
        currentDecks = new JMenuItem("Sammlungen");

        mode = new JMenu("Modus");
        learn = new JMenuItem("Lernen");
        test = new JMenuItem("Prüfen");

        exit = new JMenu("Beenden");
        end = new JMenuItem("Beenden");
    }

    private void addComponents() {
        create.add(card);
        create.add(deck);
        csv.add(anImport);
        csv.add(anExport);
        file.add(create);
        file.addSeparator();
        file.add(csv);
        file.addSeparator();
        file.add(clear);

        mode.add(learn);
        mode.add(test);

        show.add(currentCards);
        show.add(currentDecks);

        exit.add(end);

        menuBar.add(file);
        menuBar.add(mode);
        menuBar.add(show);
        menuBar.add(exit);

        add(menuBar, BorderLayout.NORTH);
    }

    private void addToolTipps() {
        card.setToolTipText("Erzeugen einer neuen Flashcard");
        deck.setToolTipText("Erzeugen einer neuen Sammlung");
        anImport.setToolTipText("Erzeugen neuer Flashcards aus einer Datei");
        anExport.setToolTipText("Exportieren bestehender Flashcards");
        learn.setToolTipText("Lernmodus starten");
        test.setToolTipText("Prüfungsmodus starten");
        clear.setToolTipText("Löscht vorhandene Flashcards/Sammlungen");
        currentCards.setToolTipText("Anzeigen vorhandener Flashcards");
        currentDecks.setToolTipText("Anzeigen vorhandener Sammlungen");
        end.setToolTipText("Programm beenden");
    }

    private void addListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                end();
            }
        });
        
        addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				login.setVisible(true);
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
			}
		});
        
        login.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
            	if (!login.isConfirmed()) {
            		end();
            	}
            }
        });

        card.addActionListener(event -> createCard());
        deck.addActionListener(event -> createDeck());

        anImport.addActionListener(e -> {
            JFileChooser fileopen = new JFileChooser("C:\\Users\\Alex\\Documents\\vfc");
            if (fileopen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                try {
                    flashcardFactory.createFromFile(file);
                    if(cardOverview.isVisible()) cardOverview.update();
                } catch (CardException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        anExport.addActionListener(e -> {
            JFileChooser fileExport = new JFileChooser("C:\\Users\\Alex\\Documents\\vfc");
            if(fileExport.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    flashcardFactory.exportAsCsv(fileExport.getSelectedFile().getAbsolutePath());
                } catch (CardException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clear.addActionListener(e -> delete());

        learn.addActionListener(e -> {
            clearView();
            chooseDialog.updateAndShow(0);
        });
        test.addActionListener(e -> {
            clearView();
            chooseDialog.updateAndShow(1);
        });

        currentCards.addActionListener(e -> {
            clearView();
            add(cardOverview);
            cardOverview.updateAndShow();
        });

        currentDecks.addActionListener(event -> {
            clearView();
            add(deckOverview);
            deckOverview.updateAndShow();
        });

        end.addActionListener(event -> end());
    }

    private void createCard() {
        clearView();
        cardCreator.updateAndShow();
        cardOverview.update();
    }

    private void createDeck() {
        clearView();
        deckCreator.updateAndShow();
        deckOverview.update();
    }

    private void delete() {
        clearView();
        deleteDialog.updateAndShow();
    }

    public void end() {
        flashcardFactory.save();
        System.exit(0);
    }

    private void clearView() {
        if(deckOverview.isVisible()) deckOverview.setVisible(false);
        if(cardOverview.isVisible()) cardOverview.setVisible(false);
    }

    public void saveClose(MainWindow mainwindow) throws IllegalArgumentException {
    	if (mainwindow == this) {    		
    		this.end();
    	} else {
    		throw new IllegalArgumentException();
    	}
    }
    
    public String getDbPath() {
    	return this.dbPath;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }

}
