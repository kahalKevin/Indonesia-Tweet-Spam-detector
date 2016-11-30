/**
 * JSastrawi is licensed under The MIT License (MIT)
 *
 * Copyright (c) 2015 Andy Librian
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package jsastrawi.cli;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import jsastrawi.cli.output.Output;
import jsastrawi.cli.output.BufferedOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
//import org.apache.commons.lang3.*;
/**
 * The main class for Command Line Interface.
 */
public class Main {

    /**
     * The main entry point function which is called first from the CLI.
     *
     * @param args Command line arguments
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
            LemmatizeCmd lemmatizeCmd = new LemmatizeCmd();
            File file = new File("tweetresultstem.txt");
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            Scanner input = new Scanner(new File("tweetresultswr.txt"));
            int i=0;
            try{
                while (input.hasNextLine()) {
                    String line = input.nextLine();
                    bw.write(lemmatizeCmd.handle(line));
                    bw.newLine();
                    bw.flush();
                    System.out.println(++i);
                }
            }
            catch(Exception e){}
    }
}
