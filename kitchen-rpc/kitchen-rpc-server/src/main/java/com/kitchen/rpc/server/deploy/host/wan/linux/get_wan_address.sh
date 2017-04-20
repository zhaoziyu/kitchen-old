#!/usr/bin/env bash

# Get OS name
OS=`uname`
IP=""
# store IP
function getWanIp(){
    #echo "Wide Area Network";
    case $OS in
        Linux) IP=`curl ifconfig.me`;;
        #FreeBSD|OpenBSD) IP=`ifconfig  | grep -E 'inet.[0-9]' | grep -v '127.0.0.1' | awk '{ print $2}'` ;;
        # SunOS) IP=`ifconfig -a | grep inet | grep -v '127.0.0.1' | awk '{ print $2} '` ;;
        *) IP="Unknown";;
    esac

    echo "$IP";
}
getWanIp $1;