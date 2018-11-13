package svm.common.generator;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 0000000000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的节点序号，可以部署在1024个节点。<br>
 * 12位序列号，毫秒内的计数，12位的序列号支持每个节点每毫秒(同一节点，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由节点序号区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。<br>
 * <b>注意：</b>以上为Twitter Snowflake说明，本类做了简化，节点序号为5位，最多支持31个节点，序列号为10位，最多支持同一毫秒1023个，相应的给前面时间戳留的位数更多<br>
 * 建议数据库主键列设置成number(18)
 */
public class SnowflakeIdWorker {
    //起始时间：2017-05-19，即本类投入使用的时间，最后生成的id中的时间戳是当前时间和起始时间的差值
    private final static long BEGIN_TIME = 1495123200000L;

    //节点序号所占位数，5位最多支持31个节点
    private final static long WORKER_ID_BITS = 5L;

    //最大节点序号：31(这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    public final static long MAX_WORKER_ID = -1L ^ -1L << WORKER_ID_BITS;

    //序列所占位数，为相同毫秒内的计数，10位的计数支持每个节点每毫秒产生1023个ID序号
    private final static long SEQUENCE_BITS = 10L;

    //节点序号向左移10位
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;

    //时间戳向左移15位
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    //生成序列的掩码，这里为1023
    public final static long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;

    private final long workerId;

    private long lastTimestamp = -1L;

    private long sequence = 0L;

    public SnowflakeIdWorker(final long workerId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & SEQUENCE_MASK;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }

        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        }

        this.lastTimestamp = timestamp;
        long nextId = (timestamp - BEGIN_TIME << TIMESTAMP_LEFT_SHIFT) | (this.workerId << WORKER_ID_SHIFT) | this.sequence;
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowflakeIdWorker worker2 = new SnowflakeIdWorker(2);
        for (int i = 0; i < 10; i++) {
            System.out.println(worker2.nextId());
        }
    }
}