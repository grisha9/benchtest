package ru.rzn.gr.myasoedov;

/**
 * Created by grisha on 26.08.19.
 */
public class SymbolPermutate {
    static int ee = 0;
    static void permute(java.util.List<Integer> arr, int k){
        for(int i = k; i < arr.size(); i++) {
            ee++;
            java.util.Collections.swap(arr, i, k);
            permute(arr, k+1);
            java.util.Collections.swap(arr, k, i);
        }
        if (k == arr.size() -1){
            System.out.println(java.util.Arrays.toString(arr.toArray()));
        }
    }
    public static void main(String[] args){
        SymbolPermutate.permute(java.util.Arrays.asList(3,4,6), 0);
    }
}
