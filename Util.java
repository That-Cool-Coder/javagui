class Util {
    public Util() {
        //printStrLn("Util is working");
    }

    public void printStrLn(String data) {
        System.out.println(data);
    }

    public String readInput() {
        String input = System.console().readLine(); 
        return input;
    }

    public int randint(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public int constrain(int val, int min, int max) {
        // (include min and max)
        if (val < min) {
            val = min;
        }
        else if (val > max) {
            val = max;
        }
        return val;
    }

    public String duplicateStr(String strToDuplicate, int amount) {
        String result = "";
        for (int i = 0; i < amount; i ++) {
            result += strToDuplicate;
        }
        return result;
    }
}