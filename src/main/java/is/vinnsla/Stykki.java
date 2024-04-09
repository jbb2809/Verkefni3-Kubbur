package is.vinnsla;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Vinnsluklasi fyrir Stykki
 *   Hefur nafn á mynd og tölu
 *****************************************************************************/

public class Stykki {

    // static breyta sem heldur utan um raðtölu stykkkja
    private static int radTala; // static raðtala stykkja sem uppfærist þegar nýtt stykki er búið til

    // tilvikskbreytur
    private final String mynd; // nafn á styleClass fyrir stykkið
    private final int rod; // tala á stykki
    private final int dalkur;
    private final int tala;

    /**
     * Smiður sem býr til stykki sem er í staðsetningu (i,j) á borði
     *
     * @param mynd mynd stykkisins
     */
    public Stykki(String mynd, int j, int i) {
        this.mynd = mynd;
        rod = i;
        dalkur = j;
        tala = radTala++;
    }

    // get aðferðir
    public String getMynd() {
        return mynd;
    }

    public int getRod() {
        return rod;
    }
    public int getDalkur() { return dalkur;}
    public int getTala() { return tala;}
    public static void resetRadTala() {radTala = 1;}
}
