#!/bin/bash
curl -s 'https://www.proxynova.com/proxy-server-list/port-8080/' > D:/Desktop/temp/nova.html;
cat D:/Desktop/temp/nova.html 					  | 
grep -oP '(?<=abbr title=")[^"]*' |
sed -e 's/$/:8080/' > D:/Desktop/temp/nova.txt;
sed '/[A-Za-z]*/d' D:/Desktop/temp/nova.txt;
grep -v '[a-z]' D:/Desktop/temp/nova.txt > D:/Desktop/temp/nova.txt;
rm -rf D:/Desktop/temp/nova.txt D:/Desktop/temp/nova.html;