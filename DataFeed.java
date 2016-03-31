/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmarketdataserver;

import java.io.BufferedReader;
import java.io.File;
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
public class DataFeed 
{
    private String historicalDataFile;
    private FileIOv2 IO;
    private String unitDelim=" :: ";
    private String dataSource;
    public DataFeed(String historicalDataFileName, String dataSource)
    {
        this.IO = new FileIOv2();
        this.dataSource=dataSource;
        this.historicalDataFile=IO.userdir()+"/"+historicalDataFileName;
        //create DATABASE FILE
        manageFileStrut();
        
        //START APPENDING TO FILE IN NEW THREAD
        Thread RD = new Thread(new ReceiveData(historicalDataFile,dataSource));
        
        RD.start(); //starts saving historical FOREX data
    }
    
    public String getCurrentForexRate(String currencyPair,String valueOfInterest,boolean print)                        //eg: AUD/USD
    {
        ArrayList<String> currencyRates = new ArrayList<String>();
//        String url = "http://webrates.truefx.com/rates/connect.html?f=html";
        String currencyPairName="";
        double bidPrice=0;
        double offerPrice=0;
        double spread=0;
        java.util.Date time;
        String returnVal="";
        currencyRates=getURLData(dataSource);
        for(int count=0; count<currencyRates.size(); count++)
        {
            if(currencyRates.get(count).toLowerCase().contains(currencyPair.toLowerCase()))
            {
                System.out.println("currency of interest: " + currencyRates.get(count));
                String[] currencyDetails = currencyRates.get(count).split(unitDelim);
                currencyPairName=currencyDetails[0];                                    //currency Pair
                time=new java.util.Date(Long.parseLong(currencyDetails[1]));            //Date of quote
                bidPrice=Double.parseDouble(currencyDetails[2]+currencyDetails[3]);     //bid price
                offerPrice=Double.parseDouble(currencyDetails[4]+currencyDetails[5]);   //offer price
                spread=offerPrice-bidPrice;                                             //currency pair spread
                
                if(print)
                {
                    System.out.println("currencyPairName: " + currencyPairName);
                    System.out.println("date: " + time);
                    System.out.println("bidPrice: " +"\t"+ bidPrice);
                    System.out.println("offerPrice: "+"\t" + offerPrice);
                    System.out.println("spread: " + spread);
                }
                if(valueOfInterest.toLowerCase().contains("name"))
                {
                    returnVal=returnVal+currencyPairName+unitDelim;
                }
                if(valueOfInterest.toLowerCase().contains("date"))
                {
                    returnVal=returnVal+time+unitDelim;
                }
                if(valueOfInterest.toLowerCase().contains("bidPrice"))
                {
                    returnVal=returnVal+bidPrice+unitDelim;
                }
                if(valueOfInterest.toLowerCase().contains("offerPrice"))
                {
                    returnVal=returnVal+offerPrice+unitDelim;
                }
                if(valueOfInterest.toLowerCase().contains("spread"))
                {
                    returnVal=returnVal+spread+unitDelim;
                }
                returnVal=returnVal.substring(0, returnVal.length()-unitDelim.length());
            }
        }
        return returnVal;
    }
    public ArrayList<String> getAvailableCurrencyPairs()
    {
        ArrayList<String> currencyPairs = new ArrayList<String>();
        ArrayList<String> currencyRates = new ArrayList<String>();
//        String url = "http://webrates.truefx.com/rates/connect.html?f=html";
//        String currencyPairName="";
        double bidPrice=0;
        double offerPrice=0;
        double spread=0;
        java.util.Date time;
        String returnVal="";
        currencyRates=getURLData(dataSource);
        for(int count=0; count<currencyRates.size(); count++)
        {
            String[] currencyDetails = currencyRates.get(count).split(unitDelim);
            currencyPairs.add(currencyDetails[0]);  
        }
        return currencyPairs;
    }
    public void getAllHistoricalData()
    {
        ArrayList<String> HistData = IO.readFile(historicalDataFile);
        
        for(String x : HistData)
        {
            System.out.println(x);
        }
    }
    public String getLatestDataRecord()
    {
       return IO.tail2(historicalDataFile, 1); 
    }
    
    
    
    
    //helper methods
    private void manageFileStrut()
    {
        if(IO.isExist(historicalDataFile))                     //does Hist file already exist
        {
            
        }
        else
        {
            IO.createFile(historicalDataFile);
        }
    }
    private ArrayList<String> getURLData(String url)
    {
        ArrayList<String> buff = new ArrayList<String>();
        try
        {
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                //System.out.println(inputLine);
                buff=formatURLDataCust(inputLine);
            }
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
    private ArrayList<String> formatURLDataCust(String data)
    {
        ArrayList<String> buff = new ArrayList<String>();
        
        data = data.replaceAll("<table>", "");
        data = data.replaceAll("</table>", "");
        String dataNo_td_tr_ = data.replaceAll("<td>", unitDelim);
        String dataNo_td_tr_2 = dataNo_td_tr_.replaceAll("</td>", "");
        String dataNo_td_tr_3 = dataNo_td_tr_2.replaceAll("</tr>", "");
        String[] dataReturns = dataNo_td_tr_3.split("<tr>");
        for(int count=1; count<dataReturns.length; count++)
        {
//            System.out.println(dataReturns[count].substring(4,dataReturns[count].length()-33));
            buff.add(dataReturns[count].substring(4,dataReturns[count].length()-33));
        }
        return buff;
    }
}
