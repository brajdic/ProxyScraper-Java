#!/bin/bash
curl -s 'https://www.sslproxies.org/' > D:/Desktop/temp/s.html;
cat D:/Desktop/temp/s.html 					 | 
grep -oP '(?<=td>)[^</td>]*' | 
sed 's/[A-Za-z]*//g' 		 | 
sed -e '{:q;N;s/\n/ /g;t q}' | 
sed 's/ /:/g' 				 | 
sed -e 's/:::/\\\n /g' 		 | 
sed -e 's/::/\\\n /g' 		 | 
sed 's/\ //g' 				 | 
sed 's/\\//g' 				 | 
sed '/^$/d' > D:/Desktop/temp/ssl.txt;
truncate -s-2 D:/Desktop/temp/ssl.txt;
rm -rf D:/Desktop/temp/s.html;