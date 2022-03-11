package com.stm.uid.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.net.NetworkInterface;
import java.util.Enumeration;

public class Test {

    private static final long machineIdBitsCnt = 5L;
    private static final long maxMachineIdSupported = -1L ^ (-1L << machineIdBitsCnt);

    public static void main(String[] args) {
        Long l = System.currentTimeMillis();
        System.out.println(l);

        System.out.println("Data Center ID : " + getDataCenterId());

        System.out.println("Machine ID : " + getMachineID());
    }

    private static Long getDataCenterId(){
        int[] ints = StringUtils.toCodePoints(SystemUtils.getHostName());
        int sums = 0;
        for (int i: ints) {
            sums += i;
        }
        return (long)(sums % 32);
    }

    private static long getMachineID() {
        long machineId;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    for(byte macPort: mac) {
                        sb.append(String.format("%02X", macPort));
                    }
                }
            }
            machineId = sb.toString().hashCode();
        } catch (Exception ex) {
            machineId = RandomUtils.nextLong(0,31);;
        }
        machineId = machineId & maxMachineIdSupported;
        return machineId;
    }
}
