/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.exampleproject.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author User
 */
public class XhtmlTabulator {

    public static void tabulate(String originFilePath, String destinationFilePath, String tabulationUnit) {
        StringBuilder result = new StringBuilder();
        Queue<String> elementQueue = new LinkedList<String>();
        try {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(originFilePath), StandardCharsets.UTF_8)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(line.startsWith("<html"))
                    {
                        while(elementQueue.size() > 0)
                        {
                            elementQueue.poll();
                        }
                    }
                    
                    if (line.startsWith("</")) {
                        elementQueue.poll();
                        StringBuilder tabulation = getTabulation(elementQueue.size(), tabulationUnit);
                        result.append(tabulation).append(line).append("\n");
                        continue;
                    }
                    else
                    {
                        StringBuilder tabulation = getTabulation(elementQueue.size(), tabulationUnit);
                        result.append(tabulation).append(line).append("\n");
                        
                        if (line.startsWith("<")) {
                            if (line.endsWith("/>") || line.endsWith("?>") || line.endsWith("-->")) {
                                continue;
                            }
                            String lineBeginning = line.split(">")[0].split(" ")[0];
                            String element = lineBeginning.substring(1);
                            if (line.endsWith("</" + element + ">")) {
                                continue;
                            } else {
                                elementQueue.add(element);
                            }
                        }
                        else if (line.endsWith("-->"))
                        {
                            elementQueue.poll();
                        }
                    }
                }
            }

            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destinationFilePath), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                writer.write(result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StringBuilder getTabulation(int depth, String tabulationUnit) {
        StringBuilder tabulation = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            tabulation.append(tabulationUnit);
        }
        return tabulation;
    }
}
