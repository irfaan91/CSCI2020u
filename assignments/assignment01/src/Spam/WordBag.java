package Spam;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by michael on 03/03/16.
 */
public class WordBag {
    private Map<String, Integer> map;
    private double numFiles;

    WordBag(){
        map = new TreeMap<>();
        numFiles = 0;
    }

    public void incrementWord(String word){
        if (!map.containsKey(word)){
            map.put(word, 1);
        } else {
            map.put(word, (map.get(word) + 1) );
        }
    }
    public Integer getWord(String word){
        if (map.containsKey(word)) return map.get(word);
        else return (new Integer(0));
    }

    public Iterator<String> getBagIter(){
        return (map.keySet().iterator());
    }
    public boolean hasWord(String word){
        return map.containsKey(word);
    }
    public void incrementFiles() {
        numFiles += 1;
    }
    public double getNumFiles(){return numFiles;}

}
