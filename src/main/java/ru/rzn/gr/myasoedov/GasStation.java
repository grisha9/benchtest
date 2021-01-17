package ru.rzn.gr.myasoedov;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/*

Input: new String[] {"4","1:1","2:2","1:2","0:1"}
Output: impossible
Input: new String[] {"4","0:1","2:2","1:2","3:1"}
Output: 4
 */

public class GasStation {
    public static String GasStation(String[] strArr) {
        int stationCount = Integer.valueOf(strArr[0]);
        List<Station> stations = IntStream.range(1, strArr.length)
                .mapToObj(value -> {
                    String[] split = strArr[value].split(":");
                    return new Station(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
                })
                .collect(Collectors.toList());

        for(int i = 0; i < stationCount; i++) {
            int currentGas = 0;
            boolean possible = true;
            for(int j = i; j < i + stationCount; j++) {
                if (!possible) {
                    break;
                }
                Station station = stations.get(j % stationCount);
                currentGas += station.gasAmount;
                currentGas -= station.gasForNext;
                if (currentGas < 0) {
                    possible = false;
                }
            }
            if (possible) {
                return String.valueOf(i + 1);
            }
        }
        return "-1";
    }

    static class Station {
        int gasAmount;
        int gasForNext;

        public Station(int gasAmount, int gasForNext) {
            this.gasAmount = gasAmount;
            this.gasForNext = gasForNext;
        }

        @Override
        public String toString() {
            return "Station{" +
                    "gasAmount=" + gasAmount +
                    ", gasForNext=" + gasForNext +
                    '}';
        }
    }


    public static void main(String[] args) {
        String[] ss =  {"4","1:1","2:2","1:2","0:1"};
        String[] ss1 = {"4","0:1","2:2","1:2","3:1"};
        String s = GasStation(ss);
        System.out.println(s);

        s = GasStation(ss1);
        System.out.println(s);


    }
}
