package other;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;

/**
 * Java 应用之日志框架 log4j
 * Apache的开源项目log4j是一个功能强大的日志组件,提供方便的日志记录。
 * 参考链接：
 * http://how2j.cn/k/log4j/log4j-tutorial/1081.html
 * Log4j详细使用教程:http://blog.csdn.net/evankaka/article/details/45815047
 * http://wiki.jikexueyuan.com/project/log4j/configuration.html
 * Created by Qian on 2018/3/11 0011.
 */
public class Log4jTest {

    /**
     * 写代码的过程中，免不了要输出各种调试信息。
     * 在没有使用任何日志工具之前，都会使用 System.out.println 来做到。
     * 缺点：
     *  1. 不知道这句话是在哪个类，哪个线程里出来的
     *  2. 不知道什么时候前后两句输出间隔了多少时间
     *  3. 无法关闭调试信息，一旦System.out.println多了之后，到处都是输出，增加定位自己需要信息的难度等等
     */
    @Test
    public void test(){
        System.out.println("跟踪信息");
        System.out.println("调试信息");
        System.out.println("输出信息");
        System.out.println("警告信息");
        System.out.println("错误信息");
        System.out.println("致命信息");
    }

    //1、基于类的名称获取日志对象
    static Logger logger = Logger.getLogger(Log4jTest.class);//ps：getRootLogger()返回应用实例没有名字的根日志
    /**
     * 使用Log4j来进行日志输出
     * BasicConfigurator.configure()： 自动快速地使用缺省Log4j环境。
     * PropertyConfigurator.configure(String configFilename) ：读取使用 Java的特性文件编写的配置文件。(properties)
     * DOMConfigurator.configure(String filename)：读取XML形式的配置文件。
     *
     * 输出结果有几个改观：
     * 1. 知道是other.Log4jTest这个类里的日志
     * 2. 是在[main]线程里的日志
     * 3. 日志级别可观察，一共有6个级别 TRACE DEBUG INFO WARN ERROR FATAL
     * 4. 日志输出级别范围可控制， 如代码所示，只输出高于DEBUG级别的，那么TRACE级别的日志自动不输出
     * 5. 每句日志消耗的毫秒数(最前面的数字)，可观察，这样就可以进行性能计算
     */
    @Test
    public void testLog4j() throws InterruptedException {
        //2、 进行默认配置
        BasicConfigurator.configure();
        //3、设置日志输出级别
        logger.setLevel(Level.DEBUG);
        //4、 进行不同级别的日志输出
        logger.trace("跟踪信息");
        logger.debug("调试信息");
        logger.info("输出信息");
        Thread.sleep(1000);//为了便于观察前后日志输出的时间差
        logger.warn("警告信息");
        logger.error("错误信息");
        logger.fatal("致命信息");
    }

    /**
     * log4j配置
     * 在src目录下添加log4j.properties文件
     */
    @Test
    public void testLog4jConfig(){
        //2、采用指定配置文件
        PropertyConfigurator.configure(".\\src\\test\\resources\\log4j.properties");//Log4j的配置方式按照log4j.properties中的设置进行
        for (int i = 0; i < 5000; i++) {
            logger.trace(i+"跟踪信息");
            logger.debug(i+"调试信息");
            logger.info(i+"输出信息");
            logger.warn(i+"警告信息");
            logger.error(i+"错误信息");
            logger.fatal(i+"致命信息");
        }
    }

    /**
     * log4j配置
     * 在src目录下添加log4j.properties文件
     */
    @Test
    public void testLog4jConfig2(){
        //2、采用指定配置文件
        //PropertyConfigurator.configure(".\\src\\test\\resources\\log4j.xml");
        DOMConfigurator.configure(".\\src\\test\\resources\\log4j.xml");
        for (int i = 0; i < 5000; i++) {
            logger.trace(i+"跟踪信息");
            logger.debug(i+"调试信息");
            logger.info(i+"输出信息");
            logger.warn(i+"警告信息");
            logger.error(i+"错误信息");
            logger.fatal(i+"致命信息");
        }
    }

    /**
     * Web项目中使用Log4j
     * 在 J2EE应用使用 Log4j，必须先在启动服务时加载 Log4j的配置文件进行初始化，可以在web.xml中进行。
     * 1、web应用的log4j使用基本上都采用：新建一个servlet，这个servlet在init函数中为log4j执行配置。一般就是读入配置文件。所以需要在web.xml中为这个servlet配置，同时设定load-on-startup为1。
     * 2、这个servlet配置log4j就是读出配置文件，然后调用configure函数。这里有两个问题：一、需要知道文件在哪里；二、需要正确的文件类型
     * 3、配置文件位置在web.xml中配置一个param即可，路径一般是相对于web的root目录
     * 4、文件类型一般有两种，一个是Java的property文件，另一种是xml文件
     *
     * 普通JavaWeb应用（基于Tomcat）中初始化Log4j的两种方式：
     * １、通过增加 InitServlet ,设置令其自启动来初始化 Log4j 。　　
     * ２、通过监听器 ServletContextListener 监听 ServletContext 的初始化事件来初始化 Log4j 。
     */

    /**
     * Spring中使用Log4j
     * ps: Spring需要依赖于commons-logging，所以使用 Spring时，会存在 commons-logging的 jar包
     * 当commons-logging.jar被加入到CLASSPATH之后，它会合理地猜测你想用的日志工具，然后进行自我设置，用户根本不需要做任何设置。（按照顺序执行）
     *      1) 首先在classpath下寻找自己的配置文件commons-logging.properties。这个属性文件至少定义org.apache.commons.logging.Log属性，它的值应该是上述任意Log接口实现的完整限定名称。如果找到，则使用其中定义的Log实现类；
            2) 如果找不到commons-logging.properties文件或属性不存在，则在查找是否已定义系统环境变量 org.apache.commons.logging.Log，找到则使用其定义的Log实现类；
            3) 否则，查看classpath中是否有Log4j的包，如果发现，则自动使用Log4j作为日志实现类（Log4JLogger），不过这时log4j本身的属性仍要通过log4j.properties文件正确配置；
            4) 如果上述查找均不能找到适当的Logging API，但应用程序正运行在JRE 1.4或更高版本上，则默认使用JRE 1.4的日志记录功能，使用JDK自身的日志实现类Jdk14Logger（JDK1.4以后才有日志实现类）；
            5) 如果上述操作都失败（JRE 版本也低于1.4），则使用commons-logging自己提供的一个简单的日志实现类SimpleLog，SimpleLog把所有日志信息直接输出到System.err。
     * 所以如果 我们导入了 Log4j的 jar包，并没有配置 commons-logging.properties，会自动使用Log4j作为日志实现类
     *
     * 参考：
     * SpringMVC中配置Log4j日志到指定目录：http://www.ibloger.net/article/1374.html
     */
}
