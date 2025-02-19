package org.example;
import org.example.TripSync;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JFrame {

    private TripSync tripSync;

    public Main() {
        super("TripSync Application");
        this.tripSync = TripSync.getInstance();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new FlowLayout());

        JButton adminButton = new JButton("Accedi come AMMINISTRATORE");
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminFrame(tripSync).setVisible(true);
            }
        });

        JButton userButton = new JButton("Accedi come PARTECIPANTE");
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserFrame(tripSync).setVisible(true);
            }
        });

        add(adminButton);
        add(userButton);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main gui = new Main();
            gui.setVisible(true);
        });
    }
}

class AdminFrame extends JFrame {
    private TripSync tripSync;

    public AdminFrame(TripSync tripSync) {
        super("Menu Amministratore");
        this.tripSync = tripSync;

        setSize(400, 300);
        setLayout(new GridLayout(7, 1));

        JButton uc1Button = new JButton("Crea Itinerario");
        JButton uc2Button = new JButton("Inserisci Partecipante");
        JButton uc3Button = new JButton("Visualizza Itinerario");
        JButton uc4Button = new JButton("Modifica Tappa");
        JButton uc7Button = new JButton("Visualizza Catalogo");
        JButton uc8Button = new JButton("Rimuovi Tappa");
        JButton uc9Button = new JButton("Calcola Costo");
        JButton closeButton = new JButton("Chiudi");

        uc1Button.addActionListener(e -> uc1CreaItinerario());
        uc2Button.addActionListener(e -> uc2InserisciPartecipante());
        uc3Button.addActionListener(e -> uc3VisualizzaItinerarioAmministratore());
        uc4Button.addActionListener(e -> uc4ModificaTappa());
        uc7Button.addActionListener(e -> uc7VisualizzaCatalogo());
        uc8Button.addActionListener(e -> uc8RimuoviTappa());
        uc9Button.addActionListener(e -> uc9CalcolaCosto());

        closeButton.addActionListener(e -> dispose());

        add(uc1Button);
        add(uc2Button);
        add(uc3Button);
        add(uc4Button);
        add(uc7Button);
        add(uc8Button);
        add(uc9Button);
        add(closeButton);

        setLocationRelativeTo(null);
    }

    private void mostraOutputConsole(String titolo) {
        String output = ConsoleOutputCapturer.stopCapture();
        if (output.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun output da mostrare.", titolo, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextArea textArea = new JTextArea(output);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setBackground(new Color(245, 245, 245));
        textArea.setForeground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));



        JOptionPane.showMessageDialog(this, textArea, titolo, JOptionPane.INFORMATION_MESSAGE);
    }


    private void uc1CreaItinerario() {
        boolean viaggioCreato = false;
        while(!viaggioCreato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio (int):");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            String partenza = JOptionPane.showInputDialog(this, "Partenza:");
            if (partenza == null) return;
            String destinazione = JOptionPane.showInputDialog(this, "Destinazione:");
            if (destinazione == null) return;
            String dataInizio = JOptionPane.showInputDialog(this, "Data inizio (yyyy-MM-dd):");
            if (dataInizio == null) return;
            String dataFine = JOptionPane.showInputDialog(this, "Data fine (yyyy-MM-dd):");
            if (dataFine == null) return;

            try {
                ConsoleOutputCapturer.startCapture();
                tripSync.creaViaggio(codice, partenza, destinazione, dataInizio, dataFine);
                mostraOutputConsole("Creazione viaggio");
                viaggioCreato=true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }

        }


        boolean aggiuntaConclusa = false;
        while (!aggiuntaConclusa) {
            String[] options = {"Aggiungi Mezzo", "Aggiungi Tappa", "Fine"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Vuoi aggiungere un Mezzo o una Tappa?",
                    "Aggiunta Itinerario",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                String nomeMezzo = JOptionPane.showInputDialog(this, "Nome del mezzo:");
                if (nomeMezzo == null) continue;
                String costoStr = JOptionPane.showInputDialog(this, "Costo del mezzo (double):");
                if (costoStr == null) continue;
                double costoMezzo = Double.parseDouble(costoStr);

                ConsoleOutputCapturer.startCapture();
                tripSync.aggiungiMezzo(nomeMezzo, costoMezzo);
                mostraOutputConsole("Aggiunta mezzo");

            } else if (choice == 1) {
                boolean tappaValida = false;
                while (!tappaValida) {
                    String luogo = JOptionPane.showInputDialog(this, "Luogo tappa:");
                    if (luogo == null) continue;
                    String inizio = JOptionPane.showInputDialog(this, "Data inizio (yyyy-MM-dd HH:mm):");
                    if (inizio == null) continue;
                    String fine = JOptionPane.showInputDialog(this, "Data fine (yyyy-MM-dd HH:mm):");
                    if (fine == null) continue;
                    String cTappa = JOptionPane.showInputDialog(this, "Costo tappa (double):");
                    if (cTappa == null) continue;
                    double costoTappa = Double.parseDouble(cTappa);


                    try {
                        ConsoleOutputCapturer.startCapture();
                        tripSync.aggiungiTappa(luogo, inizio, fine, costoTappa);
                        mostraOutputConsole("Creazione viaggio");
                        tappaValida=true;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                aggiuntaConclusa = true;
            }
        }
        ConsoleOutputCapturer.startCapture();
        tripSync.confermaInserimento();
        mostraOutputConsole("Conferma itinerario");
    }

    private void uc2InserisciPartecipante() {
        boolean viaggioTrovato=false;

        while(!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggio(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio non trovato!");

            }else{
                viaggioTrovato=true;
            }
        }

        boolean partecipanteInserito = false;
        while (!partecipanteInserito) {
            String nomeU = JOptionPane.showInputDialog(this, "Nome utente del partecipante:");
            if (nomeU == null) return;

            if (tripSync.inserisciPartecipante(nomeU) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Partecipante non trovato!");
                continue;
            }
            int conferma = JOptionPane.showConfirmDialog(this, "Confermare l'inserimento del partecipante?", "Conferma", JOptionPane.YES_NO_OPTION);
            if (conferma == JOptionPane.YES_OPTION) {
                try{
                    ConsoleOutputCapturer.startCapture();
                    tripSync.confermaPartecipante();
                    mostraOutputConsole("Partecipante Inserito");
                    partecipanteInserito=true;
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }
    private void uc3VisualizzaItinerarioAmministratore() {
        boolean viaggioTrovato=false;
        while(!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggio(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio non trovato!");

            }else{
                viaggioTrovato=true;
            }
        }
        ConsoleOutputCapturer.startCapture();
        tripSync.visualizzaItinerario();
        mostraOutputConsole("Itinerario");
    }

    private void uc4ModificaTappa() {
        boolean viaggioTrovato=false;

        while(!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggio(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio non trovato!");

            }else{
                viaggioTrovato=true;
            }
        }
        Tappa tappaSelezionata;
        boolean tappaTrovata=false;
        while (!tappaTrovata) {
            String luogo = JOptionPane.showInputDialog(this, "Luogo tappa da modificare:");
            if (luogo == null) return;
            String inizio = JOptionPane.showInputDialog(this, "Data inizio tappa:");
            if (inizio == null) return;
            String fine = JOptionPane.showInputDialog(this, "Data fine tappa:");
            if (fine == null) return;
            String costStr = JOptionPane.showInputDialog(this, "Costo tappa:");
            if (costStr == null) return;
            double cost = Double.parseDouble(costStr);
            tappaSelezionata = tripSync.selezionaTappa(luogo, inizio, fine, cost);
            if (tappaSelezionata == null) {
                JOptionPane.showMessageDialog(this, "Errore: Tappa non trovata!", "Errore", JOptionPane.ERROR_MESSAGE);

            } else {
                tappaTrovata = true;
            }
        }

        boolean tappaInserita=false;

        while (!tappaInserita){
                String nuovoLuogo = JOptionPane.showInputDialog(this, "Nuovo luogo:");
                if (nuovoLuogo == null) return;
                String nuovoInizio = JOptionPane.showInputDialog(this, "Nuovo inizio (yyyy-MM-dd HH:mm):");
                if (nuovoInizio == null) return;
                String nuovoFine = JOptionPane.showInputDialog(this, "Nuovo fine (yyyy-MM-dd HH:mm):");
                if (nuovoFine == null) return;
                String nuovoCostStr = JOptionPane.showInputDialog(this, "Nuovo costo:");
                if (nuovoCostStr == null) return;
                double nuovoCost = Double.parseDouble(nuovoCostStr);

                try{
                    ConsoleOutputCapturer.startCapture();
                    tripSync.modificaTappa(nuovoLuogo, nuovoInizio, nuovoFine, nuovoCost);
                    mostraOutputConsole("Modifica tappa");
                    tappaInserita=true;
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }



    }

    private void uc7VisualizzaCatalogo() {
        Map<Integer, ViaggioEffettuato> elencoViaggi = tripSync.getElencoViaggiEffettuati();

        if (elencoViaggi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non ci sono viaggi effettuati disponibili.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Map.Entry<Integer, ViaggioEffettuato> entry : elencoViaggi.entrySet()) {
            tripSync.selezionaViaggioEffettuato(entry.getKey());
            ConsoleOutputCapturer.startCapture();
            tripSync.visualizzaItinerarioPassato();
            mostraOutputConsole("Itinerari passati");
        }
    }

    private void uc8RimuoviTappa() {
        boolean viaggioTrovato=false;

        while(!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggio(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio non trovato!");

            }else{
                viaggioTrovato=true;
            }
        }

        boolean tappaTrovata=false;
        while (!tappaTrovata){
            String luogo = JOptionPane.showInputDialog(this, "Luogo tappa da modificare:");
            if (luogo == null) return;
            String inizio = JOptionPane.showInputDialog(this, "Data inizio tappa:");
            if (inizio == null) return;
            String fine = JOptionPane.showInputDialog(this, "Data fine tappa:");
            if (fine == null) return;
            String costStr = JOptionPane.showInputDialog(this, "Costo tappa:");
            if (costStr == null) return;
            double cost = Double.parseDouble(costStr);

            if (tripSync.selezionaTappa(luogo, inizio, fine, cost) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Tappa non trovata!", "Errore", JOptionPane.ERROR_MESSAGE);

            }else{
                tappaTrovata=true;
            }
        }

        int conferma = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler rimuovere questa tappa?", "Conferma", JOptionPane.YES_NO_OPTION);
        if (conferma == JOptionPane.YES_OPTION) {
            ConsoleOutputCapturer.startCapture();
            tripSync.eliminaTappa();
            mostraOutputConsole("Tappa eliminata");
        }
    }

    private void uc9CalcolaCosto() {
        boolean viaggioTrovato=false;

        while(!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggio(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio non trovato!");

            }else{
                viaggioTrovato=true;
            }
        }
        ConsoleOutputCapturer.startCapture();
        tripSync.calcolaCosto();
        mostraOutputConsole("Costo del viaggio");
    }


}

class UserFrame extends JFrame {
    private TripSync tripSync;

    public UserFrame(TripSync tripSync) {
        super("Menu Partecipante");
        this.tripSync = tripSync;

        setSize(400, 200);
        setLayout(new GridLayout(6, 1));
        JButton uc3Button = new JButton("Visualizza Itinerario");
        JButton uc5Button = new JButton("Conferma/Annulla Partecipazione");
        JButton uc6Button = new JButton("Inserisci Feedback");
        JButton uc7Button = new JButton("Visualizza Catalogo");
        JButton uc9Button = new JButton("Calcola Costo");
        JButton closeButton = new JButton("Chiudi");

        uc3Button.addActionListener(e -> uc3VisualizzaItinerarioPartecipante());
        uc5Button.addActionListener(e -> uc5GestisciPartecipazione());
        uc6Button.addActionListener(e -> uc6InserisciFeedback());
        uc7Button.addActionListener(e -> uc7VisualizzaCatalogoPartecipante());
        uc9Button.addActionListener(e -> uc9CalcolaCostoPartecipante());
        closeButton.addActionListener(e -> dispose());

        add(uc3Button);
        add(uc5Button);
        add(uc6Button);
        add(uc7Button);
        add(uc9Button);
        add(closeButton);
        setLocationRelativeTo(null);
    }

    private void mostraOutputConsole(String titolo) {
        String output = ConsoleOutputCapturer.stopCapture();
        if (output.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun output da mostrare.", titolo, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextArea textArea = new JTextArea(output);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setBackground(new Color(245, 245, 245));
        textArea.setForeground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));




        JOptionPane.showMessageDialog(this, textArea, titolo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void uc3VisualizzaItinerarioPartecipante() {
        boolean viaggioTrovato=false;

        while(!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggio(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio non trovato!");

            }else{
                viaggioTrovato=true;
            }
        }

        boolean partecipanteTrovato = false;
        while (!partecipanteTrovato){
            String nomeUtente = JOptionPane.showInputDialog(this, "Nome utente:");
            if (nomeUtente == null) return;
            String password = JOptionPane.showInputDialog(this, "Password:");
            if (password == null) return;

            if (!tripSync.verificaCredenziali(nomeUtente, password)) {
                JOptionPane.showMessageDialog(this, "Errore: Credenziali non valide o partecipante non registrato a questo viaggio.", "Errore", JOptionPane.ERROR_MESSAGE);

            }else{
                partecipanteTrovato=true;
            }
        }
        ConsoleOutputCapturer.startCapture();
        tripSync.visualizzaItinerario();
        mostraOutputConsole("Itinerario");
    }

    private void uc5GestisciPartecipazione() {
        boolean viaggioTrovato=false;

        while(!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggio(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio non trovato!");

            }else{
                viaggioTrovato=true;
            }
        }

        boolean partecipanteTrovato=false;



        while (!partecipanteTrovato){
            String nomeUtente = JOptionPane.showInputDialog(this, "Nome utente:");
            if (nomeUtente == null) return;
            String password = JOptionPane.showInputDialog(this, "Password:");
            if (password == null) return;

            if (tripSync.inserisciCredenziali(nomeUtente, password, "corrente") == null) {
                JOptionPane.showMessageDialog(this, "Credenziali non valide o partecipante non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);

            }else{
                partecipanteTrovato=true;
            }

            String[] options = {"Conferma Partecipazione", "Annulla Partecipazione"};
            int scelta = JOptionPane.showOptionDialog(
                    this,
                    "Vuoi confermare o annullare la partecipazione?",
                    "Gestione Partecipazione",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (scelta == 0) {
                ConsoleOutputCapturer.startCapture();
                tripSync.confermaPartecipazione(nomeUtente);
                mostraOutputConsole("Conferma partecipazione");
            } else if (scelta == 1) {
                ConsoleOutputCapturer.startCapture();
                tripSync.annullaPartecipazione(nomeUtente);
                mostraOutputConsole("Annulla partecipazione");
            }




        }




    }

    private void uc6InserisciFeedback() {
        boolean viaggioTrovato=false;

        while (!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio passato:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggioEffettuato(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio passato non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);

            }else{
                viaggioTrovato=true;
            }
        }

        boolean partecipanteTrovato=false;
        while(!partecipanteTrovato){
            String user = JOptionPane.showInputDialog(this, "Nome utente partecipante:");
            if (user == null) return;
            String pass = JOptionPane.showInputDialog(this, "Password:");
            if (pass == null) return;

            if (tripSync.inserisciCredenziali(user, pass, "effettuato") == null) {
                JOptionPane.showMessageDialog(this, "Errore: Credenziali non valide o utente non associato a questo viaggio!", "Errore", JOptionPane.ERROR_MESSAGE);

            }else{
                partecipanteTrovato=true;
            }
        }


        boolean feedbackValido = false;
        while (!feedbackValido) {
            String stelleStr = JOptionPane.showInputDialog(this, "Numero stelle (0..5):");
            if (stelleStr == null) return;
            int stelle = Integer.parseInt(stelleStr);

            String desc = JOptionPane.showInputDialog(this, "Descrizione del feedback:");
            if (desc == null) return;

            if (tripSync.inserisciFeedback(stelle, desc) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Feedback non valido. Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                feedbackValido = true;
            }
        }
        ConsoleOutputCapturer.startCapture();
        tripSync.confermaFeedback();
        mostraOutputConsole("Conferma feedback");
    }

    private void uc7VisualizzaCatalogoPartecipante() {
        Map<Integer, ViaggioEffettuato> elencoViaggi = tripSync.getElencoViaggiEffettuati();

        if (elencoViaggi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non ci sono viaggi effettuati disponibili.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Map.Entry<Integer, ViaggioEffettuato> entry : elencoViaggi.entrySet()) {
            tripSync.selezionaViaggioEffettuato(entry.getKey());
            ConsoleOutputCapturer.startCapture();
            tripSync.visualizzaItinerarioPassato();
            mostraOutputConsole("Itinerario passato");
        }

        JOptionPane.showMessageDialog(this, "Viaggi visualizzati su console");
    }

    private void uc9CalcolaCostoPartecipante() {
        boolean viaggioTrovato=false;

        while (!viaggioTrovato){
            String codStr = JOptionPane.showInputDialog(this, "Codice viaggio:");
            if (codStr == null) return;
            int codice = Integer.parseInt(codStr);

            if (tripSync.selezionaViaggio(codice) == null) {
                JOptionPane.showMessageDialog(this, "Errore: Viaggio non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);

            }else{
                viaggioTrovato=true;
            }
        }
        boolean partecipanteTrovato=false;
        while (!partecipanteTrovato){
            String nomeUtente = JOptionPane.showInputDialog(this, "Nome utente:");
            if (nomeUtente == null) return;
            String password = JOptionPane.showInputDialog(this, "Password:");
            if (password == null) return;

            if (!tripSync.verificaCredenziali(nomeUtente, password)) {
                JOptionPane.showMessageDialog(this, "Errore: Credenziali non valide o partecipante non registrato a questo viaggio.", "Errore", JOptionPane.ERROR_MESSAGE);

            }else{
                partecipanteTrovato=true;
            }
        }
        ConsoleOutputCapturer.startCapture();
        tripSync.calcolaCosto();
        mostraOutputConsole("Costo del viaggio");
    }

}

