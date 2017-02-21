package com.part.rbt;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * Created by Administrator on 2017/2/15.
 */
public class RabbitMQR {

    //MQ常量
    boolean durable = true;
    //接收器执行开关
    private boolean isRun =false;
    //是否在运行的标示
    private boolean runningFlag = false;
    //异常终止标示
    private boolean errorStop = false;

    private Connection connection = null;
    private Channel channel = null;
    private QueueingConsumer consumer = null ;
    private QueueingConsumer.Delivery delivery = null;


    public void receive() {
        isRun = true;
        runningFlag = true;

        try {

            RQBaseInfo ri = new RQBaseInfo();
            connection = RQConnectionFactory.getConnection(null, ri, 0);
            channel = connection.createChannel();
            channel.queueDeclare("test", durable, false, false, null);
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);
            consumer = new QueueingConsumer(channel);

            boolean ack = false;
            channel.basicConsume("test", ack, consumer);

            while (isRun) {
                //循环获取信息
                delivery = consumer.nextDelivery(30000);
                if (delivery == null) {
                    continue;
                }

                //尝试获取信息，如果内容异常跳过改数据
                String messageStr = null;
                //转换异常
                boolean coverMessageHasError = false;
                boolean parseMessageHasError = false;
                //尝试解析数据为Message对象
                try {
                    messageStr = new String(delivery.getBody());
                    System.out.println(messageStr);
                } catch (Exception e) {
                    coverMessageHasError = true;
                }

                if (!coverMessageHasError) {
                    //尝试处理正确数据，尝试10次 ，如果失败接受该类型信息，检查服务器
                    int tryNum = 0;
                    boolean handlerResult = handler();
                    while (!handlerResult && tryNum <= 10) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                        tryNum++;

                        handlerResult = handler();
                    }

                    if (tryNum > 10) {
                        parseMessageHasError = true;
                        isRun = false;
                    }

                }

                //处理异常不进行信息反馈，否则会丢失数据
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //其他任何异常停止该服务，并标记为异常终止
            isRun = false;
            runningFlag = false;
            System.out.println("test的接收器出现异常");
            errorStop = true;
        } finally {
            //最终回收资源
            isRun = false;
            runningFlag = false;
            if (channel != null) {
                try {
                    channel.close();
                } catch (Exception e) {
                } finally {
                    channel = null;
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                } finally {
                    connection = null;
                }

                System.out.println("test的接收器出现异常的接收器执行结束");
                //如果异常终止，将该信息放入到重启队列尝试重启
                if (errorStop) {
                    Const.M.remove("A");
                }
            }
        }
    }

    private boolean handler(){
        return true;
    }

    public void stop() {
        isRun = false;
    }

    public boolean isRun() {
        return runningFlag;
    }
}
