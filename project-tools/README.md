## **服务器启动顺序**
    1.启动注册中心 zookeeper ./zkServer_start.sh
    2.启动dubbo-admin服务器，可查看消费者和提供者是否注册 ./dubbo-admin.sh
    3.启动redis
    4.启动服务提供者服务 xx-ServiceApplication
    5.启动消费者服务 maven中 jetty:run
    6.启动activityMQ ./activemq start  | ./activemq stop 
        目录： /Users/jondai/dev/tool/apache-activemq-5.9.1/bin/macosx
    
    7.查看mysql服务是否启动 brew info mysql 

