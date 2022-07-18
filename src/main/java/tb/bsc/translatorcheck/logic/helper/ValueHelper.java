package tb.bsc.translatorcheck.logic.helper;


import java.util.Random;

public class ValueHelper {

    public static int getLottery(int size,int currentIndex) {
        int lottery = 0;

        if(size > 1)
        {
            Random rand = new Random();
            int found =rand.nextInt(0,size -1); // -1 weil index bei 0 anfaengt
            while (found == currentIndex){
                found = rand.nextInt(0,size -1 ); // -1 weil index bei 0 anfaengt
            }

            lottery = found;
        }
        return lottery;
    }
}
