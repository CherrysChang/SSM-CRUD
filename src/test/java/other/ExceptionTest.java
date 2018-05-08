package other;

/**
 * Desc:
 *
 * @author qianqian.zhang
 * @date 2018-03-15 13:31
 **/
public class ExceptionTest {
    public static void main(String[] args) throws Exception {
        try {
            try {
                throw new Sneeze();
            } catch (Annoyance a) {
                System.out.println("Caught Annoyance");
                throw a;
            }
        } catch (Sneeze s) {
            System.out.println("Caught Sneeze");
            return;
        } finally {
            System.out.println("Hello World!");
        }
    }
}

class Annoyance extends Exception {
}

class Sneeze extends Annoyance {
}
