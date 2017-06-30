#!/usr/bin/env bash
echo -n "提示： 1: 启动tomcat; 0:关闭tomcat;   :"

tomcat_path='/Users/jondai/dev/tool/tomcat7_bak/bin'
read aNum
case $aNum in
    1) echo "正在启动tomcat! $aNum!"
       cd $tomcat_path
       ./startup.sh
    ;;
    0) echo "正在关闭tomcat! $aNum!"
        cd $tomcat_path
        ./shutdown.sh
        echo ""
    ;;
    *) echo "你输入的数字不是 0 或 1 之间的!"
        continue
        echo "重来"
    ;;
esac

