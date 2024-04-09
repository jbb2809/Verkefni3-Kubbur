package is.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import java.util.Random;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Vðmótsforritun 2024
 *
 *  Lýsing  : Vinnsluklasi fyrir Kubbur
 *  Hefur random borð af stykkjum
 *  heldur utan um stig í leik og hvort honum er lokið eða í gangi
 *
 *****************************************************************************/


public class Kubbur {

    // fastar
    public static final int RADIR = 3;
    public static final int DALKAR = 3;

    // fjöldi reita sem eru rétt giskaðir
    private final IntegerProperty fjoldiReitaProperty = new SimpleIntegerProperty(0);


    // nöfn á myndunum - notað fyrir StyleClass
    private final String[][] mynd = {{"nullnull", "nulleinn", "nulltveir"},
            {"einnnull", "einneinn", "einntveir"},
            {"tveirnull", "tveireinn", "tveirtveir"}};

    // random hlutur til að rugla stykkjunum
    private final Random random = new Random();

    // Platan með random stykkjum
    private Stykki[][] kubburBord;

    // fylki sem heldur utan um það hvort stykki er á réttum stað
    private boolean[] retturStadur = {false,false,false,false,false,false,false,false,false};

    
    /**
     * Smiður fyrir kubb með radir og dalkar
     * Býr til stykkin og kubbaborðið
     *
     * @param radir  fjöldi raða
     * @param dalkar fjöldi dálka
     * @param pusl nr púsls
     */
    public Kubbur(int radir, int dalkar, int pusl) {
        // búa til stykkja og setja á random borð og í röð í fylki
        frumstillaKubb(radir, dalkar, pusl);
    }

    /**
     * Búin til stykki og sett á kubbaborðið í slembiröð
     *
     * @param radir  fjöldi raða
     * @param dalkar fjöldi dálka
     * @param pusl nr púsls
     */
    private void frumstillaKubb(int radir, int dalkar, int pusl) {
        // búa til tvívítt fylki radir x dalkar
        kubburBord = new Stykki[radir][dalkar];

        // búa til stykkin og setja á random borðið
        for (int i = 0; i < radir; i++) {
            for (int j = 0; j < dalkar; j++) {
                // búa til streng sem ákveður mynd í púsli
                String puslMynd = mynd[i][j] + pusl;
                // búa til stykki
                Stykki s = new Stykki(puslMynd,i,j);
                // setja stykkið á random borðið
                setjaRandom(s);
            }
        }
    }

    public void vixlaTolum(int i1, int j1, int i2, int j2) {
        Stykki s1 = getBordStykki(i1, j1);
        Stykki s2 = getBordStykki(i2, j2);
        setjaStykki(i1,j1,s2);
        setjaStykki(i2,j2,s1);
    }

    /**
     * Setur stykki á slembinn hátt á random borðið
     *
     * @param stykki stykki sem á að setja
     */
    private void setjaRandom(Stykki stykki) {
        boolean fundinn = false;
        while (!fundinn) {
            int i = random.nextInt(DALKAR); // random röðin
            int j = random.nextInt(RADIR); // random dálkurinn
            if (kubburBord[i][j] == null) { // ef sellan er auð er stykkið sett í selluna
                setjaStykki(i,j,stykki);
                fundinn = true;
            }
        }
    }

    // get aðferði fyrir fjölda reita sem eru réttir
    public IntegerProperty fjoldiReitaProperty() {
        return fjoldiReitaProperty;
    }

    /**
     * Setja stykki á borðið og athuga hvort það er á réttum stað. Ef svo er hækkar fjöldi reita um einn
     *
     * @param i      röð á borðinu
     * @param j      dálkur á borðinu
     * @param stykki stykkið sem er sett á borðið
     */
    public void setjaStykki(int i, int j, Stykki stykki) {
        // athuga hvort stykkið er á réttum stað
        boolean rett = i == stykki.getRod() && j == stykki.getDalkur();

        //setjum stykkið í borðið
        kubburBord[i][j] = stykki;

        // uppfæra fjölda reita
        uppfaeraFjoldaReita (rett, stykki.getTala());
    }

    /**
     * Hækka fjölda reita rétt giskaðra reita um einn
     * @param rett er giskað rétt
     */
    private void uppfaeraFjoldaReita(boolean rett, int tala) {
        retturStadur[tala-1] = rett;
        int fjoldi = 0;
        for(boolean rettur: retturStadur){
            if (rettur){
                fjoldi++;
            }
        }
        fjoldiReitaProperty.setValue(fjoldi);
    }

    /**
     * nær í stykki á random borðinu
     *
     * @param i röðin
     * @param j dálkurinn
     * @return stykkinu
     */

    public Stykki getBordStykki(int i, int j) {
        return kubburBord[i][j];
    }

    /**
     * Nær í fjölda rétt giskaðra reita
     * @return heiltala
     */
    public int  getFjoldiReita() {
        return fjoldiReitaProperty.getValue();
    }
}
