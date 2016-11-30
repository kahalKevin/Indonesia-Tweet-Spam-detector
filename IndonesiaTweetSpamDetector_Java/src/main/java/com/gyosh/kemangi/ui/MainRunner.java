/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gyosh.kemangi.ui;

import com.gyosh.kemangi.core.TaskRunner;
import com.gyosh.kemangi.core.task.CaseFolding;
import com.gyosh.kemangi.core.task.NonAlphaNumericRemoval;
import com.gyosh.kemangi.core.task.Stem;
import com.gyosh.kemangi.core.task.StopWordRemoval;
import com.gyosh.kemangi.core.task.Task;
import com.gyosh.kemangi.core.utility.Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author if412020
 */
public class MainRunner {
    private static final int TIMER_INTERVAL = 100;
    private static final String NO_INPUT = "Please specify input file!";
    private static final String NO_TASK = "Please add at least one task!";
    private static final String NO_OUTPUT = "Please specify output file!";
    private static final Logger logger = Logger.getLogger(Main.class);

    private File inputFile;
    private File outputFile;
    private TaskRunner taskRunner;
    
    public MainRunner() throws InterruptedException{
    taskRunner = new TaskRunner("tweet.txt","tweetresult.txt");
               logger.info("New task runner");
                    Task task = (Task)new CaseFolding();
                    taskRunner.addTask(task);
                    task = (Task)new NonAlphaNumericRemoval();
                    taskRunner.addTask(task);
                    task = (Task)new StopWordRemoval();
                    taskRunner.addTask(task);                    
                    task = (Task)new Stem();
                    taskRunner.addTask(task);
                    logger.info("New task added: " + task.toString());
                Runnable taskRunnerWrapper = new Runnable() {
                    public void run() {
                        try {
                            taskRunner.run();
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                };
                Thread threadrun = new Thread(taskRunnerWrapper);
                threadrun.start();
                threadrun.join();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        List<String> listLabel=new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(new File("TextNormal.csv")));
        String verify;
        File file;
        FileWriter fw;
        BufferedWriter bw;

        file = new File("tweetresult.txt");
        file.createNewFile();
        file= new File("tweet.txt");
        file.createNewFile();
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
        try{
            br.readLine();//skip the label part
            while( (verify=br.readLine()) != null ){
                if(verify != null){
                    listLabel.add(verify.charAt(verify.length()-1)+"");//simpan label di list
                    bw.write(verify.substring(0, verify.length()-2)); //tulis tweet ke file
                    bw.newLine();
                    bw.flush();
                }
            }
            br.close();
        }
        catch(Exception e){
        }
        MainRunner wordFix=new MainRunner();//menjalankan fungsi fungsi dari kemangi
        file = new File("CleanTweet.csv");
        file.createNewFile();
        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
        Scanner input = new Scanner(new File("tweetresult.txt"));//set this back to tweetresult
        try{
            bw.write("tweet,label");
            bw.newLine();
            int listLine=0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                bw.write(line+","+listLabel.get(listLine));
                bw.newLine();
                bw.flush();
                listLine++;
            }
            br.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
     }
}
