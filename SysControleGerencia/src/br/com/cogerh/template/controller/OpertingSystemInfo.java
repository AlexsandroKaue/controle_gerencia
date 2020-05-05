package br.com.cogerh.template.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;
import java.util.Set;

import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.DiskUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class OpertingSystemInfo {

	
	
	public static void main(String[] args) {
		/*String nameOS = "os.name";  
		  String versionOS = "os.version";  
		  String architectureOS = "os.arch";
		  System.out.println("\n  The information about OS");
		  System.out.println("\nName of the OS: " + 
		  System.getProperty(nameOS));
		  System.out.println("Version of the OS: " + 
		  System.getProperty(versionOS));
		  System.out.println("Architecture of THe OS: " + 
		  System.getProperty(architectureOS));
		  System.out.println("Free memory (bytes): " + 
			        Runtime.getRuntime().freeMemory());*/
	
		Sigar sigar = new Sigar();
		
		System.out.println("**************************************");
        System.out.println("*** Informations about the Memory: ***");
        System.out.println("**************************************\n");

        Mem mem = null;
        //Cpu[] cpus = null;
        DiskUsage disk =null;
        try {
            mem = sigar.getMem();
            //cpus = sigar.getCpuList();
            disk = sigar.getDiskUsage("C:");
        } catch (SigarException se) {
            se.printStackTrace();
        }

        System.out.println("Actual total free system memory: "
                + mem.getActualFree() / 1024 / 1024+ " MB");
        System.out.println("Actual total used system memory: "
                + mem.getActualUsed() / 1024 / 1024 + " MB");
        System.out.println("Total free system memory ......: " + mem.getFree()
                / 1024 / 1024+ " MB");
        System.out.println("System Random Access Memory....: " + mem.getRam()
                + " MB");
        System.out.println("Total system memory............: " + mem.getTotal()
                / 1024 / 1024+ " MB");
        System.out.println("Total used system memory.......: " + mem.getUsed()
                / 1024 / 1024+ " MB");
        System.out.println("Total used system disk.......: " + disk.getReadBytes() +" MB");


        System.out.println("\n**************************************\n");
        
		    
		    
	}

}
