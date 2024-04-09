package is.vidmot;

import is.vinnsla.Kubbur;
import is.vinnsla.Stykki;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Viðmótsforritun 2024
 *
 *  Lýsing  : Stýriklasi fyrir Kubbur application. Leyfir notanda að
 *  velja tölu og giska á hvaða reit talan er. Gefin eru stig og látið vita hvort leik er lokið
 *
 *
 *****************************************************************************/

public class KubburController {

    // fastar
    public static final String LEIK_LOKID = "Leik lokið";
    public static final String LEIKUR_I_GANGI = "Leikur í gangi";
    // viðmótshlutir
    @FXML
    private Label fxLeikurstada; // staða leiks
    @FXML
    private GridPane fxBord; // borðið
    @FXML
    private HBox fxValmynd;

    // vinnsluhlutur
    private Kubbur kubbur;
    private static Integer validStykkiRod;
    private static Integer validStykkiDalkur;
    private static int puslNr = 0;


    /**
     * Frumstillir stýrieininguna eftir að búið er að lesa inn .fxml skrána
     */
    public void initialize() {
        if(fxValmynd.getChildren().size()>1){
            fxValmynd.getChildren().removeLast();
        }
        fxBord.setDisable(false);
        Stykki.resetRadTala();
        kubbur = new Kubbur(3,3, puslNr);
        validStykkiRod = null;
        validStykkiDalkur = null;
        for(int k=0;k<9;k++){
            Button reitur = (Button) fxBord.getChildren().get(k);
            int i = GridPane.getRowIndex(reitur);
            int j = GridPane.getColumnIndex(reitur);
            eydaMynd(reitur);
            setjaMynd(kubbur.getBordStykki(i,j), reitur);
        }
        // Binda stöðu leiks í vinnslunni við viðmótið

        fxLeikurstada.textProperty().bind(Bindings.createStringBinding(() -> {
            if (kubbur.getFjoldiReita() == 9) {
                fxBord.setDisable(true);
                Button aftur = new Button("Aftur");
                aftur.setPrefSize(174,42);
                aftur.setOnAction(this::samaPusl);
                fxValmynd.getChildren().add(aftur);
                return LEIK_LOKID;
            } else
                return LEIKUR_I_GANGI;
        }, kubbur.fjoldiReitaProperty()));
    }

    @FXML
    private void skiptaPusl(ActionEvent actionEvent){
        puslNr = 1 - puslNr;
        initialize();
    }
    @FXML
    private void samaPusl(ActionEvent actionEvent){
        initialize();
    }

    /**
     * Setur stykki á borðið. Athugar hvort stykkið er á réttum stað.
     * uppfærir stigin
     *
     * @param actionEvent atburður
     */
    public void onSetjaStykki(ActionEvent actionEvent) {
        // hnappurinn sem notandi valdi
        Button reitur = (Button) actionEvent.getSource();

        // hvaða reitur var valinn á borðinu
        int i = GridPane.getRowIndex(reitur);
        int j = GridPane.getColumnIndex(reitur);

        if (validStykkiRod == null) {
            ((Button) actionEvent.getSource()).setDisable(true);
            validStykkiRod = i;
            validStykkiDalkur = j;
        } else if (!(validStykkiRod == i && validStykkiDalkur == j)) {
            vixlaReitum(validStykkiRod, validStykkiDalkur, i, j);
            validStykkiRod = null;
            validStykkiDalkur = null;
        }
    }

    private void vixlaReitum(int i1, int j1, int i2, int j2){
        Button b1 = (Button) fxBord.getChildren().get(i1 + 3*j1);
        Button b2 = (Button) fxBord.getChildren().get(i2 + 3*j2);
        b1.setDisable(false);
        b2.setDisable(false);
        kubbur.vixlaTolum(i1,j1,i2,j2);
        Stykki s1 = kubbur.getBordStykki(i1,j1);
        Stykki s2 = kubbur.getBordStykki(i2,j2);
        eydaMynd(b1);
        eydaMynd(b2);
        setjaMynd(s1, b1);
        setjaMynd(s2, b2);
    }

    /**
     * Eyðir mynd af reit á borði
     *
     * @param reitur hnappurinn sem á að verða með auðri mynd
     */
    private void eydaMynd(Button reitur) {
        if (!reitur.getStyleClass().getLast().equals("button")) {
            reitur.getStyleClass().removeLast();
        }
    }

    /**
     * Skipt er um mynd á hnappi b með mynd á Stykki.
     *
     * @param b      hnappur
     * @param stykki Stykki
     */
    private void setjaMynd(Stykki stykki, Button b) {
        String s = stykki.getMynd();
        b.getStyleClass().add(s);
    }
}