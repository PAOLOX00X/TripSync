package org.example;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.Period;

public class Viaggio {

    private int codice;
    private String partenza;
    private String destinazione;
    private String dataInizio;
    private String dataFine;
    private List<MezzoTrasporto> elencoMezzi;
    private List<Tappa> elencoTappe;
    private Map<String, Partecipante> elencoPartecipanti;
    private GestorePartecipazioni gestore;
    private Context context;

    public int getCodice() {
        return codice;
    }

    public String getPartenza() {
        return partenza;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public List<MezzoTrasporto> getElencoMezzi() {
        return elencoMezzi;
    }

    public List<Tappa> getElencoTappe() {
        return elencoTappe;
    }

    public Map<String, Partecipante> getElencoPartecipanti() {
        return elencoPartecipanti;
    }

    public GestorePartecipazioni getGestore() {
        return gestore;
    }

    public Viaggio(int codice, String partenza, String destinazione,  String dataInizio, String dataFine) {
        this.codice = codice;
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.dataInizio=dataInizio;
        this.dataFine=dataFine;
        this.elencoMezzi = new ArrayList<>();
        this.elencoTappe = new ArrayList<>();
        this.elencoPartecipanti = new HashMap<>();
        this.gestore=new GestorePartecipazioni();
    }

    public void aggiungiMezzo(String nome, double costo) {
        MezzoTrasporto mt;
        mt=new MezzoTrasporto(nome, costo);
        elencoMezzi.add(mt);
        System.out.println("Aggiunto il mezzo all'elenco ");
    }

    public Integer verificaTappa(String inizio,String fine){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dataInizio = LocalDateTime.parse(inizio, formatter);
        LocalDateTime dataFine = LocalDateTime.parse(fine, formatter);
        LocalDateTime dataInizioViaggio = LocalDateTime.parse(this.dataInizio + " 00:00", formatter);
        LocalDateTime dataFineViaggio = LocalDateTime.parse(this.dataFine + " 23:59", formatter);

        if(dataInizio.equals(dataFine)){
            throw new CredenzialiNonValideException("Errore: la data di inizio e fine e' la stessa");

        }

        if(dataInizio.isAfter(dataFine)){
            throw new CredenzialiNonValideException("Errore: il software non è una macchina del tempo. Inserire date corrette");

        }

        if (dataInizio.isBefore(dataInizioViaggio) || dataFine.isAfter(dataFineViaggio)) {
            throw new CredenzialiNonValideException("Errore: Le date della tappa devono essere comprese tra " + this.dataInizio + " e " + this.dataFine);

        }

        for (Tappa tappa : elencoTappe) {
            if (tappa.getInizio().equals(inizio) && tappa.getFine().equals(fine)) {
                throw new CredenzialiNonValideException("Errore: Esiste già una tappa con le stesse date e orari.");

            }

        }
        return 1;
    }


    public void aggiungiTappa(String luogo, String inizio, String fine, double costo) {
        if(verificaTappa(inizio, fine)==1){
            Tappa t=new Tappa(luogo, inizio, fine, costo);
            elencoTappe.add(t);
            System.out.println("Tappa aggiunta correttamente all'elenco");
        }
        else{
            throw new CredenzialiNonValideException("Errore! Impossibile aggiungere la tappa");
        }
    }

    public void confermaPartecipante(String nomeUtente, Partecipante p) {
            if(elencoPartecipanti.containsKey(nomeUtente)){
                throw new ElementoGiaPresenteException("errore! partecipante già presente");
            }
            else {
                elencoPartecipanti.put(nomeUtente, p);
                System.out.println("Partecipante aggiunto correttamente all'elenco ");
                Map<String, StatoPartecipazione> elencoPartecipazioni=gestore.getElencoPartecipazioni();
                StatoInAttesa st=new StatoInAttesa();
                elencoPartecipazioni.put(nomeUtente, st);
            }
    }

    public void visualizzaItinerario() {
        System.out.println("Codice: "+getCodice()+" Partenza: "+getPartenza()+" Destinazione: "+getDestinazione()+" Inizio: "+getDataInizio()+" Fine: "+getDataFine());
        System.out.println("Elenco Mezzi:");
        for(MezzoTrasporto mt: elencoMezzi){
            System.out.println(mt.toString());
        }
        System.out.println("Elenco Tappe:");
        for(Tappa t: elencoTappe){
            System.out.println(t.toString());
        }
    }

    public Tappa selezionaTappa(String luogo, String inizio, String fine, double costo){
            for (Tappa tappaSelezionata: elencoTappe) {
                if (tappaSelezionata.getLuogo().equals(luogo)
                        && tappaSelezionata.getInizio().equals(inizio)
                        && tappaSelezionata.getFine().equals(fine)
                        && tappaSelezionata.getCosto() == costo) {

                    return tappaSelezionata;
                }
            }
            return null;
    }

    public void modificaTappa(Tappa t, String luogo, String inizio, String fine, double costo){
        int i=elencoTappe.indexOf(t);
        if(verificaTappa(inizio, fine)==1){
            elencoTappe.get(i).setLuogo(luogo);
            elencoTappe.get(i).setInizio(inizio);
            elencoTappe.get(i).setFine(fine);
            elencoTappe.get(i).setCosto(costo);
            System.out.println("Modifica effettuata con successo!");
        }
        else{
            throw new CredenzialiNonValideException("Errore! impossibile modificare la tappa!");
        }
    }

    public void eliminaTappa(Tappa t){
        elencoTappe.remove(t);
        System.out.println("Rimozione avvenuta con successo");
    }

    public void confermaPartecipazione(String nomeUtente){
        gestore.confermaPartecipazione(nomeUtente);
    }

    public void annullaPartecipazione(String nomeUtente){
        gestore.annullaPartecipazione(nomeUtente);
    }

    public boolean verificaCredenziali(String nomeUtente, String password){
        Partecipante p=elencoPartecipanti.get(nomeUtente);
        if(p!=null && p.getPassword().equals(password)){
            Map<String, StatoPartecipazione> elencoPartecipazioni=gestore.getElencoPartecipazioni();
            StatoPartecipazione stato=elencoPartecipazioni.get(nomeUtente);
            if(stato instanceof StatoInAttesa || stato instanceof StatoConfermato){
                return true;
            }
            else return false;
        }
        else return false;
    }

    public void calcolaCosto() {
        context = new Context();
        double costoBase = 0;
        for (Tappa t : elencoTappe) {
            costoBase += t.getCosto();
        }
        for (MezzoTrasporto mt : elencoMezzi) {
            costoBase += mt.getCosto();
        }
        int numeroPartecipanti = 0;
        for (Map.Entry<String, StatoPartecipazione> entry : gestore.getElencoPartecipazioni().entrySet()) {
            if (entry.getValue() instanceof StatoConfermato || entry.getValue() instanceof StatoInAttesa) {
                numeroPartecipanti++;
            }
        }
        if (numeroPartecipanti == 0) {
            System.out.println("Non ci sono partecipanti confermati o in attesa.");
            return;
        }
        costoBase=costoBase*numeroPartecipanti;
        if (isFestivo(dataInizio, dataFine)) {
            AumentoFestivita au=new AumentoFestivita();
            context.setStrategy(au);
            costoBase = context.executeStrategy(costoBase);
        }

        double costoPartecipanteMaggiorenne = costoBase / numeroPartecipanti;
        double costoPartecipanteMinorenne = 0.0;
        int numeroMinorenni = 0;

        int numeroMaggiorenni=numeroPartecipanti;

        for (Map.Entry<String, StatoPartecipazione> entry : gestore.getElencoPartecipazioni().entrySet()) {
            if (entry.getValue() instanceof StatoConfermato || entry.getValue() instanceof StatoInAttesa) {
                Partecipante partecipante = elencoPartecipanti.get(entry.getKey());
                if (isMinorenne(partecipante.getDataNascita())) {
                    numeroMinorenni++;
                    numeroMaggiorenni--;
                    PartecipanteMinorenne pm=new PartecipanteMinorenne();
                    context.setStrategy(pm);
                    costoPartecipanteMinorenne = context.executeStrategy(costoPartecipanteMaggiorenne);
                }
            }
        }
        if (numeroMinorenni == 0) {
            System.out.println("Il costo totale del viaggio e' " + costoBase + ". Il costo per un partecipante maggiorenne e' " + costoPartecipanteMaggiorenne + ". Non ci sono partecipanti minorenni.");
        } else {
            costoBase=(costoPartecipanteMinorenne*numeroMinorenni)+(costoPartecipanteMaggiorenne*numeroMaggiorenni);
            System.out.println("Il costo totale del viaggio e' " + costoBase + ". Il costo per un partecipante maggiorenne e' " + costoPartecipanteMaggiorenne + ". Il costo per un partecipante minorenne e' " + costoPartecipanteMinorenne);
        }
    }

    public boolean isMinorenne(String dataNascita) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataNascitaDate = LocalDate.parse(dataNascita, formatter);
        LocalDate oggi = LocalDate.now();
        Period eta = Period.between(dataNascitaDate, oggi);
        return eta.getYears() < 18;
    }

    public boolean isFestivo(String dataInizio, String dataFine) {
        List<String> festivita = List.of("25-12", "01-01", "15-08");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataInizioParsed = LocalDate.parse(dataInizio, formatter);
        LocalDate dataFineParsed = LocalDate.parse(dataFine, formatter);
        LocalDate currentDate = dataInizioParsed;

        while (!currentDate.isAfter(dataFineParsed)) {
            String giornoMese = currentDate.format(DateTimeFormatter.ofPattern("dd-MM"));
            if (festivita.contains(giornoMese)) {
                return true;
            }
            currentDate = currentDate.plusDays(1);
        }
        return false;
    }

}