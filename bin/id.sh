#!/bin/bash
curl -s 'http://www.idcloak.com/proxylist/free-us-proxy-list.html' > D:/Desktop/temp/id.html;
cat D:/Desktop/temp/id.html 				 | 
grep -oP '(?<=td>)[^</td>]*' |
grep -v '[a-z]' > D:/Desktop/temp/temp.txt;
awk 'NR % 2 == 0' D:/Desktop/temp/temp.txt > D:/Desktop/temp/ipTemp.txt; 	 	# this is the ip
sed -n 1~2p D:/Desktop/temp/temp.txt > D:/Desktop/temp/portTemp.txt;      		# this is the port
paste D:/Desktop/temp/ipTemp.txt D:/Desktop/temp/portTemp.txt > D:/Desktop/temp/id.txt; 
cat D:/Desktop/temp/id.txt 					 | 
sed -e 's/\s\+/:/g' 		 |
sed '/^$/d' > D:/Desktop/temp/id.txt;
rm -rf D:/Desktop/temp/id.html D:/Desktop/temp/id.txt D:/Desktop/temp/ipTemp.txt D:/Desktop/temp/portTemp.txt D:/Desktop/temp/temp.txt;