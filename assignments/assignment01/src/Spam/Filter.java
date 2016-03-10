package Spam;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Filter
 *
 * Designed to do the training and filtering for our spam detector
 *
 * Word counting code provided by Randy Fourtier
 */
public class Filter {
    private WordBag spamBag;
    private WordBag hamBag;
    private TreeMap<String, Double> probMap;
    private boolean probChange;

    public Filter(){
        spamBag = new WordBag();
        hamBag = new WordBag();
        probMap = new TreeMap<>();
        probChange = true;
    }

    private void _train(File file, WordBag bag) throws IOException{
        if (file.isDirectory()){
            // process every file in directory
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++){
                _train(filesInDir[i], bag);
            }
        } else if (file.exists()) {
            // load all of the data, and process it into words
            bag.incrementFiles();
            Scanner scan = new Scanner(file);
            while (scan.hasNext()){
                String word = scan.next().toLowerCase();
                if (isWord(word)) {
                    bag.incrementWord(word);

                }
            }
            scan.close();
        }
    }

    public void trainSpam(File file){
        probChange = true;
        try {
            _train(file, spamBag);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void trainHam(File file){
        probChange = true;
        try {
            _train(file, hamBag);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public double spamProb(String word){
        return (spamBag.getWord(word) / spamBag.getNumFiles());
    }

    public double hamProb(String word){
        return (hamBag.getWord(word) / hamBag.getNumFiles());
    }

    private void genSpamProb(){
        if (!probChange) return;
        String curr;
        double spPr = 0;
        // spam and ham may not have the same
        // keyspace, so we need to iterate over both spaces
        Iterator<String> iter = spamBag.getBagIter();
        while (iter.hasNext()){
            curr = iter.next();
            spPr = spamProb(curr);
            probMap.put(curr,
                    (spPr / (spPr + hamProb(curr)))
            );
            spPr = 0;
        }
        iter = hamBag.getBagIter();
        while (iter.hasNext()){
            curr = iter.next();
            if (!spamBag.hasWord(curr)){
                spPr = spamProb(curr);
                probMap.put(curr,
                        (spPr / (spPr + hamProb(curr)))
                );
            }
            spPr = 0;

        }
        probChange = false;
    }

    public double probSpam(String word){
        // if it doesn't need to be done, genSpamProb will return immediately
        genSpamProb();
        Double p = probMap.get(word);
//        System.out.println(probMap.get("viagra"));
        if (p != null) return (p);
        else return 0.0;
    }


    public boolean isWord(String str){
        // the contents of matches parameter is a regex
        // the regex says to consider beginning of a line a-z caps and lowercase
        // for zero or more times and the end of a line
        return (str.matches("^[a-zA-Z]*$"));
    }

    /*
     * eehta
     *
     * computes the eehta value for a single word
     */
    private double eehta(String word){
        // use an identity to simplify the computation of eehta
        // log(x) - log(y) = log(x / y)
        double spPr = probSpam(word);
        return (Math.log( (1 - spPr) / (spPr) ));
    }

    public double test(File file, List<TestFile> fileList) {
        try {
            if (file.isDirectory()) {
                // process every file in directory
                File[] filesInDir = file.listFiles();
                for (int i = 0; i < filesInDir.length; i++) {
                    test(filesInDir[i], fileList);
                }
            } else if (file.exists()) {
                // load all of the data, and process it into words
                Scanner scan = new Scanner(file);
                double eehta = 0;
                while (scan.hasNext()) {
                    String word = scan.next().toLowerCase();
                    if (isWord(word)) {
                        eehta += eehta(word);
                    }
                }
                // calculate probability that file is spam
                double prSF = (1 / (1 + Math.pow(Math.E, eehta)));
                String parentName;
                if (file.getParentFile().getName().equalsIgnoreCase("ham")){
                    parentName = "Ham";
                } else if (file.getParentFile().getName().equalsIgnoreCase("spam")){
                    parentName = "Spam";
                } else {
                    parentName = "void";
                    System.out.println("Error occured, directory improperly set");
                }
                fileList.add(new TestFile(file.getName(), prSF, parentName));
                scan.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0.0;
    }
}
