/*
 * @(#)FindIP.java	1.2 04/08/10
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 *
 */

import java.net.InetAddress;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FindIP {
    public static void main(String[] args){
        try{
            System.out.println(InetAddress.getByName(args[0]).getHostAddress());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
