/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmarketdataserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ReceiveData implements Runnable 
{
    private String historicalDataFile;
    private String urlDataSource;
    private int updateRate;
    private FileIOv2 IO;
    public ReceiveData()
    {
        this(new FileIOv2().userdir()+"/"+"historicalDataFile.txt");
    }
    public ReceiveData(String historicalDataFile)
    {
        this.IO = new FileIOv2();
        this.updateRate=500;
        this.historicalDataFile=historicalDataFile;
        this.urlDataSource="http://webrates.truefx.com/rates/connect.html?f=html";
    }
    public ReceiveData(String historicalDataFile, String urlDataSource)
    {
        this.IO = new FileIOv2();
        this.updateRate=500;
        this.historicalDataFile=historicalDataFile;
        this.urlDataSource=urlDataSource;
    }
    public ReceiveData(String historicalDataFile, String urlDataSource,int updateRate)
    {
        this.IO = new FileIOv2();
        this.historicalDataFile=historicalDataFile;
        this.urlDataSource=urlDataSource;
        this.updateRate=updateRate;
    }
    @Override
    public void run() 
    {
        try
        {
            while(true)
            {
                getURLData(urlDataSource);
                delay(this.updateRate);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    private ArrayList<String> getURLData(String url)
    {
        ArrayList<String> buff = new ArrayList<String>();
        try
        {
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            
            IO.appendFile(in.readLine()+"\n",historicalDataFile);
            
            in.close();
        }
        catch (MalformedURLException ex) 
        {
            System.out.println("MalformedURLException");
            System.out.println(ex.getMessage());
            Logger.getLogger(DataFeed.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            System.out.println("IOException");
            System.out.println(ex.getMessage());
            Logger.getLogger(DataFeed.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception e)
        {
            System.out.println("Exception");
            System.out.println(e.getMessage());
        }
        return buff;
    } 
    private void delay(int millisec)
    {
        try
        {
            Thread.sleep(millisec);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
