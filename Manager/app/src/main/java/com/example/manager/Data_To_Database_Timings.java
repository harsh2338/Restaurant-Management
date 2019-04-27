package com.example.manager;

public class Data_To_Database_Timings
{
    String T1,T2,T3,T4,T5,T6,F1,F2,F3,F4,E1,E2,P1,P2,P3;

    public Data_To_Database_Timings(String t1, String t2, String t3, String t4, String t5, String t6) {
        T1 = t1;
        T2 = t2;
        T3 = t3;
        T4 = t4;
        T5 = t5;
        T6 = t6;
    }

    public Data_To_Database_Timings(String f1, String f2, String f3, String f4) {
        F1 = f1;
        F2 = f2;
        F3 = f3;
        F4 = f4;
    }

    public Data_To_Database_Timings(String e1, String e2) {
        E1 = e1;
        E2 = e2;
    }

    public Data_To_Database_Timings(String p1, String p2, String p3) {
        P1 = p1;
        P2 = p2;
        P3 = p3;
    }
}
