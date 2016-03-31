/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmarketdataserver;

/**
 *
 * @author root
 */
public class FXMarketDataServer 
{
    public static void main(String args[])
    {
        String historicalDataFileName="historicalDataFile.txt";
        String dataSource = "http://webrates.truefx.com/rates/connect.html?f=html";
        DataFeed df = new DataFeed(historicalDataFileName,dataSource);
    }
}
