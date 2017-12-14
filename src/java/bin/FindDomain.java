/*
 * @(#)FindDomain.java	1.2 04/07/06
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 *
 */

import java.net.InetAddress;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FindDomain {
    public static void main(String[] args){
        try{
            System.out.println(InetAddress.getLocalHost().getCanonicalHostName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
