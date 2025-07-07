#Student코드
```
package one;

public class Student {
    private int id;
    private String name;
    private int korean;
    private int math;
    private int english;

    public Studd(int id, String name, int korean, int math, int english) {
        this.id = id;
        this.name = name;
        this.korean = korean;
        this.math = math;
        this.english = english;
    }

    public int getTotal() {
        return korean + math + english;
    }

    public double getAverage() {
        return getTotal() / 3.0;
    }

    public void printInfo() {
        System.out.printf("학번: %d, 이름: %s, 국어: %d, 수학: %d, 영어: %d, 총점: %d, 평균: %.2f\n",
                id, name, korean, math, english, getTotal(), getAverage());
    }
```
#example 코드

```
package one;

import java.io.*;
import java.util.*;

public class StudentExample {
    public static void main(String[] args) {
        ArrayList<Studd> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");

                int id = Integer.parseInt(st.nextToken().trim());
                String name = st.nextToken().trim();
                int korean = Integer.parseInt(st.nextToken().trim());
                int math = Integer.parseInt(st.nextToken().trim());
                int english = Integer.parseInt(st.nextToken().trim());

                Studd s = new Studd(id, name, korean, math, english);
                students.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (Studd s : students) {
            s.printInfo();
        }
    }



```



    
