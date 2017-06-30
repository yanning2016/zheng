#!/usr/bin/env bash
echo -n "提示： 1: 启动redis; 0:关闭redis;   :"

#tomcat_path='/Users/jondai/dev/tool/tomcat7_bak/bin'
read aNum
case $aNum in
    1) echo "正在启动redis! $aNum!"
       redis-server
    ;;
    0) echo "正在关闭redis! $aNum!"
        pid=$(ps -ef | grep "redis-server" | grep -v grep | awk '{print $2}')
        echo -n "正在杀掉"$pid
        kill -9 $pid
    ;;
    *) echo "你输入的数字不是 0 或 1 之间的!"
        continue
        echo "重来"
    ;;
esac




