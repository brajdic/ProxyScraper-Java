#!/bin/bash
curl -s https://www.us-proxy.org/ > D:/Desktop/temp/p.html;
cat D:/Desktop/temp/p.html 					 | 
grep -oP '(?<=td>)[^</td>]*' | 
sed 's/[A-Za-z]*//g' 		 | 
sed -e '{:q;N;s/\n/ /g;t q}' | 
sed 's/ /:/g' 				 | 
sed -e 's/:::/\\\n /g' 		 | 
sed -e 's/::/\\\n /g' 		 | 
sed 's/\ //g' 				 | 
sed 's/\\//g' 				 | 
sed '/^$/d' > D:/Desktop/temp/p.txt;
truncate -s-2 D:/Desktop/temp/p.txt;
rm -rf D:/Desktop/temp/p.html;