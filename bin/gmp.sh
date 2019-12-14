#!/bin/bash
curl -s 'http://givemeproxy.com/' > D:/Desktop/temp/g.html;
cat D:/Desktop/temp/g.html 					 | 
grep -oP '(?<=td class="column-1">)[^</td>]*' | 
sed 's/[A-Za-z]*//g' 		 |
sed '/^$/d' > D:/Desktop/temp/gIP.txt;
cat D:/Desktop/temp/g.html 					 | 
grep -oP '(?<=td class="column-2">)[^</td>]*' | 
sed 's/[A-Za-z]*//g' 		 |
sed '/^$/d' > D:/Desktop/temp/gPort.txt;
paste D:/Desktop/temp/gIP.txt D:/Desktop/temp/gPort.txt > D:/Desktop/temp/g.txt
cat D:/Desktop/temp/g.txt | 
sed -e 's/\s\+/:/g' |
sed '/^$/d' > D:/Desktop/temp/g.txt;
truncate -s-2 D:/Desktop/temp/g.txt;
rm -rf D:/Desktop/temp/g.html D:/Desktop/temp/g.txt D:/Desktop/temp/gIP.txt D:/Desktop/temp/gPort.txt;