package ru.rzn.gr.myasoedov.hflabs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HFLabTest {
    private final int carCount;
    private Map<String, Integer> parkSet = new HashMap<>();

    public HFLabTest(int carCount) {
        this.carCount = carCount;
    }

    public boolean park(String carId, int hourStart) {
        if (parkSet.size() == carCount) {
            return false;
        }
        Integer prevValue = parkSet.putIfAbsent(carId, hourStart);
        return prevValue == null;
    }

    int unpark(String carId, int hourEnd) {
        Integer hourStart = parkSet.remove(carId);
        if (hourStart == null) {
            throw new IllegalStateException("no car " + carId);
        }
        if (hourStart > hourEnd) {
            throw new IllegalStateException("no car " + carId);
        }
        int totalPrice = 0;
        for (int i = hourStart; i < hourEnd; i++) {
            int hourIndex = i % 24;
            totalPrice += Tariff.tariff[hourIndex];
        }
        return totalPrice;
    }

    private static class Tariff {
        static int[] tariff =
                {30,   //0
                        30, //1
                        30,//2
                        30,//3
                        30,//4
                        30,//5
                        30,//6
                        50,//7
                        50,//8
                        50,//9
                        50,//10
                        50,//11
                        50,//12
                        50,//13
                        50,//14
                        50,//15
                        50,//16
                        50,//17
                        50,//18
                        50,//19
                        50,//20
                        50,//21
                        30,//22
                        30};//23
    }

    public static void main(String[] args) {
        HFLabTest normalParking = new HFLabTest(5);
        HFLabTest bigParking = new HFLabTest(2);

        System.out.println(normalParking.park("3", 5));//90
        System.out.println(normalParking.unpark("3", 8));//110

        System.out.println(normalParking.park("1", 0));//true
        System.out.println(normalParking.park("1", 0));//false
        System.out.println(normalParking.park("2", 1));//true
        System.out.println(normalParking.park("3", 0));//false
        System.out.println(normalParking.unpark("2", 1));//0
        System.out.println(normalParking.park("3", 2));//true
        try {
            normalParking.unpark("4", 0);
        } catch (IllegalStateException e) {
            System.out.println("unpark error");
        }
        System.out.println(normalParking.unpark("3", 5));//90


        System.out.println(normalParking.park("3", 8));//110
        int sum = Arrays.stream(Tariff.tariff).sum();
        System.out.println(normalParking.unpark("3", 32) + " - " + sum);

        System.out.println("----------------------");
        ProxyParking proxyParking = new ProxyParking(new HFLabTest(3), new HFLabTest(2));
        System.out.println(proxyParking.park("1", 0, CarType.BIG));//true
        System.out.println(proxyParking.park("2", 0, CarType.BIG));//true
        System.out.println(proxyParking.park("3", 0, CarType.BIG));//false

        System.out.println(proxyParking.park("10", 0, CarType.NORMAL));//true
        System.out.println(proxyParking.park("11", 0, CarType.NORMAL));//true
        System.out.println(proxyParking.park("12", 0, CarType.NORMAL));//true
        System.out.println(proxyParking.park("13", 0, CarType.NORMAL));//false

        System.out.println(proxyParking.unpark("2", 1));//30
        System.out.println(proxyParking.park("14", 1, CarType.NORMAL));//true
        System.out.println(proxyParking.park("14", 1, CarType.NORMAL));//false
    }

    enum CarType {
        NORMAL, BIG
    }

    public static class ProxyParking {
        private final HFLabTest normalParking;
        private final HFLabTest bigParking;

        public ProxyParking(HFLabTest normalParking, HFLabTest bigParking) {
            this.normalParking = normalParking;
            this.bigParking = bigParking;
        }

        public boolean park(String carId, int hourStart, CarType carType) {
            Objects.requireNonNull(carType);
            if (carType == CarType.BIG) {
                return bigParking.park(carId, hourStart);
            } else {
                boolean parkResult = normalParking.park(carId, hourStart);
                if (!parkResult) {
                    parkResult = bigParking.park(carId, hourStart);
                }
                return parkResult;
            }
        }

        int unpark(String carId, int hourEnd) {
            int priceFromBig;
            try {
                priceFromBig = bigParking.unpark(carId, hourEnd);
            } catch (Exception e) {
                priceFromBig = 0;
            }
            int priceFromNormal;
            try {
                priceFromNormal = normalParking.unpark(carId, hourEnd);
            } catch (Exception e) {
                priceFromNormal = 0;
            }
            return priceFromBig + priceFromNormal;
        }
    }
}
